package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.shapes.CRect;

import java.awt.*;

import static org.kelvinizer.constants.ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS;
import static org.kelvinizer.constants.ReferenceWindow.UNIT;

public class HoldNote extends Note{
    private final double noteHeight;
    private final double duration;
    private static final double HOLD_NOTE_WIDTH = 10.0;

    public HoldNote(int lane_num, double perfect_hit_time, double duration, double noteHeight, double last){
        super(lane_num, perfect_hit_time, duration);
        this.noteHeight = noteHeight;
        this.duration = last;
    }

    public static HoldNote parseHoldNote(String s, double offset){
        String[] t = s.split(" ");
        return new HoldNote(
                Integer.parseInt(t[0]),
                Double.parseDouble(t[1])+offset,
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3]),
                Double.parseDouble(t[4])
        );
    }

    @Override
    public CRect toBottomRect(){
        CRect cr = super.getBottomRect(Color.WHITE);
        if(Chart.CURRENT_TIME>perfect_hit_time){
            cr.setY(HORIZONTAL_JUDGEMENT_LINE_POS[lane_num / 8]);
            cr.setOutlineColor(null);
            cr.setFillColor(null);
        }
        return cr;
    }

    public CRect toDurationRect(CRect bottomRect){
        CRect r = new CRect();
        r.setSize(
                HOLD_NOTE_WIDTH,
                UNIT*(noteHeight - Math.max(0.0, noteHeight / duration * (Chart.CURRENT_TIME - perfect_hit_time)))
        );
        if (status == 3) {
            r.setFillColor(new Color(1, 1, 1, 0.25f));
        }
        else {
            r.setFillColor(Color.WHITE);
        }
        if (lane_num < 8) {
            r.setOrigin(r.getWidth() / 2.0f, r.getHeight());
        }
        else {
            r.setOrigin(r.getWidth() / 2.0f, 0);
        }
        r.setX(ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2);
        if(Chart.CURRENT_TIME>perfect_hit_time){
            r.setY(HORIZONTAL_JUDGEMENT_LINE_POS[lane_num / 8]);
        }
        else{
            r.setY(movement.dist(Chart.CURRENT_TIME - startTime));
        }
        Rectangle br = bottomRect.toJShape();
        Rectangle dr = r.toJShape();
        if(lane_num<8){
            if(br.y+br.height != dr.y+dr.height){
                r.setY(r.getY()-(dr.y+dr.height)+(br.y+br.height));
            }
        }
        else{
            if(br.y != dr.y){
                r.setY(r.getY()-dr.y+br.y);
            }
        }
        return r;
    }

    @Override
    public void judge(int signal) {
        if(signal == 0){
            if (Chart.CURRENT_TIME - perfect_hit_time > JudgementLimits.BAD_LIMIT && status == 4) {
                status = 3;
            }
        }
        else if (signal == 1) {
            double difference = perfect_hit_time - Chart.CURRENT_TIME;
            if (Math.abs(difference) <= JudgementLimits.GOOD_LIMIT) {
                actual_hit_time = Chart.CURRENT_TIME;
                if (Math.abs(difference) > JudgementLimits.PERFECT_LIMIT) {
                    status = 1;
                }
                else {
                    status = 0;
                }
            }
        }
        else{
            if (duration + perfect_hit_time - Chart.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT && status != 4) {
                status = 3;
                actual_hit_time = Double.POSITIVE_INFINITY;
            }
        }
    }

    @Override
    public boolean isActive() {
        return (status == 4) || (
                        (status == 0 || status == 1) &&
                        (perfect_hit_time + duration - Chart.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT)
                );
    }

    @Override
    public boolean hasRect() {
        return true;
    }

    @Override
    public boolean hasParticle() {
        return (status == 0 || status == 1);
    }

    @Override
    public int visibilityStatus() {
        if (
                (Chart.CURRENT_TIME >= startTime) &&
                (Chart.CURRENT_TIME <= perfect_hit_time + duration)
        ) {
            return 1;
        }
        else if (Chart.CURRENT_TIME < startTime) { return 0; }
        else { return 2; }
    }
}
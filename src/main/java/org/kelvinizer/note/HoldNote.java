package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Time;
import org.kelvinizer.motion.Motion;
import org.kelvinizer.support.CRect;

import java.awt.*;
import java.util.ArrayList;

import static org.kelvinizer.constants.GameColors.PAUSED_OPACITY;
import static org.kelvinizer.constants.General.isPaused;
import static org.kelvinizer.constants.ReferenceWindow.UNIT;

public class HoldNote extends Note{
    private final double noteHeight;
    private final double duration;
    private static final double HOLD_NOTE_WIDTH = 10.0;

    private static final Color[] HOLD_NOTE_COLOR = {
            new Color(255,255,255) ,
            new Color(255,255,255, PAUSED_OPACITY)
    };

    public HoldNote(int lane_num, double perfect_hit_time, double duration, double noteHeight, ArrayList<Motion> motions){
        super(lane_num, perfect_hit_time, motions);
        this.noteHeight = noteHeight;
        this.duration = duration;
    }

    public HoldNote(int lane_num, double perfect_hit_time, double duration, double noteHeight){
        super(lane_num, perfect_hit_time);
        this.noteHeight = noteHeight;
        this.duration = duration;
    }

    public static HoldNote parseHoldNote(String s){
        String[] t = s.split(" ");
        return new HoldNote(Integer.parseInt(t[0]), Double.parseDouble(t[1]), Double.parseDouble(t[2]), Double.parseDouble(t[3]));
    }

    @Override
    public CRect toBottomRect(){
        return super.getBottomRect(HOLD_NOTE_COLOR[isPaused]);
    }

    public CRect toDurationRect(){
        CRect r = new CRect();
        r.setSize(
                HOLD_NOTE_WIDTH,
                UNIT*(noteHeight - Math.max(0.0, noteHeight / duration * (Time.CURRENT_TIME - perfect_hit_time)))
        );
        if (status == 3) {
            r.setFillColor(HOLD_NOTE_COLOR[1]);
        }
        else {
            r.setFillColor(HOLD_NOTE_COLOR[isPaused]);
        }
        if (lane_num < 8) {
            r.setOrigin(r.getWidth() / 2.0f, r.getHeight());
        }
        else {
            r.setOrigin(r.getWidth() / 2.0f, 0);
        }
        r.setPosition(
                ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                distFromJudgementLine(Time.CURRENT_TIME)
        );
        return r;
    }

    @Override
    public void judge(int signal) {
        if(signal == 0){
            if (Time.CURRENT_TIME - perfect_hit_time > JudgementLimits.BAD_LIMIT && status == 4) {
                status = 3;
            }
        }
        else if (signal == 1) {
            double difference = perfect_hit_time - Time.CURRENT_TIME;
            if (Math.abs(difference) <= JudgementLimits.GOOD_LIMIT) {
                actual_hit_time = Time.CURRENT_TIME;
                if (Math.abs(difference) > JudgementLimits.PERFECT_LIMIT) {
                    status = 1;
                }
                else {
                    status = 0;
                }
            }
        }
        else{
            if (duration + perfect_hit_time - Time.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT && status != 4) {
                status = 3;
                actual_hit_time = -1;
            }
        }
    }

    @Override
    public boolean isActive() {
        return (status == 4) || (
                        (status == 0 || status == 1) &&
                        (perfect_hit_time + duration - Time.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT)
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
                (Time.CURRENT_TIME >= perfect_hit_time - getTotalMovementTime()) &&
                (Time.CURRENT_TIME <= perfect_hit_time + duration)
        ) {
            return 1;
        }
        else if (Time.CURRENT_TIME < perfect_hit_time - getTotalMovementTime()) { return 0; }
        else { return 2; }
    }
}
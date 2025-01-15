package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.shapes.CRect;

import java.awt.*;

public class TapNote extends Note{

    public TapNote(int lane_num, double perfect_hit_time, double duration){
        super(lane_num, perfect_hit_time, duration);
    }

    public static TapNote parseTapNote(String s, double offset){
        String[] t = s.split(" ");
        return new TapNote(
                Integer.parseInt(t[0]),
                Double.parseDouble(t[1])+offset,
                Double.parseDouble(t[2])
        );
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
            if (Math.abs(difference) <= JudgementLimits.BAD_LIMIT) {
                actual_hit_time = Chart.CURRENT_TIME;
                if (Math.abs(difference) > JudgementLimits.GOOD_LIMIT) {
                    status = 2;
                }
                else if (Math.abs(difference) > JudgementLimits.PERFECT_LIMIT) {
                    status = 1;
                }
                else {
                    status = 0;
                }
            }
        }
    }

    @Override
    public boolean isActive() {
        return status==4;
    }

    @Override
    public boolean hasRect() {
        return !hasParticle();
    }

    @Override
    public boolean hasParticle() {
        return (status == 0 || status == 1 || status == 2) && (Chart.CURRENT_TIME - actual_hit_time < JudgementLimits.NOTE_LINGERING_TIME);
    }

    @Override
    public int visibilityStatus() {
        if (
                (status == 3 || status == 4) &&
                (Chart.CURRENT_TIME >= startTime) &&
                (Chart.CURRENT_TIME <= perfect_hit_time + JudgementLimits.BAD_LIMIT) &&
                (isActive() || (!isActive() && hasParticle()))
        ) {
            return 1;
        }
        else if (
                (status == 0 || status == 1 || status == 2) &&
                        (Chart.CURRENT_TIME <= actual_hit_time + JudgementLimits.NOTE_LINGERING_TIME)
        ) {
            return 1;
        }
        else if (Chart.CURRENT_TIME < perfect_hit_time - getTotalMovementTime()) { return 0; }
        else { return 2; }
    }

    @Override
    public CRect toBottomRect(){
        return super.getBottomRect(new Color(130,222,250));
    }
}

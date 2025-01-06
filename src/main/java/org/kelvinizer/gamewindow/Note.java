package org.kelvinizer.gamewindow;

import org.kelvinizer.Constants.*;
import org.kelvinizer.support.CRect;

import java.awt.*;

public class Note implements Comparable<Object>{
    private final int lane_num;
    private final double perfect_hit_time, fall_position_ratio, fall_time, duration, note_height;
    private boolean is_sync;
    private double actual_hit_time = -1;
    private int status = 4;

    private double dist_from_judgement_line(double time) {
        double dist = ((int)ReferenceWindow.REF_WIN_H - ReferenceWindow.HORIZONTAL_JUDGEMENT_SPACING) * fall_position_ratio *
                (perfect_hit_time - time) / fall_time;
        if (duration != 0) {
            dist = Math.max(dist, 0.0);
        }
        if (lane_num < 8) {
            dist = -dist;
        }
        dist += ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[lane_num/8];
        return dist;
    }

    public Note(String verdict){
        String[] temp = verdict.split(" ");
        try{
            perfect_hit_time = Double.parseDouble(temp[0]);
            lane_num = (int)Double.parseDouble(temp[1]);
            fall_time = Double.parseDouble(temp[2]);
            fall_position_ratio = Double.parseDouble(temp[3]);
            duration = Double.parseDouble(temp[4]);
            is_sync=false;
            if (duration == 0) {
                note_height = ReferenceWindow.TAP_NOTE_HEIGHT;
            }
            else {
                note_height = (ReferenceWindow.REF_WIN_W - ReferenceWindow.HORIZONTAL_JUDGEMENT_SPACING) / fall_time * duration * Double.parseDouble(temp[5]);
            }
        }catch (NumberFormatException e){
            throw new RuntimeException("Invalid Note Constructor");
        }
    }

    void reset() {
        status = 4;
        actual_hit_time = -1;
    }

    int get_status() {
        return status;
    }

    double get_duration() {
        return duration;
    }

    double getPerfect_hit_time(){
        return perfect_hit_time;
    }

    void sync(){
        is_sync=true;
    }

    int getLaneNum(){
        return lane_num;
    }

    CRect toDurationRect(int is_paused) {
        CRect r = new CRect();
        r.setSize(
                ReferenceWindow.HOLD_NOTE_WIDTH,
                note_height - Math.max(0.0, note_height / duration * (Time.CURRENT_TIME - perfect_hit_time))
	    );

        if (status == 3) {
            r.setFillColor(GameColors.HOLD_NOTE_COLOR[1]);
        }
        else {
            r.setFillColor(GameColors.HOLD_NOTE_COLOR[is_paused]);
        }
        if (lane_num < 8) {
            r.setOrigin(r.getWidth() / 2.0f, r.getHeight());
        }
        else {
            r.setOrigin(r.getWidth() / 2.0f, 0);
        }
        r.setPosition(
                ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                dist_from_judgement_line(Time.CURRENT_TIME)
		);
        return r;
    }

    CRect toBottomRect(int is_paused){
        CRect r = new CRect();
        r.setSize((int)ReferenceWindow.NOTE_WIDTH, (int)ReferenceWindow.TAP_NOTE_HEIGHT);
        if (duration == 0) {
            r.setFillColor(new Color(
                    GameColors.TAP_NOTE_COLOR[is_paused].getRed(),
                    GameColors.TAP_NOTE_COLOR[is_paused].getGreen(),
                    GameColors.TAP_NOTE_COLOR[is_paused].getBlue(),
                    (int)(Math.max(
                        0.0,
                        GameColors.TAP_NOTE_COLOR[is_paused].getAlpha() - Math.max(
                            0.0,
                            GameColors.TAP_NOTE_COLOR[is_paused].getAlpha() / JudgementLimits.GOOD_LIMIT * (Time.CURRENT_TIME - perfect_hit_time)
                        )
			        ))
            ));
        }
        else {
            r.setFillColor(new Color(
                    GameColors.HOLD_NOTE_COLOR[is_paused].getRed(),
                    GameColors.HOLD_NOTE_COLOR[is_paused].getGreen(),
                    GameColors.HOLD_NOTE_COLOR[is_paused].getBlue(),
                    (int)(Math.max(
                            0.0,
                            GameColors.HOLD_NOTE_COLOR[is_paused].getAlpha() - Math.max(
                                    0.0, GameColors.HOLD_NOTE_COLOR[is_paused].getAlpha() / JudgementLimits.GOOD_LIMIT * (Time.CURRENT_TIME - perfect_hit_time)
                            )
                    ))
			));
        }
        r.setOutlineColor(new Color(
                GameColors.SYNC_COLOR[is_paused].getAlpha(),
                GameColors.SYNC_COLOR[is_paused].getGreen(),
                GameColors.SYNC_COLOR[is_paused].getBlue(),
                (int)(Math.max(
                    0.0,
                    GameColors.SYNC_COLOR[is_paused].getAlpha() - Math.max(
                            0.0, GameColors.SYNC_COLOR[is_paused].getAlpha() / JudgementLimits.GOOD_LIMIT * (Time.CURRENT_TIME - perfect_hit_time)
                    )
                ))
	    ));
        if (lane_num < 8) {
            r.setOrigin(r.getWidth() / 2.0f, r.getHeight());
        }
        else {
            r.setOrigin(r.getWidth() / 2.0f, 0);
        }
        if (is_sync) {
            r.setOutlineThickness(ReferenceWindow.NOTE_OUTLINE_THICKNESS);
        }
        r.setPosition(
                ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                dist_from_judgement_line(Time.CURRENT_TIME)
		);
        return r;
    }

    CRect toParticle(int is_paused) {
        double current_time = Time.CURRENT_TIME;
        while (current_time > actual_hit_time + JudgementLimits.NOTE_LINGERING_TIME) {
            current_time -= JudgementLimits.NOTE_LINGERING_TIME;
        }
        CRect r = getParticleCRect(is_paused, current_time, actual_hit_time, status);
        if (status == 2) {
            r.setPosition(
                    ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                    dist_from_judgement_line(actual_hit_time)
            );
        }
        else {
            r.setPosition(
                    ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                    ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[lane_num / 8]
		    );
        }
        return r;
    }

    private static CRect getParticleCRect(int is_paused, double current_time, double actual_hit_time, int status) {
        double current_width = ReferenceWindow.NOTE_WIDTH + (
                (ReferenceWindow.VERTICAL_JUDGEMENT_SPACING - ReferenceWindow.NOTE_WIDTH) *
                (current_time - actual_hit_time) / JudgementLimits.NOTE_LINGERING_TIME
	    );
        CRect r = new CRect(
                current_width,
                ReferenceWindow.TAP_NOTE_HEIGHT * current_width / ReferenceWindow.NOTE_WIDTH
	    );
        r.setOutlineColor(new Color(
                GameColors.PARTICLE_COLOR[is_paused][status].getRed(),
                GameColors.PARTICLE_COLOR[is_paused][status].getGreen(),
                GameColors.PARTICLE_COLOR[is_paused][status].getBlue(),
                (int)(GameColors.PARTICLE_COLOR[is_paused][status].getAlpha() / JudgementLimits.NOTE_LINGERING_TIME *
                (JudgementLimits.NOTE_LINGERING_TIME - current_time + actual_hit_time))
	    ));
        r.setOutlineThickness(ReferenceWindow.NOTE_OUTLINE_THICKNESS);
        r.setOrigin(
                r.getWidth() / 2.0f,
                r.getHeight() / 2.0f
        );
        return r;
    }

    void judge(int type) {
        if(type == 0){
            if (Time.CURRENT_TIME - perfect_hit_time > JudgementLimits.BAD_LIMIT && status == 4) {
                status = 3;
            }
            return;
        }
        if(duration==0){
            if (type == 1) {
                double difference = perfect_hit_time - Time.CURRENT_TIME;
                if (Math.abs(difference) <= JudgementLimits.BAD_LIMIT) {
                    actual_hit_time = Time.CURRENT_TIME;
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
        else {
            if (type == 1) {
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
    }

    void autoplay() {
        if (Time.CURRENT_TIME >= perfect_hit_time) {
            if (actual_hit_time == -1) {
                status = 0;
                actual_hit_time = Time.CURRENT_TIME;
            }
        }
    }

    int visibility_status() {
        if (duration == 0) {
            if (
                    (status == 3 || status == 4) &&
                    (Time.CURRENT_TIME >= perfect_hit_time - fall_time) &&
                    (Time.CURRENT_TIME <= perfect_hit_time + JudgementLimits.BAD_LIMIT) &&
                    (is_active() || (!is_active() && has_particle()))
			) {
                return 1;
            }
		    else if (
                    (status == 0 || status == 1 || status == 2) &&
                    (Time.CURRENT_TIME <= actual_hit_time + JudgementLimits.NOTE_LINGERING_TIME)
			) {
                return 1;
            }
		    else if (Time.CURRENT_TIME < perfect_hit_time - fall_time) { return 0; }
		    else { return 2; }
        }
        else {
            if (
                    (Time.CURRENT_TIME >= perfect_hit_time - fall_time) &&
                    (Time.CURRENT_TIME <= perfect_hit_time + duration)
			) {
                return 1;
            }
		    else if (Time.CURRENT_TIME < perfect_hit_time - fall_time) { return 0; }
		    else { return 2; }
        }
    }

    double get_strike_time_difference() {
        return actual_hit_time - perfect_hit_time;
    }

    boolean is_active() {
        if (duration == 0) {
            return (status == 4);
        }
        else {
            return (status == 4) ||
                    (
                            (status == 0 || status == 1) &&
                            (perfect_hit_time + duration - Time.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT)
		            );
        }
    }

    boolean has_particle() {
        if (duration == 0) {
            return (status == 0 || status == 1 || status == 2) && (Time.CURRENT_TIME - actual_hit_time < JudgementLimits.NOTE_LINGERING_TIME);
        }
        else {
            return (status == 0 || status == 1);
        }
    }

    boolean has_rect() {
        if (duration == 0) {
            return !has_particle();
        }
        else {
            return true;
        }
    }

    /**
     * @param o the object to be compared.
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof Note n){
            return (int)(perfect_hit_time-n.getPerfect_hit_time());
        }
        else{
            throw new RuntimeException("Trying to compare Note to "+o.getClass().getName());
        }
    }
}
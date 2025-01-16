package org.kelvinizer.note;

import org.kelvinizer.constants.*;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

public abstract class Note implements Comparable<Note>{
    protected final MotionManager movement;

    private static final double NOTE_WIDTH = 60.0;
    private static final double TAP_NOTE_HEIGHT = 10.0;
    private static final double NOTE_OUTLINE_THICKNESS = 4.0;
    private static final Color SYNC_COLOR = Color.YELLOW;
    private static final Color[] PARTICLE_COLOR = {Color.GREEN, Color.BLUE, Color.RED};

    protected final int lane_num;
    protected final double startTime;
    protected final double perfect_hit_time;
    protected boolean is_sync = false;
    protected double actual_hit_time = Double.POSITIVE_INFINITY;
    protected int status = 4;

    public Note(int lane_num, double perfect_hit_time, double duration){
        this.lane_num=lane_num;
        this.perfect_hit_time=perfect_hit_time;
        movement = new MotionManager(lane_num, duration);
        startTime = perfect_hit_time - duration;
    }

    private Color setColorAlpha(Color c, int opacity){
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
    }

    private CRect getParticleCRect(double current_time, double actual_hit_time, int status) {
        double current_width = NOTE_WIDTH + (
                (ReferenceWindow.VERTICAL_JUDGEMENT_SPACING - NOTE_WIDTH) *
                        (current_time - actual_hit_time) / JudgementLimits.NOTE_LINGERING_TIME
        );
        CRect r = new CRect(
                current_width,
                TAP_NOTE_HEIGHT * current_width / NOTE_WIDTH
        );
        r.setOutlineColor(setColorAlpha(
                PARTICLE_COLOR[status],
                (int)(
                        PARTICLE_COLOR[status].getAlpha() / JudgementLimits.NOTE_LINGERING_TIME *
                        (JudgementLimits.NOTE_LINGERING_TIME - current_time + actual_hit_time)
                )
        ));
        r.setOutlineThickness(NOTE_OUTLINE_THICKNESS);
        r.setOrigin(r.getWidth() / 2.0f, r.getHeight() / 2.0f);
        return r;
    }

    public void addMotion(Motion m){
        movement.addMotion(m);
    }

    public boolean isValidNote(){
        return movement.isValidMotionManager() &&
                lane_num >= 0 && lane_num < 16 &&
                perfect_hit_time >=0;
    }

    public void reset() {
        status = 4;
        actual_hit_time = Double.POSITIVE_INFINITY;
    }

    public void sync(){
        is_sync = true;
    }

    public int getStatus(){
        return status;
    }

    public int getLaneNum(){
        return lane_num;
    }

    public double getPerfectHitTime(){
        return perfect_hit_time;
    }

    public double getStartTime(){
        return startTime;
    }

    public double getStrikeTimeDifference() {
        return perfect_hit_time - actual_hit_time;
    }

    public void autoplay() {
        if (Chart.CURRENT_TIME >= perfect_hit_time) {
            if (actual_hit_time == Double.POSITIVE_INFINITY) {
                status = 0;
                actual_hit_time = Chart.CURRENT_TIME;
            }
        }
    }

    public CRect toParticleRect(){
        double current_time = Chart.CURRENT_TIME;
        while (current_time > actual_hit_time + JudgementLimits.NOTE_LINGERING_TIME) {
            current_time -= JudgementLimits.NOTE_LINGERING_TIME;
        }
        CRect r = getParticleCRect(current_time, actual_hit_time, status);
        if (status == 2) {
            r.setPosition(
                    ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                    movement.dist(actual_hit_time - startTime)
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

    protected CRect getBottomRect(Color fillColor){
        CRect r = new CRect();
        r.setSize((int)NOTE_WIDTH, (int)TAP_NOTE_HEIGHT);
        r.setFillColor(setColorAlpha(
                fillColor,
                (int)(Math.max(
                        0.0,
                        fillColor.getAlpha() - Math.max(
                                0.0,
                                fillColor.getAlpha() / JudgementLimits.GOOD_LIMIT * (Chart.CURRENT_TIME - perfect_hit_time)
                        )
                ))
        ));
        r.setOutlineColor(setColorAlpha(
                SYNC_COLOR,
                (int)(Math.max(
                        0.0,
                        SYNC_COLOR.getAlpha() - Math.max(
                                0.0, SYNC_COLOR.getAlpha() / JudgementLimits.GOOD_LIMIT * (Chart.CURRENT_TIME - perfect_hit_time)
                        )
                ))
        ));
        if (lane_num < 8) {
            r.setOrigin(r.getWidth() / 2.0f, r.getHeight());
        }
        else {
            r.setOrigin(r.getWidth() / 2.0f, 0);
        }
        if (is_sync && users.get(userIndex).syncEnabled) {
            r.setOutlineThickness(NOTE_OUTLINE_THICKNESS);
        }
        r.setPosition(
                ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                movement.dist(Chart.CURRENT_TIME - startTime)
        );
        return r;
    }

    public abstract CRect toBottomRect();
    public abstract void judge(int signal);
    public abstract boolean isActive();
    public abstract boolean hasRect();
    public abstract boolean hasParticle();
    public abstract int visibilityStatus();

    public boolean equals(Note n){
        return perfect_hit_time == n.perfect_hit_time;
    }

    /**
     * @param n the note to be compared.
     */
    @Override
    public int compareTo(Note n) {
        return (int)(perfect_hit_time-n.perfect_hit_time);
    }

    public double getTotalMovementTime() {
        return movement.getDuration();
    }
}
package org.kelvinizer.note;

import org.kelvinizer.constants.*;
import org.kelvinizer.motion.Motion;
import org.kelvinizer.support.CRect;

import java.awt.*;
import java.util.ArrayList;

import static org.kelvinizer.constants.GameColors.PAUSED_OPACITY;
import static org.kelvinizer.constants.GameColors.setColorAlpha;
import static org.kelvinizer.constants.General.isPaused;
import static org.kelvinizer.constants.ReferenceWindow.UNIT;

public abstract class Note implements Comparable<Note>{
    private final ArrayList<Motion> movement;
    private int motionPointer = 0;
    private double totalMovementTime = 0;
    private static final double NOTE_WIDTH = 48.0;
    private static final double TAP_NOTE_HEIGHT = 5.0;
    private static final double NOTE_OUTLINE_THICKNESS = 1.0;
    private static final Color[] SYNC_COLOR = {
            new Color(223,197,123),
            new Color(223,197,123,PAUSED_OPACITY)
    };
    private static final Color[][] PARTICLE_COLOR = {
            {
                    new Color(0,255,0),
                    new Color(0,0,255),
                    new Color(255,0,0)
            },
            {
                    new Color(0,255,0,PAUSED_OPACITY),
                    new Color(0,0,255,PAUSED_OPACITY),
                    new Color(255,0,0,PAUSED_OPACITY)
            }
    };

    protected final int lane_num;
    protected final double perfect_hit_time;
    protected boolean is_sync = false;
    protected double actual_hit_time = -1;
    protected int status = 4;

    public Note(int lane_num, double perfect_hit_time, ArrayList<Motion> motions){
        this.lane_num=lane_num;
        this.perfect_hit_time=perfect_hit_time;
        movement = motions;
        for(Motion m: movement){
            totalMovementTime += m.getEnd()-m.getStart();
        }
    }

    public Note(int lane_num, double perfect_hit_time){
        this(lane_num, perfect_hit_time, new ArrayList<>());
    }

    private void updateMotion(){
        if(motionPointer == movement.size()){
            return;
        }
        while(movement.get(motionPointer).getEnd() < Time.CURRENT_TIME){
            motionPointer++;
            if(motionPointer == movement.size()){
                return;
            }
        }
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
                PARTICLE_COLOR[isPaused][status],
                (int)(
                        PARTICLE_COLOR[isPaused][status].getAlpha() / JudgementLimits.NOTE_LINGERING_TIME *
                                (JudgementLimits.NOTE_LINGERING_TIME - current_time + actual_hit_time)
                )
        ));
        r.setOutlineThickness(NOTE_OUTLINE_THICKNESS);
        r.setOrigin(
                r.getWidth() / 2.0f,
                r.getHeight() / 2.0f
        );
        return r;
    }

    protected double distFromJudgementLine(double time) {
        if(motionPointer == movement.size()){
            return 0;
        }
        if(status!=2){
            updateMotion();
        }
        return UNIT*movement.get(motionPointer).dist(time);
    }

    public void addMotion(Motion m){
        assert(m.getEnd()-m.getStart()>=0);
        movement.add(m);
        totalMovementTime+=m.getEnd()-m.getStart();
    }

    public void reset() {
        status = 4;
        actual_hit_time = -1;
        motionPointer = 0;
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

    public double getStrikeTimeDifference() {
        return perfect_hit_time - actual_hit_time;
    }

    public void autoplay() {
        if (Time.CURRENT_TIME >= perfect_hit_time) {
            if (actual_hit_time == -1) {
                status = 0;
                actual_hit_time = Time.CURRENT_TIME;
            }
        }
    }

    public CRect toParticleRect(){
        double current_time = Time.CURRENT_TIME;
        while (current_time > actual_hit_time + JudgementLimits.NOTE_LINGERING_TIME) {
            current_time -= JudgementLimits.NOTE_LINGERING_TIME;
        }
        CRect r = getParticleCRect(current_time, actual_hit_time, status);
        if (status == 2) {
            r.setPosition(
                    ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                    distFromJudgementLine(actual_hit_time)
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
                                fillColor.getAlpha() / JudgementLimits.GOOD_LIMIT * (Time.CURRENT_TIME - perfect_hit_time)
                        )
                ))
        ));
        r.setOutlineColor(setColorAlpha(
                SYNC_COLOR[isPaused],
                (int)(Math.max(
                        0.0,
                        SYNC_COLOR[isPaused].getAlpha() - Math.max(
                                0.0, SYNC_COLOR[isPaused].getAlpha() / JudgementLimits.GOOD_LIMIT * (Time.CURRENT_TIME - perfect_hit_time)
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
            r.setOutlineThickness(NOTE_OUTLINE_THICKNESS);
        }
        r.setPosition(
                ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2,
                distFromJudgementLine(Time.CURRENT_TIME)
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
        return totalMovementTime;
    }
}
package org.kelvinizer.note;

import org.kelvinizer.constants.*;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

/**
 * Represents a note in the rhythm game, containing properties for movement, timing, and judgment.
 * This class is abstract and serves as a base for various note types.
 * @author Boyan Hu
 */
public abstract class Note implements Comparable<Note> {

    /**
     * Manages the motion of the note within the game.
     */
    protected final MotionManager movement;

    /**
     * The width of the note.
     */
    private static final double NOTE_WIDTH = 60.0;

    /**
     * The height of a tap note.
     */
    private static final double TAP_NOTE_HEIGHT = 10.0;

    /**
     * The outline thickness of the note.
     */
    private static final double NOTE_OUTLINE_THICKNESS = 4.0;

    /**
     * The color used for sync indication.
     */
    private static final Color SYNC_COLOR = Color.YELLOW;

    /**
     * The colors used for particle effects, indexed by the note's status.
     */
    private static final Color[] PARTICLE_COLOR = {Color.GREEN, Color.BLUE, Color.RED};

    /**
     * The lane number where the note is displayed.
     */
    protected final int lane_num;

    /**
     * The perfect hit time for the note.
     */
    protected final double perfect_hit_time;

    /**
     * The start time for the note.
     */
    protected final double startTime;

    /**
     * Indicates if the note is synced.
     */
    protected boolean is_sync = false;

    /**
     * The actual hit time of the note, initialized to positive infinity.
     */
    protected double actual_hit_time = Double.POSITIVE_INFINITY;

    /**
     * The status of the note, with a default value of 4 (inactive).
     */
    protected int status = 4;

    /**
     * Constructs a new note with the specified lane number, perfect hit time, and duration.
     *
     * @param lane_num        The lane number where the note is displayed.
     * @param perfect_hit_time The time when the note is perfectly hit.
     * @param duration        The duration the note lasts.
     */
    public Note(int lane_num, double perfect_hit_time, double duration) {
        this.lane_num = lane_num;
        this.perfect_hit_time = perfect_hit_time;
        movement = new MotionManager(lane_num, duration);
        startTime = perfect_hit_time - duration;
    }

    /**
     * Sets the alpha (opacity) value of a given color.
     *
     * @param c        The color to modify.
     * @param opacity The desired opacity value.
     * @return A new color with the specified opacity.
     */
    private Color setColorAlpha(Color c, int opacity) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
    }

    /**
     * Creates a CRect (rectangular) particle representation of the note.
     *
     * @param current_time    The current time in the game.
     * @param actual_hit_time The actual time the note was hit.
     * @param status          The current status of the note.
     * @return A CRect that represents the note particle.
     */
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
                (int)(PARTICLE_COLOR[status].getAlpha() / JudgementLimits.NOTE_LINGERING_TIME *
                        (JudgementLimits.NOTE_LINGERING_TIME - current_time + actual_hit_time)
                )
        ));
        r.setOutlineThickness(NOTE_OUTLINE_THICKNESS);
        r.setOrigin(r.getWidth() / 2.0f, r.getHeight() / 2.0f);
        return r;
    }

    /**
     * Adds motion to the note's movement manager.
     *
     * @param m The motion to be added to the movement manager.
     */
    public void addMotion(Motion m) {
        movement.addMotion(m);
    }

    /**
     * Checks if the note is valid based on its movement manager and other parameters.
     *
     * @return True if the note is valid, false otherwise.
     */
    public boolean isValidNote() {
        return movement.isValidMotionManager() &&
                lane_num >= 0 && lane_num < 16 &&
                perfect_hit_time >= 0;
    }

    /**
     * Resets the note's status and hit time to their initial values.
     */
    public void reset() {
        status = 4;
        actual_hit_time = Double.POSITIVE_INFINITY;
    }

    /**
     * Syncs the note, marking it as synchronized.
     */
    public void sync() {
        is_sync = true;
    }

    /**
     * Gets the current status of the note.
     *
     * @return The status of the note.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets the lane number where the note is displayed.
     *
     * @return The lane number of the note.
     */
    public int getLaneNum() {
        return lane_num;
    }

    /**
     * Gets the start time for the note.
     *
     * @return The start time of the note.
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * Gets the difference between the perfect hit time and the actual hit time.
     *
     * @return The difference between the perfect and actual hit times.
     */
    public double getStrikeTimeDifference() {
        return perfect_hit_time - actual_hit_time;
    }

    /**
     * Simulates autoplay behavior for the note based on the current game time.
     */
    public void autoplay() {
        if (Chart.CURRENT_TIME >= perfect_hit_time) {
            if (actual_hit_time == Double.POSITIVE_INFINITY) {
                status = 0;
                actual_hit_time = Chart.CURRENT_TIME;
            }
        }
    }

    /**
     * Creates a particle rectangle representing the note for the current time.
     *
     * @return A CRect representing the note particle.
     */
    public CRect toParticleRect() {
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

    /**
     * Creates the bottom rectangular representation of the note with a given fill color.
     * The color is adjusted for alpha transparency based on the current time and perfect hit time.
     *
     * @param fillColor The color used to fill the bottom rectangle.
     * @return A CRect representing the bottom of the note with appropriate fill and outline color.
     */
    protected CRect getBottomRect(Color fillColor) {
        CRect r = new CRect();
        r.setSize((int)NOTE_WIDTH, (int)TAP_NOTE_HEIGHT);
        r.setFillColor(setColorAlpha(
                fillColor,
                (int)(Math.max(
                        0.0,
                        fillColor.getAlpha() - Math.max(
                                0.0,
                                fillColor.getAlpha() / JudgementLimits.GOOD * (Chart.CURRENT_TIME - perfect_hit_time)
                        )
                ))
        ));
        r.setOutlineColor(setColorAlpha(
                SYNC_COLOR,
                (int)(Math.max(
                        0.0,
                        SYNC_COLOR.getAlpha() - Math.max(
                                0.0, SYNC_COLOR.getAlpha() / JudgementLimits.GOOD * (Chart.CURRENT_TIME - perfect_hit_time)
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

    /**
     * Abstract method to create the bottom rectangle for the specific type of note.
     *
     * @return A CRect representing the bottom of the note.
     */
    public abstract CRect toBottomRect();

    /**
     * Abstract method to judge the note's status based on a given signal.
     *
     * @param signal The signal indicating how the note should be judged.
     */
    public abstract void judge(int signal);

    /**
     * Abstract method to check if the note is currently active.
     *
     * @return True if the note is active, false otherwise.
     */
    public abstract boolean isActive();

    /**
     * Abstract method to check if the note has a rectangular shape.
     *
     * @return True if the note has a rectangle, false otherwise.
     */
    public abstract boolean hasRect();

    /**
     * Abstract method to check if the note has a particle effect.
     *
     * @return True if the note has a particle effect, false otherwise.
     */
    public abstract boolean hasParticle();

    /**
     * Abstract method to determine the visibility status of the note.
     *
     * @return An integer representing the visibility status of the note.
     */
    public abstract int visibilityStatus();

    /**
     * Compares this note with another note to determine if they are equal.
     * Two notes are considered equal if they have the same perfect hit time.
     *
     * @param n The note to compare this note to.
     * @return true if the notes have the same perfect hit time; false otherwise.
     */
    public boolean equals(Note n) {
        return perfect_hit_time == n.perfect_hit_time;
    }

    /**
     * Compares this note to another note based on the perfect hit time.
     *
     * @param n The note to be compared.
     * @return A negative integer, zero, or a positive integer as this note's perfect hit time
     *         is less than, equal to, or greater than the specified note's perfect hit time.
     */
    @Override
    public int compareTo(Note n) {
        return (int) (1e6*(perfect_hit_time - n.perfect_hit_time));
    }

    /**
     * Gets the total movement time of the note.
     *
     * @return The total duration of the note's movement.
     */
    public double getTotalMovementTime() {
        return movement.getDuration();
    }
}
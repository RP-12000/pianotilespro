package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.support.classes.Motion;

import java.util.ArrayList;

import static org.kelvinizer.constants.ReferenceWindow.UNIT;

/**
 * Manages the motion of {@code Note} over time in the game.
 * It stores a sequence of motions and calculates the position and movement
 * of a note based on its time and associated motions.
 * @author Boyan Hu
 */
public class MotionManager {

    /**
     * A list of motions that define the movement of the note.
     */
    private final ArrayList<Motion> movement = new ArrayList<>();

    /**
     * The duration of the note's movement.
     */
    private final double duration;

    /**
     * The lane number in which the note resides.
     */
    private final int laneNum;

    /**
     * The final position used if no motion is found for a given time.
     */
    private static final double FINAL_POS = -0.1;

    /**
     * Creates a MotionManager for a note with a specified lane and duration.
     *
     * @param laneNum The lane number in which the note resides.
     * @param duration The total duration of the note's movement.
     */
    public MotionManager(int laneNum, double duration) {
        this.laneNum = laneNum;
        this.duration = duration;
    }

    /**
     * Adds a new motion to the sequence of motions in the manager.
     *
     * @param m The motion to add.
     */
    public void addMotion(Motion m) {
        movement.add(m);
    }

    /**
     * Validates if the motion sequence is properly formed.
     * A valid sequence has the following conditions:
     * - The first motion starts at time 0.
     * - Each motion ends where the next motion starts.
     * - The last motion ends at the specified duration.
     *
     * @return true if the motion sequence is valid, false otherwise.
     */
    public boolean isValidMotionManager() {
        if (movement.get(0).getStart() != 0) {
            return false;
        }
        for (int i = 0; i < movement.size() - 1; i++) {
            if (movement.get(i).getEnd() != movement.get(i + 1).getStart()) {
                return false;
            }
        }
        return movement.get(movement.size()-1).getEnd() == duration;
    }

    /**
     * Calculates the position of the note at a given time based on the motion sequence.
     *
     * @param time The time at which to calculate the position.
     * @return The position of the note at the given time.
     */
    private double getPos(double time) {
        for (Motion m : movement) {
            if (m.contains(time)) {
                return m.getPos(time) * UNIT;
            }
        }
        return FINAL_POS / JudgementLimits.BAD * (time - duration) * UNIT;
    }

    /**
     * Calculates the distance of the note's position at a given time.
     *
     * @param time The time at which to calculate the distance.
     * @return The distance of the note at the given time.
     */
    public double dist(double time) {
        if (laneNum >= 8) {
            return ReferenceWindow.REF_WIN_H - UNIT + getPos(time);
        }
        return UNIT - getPos(time);
    }

    /**
     * Retrieves the total duration of the note's movement.
     *
     * @return The duration of the note's movement.
     */
    public double getDuration() {
        return duration;
    }
}
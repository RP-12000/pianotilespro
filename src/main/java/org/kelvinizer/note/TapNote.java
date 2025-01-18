package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.shapes.CRect;

import java.awt.*;

/**
 * The TapNote class represents a tap note in the rhythm game.
 * It extends the Note class and defines specific behavior for tap notes, such as judging the note,
 * determining its activity and visibility, and providing visual feedback with rectangles and particles.
 * @author Boyan Hu
 */
public class TapNote extends Note {

    /**
     * Constructs a TapNote object with the specified lane number, perfect hit time, and duration.
     *
     * @param lane_num The lane number where the tap note is located.
     * @param perfect_hit_time The perfect hit time for the note.
     * @param duration The duration for which the note is active.
     */
    public TapNote(int lane_num, double perfect_hit_time, double duration){
        super(lane_num, perfect_hit_time, duration);
    }

    /**
     * Parses a tap note from a string representation.
     *
     * @param s The string containing the tap note data.
     * @param offset The offset to apply to the perfect hit time.
     * @return A new TapNote object created from the parsed data.
     */
    public static TapNote parseTapNote(String s, double offset){
        String[] t = s.split(" ");
        return new TapNote(
                Integer.parseInt(t[0]),
                Double.parseDouble(t[1]) + offset,
                Double.parseDouble(t[2])
        );
    }

    /**
     * Judges the note based on the given signal.
     *
     * @param signal The signal used to judge the note. If the signal is 0, the note is marked as missed;
     *               if the signal is 1, the note is considered for a perfect, good, or bad hit.
     */
    @Override
    public void judge(int signal) {
        if(signal == 0){
            if (Chart.CURRENT_TIME - perfect_hit_time > JudgementLimits.BAD && status == 4) {
                status = 3; // Missed the note
            }
        }
        else if (signal == 1) {
            double difference = perfect_hit_time - Chart.CURRENT_TIME;
            if (Math.abs(difference) <= JudgementLimits.BAD) {
                actual_hit_time = Chart.CURRENT_TIME;
                if (Math.abs(difference) > JudgementLimits.GOOD) {
                    status = 2; // Good hit
                }
                else if (Math.abs(difference) > JudgementLimits.PERFECT) {
                    status = 1; // Perfect hit
                }
                else {
                    status = 0; // Excellent hit
                }
            }
        }
    }

    /**
     * Checks if the note is currently active.
     *
     * @return True if the note is active (i.e., it has not yet been judged as missed or hit), false otherwise.
     */
    @Override
    public boolean isActive() {
        return status == 4;
    }

    /**
     * Determines whether the note has a rectangle representation.
     *
     * @return True if the note has a rectangle, false if it has a particle representation.
     */
    @Override
    public boolean hasRect() {
        return !hasParticle();
    }

    /**
     * Determines whether the note has a particle effect.
     *
     * @return True if the note has a particle effect (i.e., it's been hit with a perfect, good, or bad status and
     *         is still within the lingering time), false otherwise.
     */
    @Override
    public boolean hasParticle() {
        return (status == 0 || status == 1 || status == 2) && (Chart.CURRENT_TIME - actual_hit_time < JudgementLimits.NOTE_LINGERING_TIME);
    }

    /**
     * Determines the visibility status of the note.
     *
     * @return An integer representing the visibility status of the note:
     *         0 = the note is invisible,
     *         1 = the note is visible,
     *         2 = the note is no longer active and should be hidden.
     */
    @Override
    public int visibilityStatus() {
        if (
                (status == 3 || status == 4) &&
                        (Chart.CURRENT_TIME >= startTime) &&
                        (Chart.CURRENT_TIME <= perfect_hit_time + JudgementLimits.BAD) &&
                        (isActive() || (!isActive() && hasParticle()))
        ) {
            return 1; // Note is visible
        }
        else if (
                (status == 0 || status == 1 || status == 2) &&
                        (Chart.CURRENT_TIME <= actual_hit_time + JudgementLimits.NOTE_LINGERING_TIME)
        ) {
            return 1; // Note is visible (within lingering time)
        }
        else if (Chart.CURRENT_TIME < perfect_hit_time - getTotalMovementTime()) {
            return 0; // Note is invisible
        }
        else {
            return 2; // Note is no longer active
        }
    }

    /**
     * Creates a bottom rectangle for the TapNote with a specified fill color.
     *
     * @return A CRect representing the bottom of the TapNote, with a fill color of light blue.
     */
    @Override
    public CRect toBottomRect(){
        return super.getBottomRect(new Color(130,222,250));
    }
}
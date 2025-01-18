package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.shapes.CRect;

import java.awt.*;

import static org.kelvinizer.constants.ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS;
import static org.kelvinizer.constants.ReferenceWindow.UNIT;

/**
 * The HoldNote class represents a hold note in the rhythm game.
 * It extends the Note class and defines specific behavior for hold notes, including handling the note's duration,
 * judging its status, and generating the visual representations for the note's bottom and duration rectangles.
 * @author Boyan Hu
 */
public class HoldNote extends Note {
    private final double noteHeight;
    private final double duration;
    private static final double HOLD_NOTE_WIDTH = 10.0;

    /**
     * Constructs a HoldNote object with the specified lane number, perfect hit time, duration, note height, and last time.
     *
     * @param lane_num The lane number where the hold note is located.
     * @param perfect_hit_time The perfect hit time for the hold note.
     * @param duration The duration for which the hold note is active.
     * @param noteHeight The height of the hold note.
     * @param last The last parameter (used in constructing the note).
     */
    public HoldNote(int lane_num, double perfect_hit_time, double duration, double noteHeight, double last){
        super(lane_num, perfect_hit_time, duration);
        this.noteHeight = noteHeight;
        this.duration = last;
    }

    /**
     * Parses a hold note from a string representation.
     *
     * @param s The string containing the hold note data.
     * @param offset The offset to apply to the perfect hit time.
     * @return A new HoldNote object created from the parsed data.
     */
    public static HoldNote parseHoldNote(String s, double offset){
        String[] t = s.split(" ");
        return new HoldNote(
                Integer.parseInt(t[0]),
                Double.parseDouble(t[1]) + offset,
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3]),
                Double.parseDouble(t[4])
        );
    }

    /**
     * Creates a bottom rectangle for the HoldNote, with a specific color and outline.
     *
     * @return A CRect representing the bottom of the HoldNote.
     */
    @Override
    public CRect toBottomRect(){
        CRect cr = super.getBottomRect(Color.WHITE);
        if(Chart.CURRENT_TIME > perfect_hit_time){
            cr.setY(HORIZONTAL_JUDGEMENT_LINE_POS[lane_num / 8]);
            cr.setOutlineColor(null);
            cr.setFillColor(null);
        }
        return cr;
    }

    /**
     * Creates a rectangle representing the duration of the HoldNote.
     *
     * @param bottomRect The bottom rectangle of the HoldNote.
     * @return A CRect representing the duration of the HoldNote.
     */
    public CRect toDurationRect(CRect bottomRect){
        CRect r = new CRect();
        r.setSize(
                HOLD_NOTE_WIDTH,
                UNIT * (noteHeight - Math.max(0.0, noteHeight / duration * (Chart.CURRENT_TIME - perfect_hit_time)))
        );
        if (status == 3) {
            r.setFillColor(new Color(1, 1, 1, 0.25f)); // Faded if missed
        }
        else {
            r.setFillColor(Color.WHITE); // Normal color
        }
        if (lane_num < 8) {
            r.setOrigin(r.getWidth() / 2.0f, r.getHeight());
        }
        else {
            r.setOrigin(r.getWidth() / 2.0f, 0);
        }
        r.setX(ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[lane_num % 8] + ReferenceWindow.VERTICAL_JUDGEMENT_SPACING / 2);
        if(Chart.CURRENT_TIME > perfect_hit_time){
            r.setY(HORIZONTAL_JUDGEMENT_LINE_POS[lane_num / 8]);
        }
        else{
            r.setY(movement.dist(Chart.CURRENT_TIME - startTime));
        }
        Rectangle br = bottomRect.toJShape();
        Rectangle dr = r.toJShape();
        if(lane_num < 8){
            if(br.y + br.height != dr.y + dr.height){
                r.setY(r.getY() - (dr.y + dr.height) + (br.y + br.height));
            }
        }
        else{
            if(br.y != dr.y){
                r.setY(r.getY() - dr.y + br.y);
            }
        }
        return r;
    }

    /**
     * Judges the note based on the given signal.
     *
     * @param signal The signal used to judge the note:
     *               0 indicates a miss (BAD),
     *               1 indicates a hit (Perfect/Good).
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
            if (Math.abs(difference) <= JudgementLimits.GOOD) {
                actual_hit_time = Chart.CURRENT_TIME;
                if (Math.abs(difference) > JudgementLimits.PERFECT) {
                    status = 1; // Good hit
                }
                else {
                    status = 0; // Perfect hit
                }
            }
        }
        else {
            if (duration + perfect_hit_time - Chart.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT && status != 4) {
                status = 3; // Missed the hold
                actual_hit_time = Double.POSITIVE_INFINITY;
            }
        }
    }

    /**
     * Checks if the note is currently active.
     *
     * @return True if the note is active, false otherwise.
     */
    @Override
    public boolean isActive() {
        return (status == 4) || (
                (status == 0 || status == 1) &&
                        (perfect_hit_time + duration - Chart.CURRENT_TIME > JudgementLimits.HOLD_NOTE_END_LIMIT)
        );
    }

    /**
     * Determines whether the note has a rectangle representation.
     *
     * @return True if the note has a rectangle, false otherwise.
     */
    @Override
    public boolean hasRect() {
        return true;
    }

    /**
     * Determines whether the note has a particle effect.
     *
     * @return True if the note has a particle effect (i.e., it's been hit with perfect or good status), false otherwise.
     */
    @Override
    public boolean hasParticle() {
        return (status == 0 || status == 1);
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
                (Chart.CURRENT_TIME >= startTime) &&
                        (Chart.CURRENT_TIME <= perfect_hit_time + duration)
        ) {
            return 1; // Note is visible
        }
        else if (Chart.CURRENT_TIME < startTime) {
            return 0; // Note is invisible (not yet started)
        }
        else {
            return 2; // Note is no longer active
        }
    }
}
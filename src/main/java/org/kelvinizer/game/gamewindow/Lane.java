package org.kelvinizer.game.gamewindow;

import org.kelvinizer.note.HoldNote;
import org.kelvinizer.note.Note;
import org.kelvinizer.shapes.CRect;

import java.util.ArrayList;

/**
 * Represents a lane in the game that holds notes, manages their visibility and judgement status,
 * and processes updates for autoplay and user input.
 * The lane stores notes in two separate lists: one for rendering and one for judgement.
 * It also manages scoring, combo tracking, and hit statistics.
 * @author Boyan Hu
 */
public class Lane {

    /**
     * List of notes in the order they will be rendered.
     */
    private final ArrayList<Note> renderOrder = new ArrayList<>();

    /**
     * List of notes in the order they will be judged.
     */
    private final ArrayList<Note> judgementOrder = new ArrayList<>();

    /**
     * Pointers used for managing the progress of notes.
     * Left pointer tracks the leftmost note to be rendered,
     * right pointer tracks the rightmost note to be rendered,
     * and active note pointer tracks the currently active note.
     */
    private int left_pointer = 0, right_pointer = 0, active_note_pointer = 0;

    /**
     * Statistics tracking different types of hits and combo states.
     */
    public static double miss, bad, good, perfect, early, late, worstHit, currentCombo, maxCombo, total;

    /**
     * Updates the pointers used to track the progress of notes, calculates the scores,
     * updates combo information, and tracks worst hit time.
     * This is called after every judgement or action on a note.
     */
    private void update_pointers() {
        // Check if the active note has been judged and update pointers accordingly
        if (active_note_pointer < judgementOrder.size()) {
            if (!judgementOrder.get(active_note_pointer).isActive()) {
                // Handle different statuses (perfect, good, bad, miss)
                // Update statistics like score, combo, etc.
                if (judgementOrder.get(active_note_pointer).getStatus() == 0) {
                    perfect++;
                    currentCombo++;
                }
                else if (judgementOrder.get(active_note_pointer).getStatus() == 1) {
                    good++;
                    currentCombo++;
                    if ((judgementOrder.get(active_note_pointer).getStrikeTimeDifference() < 0)) {
                        early++;
                    } else {
                        late++;
                    }
                }
                else if (judgementOrder.get(active_note_pointer).getStatus() == 2) {
                    bad++;
                    currentCombo = 0;
                }
                else if (judgementOrder.get(active_note_pointer).getStatus() == 3) {
                    miss++;
                    currentCombo = 0;
                }
                maxCombo = Math.max(maxCombo, currentCombo);
                worstHit = Math.max(worstHit, Math.abs(judgementOrder.get(active_note_pointer).getStrikeTimeDifference()));
                active_note_pointer++;
            }
        }

        // Update left pointer based on visibility status of notes
        if (left_pointer < renderOrder.size()) {
            if (renderOrder.get(left_pointer).visibilityStatus() == 2) {
                left_pointer++;
            }
        }

        // Update right pointer based on visibility status of notes
        if (right_pointer < renderOrder.size()) {
            if (renderOrder.get(right_pointer).visibilityStatus() == 1) {
                right_pointer++;
            }
        }

        // Update total score
        total = perfect + good + bad + miss;
    }

    /**
     * Constructs a new lane with no notes initially.
     */
    public Lane() {}

    /**
     * Adds a note to both the render order and judgement order for the lane.
     *
     * @param n The note to be added to the lane.
     */
    void addNote(Note n) {
        renderOrder.add(n);
        judgementOrder.add(n);
    }

    /**
     * Sorts the notes in both the render and judgement orders based on their start times.
     */
    void sort() {
        renderOrder.sort((Note a, Note b) -> (int)((a.getStartTime() - b.getStartTime())));
        judgementOrder.sort(Note::compareTo);
    }

    /**
     * Updates the active note with the specified type of signal (such as user input or autoplay).
     *
     * @param type The type of signal (e.g., user input or autoplay) to update the note.
     */
    void update(int type) {
        if (active_note_pointer < judgementOrder.size()) {
            judgementOrder.get(active_note_pointer).judge(type);
        }
        update_pointers();
    }

    /**
     * Updates the active note with autoplay.
     */
    void autoplay() {
        if (active_note_pointer < judgementOrder.size()) {
            judgementOrder.get(active_note_pointer).autoplay();
        }
        update_pointers();
    }

    /**
     * Converts the notes in the lane to their corresponding rectangles for rendering.
     * If a note is a HoldNote, it will create both the bottom and duration rectangles.
     *
     * @return A list of CRect objects representing the notes' rectangles for rendering.
     */
    ArrayList<CRect> toRect() {
        ArrayList<CRect> render_notes = new ArrayList<>();
        for (int i = left_pointer; i < right_pointer; i++) {
            if (renderOrder.get(i).hasRect()) {
                if (renderOrder.get(i) instanceof HoldNote h) {
                    CRect br = renderOrder.get(i).toBottomRect();
                    CRect dr = h.toDurationRect(br);
                    render_notes.add(br);
                    render_notes.add(dr);
                } else {
                    render_notes.add(renderOrder.get(i).toBottomRect());
                }
            }
            if (renderOrder.get(i).hasParticle()) {
                render_notes.add(renderOrder.get(i).toParticleRect());
            }
        }
        return render_notes;
    }

    /**
     * Restarts the lane, resetting pointers, statistics, and note statuses.
     */
    void restart() {
        left_pointer = 0;
        active_note_pointer = 0;
        right_pointer = 0;
        miss = 0.0;
        bad = 0.0;
        good = 0.0;
        perfect = 0.0;
        early = 0.0;
        late = 0.0;
        currentCombo = 0.0;
        maxCombo = 0.0;
        worstHit = 0.0;
        total = 0.0;
        for (Note n : judgementOrder) {
            n.reset();
        }
    }
}
package org.kelvinizer.gamewindow;

import org.kelvinizer.note.HoldNote;
import org.kelvinizer.note.Note;
import org.kelvinizer.support.CRect;

import java.util.ArrayList;

public class Lane {
    ArrayList<Note> lane_notes = new ArrayList<>();
    private int left_pointer, right_pointer, active_note_pointer;
    public static double miss, bad, good, perfect, early, late, total;

    private void update_pointers() {
        if (active_note_pointer < lane_notes.size() && active_note_pointer < right_pointer) {
            if (!lane_notes.get(active_note_pointer).isActive()) {
                if (lane_notes.get(active_note_pointer).getStatus() == 0) {
                    perfect++;
                }
                else if (lane_notes.get(active_note_pointer).getStatus() == 1) {
                    good++;
                    if((lane_notes.get(active_note_pointer).getStrikeTimeDifference() < 0)){
                        early++;
                    }
                    else{
                        late++;
                    }
                }
                else if (lane_notes.get(active_note_pointer).getStatus() == 2) {
                    bad++;
                }
                else if (lane_notes.get(active_note_pointer).getStatus() == 3) {
                    miss++;
                }
                active_note_pointer++;
            }
        }
        if (left_pointer < lane_notes.size()) {
            if (lane_notes.get(left_pointer).visibilityStatus() == 2) {
                left_pointer++;
            }
        }
        if (right_pointer < lane_notes.size()) {
            if (lane_notes.get(right_pointer).visibilityStatus() == 1) {
                right_pointer++;
            }
        }
        total = perfect + good + bad + miss;
    }

    public Lane() {
        left_pointer = 0;
        right_pointer = 0;
        active_note_pointer = 0;
    }

    void addNote(Note n) {
        lane_notes.add(n);
    }

    void update(int type) {
        if (active_note_pointer < right_pointer) {
            lane_notes.get(active_note_pointer).judge(type);
        }
        update_pointers();
    }

    void autoplay() {
        if (active_note_pointer < right_pointer) {
            lane_notes.get(active_note_pointer).autoplay();
        }
        update_pointers();
    }

    ArrayList<CRect> toRect() {
        ArrayList<CRect> render_notes = new ArrayList<>();
        for (int i = left_pointer; i < right_pointer; i++) {
            if (lane_notes.get(i).hasRect()) {
                render_notes.add(lane_notes.get(i).toBottomRect());
                if (lane_notes.get(i) instanceof HoldNote h) {
                    render_notes.add(h.toDurationRect());
                }
            }
            if (lane_notes.get(i).hasParticle()) {
                render_notes.add(lane_notes.get(i).toParticleRect());
            }
        }
        return render_notes;
    }

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
        for (Note n : lane_notes){
            n.reset();
        }
    }
}

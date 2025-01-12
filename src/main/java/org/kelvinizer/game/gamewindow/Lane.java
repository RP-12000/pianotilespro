package org.kelvinizer.game.gamewindow;

import org.kelvinizer.note.HoldNote;
import org.kelvinizer.note.Note;
import org.kelvinizer.shapes.CRect;

import java.util.ArrayList;

public class Lane {
    private final ArrayList<Note> renderOrder = new ArrayList<>();
    private final ArrayList<Note> judgementOrder = new ArrayList<>();
    private int left_pointer = 0, right_pointer = 0, active_note_pointer = 0;
    public static double miss, bad, good, perfect, early, late, worstHit, currentCombo, maxCombo, total;

    private void update_pointers() {
        if (active_note_pointer < judgementOrder.size()) {
            if (!judgementOrder.get(active_note_pointer).isActive()) {
                if (judgementOrder.get(active_note_pointer).getStatus() == 0) {
                    perfect++;
                    currentCombo++;
                }
                else if (judgementOrder.get(active_note_pointer).getStatus() == 1) {
                    good++;
                    currentCombo++;
                    if((judgementOrder.get(active_note_pointer).getStrikeTimeDifference() < 0)){
                        early++;
                    }
                    else{
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
        if (left_pointer < renderOrder.size()) {
            if (renderOrder.get(left_pointer).visibilityStatus() == 2) {
                left_pointer++;
            }
        }
        if (right_pointer < renderOrder.size()) {
            if (renderOrder.get(right_pointer).visibilityStatus() == 1) {
                right_pointer++;
            }
        }
        total = perfect + good + bad + miss;
    }

    public Lane() {}

    void addNote(Note n) {
        renderOrder.add(n);
        judgementOrder.add(n);
    }

    void sort(){
        renderOrder.sort((Note a, Note b) -> (int)( (a.getStartTime()-b.getStartTime())));
        judgementOrder.sort(Note::compareTo);
    }

    void update(int type) {
        if(active_note_pointer<judgementOrder.size()){
            judgementOrder.get(active_note_pointer).judge(type);
        }
        update_pointers();
    }

    void autoplay() {
        if (active_note_pointer < judgementOrder.size()) {
            judgementOrder.get(active_note_pointer).autoplay();
        }
        update_pointers();
    }

    ArrayList<CRect> toRect() {
        ArrayList<CRect> render_notes = new ArrayList<>();
        for (int i = left_pointer; i < right_pointer; i++) {
            if (renderOrder.get(i).hasRect()) {
                if (renderOrder.get(i) instanceof HoldNote h) {
                    CRect br = renderOrder.get(i).toBottomRect();
                    CRect dr = h.toDurationRect(br);
                    render_notes.add(br);
                    render_notes.add(dr);
                }
                else{
                    render_notes.add(renderOrder.get(i).toBottomRect());
                }
            }
            if (renderOrder.get(i).hasParticle()) {
                render_notes.add(renderOrder.get(i).toParticleRect());
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
        currentCombo = 0.0;
        maxCombo = 0.0;
        worstHit = 0.0;
        total = 0.0;
        for (Note n : judgementOrder){
            n.reset();
        }
    }
}

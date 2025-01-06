package org.kelvinizer.gamewindow;

import org.kelvinizer.Constants.*;
import org.kelvinizer.support.CRect;
import org.kelvinizer.support.Pair;

import java.util.ArrayList;

public class Lane {
    ArrayList<Note> lane_notes = new ArrayList<>();
    ArrayList<Pair<Double, Double>> invisible_time = new ArrayList<>();
    private int left_pointer, right_pointer, active_note_pointer, visibility_pointer;
    private int lane_num;
    public static double miss, bad, good, perfect, early, late, total;

    private void update_pointers() {
        if (active_note_pointer < lane_notes.size() && active_note_pointer < right_pointer) {
            if (!lane_notes.get(active_note_pointer).is_active()) {
                if (lane_notes.get(active_note_pointer).get_status() == 0) {
                    perfect++;
                }
                else if (lane_notes.get(active_note_pointer).get_status() == 1) {
                    good++;
                    if((lane_notes.get(active_note_pointer).get_strike_time_difference() < 0)){
                        early++;
                    }
                    else{
                        late++;
                    }
                }
                else if (lane_notes.get(active_note_pointer).get_status() == 2) {
                    bad++;
                }
                else if (lane_notes.get(active_note_pointer).get_status() == 3) {
                    miss++;
                }
                active_note_pointer++;
            }
        }
        if (left_pointer < lane_notes.size()) {
            if (lane_notes.get(left_pointer).visibility_status() == 2) {
                left_pointer++;
            }
        }
        if (right_pointer < lane_notes.size()) {
            if (lane_notes.get(right_pointer).visibility_status() == 1) {
                right_pointer++;
            }
        }
        if (visibility_pointer < invisible_time.size()) {
            if (invisible_time.get(visibility_pointer).first + invisible_time.get(visibility_pointer).second < Time.CURRENT_TIME) {
                visibility_pointer++;
            }
        }
        total = perfect + good + bad + miss;
    }

    public Lane(int l) {
        lane_num = l;
        left_pointer = 0;
        right_pointer = 0;
        active_note_pointer = 0;
        visibility_pointer = 0;
    }

    void add_note(Note n) {
        lane_notes.add(n);
    }

    void add_invisible_interval(double start, double duration) {
        assert(duration > 0);
        invisible_time.add(new Pair<>(start, duration));
    }

    void sort_note() {
        lane_notes.sort(Note::compareTo);
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

    ArrayList<CRect> to_rect(int is_paused) {
        ArrayList<CRect> render_notes = new ArrayList<>();
        for (int i = left_pointer; i < right_pointer; i++) {
            if (lane_notes.get(i).has_rect()) {
                render_notes.add(lane_notes.get(i).toBottomRect(is_paused));
                if (lane_notes.get(i).get_duration() != 0) {
                    render_notes.add(lane_notes.get(i).toDurationRect(is_paused));
                }
            }
            if (lane_notes.get(i).has_particle()) {
                render_notes.add(lane_notes.get(i).toParticle(is_paused));
            }
        }
        return render_notes;
    }

    boolean invisible() {
        if (visibility_pointer == invisible_time.size()) {
            return false;
        }
        return
                invisible_time.get(visibility_pointer).first <= Time.CURRENT_TIME &&
                invisible_time.get(visibility_pointer).first + invisible_time.get(visibility_pointer).second >= Time.CURRENT_TIME;
    }

    void restart() {
        left_pointer = 0;
        active_note_pointer = 0;
        right_pointer = 0;
        visibility_pointer = 0;
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

package org.kelvinizer.note;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.support.classes.Motion;

import java.util.ArrayList;

import static org.kelvinizer.constants.ReferenceWindow.UNIT;

public class MotionManager {
    private final ArrayList<Motion> movement = new ArrayList<>();
    private final double duration;
    private static final double FINAL_POS = -0.1;

    public MotionManager(double duration){
        this.duration = duration;
    }

    public void addMotion(Motion m){
        movement.add(m);
    }

    public boolean isValidMotionManager(){
        if(movement.getFirst().getStart()!=0){
            return false;
        }
        for(int i=0; i<movement.size()-1; i++){
            if(movement.get(i).getEnd()!=movement.get(i+1).getStart()){
                return false;
            }
        }
        return movement.getLast().getEnd()<=duration;
    }

    public double dist(double time){
        for(Motion m: movement){
            if(m.contains(time)){
                return m.getPos(time)*UNIT;
            }
        }
        return FINAL_POS/ JudgementLimits.BAD_LIMIT * time * UNIT;
    }

    public double getDuration() {
        return duration;
    }
}

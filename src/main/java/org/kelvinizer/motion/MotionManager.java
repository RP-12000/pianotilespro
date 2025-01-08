package org.kelvinizer.motion;

import org.kelvinizer.constants.Time;

import java.util.ArrayList;

public class MotionManager {
    private final ArrayList<Motion> movement = new ArrayList<>();
    private int motionPointer = 0;
    private final double duration;
    private static final double FINAL_POS = -0.1;

    public MotionManager(double duration){
        this.duration = duration;
    }

    public void addMotion(Motion m){
        movement.add(m);
    }

    public boolean isValidMotionManager(){
        if(movement.getFirst().getStartTime()!=0){
            return false;
        }
        for(int i=0; i<movement.size()-1; i++){
            if(movement.get(i).getStartTime()>movement.get(i+1).getStartTime()){
                return false;
            }
        }
        return movement.getLast().getStartTime()<=duration;
    }

    public void reset(){
        motionPointer = 0;
    }

    public void update(){
        if(motionPointer == movement.size()){
            return;
        }
        while(movement.get(motionPointer).getEndPoint() < Time.CURRENT_TIME){
            motionPointer++;
            if(motionPointer == movement.size()){
                return;
            }
        }
    }

    public double dist(double time){
        if(motionPointer == movement.size()){
            return 0;
        }
        return 0;
    }

    public double getDuration() {
        return duration;
    }
}

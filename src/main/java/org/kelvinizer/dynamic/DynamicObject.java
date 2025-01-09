package org.kelvinizer.dynamic;

import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.interfaces.Activatable;
import org.kelvinizer.support.interfaces.Drawable;

import java.util.ArrayList;

public abstract class DynamicObject implements Drawable, Activatable {
    long start = -1;
    ArrayList<Motion> horizontal = new ArrayList<>();
    ArrayList<Motion> vertical = new ArrayList<>();

    public DynamicObject(){}

    public void addHorizontalMotion(Motion m){
        horizontal.add(m);
    }

    public void addVerticalMotion(Motion m){
        vertical.add(m);
    }

    @Override
    public void activate(){
        if(start==-1){
            start = System.nanoTime();
        }
    }
}

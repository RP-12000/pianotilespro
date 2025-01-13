package org.kelvinizer.dynamic;

import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.interfaces.Activatable;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;
import java.util.ArrayList;

public abstract class DynamicObject implements Drawable, Activatable {
    long start = -1;
    protected static final long S_TO_NS_CONVERSION_FACTOR = 1000000000;
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
        activate(System.nanoTime());
    }

    public void activate(long time){
        if(start==-1){
            start = time;
        }
    }

    @Override
    public void render(Graphics2D g2d){
        render(g2d, System.nanoTime());
    }

    public abstract void render(Graphics2D g2d, long time);
}

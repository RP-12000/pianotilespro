package org.kelvinizer.dynamic;

import org.kelvinizer.support.interfaces.Activatable;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;
import java.util.ArrayList;

public class DynamicMotionManager implements Drawable, Activatable {
    private long startTime = -1;
    private final ArrayList<DynamicObject> dm = new ArrayList<>();

    public DynamicMotionManager(){}

    public void addDynamicObject(DynamicObject dynamicObject){
        dm.add(dynamicObject);
    }

    @Override
    public void activate() {
        if(startTime==-1){
            startTime = System.nanoTime();
            for(DynamicObject dO: dm){
                dO.activate(startTime);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        long t = System.nanoTime();
        for(DynamicObject dO: dm){
            dO.render(g2d, t);
        }
    }
}

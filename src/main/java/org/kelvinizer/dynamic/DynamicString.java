package org.kelvinizer.dynamic;

import org.kelvinizer.constants.Time;
import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class DynamicString extends DynamicObject implements Drawable {
    private final BoundedString bs;

    public DynamicString(String d, int stringSize, double x, double y, double width, double height){
        bs = new BoundedString(d, stringSize, x, y, width, height);
    }

    public DynamicString(String d, int stringSize, double x, double y){
        this(d, stringSize, x, y, 0, 0);
    }

    public DynamicString(String d, int stringSize){
        this(d, stringSize, 0, 0);
    }

    public DynamicString(String d){
        this(d, 0);
    }

    public DynamicString(){
        this("");
    }

    public BoundedString getBoundedString(){
        return bs;
    }

    @Override
    public void render(Graphics2D g2d, long time) {
        double timePassed = (double) (time - start) / Time.S_TO_NS_CONVERSION_FACTOR;
        for(Motion m: horizontal){
            if(m.contains(timePassed)){
                bs.getBounds().setX(m.getPos(timePassed));
            }
        }
        for(Motion m: vertical){
            if(m.contains(timePassed)){
                bs.getBounds().setY(m.getPos(timePassed));
            }
        }
        bs.render(g2d);
    }
}
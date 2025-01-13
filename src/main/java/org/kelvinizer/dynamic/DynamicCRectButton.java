package org.kelvinizer.dynamic;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;

public class DynamicCRectButton extends DynamicObject {
    private CRectButton b;

    public DynamicCRectButton(){}

    public void setCRectButton(CRectButton b){
        this.b = b;
    }

    @Override
    public void render(Graphics2D g2d, long time) {
        double timePassed = (double) (time - start) / S_TO_NS_CONVERSION_FACTOR;
        for(Motion m: horizontal){
            if(m.contains(timePassed)){
                b.getNormal().getBounds().setX(m.getPos(timePassed));
                b.getOnFocus().getBounds().setX(m.getPos(timePassed));
                b.getOnSelection().getBounds().setX(m.getPos(timePassed));
            }
        }
        for(Motion m: vertical){
            if(m.contains(timePassed)){
                b.getNormal().getBounds().setY(m.getPos(timePassed));
                b.getOnFocus().getBounds().setY(m.getPos(timePassed));
                b.getOnSelection().getBounds().setY(m.getPos(timePassed));
            }
        }
        b.render(g2d);
    }
}
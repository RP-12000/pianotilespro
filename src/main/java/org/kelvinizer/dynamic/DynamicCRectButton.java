package org.kelvinizer.dynamic;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;
import java.util.Objects;

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
                try{
                    Objects.requireNonNull(b.getOnFocus()).getBounds().setX(m.getPos(timePassed));
                    Objects.requireNonNull(b.getOnSelection()).getBounds().setX(m.getPos(timePassed));
                } catch (RuntimeException ignored) {}
            }
        }
        for(Motion m: vertical){
            if(m.contains(timePassed)){
                b.getNormal().getBounds().setY(m.getPos(timePassed));
                try{
                    Objects.requireNonNull(b.getOnFocus()).getBounds().setY(m.getPos(timePassed));
                    Objects.requireNonNull(b.getOnSelection()).getBounds().setY(m.getPos(timePassed));
                } catch (RuntimeException ignored) {}
            }
        }
        b.render(g2d);
    }

    public CRectButton getCRectButton(){
        return b;
    }
}
package org.kelvinizer.dynamic;

import org.kelvinizer.constants.Time;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DynamicImage extends DynamicObject {
    private BufferedImage bf;
    private CRect bounds;

    public DynamicImage(){}

    public void setImage(BufferedImage bf){
        this.bf = bf;
    }

    public void setBounds(CRect bounds) {
        this.bounds = bounds;
    }

    @Override
    public void render(Graphics2D g2d, long time) {
        double timePassed = (double) (System.nanoTime() - start) / Time.S_TO_NS_CONVERSION_FACTOR;
        for(Motion m: horizontal){
            if(m.contains(timePassed)){
                bounds.setX(m.getPos(timePassed));
            }
        }
        for(Motion m: vertical){
            if(m.contains(timePassed)){
                bounds.setY(m.getPos(timePassed));
            }
        }
        Rectangle r = bounds.toJShape();
        g2d.drawImage(bf, r.x, r.y, r.width, r.height, null);
    }
}

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

    @Override
    public void render(Graphics2D g2d) {
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
        g2d.drawImage(bf, (int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight(), null);
    }

    public void setBounds(CRect bounds) {
        this.bounds = bounds;
    }
}

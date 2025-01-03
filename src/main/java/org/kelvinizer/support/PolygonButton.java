package org.kelvinizer.support;

import org.kelvinizer.Constants;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PolygonButton {
    private final Polygon buttonShape;
    private Polygon buttonSpace;
    private boolean isFocused = false;
    private final Color focusedColor;
    private final Stroke focusedStroke;

    public PolygonButton(Polygon bs, Color c, Stroke s){
        buttonShape = bs;
        buttonSpace = bs;
        focusedColor=c;
        focusedStroke=s;
    }

    public PolygonButton(Polygon bs, Color c){
        this(bs, c, new BasicStroke(5.0f));
    }

    public PolygonButton(Polygon bs, Stroke s){
        this(bs, Color.BLUE, s);
    }

    public PolygonButton(Polygon bs){
        this(bs, Color.BLUE, new BasicStroke(5.0f));
    }

    public PolygonButton(Rectangle r){
        this(new Polygon(
                new int[]{r.x, r.x, r.x+r.width, r.x+r.width},
                new int[]{r.y, r.y+r.height, r.y+r.height, r.y},
                4
                )
        );
    }

    public boolean contains(Point p){
        return buttonSpace.contains(p);
    }

    public void setFocused(MouseEvent e){
        isFocused = buttonSpace.contains(e.getPoint());
    }

    public void resize(Dimension d){
        int[] newX = new int[buttonShape.npoints];
        int[] newY = new int[buttonShape.npoints];
        for(int i=0; i<buttonShape.npoints; i++){
            newX[i] = (int)(buttonShape.xpoints[i]*d.getWidth()/ Constants.ReferenceWindow.REF_WIN_W);
            newY[i] = (int)(buttonShape.ypoints[i]*d.getHeight()/ Constants.ReferenceWindow.REF_WIN_H);
        }
        buttonSpace = new Polygon(newX, newY, buttonShape.npoints);
    }

    public void draw(Graphics2D g2d){
        if(isFocused){
            g2d.setStroke(focusedStroke);
            g2d.setColor(focusedColor);
            g2d.draw(buttonShape);
        }
        else{
            g2d.setStroke(Constants.DEFAULT_STROKE);
            g2d.setColor(Constants.DEFAULT_COLOR);
            g2d.draw(buttonShape);
        }
        g2d.setColor(Constants.DEFAULT_COLOR);
        g2d.setStroke(Constants.DEFAULT_STROKE);
    }

    public void fill(Graphics2D g2d){
        draw(g2d);
        g2d.fill(buttonShape);
    }
}

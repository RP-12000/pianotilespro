package org.kelvinizer.support;

import org.kelvinizer.Constants;

import java.awt.*;

public class PolygonButton {
    private final Polygon buttonShape;
    private Polygon buttonSpace;

    public PolygonButton(Polygon bs){
        buttonShape = bs;
        buttonSpace = bs;
    }

    public PolygonButton(Rectangle r){
        buttonShape = new Polygon(
                new int[]{r.x, r.x, r.x+r.width, r.x+r.width},
                new int[]{r.y, r.y+r.height, r.y+r.height, r.y},
                4
        );
        buttonSpace = new Polygon(
                new int[]{r.x, r.x, r.x+r.width, r.x+r.width},
                new int[]{r.y, r.y+r.height, r.y+r.height, r.y},
                4
        );
    }

    public Polygon getButtonShape() {
        return buttonShape;
    }

    public boolean contains(Point p){
        return buttonSpace.contains(p);
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
}

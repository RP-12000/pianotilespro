package org.kelvinizer.shapes;

import org.kelvinizer.constants.General;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public abstract class CShape implements Drawable {
    private Color fillColor;
    private Color outlineColor;
    private double outlineThickness;

    public CShape(Color fillColor, Color outlineColor, double outlineThickness){
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
    }

    public CShape(){
        this(null, null, 0.0);
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public double getOutlineThickness() {
        return outlineThickness;
    }

    public void setOutlineThickness(double outlineThickness) {
        this.outlineThickness = outlineThickness;
    }

    @Override
    public void render(Graphics2D g2d){
        Shape s = toJShape();
        if(fillColor!=null) {
            g2d.setColor(fillColor);
            g2d.fill(s);
        }
        if(outlineColor!=null){
            g2d.setStroke(new BasicStroke((float)outlineThickness));
            g2d.setColor(outlineColor);
            g2d.draw(s);
        }
        g2d.setStroke(General.DEFAULT_STROKE);
        g2d.setColor(General.DEFAULT_COLOR);
    }

    public boolean contains(Point p){
        return toJShape().contains(p);
    }

    public abstract Shape toJShape();
    public abstract void scale(Dimension d);
}

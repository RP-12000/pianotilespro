package org.kelvinizer.support;

import org.kelvinizer.constants.General;

import java.awt.*;

public class CRect {
    private double x, y, width, height;
    private Point origin = new Point(0,0);
    private Color fillColor = null;
    private Color outlineColor = null;
    private double outlineThickness = 0.0;

    public CRect(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public CRect(double width, double height){
        this(0, 0, width, height);
    }

    public CRect(){
        this(0, 0);
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

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public void setOrigin(double x, double y){
        setOrigin(new Point((int)x, (int)y));
    }

    public void draw(Graphics2D g2d){
        if(outlineColor!=null){
            g2d.setStroke(new BasicStroke((float)outlineThickness));
            g2d.setColor(outlineColor);
            g2d.draw(toRect());
        }
        g2d.setStroke(General.DEFAULT_STROKE);
        if(fillColor!=null) {
            g2d.setColor(fillColor);
            g2d.fill(toRect());
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Rectangle toRect(){
        return new Rectangle((int)(x- origin.x), (int)(y-origin.y), (int)(width), (int)(height));
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
}
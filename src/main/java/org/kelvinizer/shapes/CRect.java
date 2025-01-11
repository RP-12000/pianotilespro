package org.kelvinizer.shapes;

import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.support.classes.Pair;

import java.awt.*;

public class CRect extends CShape {
    private double x, y, width, height;
    private Pair<Double, Double> origin;

    public CRect(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        origin = new Pair<>(width/2, height/2);
    }

    public CRect(double width, double height){
        this(0, 0, width, height);
    }

    public CRect(){
        this(0, 0);
    }

    public Pair<Double, Double> getOrigin() {
        return origin;
    }

    public void setOrigin(Pair<Double, Double> origin) {
        this.origin = origin;
    }

    public void setOrigin(double x, double y){
        setOrigin(new Pair<>(x, y));
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

    @Override
    public Rectangle toJShape(){
        return new Rectangle((int)(x- origin.first), (int)(y-origin.second), (int)(width), (int)(height));
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void scale(Dimension d){
        x = x / ReferenceWindow.REF_WIN_W * d.width;
        y = y / ReferenceWindow.REF_WIN_H * d.height;
        width = width / ReferenceWindow.REF_WIN_W * d.width;
        height = height / ReferenceWindow.REF_WIN_H * d.height;
    }
}
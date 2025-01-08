package org.kelvinizer.shapes;

import java.awt.*;

import static org.kelvinizer.constants.ReferenceWindow.*;

public class CTriangle extends CShape{
    private double x1, y1, x2, y2, x3, y3;

    public CTriangle(double x1, double y1, double x2, double y2, double x3, double y3){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public CTriangle(){
        this(0,0,0,0,0,0);
    }

    public void setFirstPoint(double x, double y){
        x1 = x;
        y1 = y;
    }

    public void setSecondPoint(double x, double y){
        x2 = x;
        y2 = y;
    }

    public void setThirdPoint(double x, double y){
        x3 = x;
        y3 = y;
    }

    @Override
    public Shape toJShape() {
        return new Polygon(
                new int[]{(int)x1, (int)x2, (int)x3},
                new int[]{(int)y1, (int)y2, (int)y3},
                3
        );
    }

    @Override
    public void scale(Dimension d){
        x1 = x1 / REF_WIN_W * d.width;
        y1 = y1 / REF_WIN_H * d.height;
        x2 = x2 / REF_WIN_W * d.width;
        y2 = y2 / REF_WIN_H * d.height;
        x3 = x3 / REF_WIN_W * d.width;
        y3 = y3 / REF_WIN_H * d.height;
    }

    public double getX1(){
        return x1;
    }

    public double getX2(){
        return x2;
    }

    public double getX3(){
        return x3;
    }

    public double getY1(){
        return y1;
    }

    public double getY2(){
        return y2;
    }

    public double getY3(){
        return y3;
    }
}
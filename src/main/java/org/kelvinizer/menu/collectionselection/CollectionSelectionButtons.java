package org.kelvinizer.menu.collectionselection;

import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CollectionSelectionButtons implements Scalable, Focusable {
    public final CTriangleButton moveLeft = new CTriangleButton();
    public final CTriangleButton moveRight = new CTriangleButton();

    private void setMoveLeft(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(60, 310);
        normal.setSecondPoint(60, 410);
        normal.setThirdPoint(30, 360);
        normal.setFillColor(Color.WHITE);
        moveLeft.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(60, 310);
        onFocus.setSecondPoint(60, 410);
        onFocus.setThirdPoint(30, 360);
        onFocus.setFillColor(Color.WHITE);
        onFocus.setOutlineColor(Color.BLUE);
        onFocus.setOutlineThickness(3.0);
        moveLeft.setOnFocus(onFocus);
    }

    private void setMoveRight(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(1020, 310);
        normal.setSecondPoint(1020, 410);
        normal.setThirdPoint(1050, 360);
        normal.setFillColor(Color.WHITE);
        moveRight.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(1020, 310);
        onFocus.setSecondPoint(1020, 410);
        onFocus.setThirdPoint(1050, 360);
        onFocus.setFillColor(Color.WHITE);
        onFocus.setOutlineColor(Color.BLUE);
        onFocus.setOutlineThickness(3.0);
        moveRight.setOnFocus(onFocus);
    }

    public CollectionSelectionButtons(){
        setMoveLeft();
        setMoveRight();
    }

    @Override
    public void scale(Dimension d) {
        moveLeft.scale(d);
        moveRight.scale(d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        moveLeft.setFocused(e);
        moveRight.setFocused(e);
    }
}
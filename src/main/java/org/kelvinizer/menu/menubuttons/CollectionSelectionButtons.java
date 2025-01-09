package org.kelvinizer.menu.menubuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CollectionSelectionButtons implements Scalable, Focusable {
    public final CRectButton back = new CRectButton();
    public final CRectButton settings = new CRectButton();
    public final CRectButton jacket = new CRectButton();
    public final CTriangleButton moveLeft = new CTriangleButton();
    public final CTriangleButton moveRight = new CTriangleButton();

    private void setBack(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(51, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        back.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(61, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        back.setOnFocus(onFocus);
    }

    private void setSettings(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1015, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        settings.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1005, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        settings.setOnFocus(onFocus);
    }

    private void setJacket(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(540, 320, 360, 360));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        jacket.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(540, 320, 360, 360));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        jacket.setOnFocus(onFocus);
    }

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
        setBack();
        setSettings();
        setJacket();
        setMoveLeft();
        setMoveRight();
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        settings.scale(d);
        jacket.scale(d);
        moveLeft.scale(d);
        moveRight.scale(d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        settings.setFocused(e);
        jacket.setFocused(e);
        moveLeft.setFocused(e);
        moveRight.setFocused(e);
    }
}
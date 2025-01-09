package org.kelvinizer.guibuttons;

import org.kelvinizer.buttons.RectangleButton;
import org.kelvinizer.buttons.TriangleButton;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Resizable;

import java.awt.*;

public class CollectionSelectionButtons implements Resizable {
    public final RectangleButton back = new RectangleButton();
    public final RectangleButton settings = new RectangleButton();
    public final RectangleButton jacket = new RectangleButton();
    public final TriangleButton moveLeft = new TriangleButton();
    public final TriangleButton moveRight = new TriangleButton();

    private void setBack(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(50, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        back.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(60, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        onFocus.getBounds().setOrigin(0,0);
        back.setOnFocus(onFocus);
    }

    private void setSettings(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1030, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        settings.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1020, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        settings.setOnFocus(onFocus);
    }

    private void setJacket(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1030, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        settings.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1020, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        settings.setOnFocus(onFocus);
    }

    private void setMoveLeft(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(100, 100);
        normal.setSecondPoint(100, 100);
        normal.setThirdPoint(100, 100);
        normal.setFillColor(Color.WHITE);
        moveLeft.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(100, 100);
        onFocus.setSecondPoint(100, 100);
        onFocus.setThirdPoint(100, 100);
        onFocus.setFillColor(Color.WHITE);
        moveLeft.setOnFocus(onFocus);
    }

    private void setMoveRight(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(100, 100);
        normal.setSecondPoint(100, 100);
        normal.setThirdPoint(100, 100);
        normal.setFillColor(Color.WHITE);
        moveRight.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(100, 100);
        onFocus.setSecondPoint(100, 100);
        onFocus.setThirdPoint(100, 100);
        onFocus.setFillColor(Color.WHITE);
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
    public void resize(Dimension d) {
        back.resize(d);
        settings.resize(d);
        jacket.resize(d);
        moveLeft.resize(d);
        moveRight.resize(d);
    }
}

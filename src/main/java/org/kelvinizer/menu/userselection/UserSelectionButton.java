package org.kelvinizer.menu.userselection;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.kelvinizer.constants.Control.*;

public class UserSelectionButton implements Scalable, Drawable, Focusable {
    public final CRectButton basic = new CRectButton();
    public final CRectButton medium = new CRectButton();
    public final CRectButton advanced = new CRectButton();
    public final CRectButton legendary = new CRectButton();

    public final CTriangleButton moveLeft = new CTriangleButton();
    public final CTriangleButton moveRight = new CTriangleButton();

    public final CRectButton select = new CRectButton();
    public final CRectButton back = new CRectButton();

    public final CRectButton addNewUser = new CRectButton();

    private void setLevelButton(CRectButton crb, String s, int x, Color c){
        BoundedString normal = new BoundedString(s, 24, x, 140, 80, 50);
        BoundedString onFocus = new BoundedString(s, 24, x, 140, 80, 50);
        BoundedString onSelect = new BoundedString(s, 24, x, 140, 80, 50);

        normal.getBounds().setOutlineThickness(1.0);
        normal.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(5.0);
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onSelect.getBounds().setOutlineThickness(3.0);
        onSelect.getBounds().setOutlineColor(Color.WHITE);
        onSelect.getBounds().setFillColor(c);

        crb.setNormal(normal);
        crb.setOnFocus(onFocus);
        crb.setOnSelection(onSelect);
    }

    private void setBack(){
        BoundedString normal = new BoundedString("", 0, 50, 50, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 60, 60, 120, 120);

        if(!back.setIcon("Back.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        back.setNormal(normal);
        back.setOnFocus(onFocus);
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

    private void setSelect(){
        BoundedString normal = new BoundedString("Select", 15, 540, 600, 200, 50);
        BoundedString onFocus = new BoundedString("Select", 15, 540, 600, 240, 60);
        BoundedString onSelect = new BoundedString("Selected", 15, 540, 600, 200, 50);

        normal.getBounds().setOutlineThickness(3.0);
        normal.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(5.0);
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onSelect.getBounds().setOutlineThickness(3.0);
        onSelect.getBounds().setOutlineColor(Color.GREEN);
        onSelect.setStringColor(Color.GREEN);

        select.setNormal(normal);
        select.setOnFocus(onFocus);
        select.setOnSelection(onSelect);
    }

    private void setAddNewUser(){
        BoundedString normal = new BoundedString("Add New User", 40, 540, 330, 600, 270);
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(3.0);
        addNewUser.setNormal(normal);

        BoundedString onFocus = new BoundedString("Add New User", 40, 540, 330, 600, 270);
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        addNewUser.setOnFocus(onFocus);
    }

    public UserSelectionButton(){
        setLevelButton(basic, "BS", 405, Color.GREEN);
        setLevelButton(medium, "MD", 495, Color.BLUE);
        setLevelButton(advanced, "AV", 585, Color.RED);
        setLevelButton(legendary, "LG", 675, Color.MAGENTA);
        setBack();
        setMoveLeft();
        setMoveRight();
        setSelect();
        setAddNewUser();
    }

    @Override
    public void render(Graphics2D g2d) {
        select.select(UserSelection.renderIndex==userIndex);
        basic.select(Selection.level.equals("BS"));
        medium.select(Selection.level.equals("MD"));
        advanced.select(Selection.level.equals("AV"));
        legendary.select(Selection.level.equals("LG"));
        back.render(g2d);
        if(UserSelection.renderIndex!=0){
            moveLeft.render(g2d);
        }
        if(UserSelection.renderIndex!=users.size()){
            select.render(g2d);
            moveRight.render(g2d);
            basic.render(g2d);
            medium.render(g2d);
            advanced.render(g2d);
            legendary.render(g2d);
        }
        else{
            addNewUser.render(g2d);
        }
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        select.setFocused(e);
        moveRight.setFocused(e);
        moveLeft.setFocused(e);
        basic.setFocused(e);
        medium.setFocused(e);
        advanced.setFocused(e);
        legendary.setFocused(e);
        addNewUser.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        select.scale(d);
        moveLeft.scale(d);
        moveLeft.scale(d);
        basic.scale(d);
        medium.scale(d);
        advanced.scale(d);
        legendary.scale(d);
        addNewUser.scale(d);
    }
}
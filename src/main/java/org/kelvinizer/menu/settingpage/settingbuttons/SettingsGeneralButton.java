package org.kelvinizer.menu.settingpage.settingbuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The {@code SettingsGeneralButton} class represents the general buttons used for settings
 * navigation and actions, such as the back button, and the move left and move right buttons.
 * Implements {@link Scalable} and {@link Focusable}.
 * @author Boyan Hu
 */
public class SettingsGeneralButton implements Scalable, Focusable {

    /** Button for navigating back in settings. */
    public final CRectButton back = new CRectButton();

    /** Button for moving left in settings. */
    public final CTriangleButton moveLeft = new CTriangleButton();

    /** Button for moving right in settings. */
    public final CTriangleButton moveRight = new CTriangleButton();

    /**
     * Sets up the appearance and behavior for the back button, including normal and focus states.
     */
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

    /**
     * Sets up the appearance and behavior for the move left button, including normal and focus states.
     */
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

    /**
     * Sets up the appearance and behavior for the move right button, including normal and focus states.
     */
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

    /**
     * Constructs a {@code SettingsGeneralButton} instance and initializes the back, move left,
     * and move right buttons by setting their appearance and behavior.
     */
    public SettingsGeneralButton(){
        setBack();
        setMoveLeft();
        setMoveRight();
    }

    /**
     * Sets the focused state for all buttons based on the mouse event.
     *
     * @param e the {@link MouseEvent} indicating the focus event
     */
    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        moveLeft.setFocused(e);
        moveRight.setFocused(e);
    }

    /**
     * Scales the buttons based on the new window dimensions.
     *
     * @param d the new dimensions of the window
     */
    @Override
    public void scale(Dimension d) {
        back.scale(d);
        moveLeft.scale(d);
        moveRight.scale(d);
    }
}
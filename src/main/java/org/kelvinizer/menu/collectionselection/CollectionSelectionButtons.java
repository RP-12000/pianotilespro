package org.kelvinizer.menu.collectionselection;

import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Represents the buttons used in the collection selection menu for navigating left and right.
 * Implements {@link Scalable} and {@link Focusable} to support responsive scaling and focus handling.
 * @author Boyan Hu
 */
public class CollectionSelectionButtons implements Scalable, Focusable {

    /** Button for moving to the previous collection. */
    public final CTriangleButton moveLeft = new CTriangleButton();

    /** Button for moving to the next collection. */
    public final CTriangleButton moveRight = new CTriangleButton();

    /**
     * Configures the appearance and behavior of the {@code moveLeft} button.
     * Sets its normal state and on-focus state using {@link CTriangle}.
     */
    private void setMoveLeft() {
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
     * Configures the appearance and behavior of the {@code moveRight} button.
     * Sets its normal state and on-focus state using {@link CTriangle}.
     */
    private void setMoveRight() {
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
     * Constructs a {@code CollectionSelectionButtons} instance and initializes the buttons
     * for navigating left and right.
     */
    public CollectionSelectionButtons() {
        setMoveLeft();
        setMoveRight();
    }

    /**
     * Scales the {@code moveLeft} and {@code moveRight} buttons based on the given dimensions.
     *
     * @param d the {@link Dimension} representing the new display size
     */
    @Override
    public void scale(Dimension d) {
        moveLeft.scale(d);
        moveRight.scale(d);
    }

    /**
     * Updates the focus state of the {@code moveLeft} and {@code moveRight} buttons
     * based on the mouse position.
     *
     * @param e the {@link MouseEvent} representing the mouse movement
     */
    @Override
    public void setFocused(MouseEvent e) {
        moveLeft.setFocused(e);
        moveRight.setFocused(e);
    }
}
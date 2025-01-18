package org.kelvinizer.menu;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents a collection of interactive selection buttons, including back, settings, play, and user options.
 * Implements {@link Scalable}, {@link Focusable}, and {@link Drawable} for scaling, focus management, and rendering.
 * @author Boyan Hu
 */
public class SelectionButtons implements Scalable, Focusable, Drawable {

    /** The "Back" button. */
    public final CRectButton back = new CRectButton();

    /** The "Settings" button. */
    public final CRectButton settings = new CRectButton();

    /** The "Play" button. */
    public final CRectButton play = new CRectButton();

    /** The "User" button. */
    public final CRectButton user = new CRectButton();

    /** The current state of the selection buttons. */
    public int state = -1;

    /** State indicating the exit action. */
    public static final int EXIT = 0;

    /** State indicating the back action. */
    public static final int BACK = 1;

    /** State indicating the settings action. */
    public static final int SETTINGS = 2;

    /** State indicating the user action. */
    public static final int USER = 3;

    /**
     * Constructs a {@code SelectionButtons} object and initializes the button styles and icons.
     */
    public SelectionButtons() {
        setBack();
        setSettings();
        setPlay();
        setUserButton();
    }

    /**
     * Configures the "Back" button's appearance and behavior.
     */
    private void setBack() {
        BoundedString normal = new BoundedString("", 0, 50, 50, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 60, 60, 120, 120);

        if (!back.setIcon("Back.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        back.setNormal(normal);
        back.setOnFocus(onFocus);
    }

    /**
     * Configures the "Settings" button's appearance and behavior.
     */
    private void setSettings() {
        BoundedString normal = new BoundedString("", 0, 1030, 50, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 1020, 60, 120, 120);

        if (!settings.setIcon("Settings.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        settings.setNormal(normal);
        settings.setOnFocus(onFocus);
    }

    /**
     * Configures the "Play" button's appearance and behavior.
     */
    private void setPlay() {
        BoundedString normal = new BoundedString("", 0, 1029, 669, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 1019, 659, 120, 120);

        if (!play.setIcon("Play.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        play.setNormal(normal);
        play.setOnFocus(onFocus);
    }

    /**
     * Configures the "User" button's appearance and behavior.
     */
    private void setUserButton() {
        BoundedString normal = new BoundedString("", 0, 50, 670, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 60, 660, 120, 120);

        if (!user.setIcon("User.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        user.setNormal(normal);
        user.setOnFocus(onFocus);
    }

    /**
     * Renders all buttons onto the provided graphics context.
     *
     * @param g2d the graphics context
     */
    @Override
    public void render(Graphics2D g2d) {
        back.render(g2d);
        settings.render(g2d);
        play.render(g2d);
        user.render(g2d);
    }

    /**
     * Updates the focus state of buttons based on mouse movement events.
     *
     * @param e the mouse event
     */
    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        settings.setFocused(e);
        play.setFocused(e);
        user.setFocused(e);
    }

    /**
     * Scales the buttons to fit the specified dimensions.
     *
     * @param d the target dimensions
     */
    @Override
    public void scale(Dimension d) {
        back.scale(d);
        settings.scale(d);
        play.scale(d);
        user.scale(d);
    }

    /**
     * Processes the current button focus state and updates the {@code state} field.
     */
    public void process() {
        if (back.isFocused()) {
            state = SelectionButtons.BACK;
        }
        if (settings.isFocused()) {
            state = SelectionButtons.SETTINGS;
        }
        if (play.isFocused()) {
            state = SelectionButtons.EXIT;
        }
        if (user.isFocused()) {
            state = SelectionButtons.USER;
        }
    }

    /**
     * Adds key bindings to an {@link AnimatablePanel} for interacting with buttons.
     *
     * @param a the animatable panel to add key bindings to
     */
    public void addKeyBindings(AnimatablePanel a) {
        a.addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = SelectionButtons.EXIT;
            }
        });
        a.addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = SelectionButtons.SETTINGS;
            }
        });
        a.addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = SelectionButtons.BACK;
            }
        });
        a.addKeyBinding(KeyEvent.VK_U, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = SelectionButtons.USER;
            }
        });
    }
}
package org.kelvinizer.menu.userselection;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.constants.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents the user selection panel where users can select or create profiles and navigate options.
 * Extends {@link AnimatablePanel} to support animations and panel transitions.
 * This panel manages user interactions with buttons, keyboard input, and rendering logic.
 * @author Boyan Hu
 */
public class UserSelection extends AnimatablePanel {

    /** The user selection button manager. */
    private final UserSelectionButton usb = new UserSelectionButton();

    /** The user selection text renderer. */
    private final UserSelectionText ust = new UserSelectionText();

    /** Index of the currently rendered user. */
    public static int renderIndex;

    /**
     * Constructs a {@code UserSelection} panel, initializing key bindings and setting the initial render index.
     */
    public UserSelection() {
        super(500);
        renderIndex = Control.userIndex;

        // Add key bindings for user navigation and actions
        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderIndex = Math.min(renderIndex + 1, Control.users.size());
            }
        });
        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderIndex = Math.max(renderIndex - 1, 0);
            }
        });
        addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (renderIndex == Control.users.size()) {
                    Control.users.add(new User());
                } else {
                    Control.userIndex = renderIndex;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }

    /**
     * Handles mouse movement events to update button focus states.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        usb.setFocused(e);
    }

    /**
     * Handles mouse click events for button interactions.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (usb.select.isFocused()) {
            Control.userIndex = renderIndex;
        }
        if (usb.back.isFocused()) {
            exit();
        }
        if (renderIndex != 0 && usb.moveLeft.isFocused()) {
            renderIndex--;
        }
        if (renderIndex != Control.users.size()) {
            if (usb.basic.isFocused()) {
                Selection.level = "BS";
            } else if (usb.medium.isFocused()) {
                Selection.level = "MD";
            } else if (usb.advanced.isFocused()) {
                Selection.level = "AV";
            } else if (usb.legendary.isFocused()) {
                Selection.level = "LG";
            } else if (usb.moveRight.isFocused()) {
                renderIndex = Math.min(renderIndex + 1, Control.users.size());
            }
        } else {
            if (usb.addNewUser.isFocused()) {
                Control.users.add(new User());
            }
        }
    }

    /**
     * Scales the user selection buttons based on the given dimensions.
     *
     * @param d the target dimensions
     */
    @Override
    public void scale(Dimension d) {
        usb.scale(d);
    }

    /**
     * Renders the user selection buttons and text onto the graphics context.
     *
     * @param g2d the graphics context
     */
    @Override
    public void render(Graphics2D g2d) {
        usb.render(g2d);
        ust.render(g2d);
    }

    /**
     * Transitions to the next panel in the application flow.
     */
    @Override
    public void toNextPanel() {
        Control.panel_index -= 4;
    }
}
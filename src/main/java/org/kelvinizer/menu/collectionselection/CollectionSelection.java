package org.kelvinizer.menu.collectionselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.menu.SelectionButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.kelvinizer.constants.Selection.collections;

/**
 * Handles the collection selection menu functionality, including rendering, navigation,
 * and user interactions. This class extends {@link AnimatablePanel} to support animated
 * transitions and provides mouse and keyboard controls for interacting with the menu.
 * @author Boyan Hu
 */
public class CollectionSelection extends AnimatablePanel {

    /** Selection buttons for menu navigation and actions. */
    private final SelectionButtons sb = new SelectionButtons();

    /** Buttons specific to the collection selection menu. */
    private final CollectionSelectionButtons csb = new CollectionSelectionButtons();

    /** Text elements displayed within the collection selection menu. */
    private final CollectionSelectionText cst = new CollectionSelectionText();

    /**
     * Constructs a {@code CollectionSelection} instance, initializes key bindings,
     * and ensures song indices are properly mapped for all collections.
     */
    public CollectionSelection() {
        super();

        // Initialize song index for each collection
        for (int i = 0; i < collections.size(); i++) {
            if (!Selection.songIndex.containsKey(collections.getSelectionString(i))) {
                Selection.songIndex.put(collections.getSelectionString(i), 0);
            }
        }

        // Add key bindings for navigation
        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collections.moveForward();
            }
        });
        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collections.moveBackward();
            }
        });

        // Add key bindings for selection buttons
        sb.addKeyBindings(this);
    }

    /**
     * Handles mouse movement events, updating focus for relevant buttons.
     *
     * @param e the {@link MouseEvent} representing the mouse movement
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        csb.setFocused(e);
        sb.setFocused(e);
    }

    /**
     * Handles mouse click events, processing button actions or collection navigation.
     *
     * @param e the {@link MouseEvent} representing the mouse click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (csb.moveRight.isFocused() && collections.notAtEnd()) {
            collections.moveForward();
        } else if (csb.moveLeft.isFocused() && collections.notAtBeginning()) {
            collections.moveBackward();
        }
        sb.process();
    }

    /**
     * Handles mouse wheel events for navigating through collections.
     *
     * @param e the {@link MouseWheelEvent} representing the mouse wheel movement
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        collections.move(e);
    }

    /**
     * Adjusts the scale of UI components to match the current display dimensions.
     *
     * @param d the {@link Dimension} representing the new display size
     */
    @Override
    public void scale(Dimension d) {
        csb.scale(d);
        sb.scale(d);
    }

    /**
     * Renders the collection selection menu, including the jacket image, navigation buttons,
     * and text elements, on the provided graphics context.
     *
     * @param g2d the {@link Graphics2D} context used for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        Selection.collectionIndex = collections.getMenuIndex();
        Selection.collectionDir = collections.getSelectionString();

        // Set and render the current selection name
        cst.selectionName.setString(collections.getSelectionString().replace('_', ' '));
        cst.selectionName.render(g2d);

        // Render navigation buttons if applicable
        if (collections.notAtBeginning()) {
            csb.moveLeft.render(g2d);
        }
        if (collections.notAtEnd()) {
            csb.moveRight.render(g2d);
        }

        // Render jacket image or placeholder if null
        if (collections.getSelectionJacket() != null) {
            g2d.drawImage(collections.getSelectionJacket(), 240, 180, 600, 360, this);
        } else {
            cst.nullJacket.render(g2d);
        }

        // Render selection buttons and text
        sb.render(g2d);
        cst.render(g2d);

        // Handle state transitions if applicable
        if (sb.state != -1) {
            exit();
        }
    }

    /**
     * Transitions to the next panel based on the state of the selection buttons.
     */
    @Override
    public void toNextPanel() {
        switch (sb.state) {
            case SelectionButtons.EXIT: Control.panel_index = 2;break;
            case SelectionButtons.BACK: Control.panel_index = 0;break;
            case SelectionButtons.SETTINGS: Control.panel_index = -Control.panel_index;break;
            case SelectionButtons.USER: Control.panel_index += 4;break;
        }
    }
}
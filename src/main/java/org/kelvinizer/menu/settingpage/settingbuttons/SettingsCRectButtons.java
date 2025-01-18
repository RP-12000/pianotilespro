package org.kelvinizer.menu.settingpage.settingbuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

/**
 * The {@code SettingsCRectButtons} class handles the functionality and rendering of the
 * setting buttons used to toggle user-specific settings such as sync, FC/AP (Full Combo/ All Perfect) hints, and hand hints.
 * Implements {@link Scalable}, {@link Drawable}, and {@link Focusable}.
 * @author Boyan Hu
 */
public class SettingsCRectButtons implements Scalable, Drawable, Focusable {

    /** Button for enabling sync. */
    public final CRectButton syncOn = new CRectButton();

    /** Button for disabling sync. */
    public final CRectButton syncOff = new CRectButton();

    /** Button for enabling FC/AP hints. */
    public final CRectButton FCAP_On = new CRectButton();

    /** Button for disabling FC/AP hints. */
    public final CRectButton FCAP_Off = new CRectButton();

    /** Button for enabling hand hints. */
    public final CRectButton handHintOn = new CRectButton();

    /** Button for disabling hand hints. */
    public final CRectButton handHintOff = new CRectButton();

    /**
     * Initializes the "On" and "Off" buttons for each setting (sync, FC/AP, and hand hints),
     * setting their appearance and behavior for normal, focused, and selected states.
     *
     * @param on the "On" button
     * @param off the "Off" button
     * @param y the vertical position of the buttons on the screen
     */
    private void setOnAndOffIndicator(CRectButton on, CRectButton off, double y){
        BoundedString normalOn = new BoundedString("On", 50);
        normalOn.setBounds(new CRect(540, y, 160, 96));
        normalOn.getBounds().setOutlineColor(Color.WHITE);
        normalOn.getBounds().setOutlineThickness(1.0);
        normalOn.setStyle(Font.PLAIN);
        on.setNormal(normalOn);

        BoundedString onFocusOn = new BoundedString("On", 50);
        onFocusOn.setBounds(new CRect(540, y, 180, 108));
        onFocusOn.getBounds().setOutlineColor(Color.BLUE);
        onFocusOn.getBounds().setOutlineThickness(5.0);
        on.setOnFocus(onFocusOn);

        BoundedString onSelectionOn = new BoundedString("On", 50);
        onSelectionOn.setBounds(new CRect(540, y, 160, 96));
        onSelectionOn.getBounds().setOutlineColor(Color.GREEN);
        onSelectionOn.getBounds().setOutlineThickness(3.0);
        onSelectionOn.setStringColor(Color.GREEN);
        on.setOnSelection(onSelectionOn);

        BoundedString normal = new BoundedString("Off", 50);
        normal.setBounds(new CRect(780, y, 160, 96));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        off.setNormal(normal);

        BoundedString onFocus = new BoundedString("Off", 50);
        onFocus.setBounds(new CRect(780, y, 180, 108));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        off.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("Off", 50);
        onSelection.setBounds(new CRect(780, y, 160, 96));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        off.setOnSelection(onSelection);
    }

    /**
     * Constructs a {@code SettingsCRectButtons} instance and initializes the buttons
     * for the settings: sync, FCAP hints, and hand hints.
     */
    public SettingsCRectButtons(){
        setOnAndOffIndicator(syncOn, syncOff, 240);
        setOnAndOffIndicator(FCAP_On, FCAP_Off, 420);
        setOnAndOffIndicator(handHintOn, handHintOff, 600);
    }

    /**
     * Scales the buttons and their associated elements based on the new window dimensions.
     *
     * @param d the new dimension of the window
     */
    @Override
    public void scale(Dimension d) {
        syncOn.scale(d);
        syncOff.scale(d);
        FCAP_On.scale(d);
        FCAP_Off.scale(d);
        handHintOn.scale(d);
        handHintOff.scale(d);
    }

    /**
     * Renders the buttons based on the current user settings (sync, FCAP, and hand hints).
     * The button states will be updated to reflect whether the features are enabled or disabled.
     *
     * @param g2d the {@link Graphics2D} object responsible for drawing
     */
    @Override
    public void render(Graphics2D g2d) {
        // Update button states based on the user's settings
        if(users.get(userIndex).syncEnabled){
            syncOff.select(false);
            syncOn.select(true);
        }
        else{
            syncOff.select(true);
            syncOn.select(false);
        }

        if(users.get(userIndex).FCAPHintEnabled){
            FCAP_Off.select(false);
            FCAP_On.select(true);
        }
        else{
            FCAP_Off.select(true);
            FCAP_On.select(false);
        }

        if(users.get(userIndex).handHintEnabled){
            handHintOff.select(false);
            handHintOn.select(true);
        }
        else{
            handHintOff.select(true);
            handHintOn.select(false);
        }

        // Render buttons
        syncOn.render(g2d);
        syncOff.render(g2d);
        FCAP_On.render(g2d);
        FCAP_Off.render(g2d);
        handHintOn.render(g2d);
        handHintOff.render(g2d);
    }

    /**
     * Sets the focus state for all buttons based on the mouse event.
     *
     * @param e the {@link MouseEvent} indicating the focus event
     */
    @Override
    public void setFocused(MouseEvent e) {
        syncOn.setFocused(e);
        syncOff.setFocused(e);
        FCAP_On.setFocused(e);
        FCAP_Off.setFocused(e);
        handHintOn.setFocused(e);
        handHintOff.setFocused(e);
    }
}
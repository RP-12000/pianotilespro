package org.kelvinizer.menu.settingpage.settingbuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.SlidingButton;
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
 * The {@code SettingsSlidingButton} class represents the sliding buttons used in the settings menu
 * to control values such as tolerance and music delay.
 * Implements {@link Scalable}, {@link Drawable}, and {@link Focusable}.
 * @author Boyan Hu
 */
public class SettingsSlidingButton implements Scalable, Drawable, Focusable {

    /** The sliding button to adjust tolerance. */
    public final SlidingButton tolerance = new SlidingButton(0, 160, users.get(userIndex).tolerance);

    /** The sliding button to adjust music delay. */
    public final SlidingButton musicDelay = new SlidingButton(-600, 600, users.get(userIndex).MUSIC_DIFFERENCE);

    /**
     * Sets up the appearance and behavior for a sliding button, including its base, slider, and verdict.
     *
     * @param sb the {@link SlidingButton} to be configured
     * @param y the vertical position for the sliding button
     */
    private void setSlidingButton(SlidingButton sb, double y){
        CRectButton slider = new CRectButton();

        // Normal state for the slider
        BoundedString normal = new BoundedString("", 0);
        normal.setBounds(new CRect(50, 50));
        normal.getBounds().setFillColor(Color.CYAN);
        slider.setNormal(normal);

        // Focused state for the slider
        BoundedString onFocus = new BoundedString("", 0);
        onFocus.setBounds(new CRect(50, 50));
        onFocus.getBounds().setFillColor(Color.CYAN);
        onFocus.getBounds().setOutlineColor(Color.YELLOW);
        onFocus.getBounds().setOutlineThickness(5.0);
        slider.setOnFocus(onFocus);

        // Base rectangle for the slider
        CRect base = new CRect(640, y+60, 400, 50);
        base.setFillColor(Color.WHITE);

        // Verdict display string
        BoundedString verdict = new BoundedString("", 25, 640, y);

        sb.setBaseAndSlider(base, slider);
        sb.setVerdict(verdict);
    }

    /**
     * Constructs a {@code SettingsSlidingButton} instance and initializes the tolerance and music delay
     * sliding buttons by setting their appearance and behavior.
     */
    public SettingsSlidingButton(){
        setSlidingButton(musicDelay, 210);
        setSlidingButton(tolerance, 390);
    }

    /**
     * Renders the sliding buttons, including displaying their current values.
     *
     * @param g2d the {@link Graphics2D} object responsible for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        musicDelay.setVerdictString((int)musicDelay.getCurrentVal() +" ms");
        musicDelay.render(g2d);
        tolerance.setVerdictString((int)tolerance.getCurrentVal() +" ms");
        tolerance.render(g2d);
    }

    /**
     * Sets the focused state for both sliding buttons based on the mouse event.
     *
     * @param e the {@link MouseEvent} indicating the focus event
     */
    @Override
    public void setFocused(MouseEvent e) {
        tolerance.setFocused(e);
        musicDelay.setFocused(e);
    }

    /**
     * Scales the sliding buttons based on the new window dimensions.
     *
     * @param d the new dimensions of the window
     */
    @Override
    public void scale(Dimension d) {
        tolerance.scale(d);
        musicDelay.scale(d);
    }
}
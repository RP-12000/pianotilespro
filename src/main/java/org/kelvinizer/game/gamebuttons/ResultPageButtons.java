package org.kelvinizer.game.gamebuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.dynamic.DynamicCRectButton;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The {@code ResultPageButtons} class represents the buttons (Back and Restart) on the result page of a game.
 * Each button is dynamic and has motion effects and focus behaviors.
 * The buttons are initialized with appropriate icons and visual effects when focused.
 * @author Boyan Hu
 */
public class ResultPageButtons implements Scalable, Focusable {

    /** The back button */
    public final DynamicCRectButton back = new DynamicCRectButton();

    /** The restart button */
    public final DynamicCRectButton restart = new DynamicCRectButton();

    /**
     * Initializes the back button with its icon, bounds, and visual effects when focused.
     * Additionally, adds horizontal motion to the button.
     */
    private void initBack(){
        CRectButton button = new CRectButton();

        BoundedString normal = new BoundedString("", 0, 36, 36, 72, 72);
        BoundedString onFocus = new BoundedString("", 0, 42, 42, 84, 84);

        if(!button.setIcon("Back.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        button.setNormal(normal);
        button.setOnFocus(onFocus);
        back.setCRectButton(button);
        back.addHorizontalMotion(new Motion(0, 1.5, -36, 36, 1));
    }

    /**
     * Initializes the restart button with its icon, bounds, and visual effects when focused.
     * Additionally, adds horizontal motion to the button.
     */
    private void initRestart(){
        CRectButton button = new CRectButton();

        BoundedString normal = new BoundedString("", 0, 1044, 36, 72, 72);
        BoundedString onFocus = new BoundedString("", 0, 1032, 48, 84, 84);

        if(!button.setIcon("Restart.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        button.setNormal(normal);
        button.setOnFocus(onFocus);
        restart.setCRectButton(button);
        restart.addHorizontalMotion(new Motion(0, 1.5, 1116, 1044, 1));
    }

    /**
     * Constructs the {@code ResultPageButtons} by initializing the back and restart buttons,
     * and adding them to the provided {@code DynamicMotionManager}.
     *
     * @param dmm the {@code DynamicMotionManager} to which the buttons will be added
     */
    public ResultPageButtons(DynamicMotionManager dmm){
        initBack();
        initRestart();
        dmm.addDynamicObject(back);
        dmm.addDynamicObject(restart);
    }

    /**
     * Sets the focus state for the back and restart buttons based on the given {@code MouseEvent}.
     *
     * @param e the {@code MouseEvent} used to determine which button is focused
     */
    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        restart.setFocused(e);
    }

    /**
     * Scales both the back and restart buttons based on the given {@code Dimension}.
     *
     * @param d the {@code Dimension} used to scale the buttons
     */
    @Override
    public void scale(Dimension d) {
        back.scale(d);
        restart.scale(d);
    }
}
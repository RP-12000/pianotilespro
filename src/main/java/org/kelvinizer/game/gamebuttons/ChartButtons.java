package org.kelvinizer.game.gamebuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The {@code ChartButtons} class represents a collection of interactive buttons (Pause, Play, Restart, Back)
 * used in a game interface. Each button is represented as a {@link CRectButton}, and it supports scaling and focus functionality.
 * The buttons are initialized with appropriate icons and visual effects when focused.
 * @author Boyan Hu
 */
public class ChartButtons implements Scalable, Focusable {

    /** The pause button */
    public final CRectButton pause = new CRectButton();

    /** The play button */
    public final CRectButton play = new CRectButton();

    /** The restart button */
    public final CRectButton restart = new CRectButton();

    /** The back button */
    public final CRectButton back = new CRectButton();

    /**
     * Initializes the pause button with its icon, bounds, and visual effects when focused.
     */
    private void initPause(){
        BoundedString normal = new BoundedString("", 0, 90, 54, 40, 40);
        BoundedString onFocus = new BoundedString("", 0, 90, 54, 48, 48);

        if(!pause.setIcon("Pause.jpg")){
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        pause.setNormal(normal);
        pause.setOnFocus(onFocus);
    }

    /**
     * Initializes the play button with its icon, bounds, and visual effects when focused.
     */
    private void initPlay(){
        BoundedString normal = new BoundedString("", 0, 600, 360, 40, 40);
        BoundedString onFocus = new BoundedString("", 0, 600, 360, 48, 48);

        if(!play.setIcon("Play.jpg")){
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        play.setNormal(normal);
        play.setOnFocus(onFocus);
    }

    /**
     * Initializes the restart button with its icon, bounds, and visual effects when focused.
     */
    private void initRestart(){
        BoundedString normal = new BoundedString("", 0, 540, 360, 40, 40);
        BoundedString onFocus = new BoundedString("", 0, 540, 360, 48, 48);

        if(!restart.setIcon("Restart.jpg")){
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        restart.setNormal(normal);
        restart.setOnFocus(onFocus);
    }

    /**
     * Initializes the back button with its icon, bounds, and visual effects when focused.
     */
    private void initBack(){
        BoundedString normal = new BoundedString("", 0, 480, 360, 40, 40);
        BoundedString onFocus = new BoundedString("", 0, 480, 360, 48, 48);

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
     * Constructs the {@code ChartButtons} and initializes all the buttons (Pause, Play, Restart, Back).
     */
    public ChartButtons(){
        initBack();
        initPlay();
        initPause();
        initRestart();
    }

    /**
     * Sets the focus state for all the buttons based on the given {@code MouseEvent}.
     *
     * @param e the {@code MouseEvent} used to determine which button is focused
     */
    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        play.setFocused(e);
        pause.setFocused(e);
        restart.setFocused(e);
    }

    /**
     * Scales all the buttons based on the given {@code Dimension}.
     *
     * @param d the {@code Dimension} used to scale the buttons
     */
    @Override
    public void scale(Dimension d) {
        back.scale(d);
        play.scale(d);
        pause.scale(d);
        restart.scale(d);
    }
}
package org.kelvinizer.game.gamebuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ChartButtons implements Scalable, Focusable {
    public final CRectButton pause = new CRectButton();
    public final CRectButton play = new CRectButton();
    public final CRectButton restart = new CRectButton();
    public final CRectButton back = new CRectButton();

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

    public ChartButtons(){
        initBack();
        initPlay();
        initPause();
        initRestart();
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        play.setFocused(e);
        pause.setFocused(e);
        restart.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        play.scale(d);
        pause.scale(d);
        restart.scale(d);
    }
}
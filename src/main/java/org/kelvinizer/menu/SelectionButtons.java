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

public class SelectionButtons implements Scalable, Focusable, Drawable {
    public final CRectButton back = new CRectButton();
    public final CRectButton settings = new CRectButton();
    public final CRectButton play = new CRectButton();
    public final CRectButton user = new CRectButton();
    public int state = -1;
    public static final int EXIT = 0;
    public static final int BACK = 1;
    public static final int SETTINGS = 2;
    public static final int USER = 3;

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

    private void setSettings(){
        BoundedString normal = new BoundedString("", 0, 1030, 50, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 1020, 60, 120, 120);

        if(!settings.setIcon("Settings.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        settings.setNormal(normal);
        settings.setOnFocus(onFocus);
    }

    private void setPlay(){
        BoundedString normal = new BoundedString("", 0, 1029, 669, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 1019, 659, 120, 120);

        if(!play.setIcon("Play.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        play.setNormal(normal);
        play.setOnFocus(onFocus);
    }

    private void setUserButton(){
        BoundedString normal = new BoundedString("", 0, 50, 670, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 60, 660, 120, 120);

        if(!user.setIcon("User.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        user.setNormal(normal);
        user.setOnFocus(onFocus);
    }

    public SelectionButtons(){
        setBack();
        setSettings();
        setPlay();
        setUserButton();
    }

    @Override
    public void render(Graphics2D g2d) {
        back.render(g2d);
        settings.render(g2d);
        play.render(g2d);
        user.render(g2d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        settings.setFocused(e);
        play.setFocused(e);
        user.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        settings.scale(d);
        play.scale(d);
        user.scale(d);
    }

    public void process(){
        if(back.isFocused()){
            state = SelectionButtons.BACK;
        }
        if(settings.isFocused()){
            state = SelectionButtons.SETTINGS;
        }
        if(play.isFocused()){
            state = SelectionButtons.EXIT;
        }
        if(user.isFocused()){
            state = SelectionButtons.USER;
        }
    }

    public void addKeyBindings(AnimatablePanel a){
        a.addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state=SelectionButtons.EXIT;
            }
        });
        a.addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state=SelectionButtons.SETTINGS;
            }
        });
        a.addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state=SelectionButtons.BACK;
            }
        });
        a.addKeyBinding(KeyEvent.VK_U, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state=SelectionButtons.USER;
            }
        });

    }
}

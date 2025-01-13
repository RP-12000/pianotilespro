package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.menu.menubuttons.SettingsButtons;
import org.kelvinizer.constants.Control;
import org.kelvinizer.menu.menutext.SettingsText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Settings extends AnimatablePanel {
    private final SettingsButtons sb = new SettingsButtons();
    private final SettingsText st = new SettingsText();

    public Settings(){
        super();
        addKeyBinding(KeyEvent.VK_A, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.isAutoplay = !Control.isAutoplay;
            }
        });
        addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.syncEnabled = !Control.syncEnabled;
            }
        });
        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sb.musicDelay.moveSlider(-1);
            }
        });
        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sb.musicDelay.moveSlider(1);
            }
        });
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }

    @Override
    public void mouseMoved(MouseEvent e){
        sb.setFocused(e);
    }

    @Override
    public void mouseDragged(MouseEvent e){
        sb.musicDelay.moveSlider(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(sb.autoplayMode.isFocused()){
            Control.isAutoplay = true;
        }
        else if(sb.normalMode.isFocused()){
            Control.isAutoplay = false;
        }
        else if(sb.syncOn.isFocused()){
            Control.syncEnabled=true;
        }
        else if(sb.syncOff.isFocused()){
            Control.syncEnabled=false;
        }
        else if(sb.back.isFocused()){
            exit();
        }
    }

    @Override
    public void scale(Dimension d){
        sb.scale(d);
    }

    @Override
    public void render(Graphics2D g2d){
        if(Control.isAutoplay){
            sb.normalMode.select(false);
            sb.autoplayMode.select(true);
        }
        else{
            sb.normalMode.select(true);
            sb.autoplayMode.select(false);
        }
        if(Control.syncEnabled){
            sb.syncOff.select(false);
            sb.syncOn.select(true);
        }
        else{
            sb.syncOff.select(true);
            sb.syncOn.select(false);
        }
        sb.render(g2d);
        st.render(g2d);
    }

    @Override
    public void toNextPanel(){
        Control.MUSIC_DIFFERENCE = (int) sb.musicDelay.getCurrentVal();
        Control.panel_index = -Control.panel_index;
    }
}
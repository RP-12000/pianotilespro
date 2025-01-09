package org.kelvinizer.gui;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.guibuttons.SettingsButtons;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.buttons.PolygonButton;
import org.kelvinizer.constants.General;
import org.kelvinizer.buttons.RectangleButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Settings extends AnimatablePanel {
    private final SettingsButtons sb = new SettingsButtons();

    public Settings(){
        super();
        addKeyBinding(KeyEvent.VK_A, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                General.isAutoplay = !General.isAutoplay;
            }
        });
        addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                General.syncEnabled = !General.syncEnabled;
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
        sb.back.setFocused(e);
        sb.normalMode.setFocused(e);
        sb.autoplayMode.setFocused(e);
        sb.syncOn.setFocused(e);
        sb.syncOff.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(sb.autoplayMode.isFocused()){
            General.isAutoplay = true;
        }
        else if(sb.normalMode.isFocused()){
            General.isAutoplay = false;
        }
        else if(sb.syncOn.isFocused()){
            General.syncEnabled=true;
        }
        else if(sb.syncOff.isFocused()){
            General.syncEnabled=false;
        }
        else if(sb.back.isFocused()){
            exit();
        }
    }

    @Override
    public void resizeButtons(Dimension d){
        sb.resize(d);
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(General.isAutoplay){
            sb.normalMode.select(false);
            sb.autoplayMode.select(true);
        }
        else{
            sb.normalMode.select(true);
            sb.autoplayMode.select(false);
        }
        if(General.syncEnabled){
            sb.syncOff.select(false);
            sb.syncOn.select(true);
        }
        else{
            sb.syncOff.select(true);
            sb.syncOn.select(false);
        }
        sb.render(g2d);
    }

    @Override
    public void toNextPanel(){
        General.panel_index -= General.numPanels;
    }
}
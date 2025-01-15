package org.kelvinizer.menu.settingpage;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsCRectButtons;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsGeneralButton;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsSlidingButton;
import org.kelvinizer.menu.settingpage.settingbuttons.UserSettingButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Settings extends AnimatablePanel {
    private final SettingsCRectButtons scb = new SettingsCRectButtons();
    private final SettingsGeneralButton sgb = new SettingsGeneralButton();
    private final SettingsSlidingButton ssb = new SettingsSlidingButton();
    private final UserSettingButtons usb = new UserSettingButtons();
    private final SettingsText st = new SettingsText();
    public static int page = 1;

    public Settings(){
        super();
        addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==1){
                    Control.syncEnabled = !Control.syncEnabled;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_H, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==1){
                    Control.handHintEnabled = !Control.handHintEnabled;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_F, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==1){
                    Control.FCAPHintEnabled = !Control.FCAPHintEnabled;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_E, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==3){
                    usb.exportUser();
                }
            }
        });
        addKeyBinding(KeyEvent.VK_I, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==3){
                    usb.importUser();
                }
            }
        });
        addKeyBinding(KeyEvent.VK_PAGE_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page=Math.min(3, page+1);
            }
        });
        addKeyBinding(KeyEvent.VK_PAGE_UP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page=Math.max(1, page-1);
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
        scb.setFocused(e);
        sgb.setFocused(e);
        ssb.setFocused(e);
        usb.setFocused(e);
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(page==2){
            ssb.musicDelay.moveSlider(e);
            ssb.frameRate.moveSlider(e);
            ssb.tolerance.moveSlider(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        switch (page){
            case 1 ->{
                if(scb.syncOn.isFocused()){
                    Control.syncEnabled=true;
                }
                else if(scb.syncOff.isFocused()){
                    Control.syncEnabled=false;
                }
                else if(scb.FCAP_On.isFocused()){
                    Control.FCAPHintEnabled=true;
                }
                else if(scb.FCAP_Off.isFocused()){
                    Control.FCAPHintEnabled=false;
                }
                else if(scb.handHintOn.isFocused()){
                    Control.handHintEnabled=true;
                }
                else if(scb.handHintOff.isFocused()){
                    Control.handHintEnabled=false;
                }
            }
            case 3 ->{
                if(usb.exportUser.isFocused()){
                    usb.exportUser();
                }
                else if(usb.importUser.isFocused()){
                    usb.importUser();
                }
            }
        }
        if(sgb.back.isFocused()){
            exit();
        }
        else if(sgb.moveLeft.isFocused()){
            page=Math.max(1, page-1);
        }
        else if(sgb.moveRight.isFocused()){
            page=Math.min(3, page+1);
        }
    }

    @Override
    public void scale(Dimension d){
        scb.scale(d);
        ssb.scale(d);
        sgb.scale(d);
        usb.scale(d);
    }

    @Override
    public void render(Graphics2D g2d){
        sgb.back.render(g2d);
        if(page!=1){
            sgb.moveLeft.render(g2d);
        }
        if(page!=3){
            sgb.moveRight.render(g2d);
        }
        switch (page){
            case 1 -> scb.render(g2d);
            case 2 -> ssb.render(g2d);
            case 3 -> usb.render(g2d);
        }
        st.updateText(page);
        st.render(g2d);
        Control.MUSIC_DIFFERENCE = (int) ssb.musicDelay.getCurrentVal();
        Control.newFPS = (int) ssb.frameRate.getCurrentVal();
        Control.tolerance = (int) ssb.tolerance.getCurrentVal();
    }

    @Override
    public void toNextPanel(){
        Control.panel_index = -Control.panel_index;
    }
}
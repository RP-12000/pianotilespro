package org.kelvinizer.menu.settingpage;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsCRectButtons;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsGeneralButton;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsSlidingButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

public class Settings extends AnimatablePanel {
    private final SettingsCRectButtons scb = new SettingsCRectButtons();
    private final SettingsGeneralButton sgb = new SettingsGeneralButton();
    private final SettingsSlidingButton ssb = new SettingsSlidingButton();
    private final SettingsText st = new SettingsText();
    public static int page = 1;

    public Settings(){
        super();
        addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==1){
                    users.get(userIndex).syncEnabled = !users.get(userIndex).syncEnabled;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_H, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==1){
                    users.get(userIndex).handHintEnabled = !users.get(userIndex).handHintEnabled;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_F, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(page==1){
                    users.get(userIndex).FCAPHintEnabled = !users.get(userIndex).FCAPHintEnabled;
                }
            }
        });
        addKeyBinding(KeyEvent.VK_PAGE_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page=Math.min(2, page+1);
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
        if (page == 1) {
            if (scb.syncOn.isFocused()) {
                users.get(userIndex).syncEnabled = true;
            } else if (scb.syncOff.isFocused()) {
                users.get(userIndex).syncEnabled = false;
            } else if (scb.FCAP_On.isFocused()) {
                users.get(userIndex).FCAPHintEnabled = true;
            } else if (scb.FCAP_Off.isFocused()) {
                users.get(userIndex).FCAPHintEnabled = false;
            } else if (scb.handHintOn.isFocused()) {
                users.get(userIndex).handHintEnabled = true;
            } else if (scb.handHintOff.isFocused()) {
                users.get(userIndex).handHintEnabled = false;
            }
        }
        if(sgb.back.isFocused()){
            exit();
        }
        else if(sgb.moveLeft.isFocused()){
            page=Math.max(1, page-1);
        }
        else if(sgb.moveRight.isFocused()){
            page=Math.min(2, page+1);
        }
    }

    @Override
    public void scale(Dimension d){
        scb.scale(d);
        ssb.scale(d);
        sgb.scale(d);
    }

    @Override
    public void render(Graphics2D g2d){
        sgb.back.render(g2d);
        if(page==1){
            sgb.moveRight.render(g2d);
            scb.render(g2d);
        }
        if(page==2){
            sgb.moveLeft.render(g2d);
            ssb.render(g2d);
        }
        st.updateText(page);
        st.render(g2d);
        users.get(userIndex).MUSIC_DIFFERENCE = (int) ssb.musicDelay.getCurrentVal();
        users.get(userIndex).tolerance = (int) ssb.tolerance.getCurrentVal();
    }

    @Override
    public void toNextPanel(){
        Control.panel_index = -Control.panel_index;
    }
}
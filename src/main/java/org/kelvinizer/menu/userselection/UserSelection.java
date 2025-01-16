package org.kelvinizer.menu.userselection;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.constants.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UserSelection extends AnimatablePanel {
    private final UserSelectionButton usb = new UserSelectionButton();
    private final UserSelectionText ust = new UserSelectionText();
    public static int renderIndex;

    public UserSelection(){
        super(500);
        renderIndex = Control.userIndex;
        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderIndex = Math.min(renderIndex+1, Control.users.size());
            }
        });
        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderIndex = Math.max(renderIndex-1, 0);
            }
        });
        addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(renderIndex==Control.users.size()){
                    Control.users.add(new User());
                }
                else{
                    Control.userIndex = renderIndex;
                }
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
        usb.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(usb.select.isFocused()){
            Control.userIndex = renderIndex;
        }
        if(usb.back.isFocused()){
            exit();
        }
        if(renderIndex!=0){
            if(usb.moveLeft.isFocused()){
                renderIndex--;
            }
        }
        if(renderIndex!=Control.users.size()){
            if(usb.basic.isFocused()){
                Selection.level="BS";
            }
            else if(usb.medium.isFocused()){
                Selection.level="MD";
            }
            else if(usb.advanced.isFocused()){
                Selection.level="AV";
            }
            else if(usb.legendary.isFocused()){
                Selection.level="LG";
            }
            else if(usb.moveRight.isFocused()){
                renderIndex = Math.min(renderIndex+1, Control.users.size());
            }
        }
        else{
            if(usb.addNewUser.isFocused()){
                Control.users.add(new User());
            }
        }
    }

    @Override
    public void scale(Dimension d){
        usb.scale(d);
    }

    @Override
    public void render(Graphics2D g2d){
        usb.render(g2d);
        ust.render(g2d);
    }

    @Override
    public void toNextPanel(){
        Control.panel_index-=4;
    }
}
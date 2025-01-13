package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.constants.Control;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.menu.menubuttons.CollectionSelectionButtons;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.JacketMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CollectionSelection extends AnimatablePanel {
    private final JacketMenu collections = new JacketMenu("Chart", Selection.collectionIndex);
    private boolean goBack = false;
    private boolean toSettings = false;

    private final CollectionSelectionButtons csb = new CollectionSelectionButtons();
    private final BoundedString emptyFolder = new BoundedString("Nothing is here QAQ", 50, 540, 360);
    private final BoundedString nullJacket = new BoundedString("No jacket preview available", 15, 540, 330, 360, 360);
    private final BoundedString selectionName = new BoundedString("", 0, 540, 620, 200, 50);

    public CollectionSelection(){
        super();
        selectionName.getBounds().setOutlineColor(Color.WHITE);
        selectionName.getBounds().setOutlineThickness(2.0);
        selectionName.setMaxStringSize(50);
        nullJacket.setStyle(Font.ITALIC);
        nullJacket.getBounds().setOutlineColor(Color.WHITE);
        nullJacket.getBounds().setOutlineThickness(1.0);
        for(int i=0; i<collections.size(); i++){
            if(!Selection.songIndex.containsKey(collections.getSelectionString(i))){
                Selection.songIndex.put(collections.getSelectionString(i), 0);
            }
        }
        if(!collections.isEmpty()){
            addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    collections.moveForward();
                }
            });
            addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    collections.moveBackward();
                }
            });
            addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exit();
                }
            });
        }
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack=true;
                exit();
            }
        });
        addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toSettings = true;
                exit();
            }
        });
    }

    @Override
    public void mouseMoved(MouseEvent e){
        csb.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(!collections.isEmpty()){
            if(csb.moveRight.isFocused() && !collections.atEnd()){
                collections.moveForward();
            }
            else if(csb.moveLeft.isFocused() && !collections.atBeginning()){
                collections.moveBackward();
            }
            else if(csb.jacket.isFocused()){
                exit();
            }
        }
        if(csb.back.isFocused()){
            goBack = true;
            exit();
        }
        if(csb.settings.isFocused()){
            toSettings = true;
            exit();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        collections.move(e);
    }

    @Override
    public void scale(Dimension d){
        csb.scale(d);
    }

    @Override
    public void render(Graphics2D g2d){
        if(collections.isEmpty()){
            emptyFolder.render(g2d);
        }
        else{
            selectionName.setString(collections.getSelectionString());
            selectionName.render(g2d);
            if(!collections.atBeginning()){
                csb.moveLeft.render(g2d);
            }
            if(!collections.atEnd()){
                csb.moveRight.render(g2d);
            }
            csb.jacket.render(g2d);
            if(collections.getSelectionJacket()!=null){
                g2d.drawImage(collections.getSelectionJacket(), 360, 150, 360, 360, this);
            }
            else{
                nullJacket.render(g2d);
            }
        }
        csb.back.render(g2d);
        csb.settings.render(g2d);
    }

    @Override
    public void toNextPanel() {
        Selection.collectionIndex = collections.getMenuIndex();
        if(goBack){
            Control.panel_index = 0;
        }
        else if(toSettings){
            Control.panel_index = -Control.panel_index;
        }
        else{
            Selection.collectionDir = collections.getSelectionString();
            Control.panel_index = 2;
        }
    }
}
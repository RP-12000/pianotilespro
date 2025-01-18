package org.kelvinizer.menu.collectionselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.menu.SelectionButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.kelvinizer.constants.Selection.collections;

public class CollectionSelection extends AnimatablePanel {
    private final SelectionButtons sb = new SelectionButtons();
    private final CollectionSelectionButtons csb = new CollectionSelectionButtons();
    private final CollectionSelectionText cst = new CollectionSelectionText();

    public CollectionSelection(){
        super();
        for(int i=0; i<collections.size(); i++){
            if(!Selection.songIndex.containsKey(collections.getSelectionString(i))){
                Selection.songIndex.put(collections.getSelectionString(i), 0);
            }
        }
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
        sb.addKeyBindings(this);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        csb.setFocused(e);
        sb.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(csb.moveRight.isFocused() && collections.notAtEnd()){
            collections.moveForward();
        }
        else if(csb.moveLeft.isFocused() && collections.notAtBeginning()){
            collections.moveBackward();
        }
        sb.process();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        collections.move(e);
    }

    @Override
    public void scale(Dimension d){
        csb.scale(d);
        sb.scale(d);
    }

    @Override
    public void render(Graphics2D g2d){
        Selection.collectionIndex = collections.getMenuIndex();
        Selection.collectionDir = collections.getSelectionString();
        cst.selectionName.setString(collections.getSelectionString().replace('_', ' '));
        cst.selectionName.render(g2d);
        if(collections.notAtBeginning()){
            csb.moveLeft.render(g2d);
        }
        if(collections.notAtEnd()){
            csb.moveRight.render(g2d);
        }
        if(collections.getSelectionJacket()!=null){
            g2d.drawImage(collections.getSelectionJacket(), 240, 180, 600, 360, this);
        }
        else{
            cst.nullJacket.render(g2d);
        }
        sb.render(g2d);
        cst.render(g2d);
        if(sb.state!=-1){
            exit();
        }
    }

    @Override
    public void toNextPanel() {
        switch(sb.state){
            case SelectionButtons.EXIT -> Control.panel_index = 2;
            case SelectionButtons.BACK -> Control.panel_index = 0;
            case SelectionButtons.SETTINGS -> Control.panel_index = -Control.panel_index;
            case SelectionButtons.USER -> Control.panel_index+=4;
        }
    }
}
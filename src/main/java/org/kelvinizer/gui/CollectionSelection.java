package org.kelvinizer.gui;

import org.kelvinizer.constants.General;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.guibuttons.CollectionSelectionButtons;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.JacketMenu;
import org.kelvinizer.buttons.PolygonButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CollectionSelection extends AnimatablePanel {
    private final JacketMenu collections = new JacketMenu("Chart", Selection.collectionIndex, 50);
    private boolean goBack = false;
    private boolean toSettings = false;

    private final CollectionSelectionButtons csb = new CollectionSelectionButtons();

    private final PolygonButton previous = new PolygonButton(
            new Polygon(new int[]{60, 60, 30}, new int[]{310, 410, 360}, 3)
    );
    private final PolygonButton next = new PolygonButton(
            new Polygon(new int[]{1020, 1020, 1050}, new int[]{310, 410, 360}, 3)
    );
    private final PolygonButton jacket = new PolygonButton(
            new Rectangle(360, 140, 360, 360)
    );

    public CollectionSelection(){
        super();
        collections.setBounds(new CRect(540, 620, 200, 50));
        collections.setOutlineColor(Color.WHITE);
        collections.setOutlineThickness(2.0);
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
        if(!collections.atBeginning()){
            previous.setFocused(e);
        }
        if(!collections.atEnd()){
            next.setFocused(e);
        }
        jacket.setFocused(e);
        csb.back.setFocused(e);
        csb.settings.setFocused(e);
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
    public void resizeButtons(Dimension d){
        csb.resize(d);
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(collections.isEmpty()){
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            g2d.drawString("Nothing is in here QAQ\n",540, 360);
        }
        else{
            collections.drawSelectionString(g2d);
            if(!collections.atBeginning()){
                csb.moveLeft.render(g2d);
            }
            if(!collections.atEnd()){
                csb.moveRight.render(g2d);
            }
            csb.jacket.render(g2d);
            if(collections.getSelectionJacket()!=null){
                g2d.drawImage(collections.getSelectionJacket(), 360, 140, 360, 360, this);
            }
            else{
                g2d.setFont(new Font("Arial", Font.ITALIC, 15));
                g2d.drawString("No jacket preview available", 450, 330);
            }
        }
        csb.back.render(g2d);
        csb.settings.render(g2d);
    }

    @Override
    public void toNextPanel() {
        Selection.collectionIndex = collections.getMenuIndex();
        if(goBack){
            General.panel_index = 0;
        }
        else if(toSettings){
            General.panel_index += General.numPanels;
        }
        else{
            Selection.collectionDir = collections.getSelectionString();
            General.panel_index = 2;
        }
    }
}
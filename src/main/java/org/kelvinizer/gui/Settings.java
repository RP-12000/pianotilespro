package org.kelvinizer.gui;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.support.BoundedString;
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
    private final RectangleButton normal = new RectangleButton();
    private final RectangleButton autoplay = new RectangleButton();
    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );

    public Settings(){
        super();
        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                General.isAutoplay = !General.isAutoplay;
            }
        });
        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                General.isAutoplay = !General.isAutoplay;
            }
        });

        BoundedString temp = new BoundedString("Normal", 50, 200, 100, 300, 100);
        temp.setVerticalWhiteSpace(30);
        temp.setHorizontalWhiteSpace(50);
        temp.getBounds().setOutlineColor(Color.WHITE);
        temp.getBounds().setOutlineThickness(2.0);
        normal.setNormalMode(temp.clone());

        temp.getBounds().setOutlineColor(Color.BLUE);
        temp.getBounds().setOutlineThickness(5.0);
        normal.setOnFocus(temp.clone());

        temp.getBounds().setOutlineColor(Color.GREEN);
        temp.getBounds().setOutlineThickness(2.0);
        temp.setStringColor(Color.GREEN);
        normal.setOnSelection(temp.clone());

        temp.setBounds(new CRect(700, 100, 300, 100));
        temp.setString("Autoplay");
        temp.setVerticalWhiteSpace(30);
        temp.setHorizontalWhiteSpace(50);
        temp.getBounds().setOutlineColor(Color.WHITE);
        temp.getBounds().setOutlineThickness(2.0);
        autoplay.setNormalMode(temp.clone());

        temp.getBounds().setOutlineColor(Color.BLUE);
        temp.getBounds().setOutlineThickness(5.0);
        autoplay.setOnFocus(temp.clone());

        temp.getBounds().setOutlineColor(Color.GREEN);
        temp.getBounds().setOutlineThickness(2.0);
        temp.setStringColor(Color.GREEN);
        autoplay.setOnSelection(temp.clone());
    }

    @Override
    public void mouseMoved(MouseEvent e){
        autoplay.setFocused(e);
        normal.setFocused(e);
        back.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(autoplay.isFocused()){
            General.isAutoplay = true;
        }
        else if(normal.isFocused()){
            General.isAutoplay = false;
        }
        else if(back.contains(e.getPoint())){
            exit();
        }
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(General.isAutoplay){
            autoplay.select(true);
            normal.select(false);
        }
        else{
            autoplay.select(false);
            normal.select(true);
        }
        normal.draw(g2d);
        autoplay.draw(g2d);
        back.draw(g2d);
    }

    @Override
    public void toNextPanel(){
        General.panel_index -= General.numPanels;
    }
}
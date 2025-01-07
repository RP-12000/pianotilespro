package org.kelvinizer.gui;

import org.kelvinizer.constants.General;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.support.JacketMenu;
import org.kelvinizer.support.PolygonButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CollectionSelection extends AnimatablePanel {
    private final JacketMenu collections = new JacketMenu("Chart", Selection.collectionIndex);
    private boolean goBack = false;
    private boolean toSettings = false;

    private final Font NULL_FOLDER_FONT = new Font("Arial", Font.BOLD, 25);
    private final Font MENU_FONT = new Font("Arial", Font.BOLD, 50);

    private final PolygonButton previous = new PolygonButton(
            new Polygon(new int[]{60, 60, 30}, new int[]{310, 410, 360}, 3)
    );
    private final PolygonButton next = new PolygonButton(
            new Polygon(new int[]{1020, 1020, 1050}, new int[]{310, 410, 360}, 3)
    );
    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );
    private final PolygonButton jacket = new PolygonButton(
            new Rectangle(360, 140, 360, 360)
    );
    private final PolygonButton settings = new PolygonButton(
            new Rectangle((int) ReferenceWindow.REF_WIN_W-100, 0, 100, 100)
    );

    public CollectionSelection(){
        super();
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
        settings.setFocused(e);
        back.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(!collections.isEmpty()){
            if(next.contains(e.getPoint()) && !collections.atEnd()){
                collections.moveForward();
            }
            else if(previous.contains((e.getPoint())) && !collections.atBeginning()){
                collections.moveBackward();
            }
            else if(jacket.contains(e.getPoint())){
                exit();
            }
        }
        if(back.contains(e.getPoint())){
            goBack = true;
            exit();
        }
        if(settings.contains(e.getPoint())){
            toSettings = true;
            exit();
        }
    }

    @Override
    public void resizeButtons(Dimension d){
        previous.resize(d);
        next.resize(d);
        back.resize(d);
        settings.resize(d);
        jacket.resize(d);
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(collections.isEmpty()){
            g2d.setColor(Color.WHITE);
            g2d.setFont(NULL_FOLDER_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    "Nothing is in here QAQ\n",
                    540 - (metrics.stringWidth("Nothing is in here QAQ\n") / 2),
                    360 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
        }
        else{
            g2d.setColor(Color.WHITE);
            g2d.setFont(MENU_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    collections.getSelectionString(),
                    540 - (metrics.stringWidth(collections.getSelectionString()) / 2),
                    600 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5.0f));
            g2d.drawRect(
                    540 - (metrics.stringWidth(collections.getSelectionString()) / 2) - 5,
                    600 - ((metrics.getAscent() + metrics.getDescent()) / 2) - 5,
                    metrics.stringWidth(collections.getSelectionString()) + 10,
                    metrics.getAscent() + metrics.getDescent()+ 10
            );
            g2d.setStroke(new BasicStroke(1.0f));
            if(!collections.atBeginning()){
                previous.fill(g2d);
            }
            if(!collections.atEnd()){
                next.fill(g2d);
            }
            jacket.draw(g2d);
            if(collections.getSelectionJacket()!=null){
                g2d.drawImage(collections.getSelectionJacket(), 360, 140, 360, 360, this);
            }
            else{
                g2d.setFont(new Font("Arial", Font.ITALIC, 15));
                metrics = g2d.getFontMetrics();
                g2d.drawString(
                        "No jacket preview available",
                        540 - (metrics.stringWidth("No jacket preview available") / 2),
                        320 + ((metrics.getAscent() - metrics.getDescent()) / 2)
                );
            }
        }
        settings.draw(g2d);
        back.draw(g2d);
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
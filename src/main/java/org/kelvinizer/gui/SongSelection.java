package org.kelvinizer.gui;

import org.kelvinizer.Constants;
import org.kelvinizer.Constants.*;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.gamewindow.Song;
import org.kelvinizer.support.JacketMenu;
import org.kelvinizer.support.PolygonButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SongSelection extends AnimatablePanel {
    private final JacketMenu menu = new JacketMenu("Chart/"+Constants.Selection.collectionDir, Constants.Selection.songIndex);
    private final ArrayList<Song> songs = new ArrayList<>();
    private boolean goBack = false;
    private boolean toSettings = false;
    private boolean isValid;

    private final Font NULL_FOLDER_FONT = new Font("Arial", Font.BOLD, 25);
    private final Font MENU_FONT = new Font("Arial", Font.BOLD, 50);

    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );
    private final PolygonButton settings = new PolygonButton(
            new Rectangle((int) Constants.ReferenceWindow.REF_WIN_W-100, 0, 100, 100)
    );
    private final PolygonButton play = new PolygonButton(
            new Rectangle((int) Constants.ReferenceWindow.REF_WIN_W-100, (int) Constants.ReferenceWindow.REF_WIN_H-100, 100, 100)
    );
    private final PolygonButton basic = new PolygonButton(
            new Rectangle()
    );
    private final PolygonButton medium = new PolygonButton(
            new Rectangle()
    );
    private final PolygonButton advanced = new PolygonButton(
            new Rectangle()
    );
    private final PolygonButton legendary = new PolygonButton(
            new Rectangle()
    );
    private final PolygonButton moveUp = new PolygonButton(
            new Polygon()
    );
    private final PolygonButton moveDown = new PolygonButton(
            new Polygon()
    );

    public SongSelection(){
        super();
        check();
        if(!menu.isEmpty() && isValid){
            addKeyBinding(KeyEvent.VK_UP, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.moveForward();
                }
            });
            addKeyBinding(KeyEvent.VK_DOWN, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.moveBackward();
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

    private void check(){
        for(int i=0; i<menu.size(); i++){
            try{
                songs.add(new Song("Chart/"+Constants.Selection.collectionDir+"/"+menu.getSelectionString(i)));
            } catch (RuntimeException | IOException e) {
                isValid=false;
                songs.clear();
                return;
            }
        }
        isValid=true;
    }

    @Override
    public void resizeButtons(Dimension d){
        back.resize(d);
        settings.resize(d);
        play.resize(d);
        basic.resize(d);
        medium.resize(d);
        advanced.resize(d);
        legendary.resize(d);
        moveUp.resize(d);
        moveDown.resize(d);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        back.setFocused(e);
        settings.setFocused(e);
        play.setFocused(e);
        basic.setFocused(e);
        medium.setFocused(e);
        if(songs.get(menu.getMenuIndex()).hasLG()){
            legendary.setFocused(e);
        }
        if(!menu.atBeginning()){
            moveUp.setFocused(e);
        }
        if(!menu.atEnd()){
            moveDown.setFocused(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(back.contains(e.getPoint())){
            goBack = true;
            exit();
        }
        else if(settings.contains(e.getPoint())){
            toSettings = true;
            exit();
        }
        if(!menu.isEmpty() && isValid){
            if(play.contains(e.getPoint())){
                exit();
            }
            else if(basic.contains(e.getPoint())){
                Selection.level = "BS";
            }
            else if(medium.contains(e.getPoint())){
                Selection.level = "MD";
            }
            else if(advanced.contains(e.getPoint())){
                Selection.level = "AV";
            }
            else if(songs.get(menu.getMenuIndex()).hasLG() && legendary.contains(e.getPoint())){
                Selection.level = "LG";
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        menu.move(e);
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(menu.isEmpty()){
            g2d.setColor(Color.WHITE);
            g2d.setFont(NULL_FOLDER_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    "Nothing is in here QAQ\n",
                    540 - (metrics.stringWidth("Nothing is in here QAQ\n") / 2),
                    360 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
        }
        else if(!isValid){
            g2d.setColor(Color.WHITE);
            g2d.setFont(NULL_FOLDER_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    "Collection Corrupted QAQ\n",
                    540 - (metrics.stringWidth("Nothing is in here QAQ\n") / 2),
                    360 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
        }
        else{

        }
    }

    @Override
    public void toNextPanel(){
        if(goBack){
            Constants.panel_index = 1;
        }
        else if(toSettings){
            Constants.panel_index += 10;
        }
        else{
            Selection.songDir = menu.getSelectionString();
            Constants.panel_index = 2;
        }
    }
}
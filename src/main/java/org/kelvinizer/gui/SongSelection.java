package org.kelvinizer.gui;

import org.kelvinizer.constants.General;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.gamewindow.Song;
import org.kelvinizer.support.CRect;
import org.kelvinizer.support.JacketMenu;
import org.kelvinizer.support.PolygonButton;
import org.kelvinizer.support.Triple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SongSelection extends AnimatablePanel {
    private final JacketMenu songs = new JacketMenu("Chart/"+ Selection.collectionDir, Selection.songIndex, 20);
    private final ArrayList<Song> songData = new ArrayList<>();
    private boolean goBack = false;
    private boolean toSettings = false;
    private boolean isValid;

    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );
    private final PolygonButton settings = new PolygonButton(
            new Rectangle((int) ReferenceWindow.REF_WIN_W-113, 0, 100, 100)
    );
    private final PolygonButton play = new PolygonButton(
            new Rectangle((int) ReferenceWindow.REF_WIN_W-113, (int) ReferenceWindow.REF_WIN_H-135, 100, 100)
    );
    private final PolygonButton basic = new PolygonButton(
            new Rectangle(800, 500, 30, 30)
    );
    private final PolygonButton medium = new PolygonButton(
            new Rectangle(520, 500, 30, 30)
    );
    private final PolygonButton advanced = new PolygonButton(
            new Rectangle(570, 500, 30, 30)
    );
    private final PolygonButton legendary = new PolygonButton(
            new Rectangle(620, 500, 30, 30)
    );
    private final PolygonButton moveUp = new PolygonButton(
            new Polygon(new int[]{100, 100, 200}, new int[]{100, 200, 200}, 3)
    );
    private final PolygonButton moveDown = new PolygonButton(
            new Polygon(new int[]{300, 300, 400}, new int[]{300, 400, 400}, 3)
    );

    public SongSelection(){
        super();
        check();
        songs.setTextBoundary(new CRect(200, 360, 150, 50));
        songs.getTextBoundary().setOrigin(
                songs.getTextBoundary().getWidth()/2,
                songs.getTextBoundary().getHeight()/2
        );
        songs.getTextBoundary().setOutlineColor(Color.WHITE);
        songs.getTextBoundary().setOutlineThickness(3.0);
        if(!songs.isEmpty() && isValid){
            addKeyBinding(KeyEvent.VK_UP, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    songs.moveBackward();
                }
            });
            addKeyBinding(KeyEvent.VK_DOWN, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    songs.moveForward();
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
        for(int i = 0; i< songs.size(); i++){
            try{
                songData.add(new Song("Chart/"+ Selection.collectionDir+"/"+ songs.getSelectionString(i)));
            } catch (RuntimeException | IOException e) {
                isValid=false;
                songData.clear();
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
        if(!songs.isEmpty() && isValid){
            play.setFocused(e);
            basic.setFocused(e);
            medium.setFocused(e);
            advanced.setFocused(e);
            if(songData.get(songs.getMenuIndex()).hasLG()){
                legendary.setFocused(e);
            }
            if(!songs.atBeginning()){
                moveUp.setFocused(e);
            }
            if(!songs.atEnd()){
                moveDown.setFocused(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(back.contains(e.getPoint())){
            goBack = true;
            exit();
        }
        if(settings.contains(e.getPoint())){
            toSettings = true;
            exit();
        }
        if(!songs.isEmpty() && isValid){
            if(moveUp.contains(e.getPoint())){
                songs.moveForward();
                if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
            if(moveDown.contains(e.getPoint())){
                songs.moveBackward();
                if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
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
            else if(songData.get(songs.getMenuIndex()).hasLG() && legendary.contains(e.getPoint())){
                Selection.level = "LG";
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        songs.move(e);
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        settings.draw(g2d);
        back.draw(g2d);
        if(songs.isEmpty()){
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("Nothing is in here QAQ\n", 540, 360);
        }
        else if(!isValid){
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("Collection Corrupted QAQ\n", 540, 360);
        }
        else{
            if(songs.getSelectionJacket()!=null){
                g2d.drawImage(songs.getSelectionJacket(), 520, 180, 480, 300, this);
            }
            play.draw(g2d);
            songs.getTextBoundary().draw(g2d);
            if(!songs.atBeginning()){
                moveUp.fill(g2d);
            }
            if(!songs.atEnd()){
                moveDown.fill(g2d);
            }
            basic.draw(g2d);
            medium.draw(g2d);
            advanced.draw(g2d);
            if(songData.get(songs.getMenuIndex()).hasLG()){
                legendary.draw(g2d);
            }
            Triple<Font, Float, Float> params = songs.getTextRenderParams(g2d, "Arial", Font.PLAIN);
            g2d.setFont(params.first);
            g2d.drawString(songs.getSelectionString(), params.second, params.third);
        }
    }

    @Override
    public void toNextPanel(){
        if(goBack){
            General.panel_index = 1;
        }
        else if(toSettings){
            General.panel_index += General.numPanels;
        }
        else{
            Selection.songDir = songs.getSelectionString();
            General.panel_index = 2;
        }
    }
}
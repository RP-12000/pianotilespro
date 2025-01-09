package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.menu.menubuttons.SongSelectionButtons;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.JacketMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SongSelection extends AnimatablePanel {
    private final JacketMenu songs = new JacketMenu(
            "Chart/"+ Selection.collectionDir,
            Selection.songIndex.get(Selection.collectionDir),
            20
    );
    private final ArrayList<Song> songData = new ArrayList<>();
    private boolean goBack = false;
    private boolean toSettings = false;

    private final SongSelectionButtons ssb = new SongSelectionButtons();

    public SongSelection(){
        super();
        songs.setBounds(new CRect(300, 350, 400, 60));
        songs.setOutlineColor(Color.WHITE);
        songs.setOutlineThickness(5.0);
        for(int i = 0; i< songs.size(); i++){
            try{
                songData.add(new Song("Chart/"+ Selection.collectionDir+"/"+ songs.getSelectionString(i)));
            } catch (RuntimeException | IOException e) {
                Selection.isValidCollection=false;
            }
        }
        if(!songs.isEmpty() && Selection.isValidCollection){
            addKeyBinding(KeyEvent.VK_UP, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    songs.moveBackward();
                    if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                        Selection.level = "AV";
                    }
                }
            });
            addKeyBinding(KeyEvent.VK_DOWN, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    songs.moveForward();
                    if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                        Selection.level = "AV";
                    }
                }
            });
            addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (Selection.level){
                        case "BS" -> Selection.level = "MD";
                        case "MD" -> Selection.level = "AV";
                        case "AV" -> Selection.level = "LG";
                    }
                    if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                        Selection.level = "AV";
                    }
                }
            });
            addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (Selection.level){
                        case "LG" -> Selection.level = "AV";
                        case "AV" -> Selection.level = "MD";
                        case "MD" -> Selection.level = "BS";
                    }
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
    public void scale(Dimension d){
        ssb.scale(d);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        ssb.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(ssb.back.isFocused()){
            goBack = true;
            exit();
        }
        if(ssb.settings.isFocused()){
            toSettings = true;
            exit();
        }
        if(!songs.isEmpty() && Selection.isValidCollection){
            if(ssb.moveUp.isFocused()){
                songs.moveBackward();
                if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
            if(ssb.moveDown.isFocused()){
                songs.moveForward();
                if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
            if(ssb.play.isFocused()){
                exit();
            }
            else if(ssb.basic.isFocused()){
                Selection.level = "BS";
            }
            else if(ssb.medium.isFocused()){
                Selection.level = "MD";
            }
            else if(ssb.advanced.isFocused()){
                Selection.level = "AV";
            }
            else if(songData.get(songs.getMenuIndex()).hasLG() && ssb.legendary.isFocused()){
                Selection.level = "LG";
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        songs.move(e);
        if(!songData.get(songs.getMenuIndex()).hasLG() && Selection.level.equals("LG")){
            Selection.level = "AV";
        }
    }

    @Override
    public void render(Graphics2D g2d){
        ssb.settings.render(g2d);
        ssb.back.render(g2d);
        if(songs.isEmpty()){
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("Nothing is in here QAQ\n", 540, 360);
        }
        else if(!Selection.isValidCollection){
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("Collection Corrupted QAQ\n", 540, 360);
        }
        else{
            if(songs.getSelectionJacket()!=null){
                g2d.drawImage(songs.getSelectionJacket(), 520, 180, 480, 300, this);
            }
            else{
                g2d.drawRect(520, 180, 480, 300);
                g2d.setFont(new Font("Arial", Font.ITALIC, 15));
                g2d.drawString("No jacket preview available", 675, 340);
            }
            ssb.play.render(g2d);
            songs.drawSelectionString(g2d);
            if(!songs.atBeginning()){
                ssb.moveUp.render(g2d);
            }
            if(!songs.atEnd()){
                ssb.moveDown.render(g2d);
            }
            ssb.renderLevels(g2d, songData.get(songs.getMenuIndex()));
        }
    }

    @Override
    public void toNextPanel(){
        Selection.songIndex.put(Selection.collectionDir, songs.getMenuIndex());
        if(goBack){
            Control.panel_index = 1;
            return;
        }
        if(toSettings){
            Control.panel_index += Control.numPanels;
        }
        else{
            Selection.songDir = songs.getSelectionString();
            Control.panel_index = 3;
        }
    }
}
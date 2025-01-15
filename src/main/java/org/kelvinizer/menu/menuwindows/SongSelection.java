package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.menu.menubuttons.SongSelectionButtons;
import org.kelvinizer.menu.menutext.SongSelectionText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static org.kelvinizer.constants.Selection.*;

public class SongSelection extends AnimatablePanel {
    private boolean goBack = false;
    private boolean toSettings = false;
    private boolean startEndScene = false;

    private final SongSelectionButtons ssb = new SongSelectionButtons();
    private final SongSelectionText sst = new SongSelectionText();

    public SongSelection(){
        super();
        addKeyBinding(KeyEvent.VK_UP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songs.get(collectionDir).moveBackward();
                songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
                if(!getSongData().hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
        });
        addKeyBinding(KeyEvent.VK_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songs.get(collectionDir).moveForward();
                songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
                if(!getSongData().hasLG() && Selection.level.equals("LG")){
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
                if(!getSongData().hasLG() && Selection.level.equals("LG")){
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
                exit(4000);
            }
        });
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
                songs.get(collectionDir).moveBackward();
                songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
                if(!getSongData().hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
            if(ssb.moveDown.isFocused()){
                songs.get(collectionDir).moveForward();
                songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
                if(!getSongData().hasLG() && Selection.level.equals("LG")){
                    Selection.level = "AV";
                }
            }
            if(ssb.play.isFocused()){
                exit(4000);
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
            else if(getSongData().hasLG() && ssb.legendary.isFocused()){
                Selection.level = "LG";
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        songs.get(collectionDir).move(e);
        if(!getSongData().hasLG() && Selection.level.equals("LG")){
            Selection.level = "AV";
        }
    }

    @Override
    public void onDisappearance(Graphics2D g2d){
        if(!goBack && !toSettings){
            if(!startEndScene){
                sst.setEndStrings(getSongData(), songs.get(collectionDir).getSelectionJacket());
                sst.dm.activate();
                startEndScene = true;
            }
            sst.dm.render(g2d);
        }
        else{
            super.onDisappearance(g2d);
        }
    }

    @Override
    public void render(Graphics2D g2d){
        ssb.settings.render(g2d);
        ssb.back.render(g2d);
        songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
        if(songs.isEmpty()){
            sst.emptyFolder.render(g2d);
        }
        else if(!Selection.isValidCollection){
            sst.corruptedFolder.render(g2d);
        }
        else{
            if(songs.get(collectionDir).getSelectionJacket()!=null){
                g2d.drawImage(songs.get(collectionDir).getSelectionJacket(), 520, 180, 480, 300, this);
            }
            else{
                sst.nullJacket.render(g2d);
            }
            ssb.play.render(g2d);
            sst.updateSelectionStrings(songs.get(collectionDir), songData.get(collectionDir));
            sst.renderCurrent(g2d);
            if(!songs.get(collectionDir).atBeginning()){
                ssb.moveUp.render(g2d);
                sst.renderPrevious(g2d);
            }
            if(!songs.get(collectionDir).atEnd()){
                ssb.moveDown.render(g2d);
                sst.renderNext(g2d);
            }
            ssb.renderLevels(g2d, getSongData());
        }
    }

    @Override
    public void toNextPanel(){
        songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
        if(goBack){
            Control.panel_index = 1;
            return;
        }
        if(toSettings){
            Control.panel_index = -Control.panel_index;
        }
        else{
            songDir = songs.get(collectionDir).getSelectionString();
            songJacket = songs.get(collectionDir).getSelectionJacket();
            chartConstant = getSongData().getCharterData().second;
            Control.panel_index = 3;
        }
    }
}
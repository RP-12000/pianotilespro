package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.menu.menubuttons.SongSelectionButtons;
import org.kelvinizer.menu.menutext.SongSelectionText;
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
            "Chart/"+ Selection.collectionDir, Selection.songIndex.get(Selection.collectionDir)
    );
    private final ArrayList<Song> songData = new ArrayList<>();
    private boolean goBack = false;
    private boolean toSettings = false;
    private boolean startEndScene = false;

    private final SongSelectionButtons ssb = new SongSelectionButtons();
    private final SongSelectionText sst = new SongSelectionText();

    public SongSelection(){
        super();
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
                    exit(4000);
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
    public void onDisappearance(Graphics2D g2d){
        if(!goBack && !toSettings){
            if(!startEndScene){
                sst.setEndStrings(songData.get(songs.getMenuIndex()), songs.getSelectionJacket());
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
        if(songs.isEmpty()){
            sst.emptyFolder.render(g2d);
        }
        else if(!Selection.isValidCollection){
            sst.corruptedFolder.render(g2d);
        }
        else{
            if(songs.getSelectionJacket()!=null){
                g2d.drawImage(songs.getSelectionJacket(), 520, 180, 480, 300, this);
            }
            else{
                sst.nullJacket.render(g2d);
            }
            ssb.play.render(g2d);
            sst.updateSelectionStrings(songs, songData);
            sst.renderCurrent(g2d);
            if(!songs.atBeginning()){
                ssb.moveUp.render(g2d);
                sst.renderPrevious(g2d);
            }
            if(!songs.atEnd()){
                ssb.moveDown.render(g2d);
                sst.renderNext(g2d);
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
            Control.panel_index = -Control.panel_index;
        }
        else{
            Selection.songDir = songs.getSelectionString();
            Selection.songJacket = songs.getSelectionJacket();
            switch (Selection.level){
                case "BS" -> Selection.chartConstant = songData.get(songs.getMenuIndex()).getBasicData().second;
                case "MD" -> Selection.chartConstant = songData.get(songs.getMenuIndex()).getMediumData().second;
                case "AV" -> Selection.chartConstant = songData.get(songs.getMenuIndex()).getAdvancedData().second;
                case "LG" -> Selection.chartConstant = songData.get(songs.getMenuIndex()).getLegendaryData().second;
            }
            Control.panel_index = 3;
        }
    }
}
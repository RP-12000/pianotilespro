package org.kelvinizer.menu.songselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.menu.SelectionButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;
import static org.kelvinizer.constants.Selection.*;

/**
 * The {@code SongSelection} class handles the song selection interface, including navigation through the
 * available songs, level selection, and the behavior of the UI elements related to the song selection panel.
 * It extends {@link AnimatablePanel} and manages user interactions such as keyboard inputs, mouse movements,
 * and button clicks. It updates the song and level selection based on the user's actions and renders the
 * corresponding UI elements.
 */
public class SongSelection extends AnimatablePanel {

    /**
     * A flag indicating whether the end scene has started.
     */
    private boolean startEndScene = false;

    /**
     * The {@code SelectionButtons} instance used to handle general selection buttons.
     */
    private final SelectionButtons sb = new SelectionButtons();

    /**
     * The {@code SongSelectionButtons} instance used to handle the song-specific selection buttons.
     */
    private final SongSelectionButtons ssb = new SongSelectionButtons();

    /**
     * The {@code SongSelectionText} instance that manages text rendering for the song selection panel.
     */
    private final SongSelectionText sst = new SongSelectionText();

    /**
     * Constructs a new {@code SongSelection} panel, initializing key bindings and selection behavior.
     */
    public SongSelection(){
        super();

        // Key bindings for navigating and selecting levels and songs
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
        addKeyBinding(KeyEvent.VK_A, false, KeyEvent.CTRL_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                users.get(userIndex).isAutoplay = !users.get(userIndex).isAutoplay;
            }
        });

        // Add key bindings for the selection buttons
        sb.addKeyBindings(this);
        if(!getSongData().hasLG() && Selection.level.equals("LG")){
            Selection.level = "AV";
        }
    }

    /**
     * Scales the UI components based on the given {@code Dimension}.
     *
     * @param d The {@code Dimension} to scale the UI components to.
     */
    @Override
    public void scale(Dimension d){
        ssb.scale(d);
        sb.scale(d);
    }

    /**
     * Handles mouse movement events by updating the focus on the selection buttons.
     *
     * @param e The {@code MouseEvent} representing the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e){
        ssb.setFocused(e);
        sb.setFocused(e);
    }

    /**
     * Handles mouse click events and processes actions such as moving between songs and selecting levels.
     *
     * @param e The {@code MouseEvent} representing the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e){
        sb.process();
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
        if(sb.play.isFocused()){
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

    /**
     * Handles mouse wheel events to move through the song list.
     *
     * @param e The {@code MouseWheelEvent} representing the mouse wheel movement.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        songs.get(collectionDir).move(e);
        if(!getSongData().hasLG() && Selection.level.equals("LG")){
            Selection.level = "AV";
        }
    }

    /**
     * Handles the disappearance of the {@code SongSelection} panel and triggers the end scene animation.
     *
     * @param g2d The {@code Graphics2D} object used for rendering.
     */
    @Override
    public void onDisappearance(Graphics2D g2d){
        if(sb.state == 0){
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

    /**
     * Renders the UI components for the song selection panel, including the song jacket, song information,
     * and the selection buttons.
     *
     * @param g2d The {@code Graphics2D} object used for rendering.
     */
    @Override
    public void render(Graphics2D g2d){
        songDir = songs.get(collectionDir).getSelectionString();
        songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
        if(songs.get(collectionDir).getSelectionJacket()!=null){
            g2d.drawImage(songs.get(collectionDir).getSelectionJacket(), 520, 180, 480, 300, this);
        }
        else{
            sst.nullJacket.render(g2d);
        }
        sb.render(g2d);
        sst.updateSelectionStrings(songs.get(collectionDir), songData.get(collectionDir));
        sst.renderCurrent(g2d);
        if(songs.get(collectionDir).notAtBeginning()){
            ssb.moveUp.render(g2d);
            sst.renderPrevious(g2d);
        }
        if(songs.get(collectionDir).notAtEnd()){
            ssb.moveDown.render(g2d);
            sst.renderNext(g2d);
        }
        ssb.renderLevels(g2d, getSongData());
        if(sb.state!=-1){
            if(sb.state==0){
                exit(4000);
            }
            else{
                exit();
            }
        }
    }

    /**
     * Transitions to the next panel based on the current selection, updating the {@code Control.panel_index}.
     */
    @Override
    public void toNextPanel(){
        songIndex.put(collectionDir, songs.get(collectionDir).getMenuIndex());
        switch(sb.state){
            case SelectionButtons.EXIT -> {
                songJacket = songs.get(collectionDir).getSelectionJacket();
                chartConstant = getSongData().getCharterData().second;
                Control.panel_index = 3;
            }
            case SelectionButtons.BACK -> Control.panel_index = 1;
            case SelectionButtons.SETTINGS -> Control.panel_index = -Control.panel_index;
            case SelectionButtons.USER -> Control.panel_index+=4;
        }
    }
}
package org.kelvinizer.gui;

import org.kelvinizer.constants.General;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.gamewindow.Song;
import org.kelvinizer.guibuttons.SongSelectionButtons;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.JacketMenu;
import org.kelvinizer.buttons.PolygonButton;

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
    private boolean isValid;

    private final SongSelectionButtons ssb = new SongSelectionButtons();

    private final PolygonButton play = new PolygonButton(
            new Rectangle((int) ReferenceWindow.REF_WIN_W-113, (int) ReferenceWindow.REF_WIN_H-135, 100, 100)
    );
    private final PolygonButton basic = new PolygonButton(
            new Rectangle(520, 480, 50, 50)
    );
    private final PolygonButton medium = new PolygonButton(
            new Rectangle(570, 480, 50, 50)
    );
    private final PolygonButton advanced = new PolygonButton(
            new Rectangle(620, 480, 50, 50)
    );
    private final PolygonButton legendary = new PolygonButton(
            new Rectangle(670, 480, 50, 50)
    );
    private final PolygonButton moveUp = new PolygonButton(
            new Polygon(new int[]{220, 380, 300}, new int[]{150, 150, 100}, 3)
    );
    private final PolygonButton moveDown = new PolygonButton(
            new Polygon(new int[]{220, 380, 300}, new int[]{550, 550, 600}, 3)
    );

    public SongSelection(){
        super();
        check();
        songs.setBounds(new CRect(300, 350, 400, 60));
        songs.setOutlineColor(Color.WHITE);
        songs.setOutlineThickness(5.0);
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
        ssb.resize(d);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        ssb.back.setFocused(e);
        ssb.settings.setFocused(e);
        if(!songs.isEmpty() && isValid){
            ssb.play.setFocused(e);
            ssb.basic.setFocused(e);
            ssb.medium.setFocused(e);
            ssb.advanced.setFocused(e);
            if(songData.get(songs.getMenuIndex()).hasLG()){
                ssb.legendary.setFocused(e);
            }
            if(!songs.atBeginning()){
                ssb.moveUp.setFocused(e);
            }
            if(!songs.atEnd()){
                ssb.moveDown.setFocused(e);
            }
        }
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
        if(!songs.isEmpty() && isValid){
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
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        ssb.settings.render(g2d);
        ssb.back.render(g2d);
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
            ssb.deselectAllLevel();
            ssb.addLevelToButtons(songData.get(songs.getMenuIndex()));
            switch (Selection.level) {
                case "BS" -> basic.setFocused(true);
                case "MD" -> medium.setFocused(true);
                case "AV" -> advanced.setFocused(true);
                case "LG" -> legendary.setFocused(true);
            }
            ssb.basic.render(g2d);
            ssb.medium.render(g2d);
            ssb.advanced.render(g2d);
            if(songData.get(songs.getMenuIndex()).hasLG()){
                ssb.legendary.render(g2d);
            }
        }
    }

    @Override
    public void toNextPanel(){
        Selection.songIndex.put(Selection.collectionDir, songs.getMenuIndex());
        if(goBack){
            General.panel_index = 1;
            return;
        }
        if(toSettings){
            General.panel_index += General.numPanels;
        }
        else{
            Selection.songDir = songs.getSelectionString();
            General.panel_index = 2;
        }
    }
}
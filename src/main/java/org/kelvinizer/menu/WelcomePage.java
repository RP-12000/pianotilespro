package org.kelvinizer.menu;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.constants.Control;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.JacketMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.kelvinizer.constants.Selection.*;
import static org.kelvinizer.constants.Selection.collections;

public class WelcomePage extends AnimatablePanel {
    private final BoundedString gameName = new BoundedString("Piano Tiles Pro", 81, 540, 200);
    private final BoundedString gameVersion = new BoundedString("v0.0.0-a", 27, 540, 590);
    private final BoundedString enter = new BoundedString("Press Enter to start", 16, 540, 630, 540, 30);
    private final CRectButton play = new CRectButton();

    private final BoundedString loading = new BoundedString("", 16, 540, 630, 540, 30);
    private final CRect bar = new CRect(270, 630, 0, 30);
    private static int state = 0;
    private static boolean success = false;

    public WelcomePage(){
        super(2000);
        enter.setStyle(Font.ITALIC);
        loading.setStringColor(Color.GREEN);
        loading.getBounds().setOutlineColor(new Color(255,255,255,200));
        loading.getBounds().setOutlineThickness(3.0);
        bar.setFillColor(Color.WHITE);
        BoundedString normal = new BoundedString("", 0, 540, 400, 200, 200);
        BoundedString onFocus = new BoundedString("", 0, 540, 400, 240, 240);

        if(!play.setIcon("Play.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        play.setNormal(normal);
        play.setOnFocus(onFocus);

        addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(success){
                    exit(2000);
                }
            }
        });
    }

    @Override
    public void scale(Dimension d){
        play.scale(d);
    }

    @Override
    public void onAppearance(Graphics2D g2d){
        setAppearingOpacity(g2d);
        gameName.render(g2d);
        gameVersion.render(g2d);
    }

    @Override
    public void render(Graphics2D g2d){
        Thread t = new Thread(this::errorCheck);
        if(state==0){
            t.start();
            state=1;
        }
        gameName.render(g2d);
        gameVersion.render(g2d);
        if(state!=2 || !success){
            bar.render(g2d);
            loading.render(g2d);
        }
        if(success){
            play.render(g2d);
            enter.render(g2d);
        }
    }

    private void errorCheck(){
        try{
            loading.setString("Scanning for Songs");
            collections = new JacketMenu("Chart", 0);
            int totalSongs = -1;
            for(int i=0; i<collections.size(); i++){
                JacketMenu jm = new JacketMenu("Chart/"+collections.getSelectionString(i), 0);
                totalSongs+=jm.size();
                songIndex.put(collections.getSelectionString(i), 0);
                songs.put(collections.getSelectionString(i), jm);
                songData.put(collections.getSelectionString(i), new ArrayList<>());
                loading.setString("Found "+totalSongs+1+" songs");
            }
            for(int i=0; i<collections.size(); i++){
                for(int j=0; j<songs.get(collections.getSelectionString(i)).size(); j++){
                    loading.setString("Loading "+collections.getSelectionString(i)+" -> "+songs.get(collections.getSelectionString(i)).getSelectionString(j));
                    songData.get(collections.getSelectionString(i)).add(
                            new Song("Chart/"+collections.getSelectionString(i)+"/"+songs.get(collections.getSelectionString(i)).getSelectionString(j))
                    );
                    bar.setWidth(bar.getWidth()+loading.getBounds().getWidth()/totalSongs);
                }
            }
            collectionDir = collections.getSelectionString(0);
            success=true;
        } catch (RuntimeException e) {
            bar.setFillColor(Color.DARK_GRAY);
            loading.setStringColor(Color.RED);
        }
        state = 2;
    }

    @Override
    public void mouseMoved(MouseEvent e){
        if(success){
            play.setFocused(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(success && play.isFocused()){
            exit();
        }
    }

    @Override
    public void toNextPanel() {
        Control.panel_index=1;
    }
}
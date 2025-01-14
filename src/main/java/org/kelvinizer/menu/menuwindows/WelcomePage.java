package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.constants.Control;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.JacketMenu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.kelvinizer.constants.Control.getResourcePathName;
import static org.kelvinizer.constants.Control.userName;
import static org.kelvinizer.constants.Selection.*;
import static org.kelvinizer.constants.Selection.collections;

public class WelcomePage extends AnimatablePanel {
    private int click_count=0;
    private final BoundedString gameName = new BoundedString("Piano Tiles Pro", 81, 540, 200);
    private final BoundedString gameVersion = new BoundedString("v0.0.0-a", 27, 540, 590);
    private final BoundedString startVerdict = new BoundedString("Double click anywhere to start", 20, 540, 630);
    private final CRectButton play = new CRectButton();
    private boolean success = false;

    public WelcomePage(){
        super(2000);
        startVerdict.setStyle(Font.ITALIC);
    }

    @Override
    public void onAppearance(Graphics2D g2d){
        Thread t = new Thread(this::errorCheck);
        double timePassed = (System.nanoTime()-start_time)/1e9;
        setGlobalOpacity(g2d, timePassed);
        gameName.render(g2d);
        gameVersion.render(g2d);
        if(timePassed>=1 && !t.isAlive()){
            t.start();
        }

    }

    private void errorCheck(){
        try {
            BufferedReader userInfo = new BufferedReader(new FileReader(getResourcePathName("Chart/user.txt")));
            userName = Objects.requireNonNull(userInfo.readLine());
            userInfo.close();
        } catch (IOException | RuntimeException e){
            try {
                PrintWriter newInfo = new PrintWriter(new FileWriter(getResourcePathName("Chart/user.txt")));
                userName = "User"+(int)(Math.random()*1e10);
                newInfo.println(userName);
                newInfo.close();
            } catch (IOException ex) {
                throw new RuntimeException("Unable to load game");
            }
        }
        try{
            collections = new JacketMenu("Chart", 0);
            for(int i=0; i<collections.size(); i++){
                songIndex.put(collections.getSelectionString(i), 0);
                songs.put(collections.getSelectionString(i), new JacketMenu("Chart/"+collections.getSelectionString(i), 0));
                songData.put(collections.getSelectionString(i), new ArrayList<>());
                for(int j=0; j<songs.get(collections.getSelectionString(i)).size(); j++){
                    songData.get(collections.getSelectionString(i)).add(
                            new Song("Chart/"+collections.getSelectionString(i)+"/"+songs.get(collections.getSelectionString(i)).getSelectionString(j))
                    );
                }
            }
            collectionDir = collections.getSelectionString(0);
        } catch (IOException | RuntimeException e) {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click_count++;
        if(click_count==2){
            removeMouseListener(this);
            exit(2000);
        }
        repaint();
    }

    @Override
    public void render(Graphics2D g2d){
        gameName.render(g2d);
        gameVersion.render(g2d);
        startVerdict.render(g2d);
    }

    @Override
    public void toNextPanel() {
        Control.panel_index=1;
    }
}
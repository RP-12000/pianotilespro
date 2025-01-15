package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.ResultPage;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.menu.WelcomePage;
import org.kelvinizer.menu.collectionselection.CollectionSelection;
import org.kelvinizer.menu.settingpage.Settings;
import org.kelvinizer.menu.songselection.SongSelection;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.*;

public class App extends JFrame {
    private AnimatablePanel display;
    int lastPanel = 0;

    public App(){
        boot();
        initSettings();
        ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(this::runGame, 0, 1000/Control.FPS, TimeUnit.MILLISECONDS);
        setVisible(true);
    }

    private void printSettings(PrintWriter pw){
        pw.println(userName);
        pw.println(isAutoplay);
        pw.println(syncEnabled);
        pw.println(FCAPHintEnabled);
        pw.println(handHintEnabled);
        pw.println(newFPS);
        pw.println(MUSIC_DIFFERENCE);
        pw.println(tolerance);
        pw.close();
    }

    private void boot(){
        setTitle("PianoTilesPro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0,
                (int) ReferenceWindow.REF_WIN_W+ReferenceWindow.extraWidth,
                (int) ReferenceWindow.REF_WIN_H+ReferenceWindow.extraHeight
        );
        display = new WelcomePage();
        add(display);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    printSettings(new PrintWriter(getResourcePathName("Chart/settings.txt")));
                    for(Map.Entry<String, ArrayList<Song>> entry: songData.entrySet()){
                        for(Song s: entry.getValue()){
                            s.exportBestToFile();
                        }
                    }
                    System.exit(0);
                } catch(IOException ignored){}
            }
        });
    }

    private void initSettings(){
        try {
            BufferedReader userInfo = new BufferedReader(new FileReader(getResourcePathName("Chart/settings.txt")));
            userName = userInfo.readLine();
            isAutoplay = Boolean.parseBoolean(userInfo.readLine());
            syncEnabled = Boolean.parseBoolean(userInfo.readLine());
            FCAPHintEnabled = Boolean.parseBoolean(userInfo.readLine());
            handHintEnabled = Boolean.parseBoolean(userInfo.readLine());
            FPS = Integer.parseInt(userInfo.readLine());
            newFPS = FPS;
            MUSIC_DIFFERENCE = Integer.parseInt(userInfo.readLine());
            tolerance = Integer.parseInt(userInfo.readLine());
            userInfo.close();
        } catch (IOException | RuntimeException e){
            try {
                PrintWriter newInfo = new PrintWriter(new FileWriter(getResourcePathName("Chart")+"/settings.txt"));
                userName = "User"+(long)(Math.random()*1e10);
                printSettings(newInfo);
            } catch (IOException ex) {
                throw new RuntimeException("Unable to load game");
            }
        }
    }

    private void runGame(){
        if(lastPanel!= Control.panel_index){
            remove(display);
            display = null;//Java Garbage Collector
            switch (Control.panel_index){
                case 0 -> display = new WelcomePage();
                case 1 -> display = new CollectionSelection();
                case 2 -> display = new SongSelection();
                case 3 ->{
                    try {
                        display = new Chart();
                    } catch (IOException | LineUnavailableException | RuntimeException e) {
                        throw new RuntimeException("Corrupted Chart Detected");
                    }
                }
                case 4 -> display = new ResultPage();
                default -> display = new Settings();
            }
            display.setBounds(0, 0,
                    getSize().width-ReferenceWindow.extraWidth,
                    getSize().height-ReferenceWindow.extraHeight
            );
            add(display);
            revalidate();
            lastPanel = panel_index;
        }
        else{
            display.setBounds(0, 0,
                    getSize().width-ReferenceWindow.extraWidth,
                    getSize().height-ReferenceWindow.extraHeight
            );
        }
        display.scale(new Dimension(
                getSize().width-ReferenceWindow.extraWidth,
                getSize().height-ReferenceWindow.extraHeight
        ));
    }
}
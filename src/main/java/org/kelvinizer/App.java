package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Time;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.ResultPage;
import org.kelvinizer.menu.menuwindows.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.*;

import static org.kelvinizer.constants.Control.*;

public class App extends JFrame {
    private AnimatablePanel display;
    int lastPanel = 0;

    public App(){
        boot();
        initSettings();
        ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(this::runGame, 0, 1000/Time.FPS, TimeUnit.MILLISECONDS);
        setVisible(true);
    }

    private void boot(){
        setTitle("PianoTilesPro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0,
                (int) ReferenceWindow.REF_WIN_W+ReferenceWindow.extraWidth,
                (int) ReferenceWindow.REF_WIN_H+ReferenceWindow.extraHeight
        );
        display = new WelcomePage();
        add(display);
    }

    private void initSettings(){
        try {
            BufferedReader userInfo = new BufferedReader(new FileReader(getResourcePathName("Chart/settings.txt")));
            userName = userInfo.readLine();
            isAutoplay = Boolean.parseBoolean(userInfo.readLine());
            syncEnabled = Boolean.parseBoolean(userInfo.readLine());
            userInfo.close();
        } catch (IOException | RuntimeException e){
            try {
                PrintWriter newInfo = new PrintWriter(new FileWriter(getResourcePathName("")+"settings.txt"));
                userName = "User"+(int)(Math.random()*1e10);
                newInfo.println(userName);
                newInfo.println(true);
                newInfo.println(true);
                newInfo.close();
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
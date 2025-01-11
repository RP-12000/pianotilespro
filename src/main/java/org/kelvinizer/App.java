package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Time;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.menu.menuwindows.*;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.*;

import static org.kelvinizer.constants.Control.*;

public class App extends JFrame {
    private AnimatablePanel display;
    int lastPanel = 0;

    public App(){
        boot();
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

    private void runGame(){
        if(lastPanel!= Control.panel_index){
            remove(display);
            switch (Control.panel_index){
                case 0 -> display = new WelcomePage();
                case 1 -> display = new CollectionSelection();
                case 2 -> display = new SongSelection();
                case 3 ->{
                    try {
                        display = new Chart();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
        display.scale(getSize());
    }
}
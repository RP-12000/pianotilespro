package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.General;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Time;
import org.kelvinizer.gamewindow.Chart;
import org.kelvinizer.menu.guiwindows.CollectionSelection;
import org.kelvinizer.menu.guiwindows.Settings;
import org.kelvinizer.menu.guiwindows.SongSelection;
import org.kelvinizer.menu.guiwindows.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.*;

import static org.kelvinizer.constants.General.*;

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
        Insets insets = getInsets();
        ReferenceWindow.extraWidth = insets.left + insets.right;
        ReferenceWindow.extraHeight = insets.top + insets.bottom;
        setSize((int) ReferenceWindow.REF_WIN_W+ReferenceWindow.extraWidth, (int) ReferenceWindow.REF_WIN_H+ReferenceWindow.extraHeight);
        display = new WelcomePage();
        add(display);
    }

    private void runGame(){
        if(lastPanel!=General.panel_index){
            remove(display);
            switch (General.panel_index){
                case 0:
                    display = new WelcomePage();break;
                case 1:
                    display = new CollectionSelection();break;
                case 2:
                    display = new SongSelection();break;
                case 3:
                    try {
                        display = new Chart();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    display = new Settings();break;
            }
            add(display);
            revalidate();
            lastPanel = panel_index;
        }
        display.setBounds(0, 0, getSize().width, getSize().height);
        display.resizeButtons(getSize());
    }
}
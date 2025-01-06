package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.gamewindow.Chart;
import org.kelvinizer.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

import static org.kelvinizer.Constants.*;

public class App extends JFrame {
    private AnimatablePanel display = new WelcomePage();
    int lastPanel = 0;
    Dimension last_size = new Dimension((int)Constants.ReferenceWindow.REF_WIN_W, (int)Constants.ReferenceWindow.REF_WIN_H);

    public App(){
        boot();
        ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(this::runGame, 0, 1000/Time.FPS, TimeUnit.MILLISECONDS);
        setVisible(true);
    }

    private void boot(){
        setTitle("PianoTilesPro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((int)Constants.ReferenceWindow.REF_WIN_W, (int)Constants.ReferenceWindow.REF_WIN_H);
        add(display);
    }

    private void runGame(){
        if(lastPanel!=panel_index){
            remove(display);
            switch (panel_index){
                case 0:
                    display = new WelcomePage();break;
                case 1:
                    display = new CollectionSelection();break;
                case 2:
                    display = new SongSelection();break;
                case 3:
                    display = new Chart();break;
                default:
                    display = new Settings();break;
            }
            add(display);
            revalidate();
            lastPanel = panel_index;
        }
        if(getSize()!=last_size) {
            last_size = getSize();
            display.setBounds(0, 0, last_size.width, last_size.height);
            display.resizeButtons(last_size);
        }
    }
}
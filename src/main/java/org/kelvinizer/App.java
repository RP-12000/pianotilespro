package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import static org.kelvinizer.Constants.*;

public class App extends JFrame {
    private final ArrayList<AnimatablePanel> panels = new ArrayList<>();
    int last_panel = 0;
    Dimension last_size = new Dimension(Constants.ReferenceWindow.REF_WIN_W, Constants.ReferenceWindow.REF_WIN_H);

    public App(){
        bootUp();
        ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(this::runGame, 0, 1000/Time.FPS, TimeUnit.MILLISECONDS);
        setVisible(true);
    }

    private void bootUp(){
        setTitle("PianoTilesPro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.ReferenceWindow.REF_WIN_W, Constants.ReferenceWindow.REF_WIN_H);
        addPanel(new WelcomePage());
        addPanel(new SelectionPage());
        addPanel(new ChartSelection());
        add(panels.get(0));
    }

    private void addPanel(AnimatablePanel a){
        a.setBounds(0, 0, Constants.ReferenceWindow.REF_WIN_W, Constants.ReferenceWindow.REF_WIN_H);
        panels.add(a);
    }

    public void runGame(){
        if(last_panel!=panel_index){
            remove(panels.get(last_panel));
            last_panel=panel_index;
            if(last_panel==2){
                panels.set(2, new ChartSelection());
            }
            add(panels.get(last_panel));
            revalidate();
        }
        if(getSize()!=last_size){
            last_size=getSize();
            panels.get(last_panel).setBounds(0, 0, last_size.width, last_size.height);
            panels.get(last_panel).resizeButtons(last_size);
        }
        panels.get(last_panel).repaint();
    }
}
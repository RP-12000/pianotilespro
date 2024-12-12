package org.kelvinizer;

import org.kelvinizer.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.kelvinizer.Constants.*;

public class App extends JFrame {
    private ArrayList<JPanel> panels = new ArrayList<JPanel>();

    public App(){
        setTitle("PianoTilesPro");
        setSize(Constants.ReferenceWindow.REF_WIN_W, Constants.ReferenceWindow.REF_WIN_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panels.add(new WelcomePage());
        panels.add(new CollectionSelection());
        for(JPanel p: panels){
            add(p);
        }
        setVisible(true);
    }

    public void runGame(){
        Time.global_timer = System.nanoTime();
        long last_start_time = System.nanoTime();
        while(true){
            if(System.nanoTime()-last_start_time >= (long)1e9/Time.fps){
                for(JPanel p: panels){
                    p.setVisible(false);
                }
                panels.get(PanelControl.panel_index).setVisible(true);
                panels.get(PanelControl.panel_index).repaint();
                last_start_time = System.nanoTime();
            }
        }

    }
}

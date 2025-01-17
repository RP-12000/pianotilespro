package org.kelvinizer;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.constants.User;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.ResultPage;
import org.kelvinizer.menu.WelcomePage;
import org.kelvinizer.menu.collectionselection.CollectionSelection;
import org.kelvinizer.menu.settingpage.Settings;
import org.kelvinizer.menu.songselection.SongSelection;
import org.kelvinizer.menu.userselection.UserSelection;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;

import static org.kelvinizer.constants.Control.*;

public class App extends JFrame {
    private AnimatablePanel display;
    int lastPanel = 0;

    public App(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("ptp_settings"));
            FPS = Integer.parseInt(br.readLine());
            userIndex = Integer.parseInt(br.readLine());
            firstTimeOpen = Boolean.parseBoolean(br.readLine());
            Selection.collectionIndex = Integer.parseInt(br.readLine());
        } catch (Exception ignored) {}
        newFPS = FPS;
        boot();
        ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(this::runGame, 0, 1000/Control.FPS, TimeUnit.MILLISECONDS);
        setVisible(true);
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
                try(PrintWriter pw = new PrintWriter("ptp_settings")){
                    pw.println(newFPS+"\n"+userIndex+"\n"+firstTimeOpen+"\n"+ Selection.collectionIndex);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try{
                    Files.deleteIfExists(Paths.get(".refresh"));
                } catch (IOException ignored) {}
                for(User u: users){
                    u.exportUser();
                }
                System.exit(0);
            }
        });
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
                    } catch (IOException | LineUnavailableException | RuntimeException | UnsupportedAudioFileException e) {
                        throw new RuntimeException("Corrupted Chart Detected");
                    }
                }
                case 4 -> display = new ResultPage();
                case 5, 6-> display = new UserSelection();
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
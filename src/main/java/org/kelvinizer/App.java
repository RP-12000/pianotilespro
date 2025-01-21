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

/**
 * The {@code App} class represents the main application window for the PianoTilesPro game.
 * It extends {@link JFrame} and serves as the entry point for initializing the game interface,
 * managing different game panels, and handling user settings and panel transitions.
 * @author Boyan Hu
 */
public class App extends JFrame {

    /** The currently displayed panel. */
    private AnimatablePanel display;

    /** The index of the last panel displayed. */
    private int lastPanel = 0;

    /**
     * Constructs an {@code App} instance and initializes the game.
     * The constructor reads the settings from a file and starts the game loop in a scheduled executor.
     * It also makes the window visible.
     */
    public App(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("ptp_settings"));
            userIndex = Integer.parseInt(br.readLine());
            firstTimeOpen = Boolean.parseBoolean(br.readLine());
            Selection.collectionIndex = Integer.parseInt(br.readLine());
        } catch (Exception ignored) {}
        boot();
        ScheduledExecutorService gameLoop = Executors.newSingleThreadScheduledExecutor();
        gameLoop.scheduleAtFixedRate(this::runGame, 0, 1000/Control.FPS, TimeUnit.MILLISECONDS);
        setVisible(true);
    }

    /**
     * Initializes the main window and sets up the initial panel and window closing behavior.
     * The window is titled "PianoTilesPro" and its size is based on reference window constants.
     * The window will dispose properly when closed, saving user settings and performing clean-up tasks.
     */
    private void boot(){
        setTitle("PianoTilesPro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0,
                (int) ReferenceWindow.REF_WIN_W + ReferenceWindow.extraWidth,
                (int) ReferenceWindow.REF_WIN_H + ReferenceWindow.extraHeight
        );
        display = new WelcomePage();
        add(display);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when the window is in the process of being closed.
             * It saves user settings, deletes temporary files, and exports user data before exiting.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if(users.isEmpty()){
                    return;
                }
                try(PrintWriter pw = new PrintWriter("ptp_settings")){
                    pw.println(userIndex+"\n"+firstTimeOpen+"\n"+ Selection.collectionIndex);
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

    /**
     * The game loop method that runs periodically to update the displayed panel based on the current panel index.
     * It switches the displayed panel when the panel index changes, and it properly sets the bounds
     * and scaling of the new panel.
     */
    private void runGame(){
        if(lastPanel != Control.panel_index){
            remove(display);
            display = null; // Java Garbage Collector
            switch (Control.panel_index){
                case 0: display = new WelcomePage();break;
                case 1: display = new CollectionSelection();break;
                case 2: display = new SongSelection();break;
                case 3:{
                    try {
                        display = new Chart();
                    } catch (IOException | LineUnavailableException | RuntimeException | UnsupportedAudioFileException e) {
                        throw new RuntimeException("Corrupted Chart Detected");
                    }break;
                }
                case 4: display = new ResultPage();break;
                case 5: display = new UserSelection();break;
                case 6: display = new UserSelection();break;
                default: display = new Settings();break;
            }
            display.setBounds(0, 0,
                    getSize().width - ReferenceWindow.extraWidth,
                    getSize().height - ReferenceWindow.extraHeight
            );
            add(display);
            revalidate();
            lastPanel = panel_index;
        } else {
            display.setBounds(0, 0,
                    getSize().width - ReferenceWindow.extraWidth,
                    getSize().height - ReferenceWindow.extraHeight
            );
        }
        display.scale(new Dimension(
                getSize().width - ReferenceWindow.extraWidth,
                getSize().height - ReferenceWindow.extraHeight
        ));
    }
}
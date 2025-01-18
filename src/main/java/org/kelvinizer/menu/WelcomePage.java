package org.kelvinizer.menu;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.User;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.JacketMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.*;

/**
 * Represents the welcome page of the application, providing a graphical interface
 * to load resources and navigate to the next panel.
 * Extends the {@link AnimatablePanel} class to enable animations.
 * @author Boyan Hu
 */
public class WelcomePage extends AnimatablePanel {

    /** The title of the game displayed on the welcome page. */
    private final BoundedString gameName = new BoundedString("Piano Tiles Pro", 81, 540, 200);

    /** The version of the game displayed on the welcome page. */
    private final BoundedString gameVersion = new BoundedString(GAME_VERSION, 27, 540, 590);

    /** The instruction text displayed when the game is ready to start. */
    private final BoundedString enter = new BoundedString("Press Enter to start", 16, 540, 630, 540, 30);

    /** The play button on the welcome page. */
    private final CRectButton play = new CRectButton();

    /** The loading text displayed during resource loading. */
    private final BoundedString loading = new BoundedString("", 16, 540, 630, 540, 30);

    /** The loading progress bar displayed during resource loading. */
    private final CRect bar = new CRect(270, 630, 0, 30);

    /** The current state of the welcome page. */
    private static int state = 0;

    /** Indicates whether the loading process was successful. */
    private static boolean success = false;

    /**
     * Constructs a new {@code WelcomePage} instance, initializing the UI elements
     * and setting up key bindings.
     */
    public WelcomePage() {
        super(2000);
        enter.setStyle(Font.ITALIC);
        loading.setStringColor(Color.GREEN);
        loading.getBounds().setOutlineColor(new Color(255, 255, 255, 200));
        loading.getBounds().setOutlineThickness(3.0);
        bar.setFillColor(Color.WHITE);

        BoundedString normal = new BoundedString("", 0, 540, 400, 200, 200);
        BoundedString onFocus = new BoundedString("", 0, 540, 400, 240, 240);

        if (!play.setIcon("Play.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        play.setNormal(normal);
        play.setOnFocus(onFocus);

        addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (success) {
                    exit(2000);
                }
            }
        });
    }

    /**
     * Scales the UI elements to fit the given dimensions.
     *
     * @param d the dimensions to scale to
     */
    @Override
    public void scale(Dimension d) {
        play.scale(d);
    }

    /**
     * Renders the welcome page when it appears.
     *
     * @param g2d the graphics context
     */
    @Override
    public void onAppearance(Graphics2D g2d) {
        setAppearingOpacity(g2d);
        gameName.render(g2d);
        gameVersion.render(g2d);
    }

    /**
     * Renders the welcome page elements and manages the loading state.
     *
     * @param g2d the graphics context
     */
    @Override
    public void render(Graphics2D g2d) {
        Thread t = new Thread(this::errorCheck);
        if (state == 0) {
            t.start();
            state = 1;
        }
        gameName.render(g2d);
        gameVersion.render(g2d);
        if (state != 2 || !success) {
            bar.render(g2d);
            loading.render(g2d);
        }
        if (success) {
            play.render(g2d);
            enter.render(g2d);
        }
    }

    /**
     * Loads the resources and handles errors during the process.
     */
    private void errorCheck() {
        try {
            loading.setString("Loading users");
            getAllUsers();
            if (users.size() <= userIndex) {
                userIndex = 0;
            }
            loading.setString("Scanning for Songs");
            collections = new JacketMenu("Chart", collectionIndex);
            int totalSongs = -1;
            for (int i = 0; i < collections.size(); i++) {
                JacketMenu jm = new JacketMenu("Chart/" + collections.getSelectionString(i), 0);
                totalSongs += jm.size();
                songIndex.put(collections.getSelectionString(i), 0);
                songs.put(collections.getSelectionString(i), jm);
                songData.put(collections.getSelectionString(i), new ArrayList<>());
                loading.setString("Found " + (totalSongs + 1) + " songs");
            }
            for (int i = 0; i < collections.size(); i++) {
                for (int j = 0; j < songs.get(collections.getSelectionString(i)).size(); j++) {
                    loading.setString("Loading " + collections.getSelectionString(i) + " -> " +
                            songs.get(collections.getSelectionString(i)).getSelectionString(j));
                    songData.get(collections.getSelectionString(i)).add(
                            new Song("Chart/" + collections.getSelectionString(i) + "/" +
                                    songs.get(collections.getSelectionString(i)).getSelectionString(j))
                    );
                    bar.setWidth(bar.getWidth() + loading.getBounds().getWidth() / totalSongs);
                }
            }
            collectionDir = collections.getSelectionString(0);
            success = true;
            firstTimeOpen = false;
        } catch (RuntimeException e) {
            bar.setFillColor(Color.DARK_GRAY);
            loading.setStringColor(Color.RED);
        }
        state = 2;
    }

    /**
     * Handles mouse movement events and updates button focus.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (success) {
            play.setFocused(e);
        }
    }

    /**
     * Handles mouse click events and performs actions based on button focus.
     *
     * @param e the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (success && play.isFocused()) {
            exit();
        }
    }

    /**
     * Determines the next panel to navigate to based on the application's state.
     */
    @Override
    public void toNextPanel() {
        if (users.isEmpty()) {
            users.add(new User());
            Control.panel_index = -1;
        } else {
            Control.panel_index = 1;
        }
    }
}
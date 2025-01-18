package org.kelvinizer.constants;

import org.kelvinizer.game.gamewindow.ScoreData;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.support.classes.JacketMenu;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

/**
 * The {@code Selection} class handles the selection of collections, songs, and associated data.
 * It stores the active collection, song, and user data, including metadata such as song jackets and score data.
 * <p>
 * This class also manages the index and directory paths for collections and songs, allowing easy navigation
 * and retrieval of song data.
 * </p>
 *
 * @author Boyan Hu
 */
public class Selection {

    /** The current collection index. */
    public static int collectionIndex = 0;

    /** The current collection menu. */
    public static JacketMenu collections;

    /** A mapping of collection names to their respective song lists. */
    public static HashMap<String, JacketMenu> songs = new HashMap<>();

    /** A mapping of collection names to the index of the currently selected song. */
    public static HashMap<String, Integer> songIndex = new HashMap<>();

    /** A mapping of collection names to the list of song data. */
    public static final HashMap<String, ArrayList<Song>> songData = new HashMap<>();

    /** The directory path for the current collection. */
    public static String collectionDir = "";

    /** The directory path for the current song. */
    public static String songDir = "";

    /** The difficulty level of the selected song. */
    public static String level = "BS";

    /** A constant used for chart calculations. */
    public static double chartConstant = 0;

    /** The jacket image for the current song. */
    public static BufferedImage songJacket = null;

    /**
     * Retrieves the {@link Song} object for the currently selected song.
     *
     * @return the {@link Song} data for the selected song in the current collection.
     */
    public static Song getSongData() {
        return songData.get(collectionDir).get(songIndex.get(collectionDir));
    }

    /**
     * Retrieves the {@link ScoreData} for the current user.
     *
     * @return the {@link ScoreData} object associated with the current user.
     */
    public static ScoreData getScoreData() {
        return users.get(userIndex).getScoreData();
    }
}
package org.kelvinizer.game.gamewindow;

import org.kelvinizer.constants.Selection;
import org.kelvinizer.support.classes.Pair;

import java.io.*;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.firstTimeOpen;
import static org.kelvinizer.constants.Control.getResourceInput;

/**
 * Represents a song in the game, including its metadata (composer, illustration, chart data)
 * and provides access to song details such as the song's name, composer, chart information, and offset.
 * It also validates the song's charts and handles data related to different levels of difficulty.
 * @author Boyan Hu
 */
public class Song {

    /**
     * Directory path of the song, used to locate associated resources.
     */
    private final String absoluteDir;

    /**
     * Name of the song, extracted from the song's directory.
     */
    private final String songName;

    /**
     * Composer of the song, read from the song's credits.
     */
    private final String composer;

    /**
     * Illustration artist or related visual details for the song.
     */
    private final String illustration;

    /**
     * Flag indicating if the song has a "LG" (large) chart.
     */
    private final boolean lg;

    /**
     * A map containing different chart data, each represented by a Pair of chart type and its value.
     */
    private final HashMap<String, Pair<String, Double>> data = new HashMap<>();

    /**
     * Offset value for the song, used for timing adjustments in the game.
     */
    public final double OFFSET;

    /**
     * Constructs a new song from the given directory path, reading its metadata and chart information.
     * The constructor reads the song's credits and chart data from a text file.
     * If the chart files are invalid, a RuntimeException will be thrown.
     *
     * @param dir The directory path to the song's resources.
     * @throws RuntimeException if there is an error reading the song's credits or if the chart data is invalid.
     */
    public Song(String dir) throws RuntimeException {
        absoluteDir = dir;
        String[] sp = dir.split("/");
        songName = sp[sp.length - 1];

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getResourceInput(dir + "/credits.txt")));
            composer = br.readLine();
            illustration = br.readLine();
            OFFSET = Double.parseDouble(br.readLine());
            lg = Boolean.parseBoolean(br.readLine());

            // Parse chart data for different difficulties (BS, MD, AV, LG)
            String[] temp = br.readLine().split(" ");
            data.put("BS", new Pair<>(temp[0], Double.parseDouble(temp[1])));

            temp = br.readLine().split(" ");
            data.put("MD", new Pair<>(temp[0], Double.parseDouble(temp[1])));

            temp = br.readLine().split(" ");
            data.put("AV", new Pair<>(temp[0], Double.parseDouble(temp[1])));

            if (lg) {
                temp = br.readLine().split(" ");
                data.put("LG", new Pair<>(temp[0], Double.parseDouble(temp[1])));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error when reading credits for this song: " + songName);
        }

        // Validate chart data if this is the first time opening the song
        if (firstTimeOpen) {
            if (Chart.invalidChart(this, "BS")) {
                throw new RuntimeException("Broken Basic Chart");
            }
            else if (Chart.invalidChart(this, "MD")) {
                throw new RuntimeException("Broken MD Chart");
            }
            else if (Chart.invalidChart(this, "AV")) {
                throw new RuntimeException("Broken AV Chart");
            }
            else if (lg && Chart.invalidChart(this, "LG")) {
                throw new RuntimeException("Broken LG Chart");
            }
        }
    }

    /**
     * Returns whether the song has a "LG" (large) chart.
     *
     * @return true if the song has a "LG" chart, false otherwise.
     */
    public boolean hasLG() {
        return lg;
    }

    /**
     * Returns the composer of the song.
     *
     * @return The composer of the song.
     */
    public String getComposer() {
        return composer;
    }

    /**
     * Returns the illustration or artist associated with the song.
     *
     * @return The illustration or artist name.
     */
    public String getIllustration() {
        return illustration;
    }

    /**
     * Returns the data for the selected chart level.
     * The level is determined by the current selection in the game.
     *
     * @return A Pair containing the chart type and its corresponding value for the current level.
     */
    public Pair<String, Double> getCharterData() {
        return data.get(Selection.level);
    }

    /**
     * Returns the data for the specified chart level.
     *
     * @param s The name of the chart level (e.g., "BS", "MD", "AV", "LG").
     * @return A Pair containing the chart type and its corresponding value for the specified level.
     */
    public Pair<String, Double> getCharterData(String s) {
        return data.get(s);
    }

    /**
     * Returns the name of the song.
     *
     * @return The name of the song.
     */
    public String getSongName() {
        return songName;
    }

    /**
     * Returns the absolute directory path of the song's resources.
     *
     * @return The absolute directory path.
     */
    public String getAbsoluteDir() {
        return absoluteDir;
    }
}
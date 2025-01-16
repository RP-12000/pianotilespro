package org.kelvinizer.constants;

import org.kelvinizer.game.gamewindow.ScoreData;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.support.classes.JacketMenu;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

public class Selection {
    public static int collectionIndex = 0;
    public static JacketMenu collections;
    public static HashMap<String, JacketMenu> songs = new HashMap<>();//Collection_name, the list of songs names
    public static HashMap<String, Integer> songIndex = new HashMap<>();//Collection_name, index of the song in the collection
    public static final HashMap<String, ArrayList<Song>> songData = new HashMap<>();//Collection_name, the list of song data

    public static String collectionDir = "";
    public static String songDir = "";
    public static String level = "BS";
    public static double chartConstant = 0;
    public static BufferedImage songJacket = null;

    public static Song getSongData(){
        return songData.get(collectionDir).get(songIndex.get(collectionDir));
    }

    public static ScoreData getScoreData(){
        return users.get(userIndex).getScoreData();
    }
}
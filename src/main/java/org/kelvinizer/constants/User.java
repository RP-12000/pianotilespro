package org.kelvinizer.constants;

import org.kelvinizer.game.gamewindow.ScoreData;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.classes.Triple;

import java.io.*;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.*;

/**
 * Represents a user with their settings, game data, and score information.
 * Provides methods to load and save user data, as well as access scores and FC/AP (Full Combo/All Perfect) statistics.
 * @author Boyan Hu
 */
public class User {

    /** The name of the user. */
    public String userName;

    /** Flag indicating whether autoplay is enabled. */
    public boolean isAutoplay = false;

    /** Flag indicating whether synchronization is enabled. */
    public boolean syncEnabled = true;

    /** Flag indicating whether FCAP (Full Combo/All Perfect) hint is enabled. */
    public boolean FCAPHintEnabled = true;

    /** Flag indicating whether hints for left and right hand is enabled. */
    public boolean handHintEnabled = false;

    /** The music difference setting. */
    public int MUSIC_DIFFERENCE = 0;

    /** The tolerance setting for the user. */
    public int tolerance = 80;

    /** A map storing the user's game data, indexed by collection, song, and level. */
    public HashMap<String, HashMap<String, HashMap<String, ScoreData>>> userData = new HashMap<>();

    /** Flag indicating whether the user is valid. */
    public boolean isValidUser = true;

    /**
     * Reads the user's settings from the given BufferedReader.
     *
     * @param userInfo The BufferedReader to read user settings from.
     * @throws RuntimeException If an error occurs while reading the settings.
     */
    private void readSettings(BufferedReader userInfo){
        try {
            userName = userInfo.readLine();
            isAutoplay = Boolean.parseBoolean(userInfo.readLine());
            syncEnabled = Boolean.parseBoolean(userInfo.readLine());
            FCAPHintEnabled = Boolean.parseBoolean(userInfo.readLine());
            handHintEnabled = Boolean.parseBoolean(userInfo.readLine());
            MUSIC_DIFFERENCE = Integer.parseInt(userInfo.readLine());
            tolerance = Integer.parseInt(userInfo.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the user's settings to the given PrintWriter.
     *
     * @param pw The PrintWriter to write the user's settings to.
     */
    private void printSettings(PrintWriter pw){
        pw.println(userName);
        pw.println(isAutoplay);
        pw.println(syncEnabled);
        pw.println(FCAPHintEnabled);
        pw.println(handHintEnabled);
        pw.println(MUSIC_DIFFERENCE);
        pw.println(tolerance);
    }

    /**
     * Constructor that initializes a User object by reading data from a file.
     *
     * @param f The file containing the user data.
     */
    public User(File f){
        try{
            BufferedReader br;
            String temp;
            try{
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try{
                temp = br.readLine();
                String[] list1 = temp.split(" ");
                if(!list1[0].equals(GAME_VERSION)){
                    isValidUser=false;
                }
                readSettings(br);
                for(int i=0; i<Integer.parseInt(list1[1]); i++){
                    temp = br.readLine();
                    String[] list2 = temp.split(" ");
                    String collection = list2[0];
                    userData.put(collection, new HashMap<>());
                    for(int j=0; j<Integer.parseInt(list2[1]); j++){
                        temp = br.readLine();
                        String[] list3 = temp.split(" ");
                        userData.get(collection).put(list3[0], new HashMap<>());
                        userData.get(collection).get(list3[0]).put("BS", new ScoreData(br.readLine()));
                        userData.get(collection).get(list3[0]).put("MD", new ScoreData(br.readLine()));
                        userData.get(collection).get(list3[0]).put("AV", new ScoreData(br.readLine()));
                        if(Boolean.parseBoolean(list3[1])){
                            userData.get(collection).get(list3[0]).put("LG", new ScoreData(br.readLine()));
                        }
                    }
                }
            } catch (IOException | RuntimeException e) {
                isValidUser=false;
            }
            String[] s = f.getCanonicalPath().split("\\\\");
            userName = s[s.length-1].split("\\.")[0];
        } catch (IOException e) {
            isValidUser=false;
        }
    }

    /**
     * Exports the user's data to a file in the "Users" directory.
     */
    public void exportUser(){
        try{
            PrintWriter user = new PrintWriter(new FileWriter("Users/"+userName+".ptpuser"));
            user.println(GAME_VERSION+" "+collections.size());
            printSettings(user);
            for(int i=0; i<collections.size(); i++){
                String cd = collections.getSelectionString(i);
                user.println(cd+" "+songData.get(cd).size());
                for(int j=0; j<songData.get(cd).size(); j++){
                    String sd = songData.get(cd).get(j).getSongName();
                    user.println(sd+" "+songData.get(cd).get(j).hasLG());
                    user.println(userData.get(cd).get(sd).get("BS"));
                    user.println(userData.get(cd).get(sd).get("MD"));
                    user.println(userData.get(cd).get(sd).get("AV"));
                    if(songData.get(cd).get(j).hasLG()){
                        user.println(userData.get(cd).get(sd).get("LG"));
                    }
                }
            }
            user.close();
        } catch (IOException ignored) {}
    }

    /**
     * Initializes the user's data by iterating through all collections and songs, and setting default score data.
     */
    private void init(){
        for(int i=0; i<collections.size(); i++){
            String cd = collections.getSelectionString(i);
            userData.put(cd, new HashMap<>());
            for(int j=0; j<songData.get(cd).size(); j++){
                userData.get(cd).put(songData.get(cd).get(j).getSongName(), new HashMap<>());
                userData.get(cd).get(songData.get(cd).get(j).getSongName()).put("BS", new ScoreData());
                userData.get(cd).get(songData.get(cd).get(j).getSongName()).put("MD", new ScoreData());
                userData.get(cd).get(songData.get(cd).get(j).getSongName()).put("AV", new ScoreData());
                if(songData.get(cd).get(j).hasLG()){
                    userData.get(cd).get(songData.get(cd).get(j).getSongName()).put("LG", new ScoreData());
                }
            }
        }
        exportUser();
    }

    /**
     * Constructor to create a User object with the specified user name.
     *
     * @param userName The user's name.
     */
    public User(String userName){
        this.userName = userName;
        init();
    }

    /**
     * Default constructor that generates a random user name and initializes the user data.
     */
    public User(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<10; i++){
            sb.append((int) (Math.random() * 10));
        }
        userName = "User"+sb;
        init();
    }

    /**
     * Retrieves the score data for the current user, song, and level.
     *
     * @return The score data for the current user, song, and level.
     */
    public ScoreData getScoreData(){
        return userData.get(collectionDir).get(songDir).get(level);
    }

    /**
     * Retrieves FCAP (Full Combo/All Perfect) statistics for a given collection and level.
     *
     * @param cd The collection ID.
     * @param level The level ID (e.g., "LG").
     * @return A pair containing the total number of songs and a triple of FCAP statistics: new charts, full combos, and perfect scores.
     */
    public Pair<Integer, Triple<Integer, Integer, Integer>> getFCAPData(String cd, String level){
        Pair<Integer, Triple<Integer, Integer, Integer>> ans = new Pair<>(0, new Triple<>(0, 0, 0));
        if(level.equals("LG")){
            for(int i=0; i<songData.get(cd).size(); i++){
                if(songData.get(cd).get(i).hasLG()){
                    ans.first++;
                }
                if(
                        songData.get(cd).get(i).hasLG() &&
                        !userData.get(cd).get(songData.get(cd).get(i).getSongName()).get(level).newChart
                ){
                    ans.second.first++;
                }
                if(
                        songData.get(cd).get(i).hasLG() &&
                        userData.get(cd).get(songData.get(cd).get(i).getSongName()).get(level).fc
                ){
                    ans.second.second++;
                }
                if(
                        songData.get(cd).get(i).hasLG() &&
                        userData.get(cd).get(songData.get(cd).get(i).getSongName()).get(level).score==1000000
                ){
                    ans.second.third++;
                }
            }
        }
        else{
            for(int i=0; i<songData.get(cd).size(); i++){
                ans.first++;
                if(!userData.get(cd).get(songData.get(cd).get(i).getSongName()).get(level).newChart){
                    ans.second.first++;
                }
                if(userData.get(cd).get(songData.get(cd).get(i).getSongName()).get(level).fc){
                    ans.second.second++;
                }
                if(userData.get(cd).get(songData.get(cd).get(i).getSongName()).get(level).score==1000000){
                    ans.second.third++;
                }
            }
        }
        return ans;
    }

    /**
     * Retrieves the FCAP (Full Combo/All Perfect) data for the entire game at a specific difficulty level.
     * This method aggregates the FCAP data across all collections in the game.
     *
     * @param level The difficulty level to retrieve the FCAP data for (e.g., "BS", "MD", "AV", or "LG").
     * @return A {@code Pair} containing the total number of songs with FCAP data, and a {@code Triple} containing:
     *         - The count of songs with new charts,
     *         - The count of songs with a full combo (FC),
     *         - The count of songs with a perfect score (score equals 1000000).
     */
    public Pair<Integer, Triple<Integer, Integer, Integer>> getWholeGameFCAPData(String level) {
        Pair<Integer, Triple<Integer, Integer, Integer>> ans = new Pair<>(0, new Triple<>(0,0,0));
        for (int i = 0; i < collections.size(); i++) {
            Pair<Integer, Triple<Integer, Integer, Integer>> a = getFCAPData(collections.getSelectionString(i), level);
            ans.first += a.first;
            ans.second.first += a.second.first;
            ans.second.second += a.second.second;
            ans.second.third += a.second.third;
        }
        return ans;
    }
}

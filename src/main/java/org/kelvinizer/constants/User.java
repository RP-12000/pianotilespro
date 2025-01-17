package org.kelvinizer.constants;

import org.kelvinizer.game.gamewindow.ScoreData;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.classes.Triple;

import java.io.*;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.*;

public class User {
    public String userName;
    public boolean isAutoplay = false;
    public boolean syncEnabled = true;
    public boolean FCAPHintEnabled = true;
    public boolean handHintEnabled = false;
    public int MUSIC_DIFFERENCE = 0;
    public int tolerance = 80;
    public HashMap<String, HashMap<String, HashMap<String, ScoreData>>> userData = new HashMap<>();
    public boolean isValidUser = true;

    private void readSettings(BufferedReader userInfo){
        try{
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

    private void printSettings(PrintWriter pw){
        pw.println(userName);
        pw.println(isAutoplay);
        pw.println(syncEnabled);
        pw.println(FCAPHintEnabled);
        pw.println(handHintEnabled);
        pw.println(MUSIC_DIFFERENCE);
        pw.println(tolerance);
    }

    public User(File f){
        try{
            String[] s = f.getCanonicalPath().split("\\\\");
            userName = s[s.length-1].split("\\.")[0];
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
        } catch (IOException e) {
            isValidUser=false;
        }
    }

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

    public User(String userName){
        this.userName = userName;
        init();
    }

    public User(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<10; i++){
            sb.append((int) (Math.random() * 10));
        }
        userName = "User"+sb;
        init();
    }

    public ScoreData getScoreData(){
        return userData.get(collectionDir).get(songDir).get(level);
    }

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

    public Pair<Integer, Triple<Integer, Integer, Integer>> getWholeGameFCAPData(String level){
        Pair<Integer, Triple<Integer, Integer, Integer>> ans = new Pair<>(0, new Triple<>(0,0,0));
        for(int i=0; i<collections.size(); i++){
            Pair<Integer, Triple<Integer, Integer, Integer>> a = getFCAPData(collections.getSelectionString(i), level);
            ans.first+=a.first;
            ans.second.first+=a.second.first;
            ans.second.second+=a.second.second;
            ans.second.third+=a.second.third;
        }
        return ans;
    }
}

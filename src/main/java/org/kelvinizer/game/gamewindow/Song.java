package org.kelvinizer.game.gamewindow;

import org.kelvinizer.constants.Selection;
import org.kelvinizer.support.classes.Pair;

import java.io.*;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.getResourcePathName;

public class Song {
    private final String songName, composer, illustration;
    private final boolean lg;
    private boolean newSong = false;
    public final HashMap<String, Pair<String, Double>> data = new HashMap<>();
    public final HashMap<String, ScoreData> historyBest = new HashMap<>();

    public Song(String dir) throws RuntimeException {
        String[] splitedDir = dir.split("/");
        songName = splitedDir[splitedDir.length-1];
        try{
            BufferedReader br = new BufferedReader(new FileReader(getResourcePathName(dir+"/credits.txt")));
            composer = br.readLine();
            illustration = br.readLine();
            lg = Boolean.parseBoolean(br.readLine());
            String[] temp = br.readLine().split(" ");
            data.put("BS", new Pair<>(temp[0], Double.parseDouble(temp[1])));
            temp = br.readLine().split(" ");
            data.put("MD", new Pair<>(temp[0], Double.parseDouble(temp[1])));
            temp = br.readLine().split(" ");
            data.put("AV", new Pair<>(temp[0], Double.parseDouble(temp[1])));
            if(lg){
                temp = br.readLine().split(" ");
                data.put("LG", new Pair<>(temp[0], Double.parseDouble(temp[1])));
            }
        } catch (IOException e){
            throw new RuntimeException("Error when reading credits for this song: "+songName);
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(getResourcePathName(dir+"/user.txt")));
            historyBest.put("BS", new ScoreData(br.readLine()));
            historyBest.put("MD", new ScoreData(br.readLine()));
            historyBest.put("AV", new ScoreData(br.readLine()));
            if(lg){
                historyBest.put("LG", new ScoreData(br.readLine()));
            }
        } catch (IOException | RuntimeException e) {
            PrintWriter pw;
            try {
                pw = new PrintWriter(getResourcePathName("")+dir+"/user.txt");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            pw.println(true);
            pw.println("""
                    0 0 -1 0 false
                    0 0 -1 0 false
                    0 0 -1 0 false
                    """);
            historyBest.put("BS", new ScoreData());
            historyBest.put("MD", new ScoreData());
            historyBest.put("AV", new ScoreData());
            if(lg){
                pw.println("0 0 -1 0 false");
                historyBest.put("LG", new ScoreData());
            }
            newSong = true;
            pw.close();
        }

        if(!Chart.isValidChart(dir, "BS")){
            throw new RuntimeException("Broken Basic Chart");
        }
        else if(!Chart.isValidChart(dir, "MD")){
            throw new RuntimeException("Broken MD Chart");
        }
        else if(!Chart.isValidChart(dir, "AV")){
            throw new RuntimeException("Broken MD Chart");
        }
        else if(lg && !Chart.isValidChart(dir, "LG")){
            throw new RuntimeException("Broken MD Chart");
        }
    }

    public boolean hasLG(){
        return lg;
    }

    public String getComposer() {
        return composer;
    }

    public String getIllustration() {
        return illustration;
    }

    public Pair<String, Double> getCharterData() {
        return data.get(Selection.level);
    }

    public Pair<String, Double> getCharterData(String s){
        return data.get(s);
    }

    public String getSongName() {
        return songName;
    }

    public boolean isNewSong() {
        return newSong;
    }
}

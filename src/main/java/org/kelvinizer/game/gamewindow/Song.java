package org.kelvinizer.game.gamewindow;

import org.kelvinizer.constants.Selection;
import org.kelvinizer.support.classes.Pair;

import java.io.*;
import java.util.HashMap;

import static org.kelvinizer.constants.Control.firstTimeOpen;
import static org.kelvinizer.constants.Control.getResourceInput;

public class Song {
    private final String absoluteDir, songName, composer, illustration;
    private final boolean lg;
    private final HashMap<String, Pair<String, Double>> data = new HashMap<>();
    public final double OFFSET;

    public Song(String dir) throws RuntimeException {
        absoluteDir = dir;
        String[] sp = dir.split("/");
        songName = sp[sp.length-1];
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(getResourceInput(dir+"/credits.txt")));
            composer = br.readLine();
            illustration = br.readLine();
            OFFSET = Double.parseDouble(br.readLine());
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
        if(firstTimeOpen){
            if(Chart.invalidChart(this, "BS")){
                throw new RuntimeException("Broken Basic Chart");
            }
            else if(Chart.invalidChart(this, "MD")){
                throw new RuntimeException("Broken MD Chart");
            }
            else if(Chart.invalidChart(this, "AV")){
                throw new RuntimeException("Broken MD Chart");
            }
            else if(lg && Chart.invalidChart(this, "LG")){
                throw new RuntimeException("Broken MD Chart");
            }
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

    public String getAbsoluteDir(){
        return absoluteDir;
    }
}

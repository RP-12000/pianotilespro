package org.kelvinizer.gamewindow;

import org.kelvinizer.support.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Song {
    private final String composer, illustration;
    private final boolean lg;
    private final Pair<String, Double> basicData, mediumData, advancedData, legendaryData;

    public Song(String dir) throws IOException, NullPointerException {
        BufferedReader br = new BufferedReader(new FileReader(dir+"/credits.txt"));
        composer = br.readLine();
        illustration = br.readLine();
        lg = Boolean.parseBoolean(br.readLine());
        String[] temp = br.readLine().split(" ");
        basicData = new Pair<>(temp[0], Double.parseDouble(temp[1]));
        temp = br.readLine().split(" ");
        mediumData = new Pair<>(temp[0], Double.parseDouble(temp[1]));
        temp = br.readLine().split(" ");
        advancedData = new Pair<>(temp[0], Double.parseDouble(temp[1]));
        if(lg){
            temp = br.readLine().split(" ");
            legendaryData = new Pair<>(temp[0], Double.parseDouble(temp[1]));
        }
        else{
            legendaryData = null;
        }
        if(!Chart.isValidChart(dir+"/BS")){
            throw new RuntimeException("Broken Basic Chart");
        }
        else if(!Chart.isValidChart(dir+"/MD")){
            throw new RuntimeException("Broken MD Chart");
        }
        else if(!Chart.isValidChart(dir+"/AV")){
            throw new RuntimeException("Broken AV Chart");
        }
        else if(lg && !Chart.isValidChart(dir + "/LG")){
            throw new RuntimeException("Broken LG Chart");
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

    public Pair<String, Double> getBasicData() {
        return basicData;
    }

    public Pair<String, Double> getMediumData() {
        return mediumData;
    }

    public Pair<String, Double> getAdvancedData() {
        return advancedData;
    }

    public Pair<String, Double> getLegendaryData() {
        return legendaryData;
    }
}

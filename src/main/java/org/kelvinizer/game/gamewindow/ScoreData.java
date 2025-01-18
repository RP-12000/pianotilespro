package org.kelvinizer.game.gamewindow;

import org.kelvinizer.support.classes.BoundedString;

import java.awt.*;

import static org.kelvinizer.constants.JudgementLimits.maxScore;

public class ScoreData {
    public int score;
    public int maxCombo;
    public double acc;
    public double worstHit;
    public boolean fc;
    public boolean newChart;

    public ScoreData(String s){
        String[] t = s.split(" ");
        score = Integer.parseInt(t[0]);
        maxCombo = Integer.parseInt(t[1]);
        acc = Double.parseDouble(t[2]);
        worstHit = Double.parseDouble(t[3]);
        fc = Boolean.parseBoolean(t[4]);
        newChart = Boolean.parseBoolean(t[5]);
    }

    public ScoreData(){
        this(0, 0, 0, Double.POSITIVE_INFINITY, false, true);
    }

    public ScoreData(int score, int maxCombo, double acc, double worstHit, boolean fc, boolean newChart){
        this.score = score;
        this.maxCombo = maxCombo;
        this.acc = acc;
        this.worstHit = worstHit;
        this.fc = fc;
        this.newChart = newChart;
    }

    public String getScoreString(){
        return toScoreString(this.score);
    }

    public static String toScoreString(int score){
        int zeroCount = 0;
        int tempScore = score;
        StringBuilder prefix = new StringBuilder();
        while(tempScore<maxScore && zeroCount<Math.log10(maxScore)){
            prefix.append("0");
            zeroCount++;
            tempScore=tempScore*10;
        }
        return prefix.toString()+score;
    }

    public String getAccuracyString(){
        if(acc==Double.POSITIVE_INFINITY){
            return "N/A %";
        }
        return String.format("%.2f", acc*100)+" %";
    }

    public String getWorstHitString(){
        return String.format("%.2f", worstHit*1000)+" ms";
    }

    public void setGradeString(BoundedString grade){
        if(fc){
            grade.setStringColor(Color.BLUE);
        }
        else{
            grade.setStringColor(Color.WHITE);
        }
        if(score<700000){
            grade.setString("F");
        }
        else if(score<820000){
            grade.setString("C");
        }
        else if(score<880000){
            grade.setString("B");
        }
        else if(score<920000){
            grade.setString("A");
        }
        else if(score<960000){
            grade.setString("S");
        }
        else if(score<1000000){
            grade.setString("V");
        }
        else{
            if(worstHit<=1/60.0){
                grade.setString("T");
                grade.setStringColor(Color.MAGENTA);
            }
            else{
                grade.setString("P");
                grade.setStringColor(Color.GREEN);
            }
        }
    }

    @Override
    public String toString(){
        return score+" "+maxCombo+" "+acc+" "+worstHit+" "+fc+" "+newChart;
    }
}
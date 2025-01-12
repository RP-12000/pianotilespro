package org.kelvinizer.game.gametext;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.Lane;
import org.kelvinizer.support.classes.BoundedString;

import java.awt.*;

public class ChartText {
    public final BoundedString score = new BoundedString("0000000", 30, 990, 108);
    public final BoundedString songName = new BoundedString(Selection.songDir, 15, 90, 660, 140, 150);
    public final BoundedString level = new BoundedString(Selection.level+" "+Selection.chartConstant, 15, 990, 660);
    public final BoundedString combo = new BoundedString("", 30, 90, 108, 140, 150);
    public final BoundedString acc = new BoundedString("", 15, 990, 144);
    public final BoundedString autoplay = new BoundedString("[Autoplay]", 15, 90, 144);

    public final BoundedString perfect = new BoundedString("");
    public final BoundedString good = new BoundedString("");
    public final BoundedString bad = new BoundedString("");
    public final BoundedString miss = new BoundedString("");
    public final BoundedString early = new BoundedString("");
    public final BoundedString late = new BoundedString("");
    public final BoundedString maxCombo = new BoundedString("");
    public final BoundedString worstHit = new BoundedString("");

    private final double minVisibleCombo = 3;
    private final double goodPercentage = 0.65;
    private final double comboScorePercentage = 0.1;
    private final int maxScore = 1000000;

    private void setColor(){
        perfect.setStringColor(Color.GREEN);
        good.setStringColor(Color.BLUE);
        bad.setStringColor(Color.RED);
        autoplay.setStringColor(Color.GREEN);
    }

    private void setMaxStringSizes(){
        songName.setMaxStringSize(15);
        combo.setMaxStringSize(30);
    }

    public ChartText(){
        setColor();
        setMaxStringSizes();
    }

    public void updateText(){
        perfect.setString("Perfect: "+ (int)Lane.perfect);
        good.setString("Good: "+(int)Lane.good);
        bad.setString("Bad "+(int)Lane.bad);
        miss.setString("Miss: "+(int)Lane.miss);
        early.setString("Miss: "+(int)Lane.early);
        late.setString("Miss: "+(int)Lane.late);
        worstHit.setString("Miss: "+(int)Lane.worstHit);
        maxCombo.setString("Max Combo: "+(int)Lane.maxCombo);
        double currentAccuracy = (Lane.perfect+Lane.good*goodPercentage)/Lane.total;
        if(Lane.total!=0){
            acc.setString(currentAccuracy*100+" %");
        }
        else{
            acc.setString("N/A %");
        }
        if(Lane.currentCombo>=minVisibleCombo){
            combo.setString((int)Lane.currentCombo+" COMBO");
        }
        else{
            combo.setString("");
        }
        int currentScore = (int) Math.round(
                currentAccuracy*Lane.total/Chart.noteCount*maxScore*(1-comboScorePercentage)+
                Lane.maxCombo/ Chart.noteCount*maxScore*comboScorePercentage
        );
        int zeroCount = 0;
        int tempScore = currentScore;
        StringBuilder prefix = new StringBuilder();
        while(tempScore<maxScore && zeroCount<Math.log10(maxScore)){
            prefix.append("0");
            zeroCount++;
            tempScore=tempScore*10;
        }
        score.setString(prefix.toString()+currentScore);
    }

    public void drawRegularText(Graphics2D g2d){
        score.render(g2d);
        songName.render(g2d);
        level.render(g2d);
        combo.render(g2d);
        acc.render(g2d);
        if(Control.isAutoplay){
            autoplay.render(g2d);
        }
    }

    public void drawPausedText(){

    }
}
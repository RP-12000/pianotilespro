package org.kelvinizer.game.gametext;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.Lane;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class ChartText implements Drawable {
    public final BoundedString score = new BoundedString("0000000", 30, 990, 108);
    public final BoundedString songName = new BoundedString(Selection.songDir, 20, 90, 660, 140, 150);
    public final BoundedString level = new BoundedString(Selection.level+" "+Selection.chartConstant, 20, 990, 660);
    public final BoundedString combo = new BoundedString("", 30, 90, 108, 140, 150);
    public final BoundedString acc = new BoundedString("", 15, 990, 144);
    public final BoundedString userName = new BoundedString(Control.userName, 15, 90, 144);

    public final BoundedString perfect = new BoundedString("", 18, 90, 300, 140, 30);
    public final BoundedString good = new BoundedString("", 18, 90, 340, 140, 30);
    public final BoundedString bad = new BoundedString("", 18, 90, 380, 140, 30);
    public final BoundedString miss = new BoundedString("", 18, 90, 420, 140, 30);
    public final BoundedString early = new BoundedString("", 18, 990, 300, 140, 30);
    public final BoundedString late = new BoundedString("", 18, 990, 340, 140, 30);
    public final BoundedString maxCombo = new BoundedString("", 18, 990, 380, 140, 30);
    public final BoundedString worstHit = new BoundedString("", 18, 990, 420, 140, 30);

    private final double minVisibleCombo = 3;
    private final double goodPercentage = 0.65;
    private final double comboScorePercentage = 0.1;
    private final int maxScore = 1000000;

    private void setMaxStringSizes(){
        songName.setMaxStringSize(20);
        combo.setMaxStringSize(30);
        perfect.setMaxStringSize(20);
        good.setMaxStringSize(20);
        bad.setMaxStringSize(20);
        miss.setMaxStringSize(20);
        early.setMaxStringSize(20);
        late.setMaxStringSize(20);
        maxCombo.setMaxStringSize(20);
        worstHit.setMaxStringSize(20);
    }

    public ChartText(){
        setMaxStringSizes();
    }

    public void updateText(){
        double currentAccuracy = (Lane.perfect+Lane.good*goodPercentage)/Lane.total;
        if(Lane.total!=0){
            acc.setString(String.format("%.2f", currentAccuracy*100)+" %");
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
        perfect.setString("Perfect: "+ (int)Lane.perfect);
        good.setString("Good: "+(int)Lane.good);
        bad.setString("Bad "+(int)Lane.bad);
        miss.setString("Miss: "+(int)Lane.miss);
        early.setString("Early: "+(int)Lane.early);
        late.setString("Late: "+(int)Lane.late);
        maxCombo.setString("Max Combo: "+(int)Lane.maxCombo);
        worstHit.setString("Worst Hit: "+String.format("%.2f", Lane.worstHit*1000)+" ms");
    }

    @Override
    public void render(Graphics2D g2d){
        score.render(g2d);
        songName.render(g2d);
        level.render(g2d);
        combo.render(g2d);
        acc.render(g2d);
        if(Control.isAutoplay){
            userName.setStringColor(Color.GREEN);
        }
        userName.render(g2d);
        perfect.render(g2d);
        good.render(g2d);
        bad.render(g2d);
        miss.render(g2d);
        early.render(g2d);
        late.render(g2d);
        maxCombo.render(g2d);
        worstHit.render(g2d);
    }
}
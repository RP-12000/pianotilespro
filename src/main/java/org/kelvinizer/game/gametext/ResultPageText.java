package org.kelvinizer.game.gametext;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.dynamic.DynamicImage;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.dynamic.DynamicString;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.Lane;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;

import static org.kelvinizer.constants.JudgementLimits.*;

public class ResultPageText {
    public final DynamicString scoreText = new DynamicString("", 80, 280, 175);
    public final DynamicString newBestScore = new DynamicString("", 15);
    public final DynamicString grade = new DynamicString("", 120, 860, 175);

    public final DynamicString maxCombo = new DynamicString("", 20, 650, 340, 120, 50);
    public final DynamicString maxComboVerdict = new DynamicString("Max Combo", 15, 650, 420);
    public final DynamicString bestMaxCombo = new DynamicString("", 15);
    public final DynamicString worstHit = new DynamicString("", 30, 800, 340, 120, 50);
    public final DynamicString worstHitVerdict = new DynamicString("Worst Hit", 15, 800, 420);
    public final DynamicString bestWorstHit = new DynamicString("", 15);
    public final DynamicString accuracy = new DynamicString("", 30, 950, 340, 120, 50);
    public final DynamicString accuracyVerdict = new DynamicString("Accuracy", 15, 950, 420);
    public final DynamicString bestAccuracy = new DynamicString("", 15);

    public final DynamicString perfect = new DynamicString("", 15, 695, 540, 150, 30);
    public final DynamicString bad = new DynamicString("", 15, 695, 585, 150, 30);
    public final DynamicString miss = new DynamicString("", 15, 695, 630, 150, 30);
    public final DynamicString good = new DynamicString("", 15, 905, 540, 150, 30);
    public final DynamicString early = new DynamicString("", 15, 905, 585, 150, 30);
    public final DynamicString late = new DynamicString("", 15, 905, 630, 150, 30);

    public final DynamicString scoreBounds = new DynamicString("", 0, 540, 175, 1000, 150);
    public final DynamicString accBounds = new DynamicString("", 0, 810, 375, 480, 150);
    public final DynamicString noteBounds = new DynamicString("", 0, 810, 585, 480, 150);

    public final DynamicString songName = new DynamicString(Selection.songDir, 15, 136, 630, 192, 60);
    public final DynamicString level = new DynamicString(Selection.level+" "+Selection.chartConstant, 15, 280, 630, 96, 60);
    public final DynamicString userName = new DynamicString(Control.userName, 15, 424, 630, 192, 60);

    public final DynamicImage jacket = new DynamicImage(new CRect(280, 450, 480, 300));

    private void addMotion(DynamicString ds){
        ds.addHorizontalMotion(new Motion(
                0, 1.5,
                ds.getBoundedString().getBounds().getX()- ReferenceWindow.REF_WIN_W,
                ds.getBoundedString().getBounds().getX(),
                0.5
        ));
    }

    private void initMotion(){
        jacket.addHorizontalMotion(new Motion(0, 1.5, -800, 280, 0.5));
        addMotion(scoreText);
        addMotion(newBestScore);
        addMotion(grade);

        addMotion(maxCombo);
        addMotion(maxComboVerdict);
        addMotion(bestMaxCombo);
        addMotion(worstHit);
        addMotion(worstHitVerdict);
        addMotion(bestWorstHit);
        addMotion(accuracy);
        addMotion(accuracyVerdict);
        addMotion(bestAccuracy);

        addMotion(perfect);
        addMotion(good);
        addMotion(bad);
        addMotion(miss);
        addMotion(early);
        addMotion(late);

        addMotion(scoreBounds);
        addMotion(accBounds);
        addMotion(noteBounds);

        addMotion(songName);
        addMotion(level);
        addMotion(userName);
    }

    private void initBound(DynamicString ds){
        ds.getBoundedString().getBounds().setOutlineColor(grade.getBoundedString().getStringColor());
        ds.getBoundedString().getBounds().setOutlineThickness(3.0f);
    }

    private void initBounds(){
        initBound(songName);
        initBound(level);
        initBound(userName);
        initBound(scoreBounds);
        initBound(accBounds);
        initBound(noteBounds);
        if(Control.isAutoplay){
            userName.getBoundedString().setStringColor(Color.GREEN);
        }
    }

    private void initMaxStringSize(){
        songName.getBoundedString().setMaxStringSize(20);
        userName.getBoundedString().setMaxStringSize(20);
        level.getBoundedString().setMaxStringSize(20);
        perfect.getBoundedString().setMaxStringSize(30);
        good.getBoundedString().setMaxStringSize(30);
        bad.getBoundedString().setMaxStringSize(30);
        miss.getBoundedString().setMaxStringSize(30);
        early.getBoundedString().setMaxStringSize(30);
        late.getBoundedString().setMaxStringSize(30);
        maxCombo.getBoundedString().setMaxStringSize(30);
        worstHit.getBoundedString().setMaxStringSize(30);
        accuracy.getBoundedString().setMaxStringSize(30);
    }

    private void calculateGrade(int score){
        if(Lane.maxCombo == Chart.noteCount){
            grade.getBoundedString().setStringColor(Color.BLUE);
        }
        if(score<700000){
            grade.getBoundedString().setString("F");
        }
        else if(score<820000){
            grade.getBoundedString().setString("C");
        }
        else if(score<880000){
            grade.getBoundedString().setString("B");
        }
        else if(score<920000){
            grade.getBoundedString().setString("A");
        }
        else if(score<960000){
            grade.getBoundedString().setString("S");
        }
        else if(score<1000000){
            grade.getBoundedString().setString("V");
        }
        else{
            grade.getBoundedString().setStringColor(Color.GREEN);
            grade.getBoundedString().setString("P");
        }
    }

    private void setText(){
        double currentAccuracy = (Lane.perfect+Lane.good*goodPercentage)/Lane.total;
        if(Lane.total!=0){
            accuracy.getBoundedString().setString(String.format("%.2f", currentAccuracy*100)+" %");
        }
        else{
            accuracy.getBoundedString().setString("N/A %");
        }
        int currentScore = (int) Math.round(
                currentAccuracy*Lane.total/ Chart.noteCount*maxScore*(1-comboScorePercentage)+
                        Lane.maxCombo/ Chart.noteCount*maxScore*comboScorePercentage
        );
        calculateGrade(currentScore);
        initBounds();
        int zeroCount = 0;
        int tempScore = currentScore;
        StringBuilder prefix = new StringBuilder();
        while(tempScore<maxScore && zeroCount<Math.log10(maxScore)){
            prefix.append("0");
            zeroCount++;
            tempScore=tempScore*10;
        }
        scoreText.getBoundedString().setString(prefix.toString()+currentScore);
        perfect.getBoundedString().setString("Perfect: "+ (int)Lane.perfect);
        good.getBoundedString().setString("Good: "+(int)Lane.good);
        bad.getBoundedString().setString("Bad "+(int)Lane.bad);
        miss.getBoundedString().setString("Miss: "+(int)Lane.miss);
        early.getBoundedString().setString("Early: "+(int)Lane.early);
        late.getBoundedString().setString("Late: "+(int)Lane.late);
        maxCombo.getBoundedString().setString(String.valueOf((int)Lane.maxCombo));
        worstHit.getBoundedString().setString(String.format("%.2f", Lane.worstHit*1000)+" ms");
    }

    public ResultPageText(DynamicMotionManager dmm){
        setText();
        initMotion();
        initMaxStringSize();
        jacket.setImage(Selection.songJacket);
        dmm.addDynamicObject(scoreText);
        dmm.addDynamicObject(newBestScore);
        dmm.addDynamicObject(grade);

        dmm.addDynamicObject(maxCombo);
        dmm.addDynamicObject(maxComboVerdict);
        dmm.addDynamicObject(bestMaxCombo);
        dmm.addDynamicObject(worstHit);
        dmm.addDynamicObject(worstHitVerdict);
        dmm.addDynamicObject(bestWorstHit);
        dmm.addDynamicObject(accuracy);
        dmm.addDynamicObject(accuracyVerdict);
        dmm.addDynamicObject(bestAccuracy);

        dmm.addDynamicObject(perfect);
        dmm.addDynamicObject(good);
        dmm.addDynamicObject(bad);
        dmm.addDynamicObject(miss);
        dmm.addDynamicObject(early);
        dmm.addDynamicObject(late);

        dmm.addDynamicObject(scoreBounds);
        dmm.addDynamicObject(accBounds);
        dmm.addDynamicObject(noteBounds);

        dmm.addDynamicObject(songName);
        dmm.addDynamicObject(level);
        dmm.addDynamicObject(userName);

        dmm.addDynamicObject(jacket);
    }
}
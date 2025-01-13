package org.kelvinizer.game.gametext;

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
    public final DynamicString scoreText = new DynamicString("", 50);
    public final DynamicString newBestScore = new DynamicString("", 15);
    public final DynamicString grade = new DynamicString("", 100);

    public final DynamicString maxCombo = new DynamicString("", 30);
    public final DynamicString maxComboVerdict = new DynamicString("Max Combo", 15);
    public final DynamicString bestMaxCombo = new DynamicString("", 15);
    public final DynamicString worstHit = new DynamicString("", 30);
    public final DynamicString worstHitVerdict = new DynamicString("Worst Hit", 15);
    public final DynamicString bestWorstHit = new DynamicString("", 15);
    public final DynamicString accuracy = new DynamicString("", 30);
    public final DynamicString accuracyVerdict = new DynamicString("Accuracy", 15);
    public final DynamicString bestAccuracy = new DynamicString("", 15);

    public final DynamicString perfect = new DynamicString("", 15);
    public final DynamicString perfectVerdict = new DynamicString("Perfect", 10);
    public final DynamicString good = new DynamicString("", 15);
    public final DynamicString goodVerdict = new DynamicString("Good", 10);
    public final DynamicString bad = new DynamicString("", 15);
    public final DynamicString badVerdict = new DynamicString("Bad", 10);
    public final DynamicString miss = new DynamicString("", 15);
    public final DynamicString missVerdict = new DynamicString("Miss", 10);
    public final DynamicString early = new DynamicString("", 8);
    public final DynamicString earlyVerdict = new DynamicString("Early", 12);
    public final DynamicString late = new DynamicString("", 8);
    public final DynamicString lateVerdict = new DynamicString("Early", 12);

    public final DynamicString scoreBounds = new DynamicString("", 0);
    public final DynamicString accBounds = new DynamicString("", 0);
    public final DynamicString noteBounds = new DynamicString("", 0);

    public final DynamicImage jacket = new DynamicImage(new CRect(300, 360, 480, 300));

    private void addMotion(){
        double introTime = 3.0;
        double acc = 0.3;
        jacket.addHorizontalMotion(new Motion(0, introTime, -780, 300, acc));

        scoreText.addHorizontalMotion(new Motion(
                0, introTime,
                scoreText.getBoundedString().getBounds().getX()- ReferenceWindow.REF_WIN_W,
                scoreText.getBoundedString().getBounds().getX(),
                acc
        ));

        newBestScore.addHorizontalMotion(new Motion(
                0, introTime,
                newBestScore.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                newBestScore.getBoundedString().getBounds().getX(),
                acc
        ));

        grade.addHorizontalMotion(new Motion(
                0, introTime,
                grade.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                grade.getBoundedString().getBounds().getX(),
                acc
        ));

        maxCombo.addHorizontalMotion(new Motion(
                0, introTime,
                maxCombo.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                maxCombo.getBoundedString().getBounds().getX(),
                acc
        ));

        maxComboVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                maxComboVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                maxComboVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        bestMaxCombo.addHorizontalMotion(new Motion(
                0, introTime,
                bestMaxCombo.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                bestMaxCombo.getBoundedString().getBounds().getX(),
                acc
        ));

        worstHit.addHorizontalMotion(new Motion(
                0, introTime,
                worstHit.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                worstHit.getBoundedString().getBounds().getX(),
                acc
        ));

        worstHitVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                worstHitVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                worstHitVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        bestWorstHit.addHorizontalMotion(new Motion(
                0, introTime,
                bestWorstHit.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                bestWorstHit.getBoundedString().getBounds().getX(),
                acc
        ));

        accuracy.addHorizontalMotion(new Motion(
                0, introTime,
                accuracy.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                accuracy.getBoundedString().getBounds().getX(),
                acc
        ));

        accuracyVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                accuracyVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                accuracyVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        bestAccuracy.addHorizontalMotion(new Motion(
                0, introTime,
                bestAccuracy.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                bestAccuracy.getBoundedString().getBounds().getX(),
                acc
        ));

        perfect.addHorizontalMotion(new Motion(
                0, introTime,
                perfect.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                perfect.getBoundedString().getBounds().getX(),
                acc
        ));

        perfectVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                perfectVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                perfectVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        good.addHorizontalMotion(new Motion(
                0, introTime,
                good.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                good.getBoundedString().getBounds().getX(),
                acc
        ));

        goodVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                goodVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                goodVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        bad.addHorizontalMotion(new Motion(
                0, introTime,
                bad.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                bad.getBoundedString().getBounds().getX(),
                acc
        ));

        badVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                badVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                badVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        miss.addHorizontalMotion(new Motion(
                0, introTime,
                miss.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                miss.getBoundedString().getBounds().getX(),
                acc
        ));

        missVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                missVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                missVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        early.addHorizontalMotion(new Motion(
                0, introTime,
                early.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                early.getBoundedString().getBounds().getX(),
                acc
        ));

        earlyVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                earlyVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                earlyVerdict.getBoundedString().getBounds().getX(),
                acc
        ));

        late.addHorizontalMotion(new Motion(
                0, introTime,
                late.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                late.getBoundedString().getBounds().getX(),
                acc
        ));

        lateVerdict.addHorizontalMotion(new Motion(
                0, introTime,
                lateVerdict.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                lateVerdict.getBoundedString().getBounds().getX(),
                acc
        ));
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
        int zeroCount = 0;
        int tempScore = currentScore;
        StringBuilder prefix = new StringBuilder();
        while(tempScore<maxScore && zeroCount<Math.log10(maxScore)){
            prefix.append("0");
            zeroCount++;
            tempScore=tempScore*10;
        }
        scoreText.getBoundedString().setString(prefix.toString()+currentScore);
        perfect.getBoundedString().setString(String.valueOf((int) Lane.perfect));
        good.getBoundedString().setString(String.valueOf((int) Lane.good));
        bad.getBoundedString().setString(String.valueOf((int) Lane.bad));
        miss.getBoundedString().setString(String.valueOf((int) Lane.miss));
        early.getBoundedString().setString(String.valueOf((int) Lane.early));
        late.getBoundedString().setString(String.valueOf((int) Lane.late));
        maxCombo.getBoundedString().setString(String.valueOf((int) Lane.maxCombo));
        worstHit.getBoundedString().setString((Lane.worstHit * 1000) + " ms");
    }

    public ResultPageText(DynamicMotionManager dmm){
        setText();
        addMotion();
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
        dmm.addDynamicObject(perfectVerdict);
        dmm.addDynamicObject(good);
        dmm.addDynamicObject(goodVerdict);
        dmm.addDynamicObject(bad);
        dmm.addDynamicObject(badVerdict);
        dmm.addDynamicObject(miss);
        dmm.addDynamicObject(missVerdict);
        dmm.addDynamicObject(early);
        dmm.addDynamicObject(earlyVerdict);
        dmm.addDynamicObject(late);
        dmm.addDynamicObject(lateVerdict);

        dmm.addDynamicObject(scoreBounds);
        dmm.addDynamicObject(accBounds);
        dmm.addDynamicObject(noteBounds);

        dmm.addDynamicObject(jacket);
    }
}
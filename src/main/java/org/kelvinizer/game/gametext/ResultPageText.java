package org.kelvinizer.game.gametext;

import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.dynamic.DynamicImage;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.dynamic.DynamicString;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.Lane;
import org.kelvinizer.game.gamewindow.ScoreData;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.Motion;

import java.awt.*;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;
import static org.kelvinizer.constants.JudgementLimits.*;
import static org.kelvinizer.constants.Selection.*;

/**
 * Class responsible for managing and rendering the result page text and related graphics
 * during the game.
 * It updates and displays various statistics such as score, accuracy, combo, and worst hit,
 * as well as handling the creation of dynamic motion for UI elements.
 * @author Boyan Hu
 */
public class ResultPageText {
    /**
     * The score text displayed on the result page, representing the user's score.
     */
    private final DynamicString scoreText = new DynamicString("", 80, 280, 175);

    /**
     * The text displayed when the user achieves a new best score.
     */
    private final DynamicString newBestScore = new DynamicString("", 30, 600, 180);

    /**
     * The grade or rating text displayed on the result page.
     */
    private final DynamicString grade = new DynamicString("", 120, 860, 175);

    /**
     * The text representing the maximum combo achieved by the user.
     */
    private final DynamicString maxCombo = new DynamicString("", 20, 650, 340, 120, 50);

    /**
     * The verdict text displayed next to the max combo statistic, labeled "Max Combo".
     */
    private final DynamicString maxComboVerdict = new DynamicString("Max Combo", 15, 650, 420);

    /**
     * The best max combo achieved by the user.
     */
    private final DynamicString bestMaxCombo = new DynamicString("", 15, 650, 380);

    /**
     * The worst hit text displayed for the user, showing their worst hit result.
     */
    private final DynamicString worstHit = new DynamicString("", 30, 800, 340, 120, 50);

    /**
     * The verdict text displayed next to the worst hit statistic, labeled "Worst Hit".
     */
    private final DynamicString worstHitVerdict = new DynamicString("Worst Hit", 15, 800, 420);

    /**
     * The best worst hit result achieved by the user.
     */
    private final DynamicString bestWorstHit = new DynamicString("", 15, 800, 380);

    /**
     * The accuracy text displayed for the user, showing their accuracy result.
     */
    private final DynamicString accuracy = new DynamicString("", 30, 950, 340, 120, 50);

    /**
     * The verdict text displayed next to the accuracy statistic, labeled "Accuracy".
     */
    private final DynamicString accuracyVerdict = new DynamicString("Accuracy", 15, 950, 420);

    /**
     * The best accuracy result achieved by the user.
     */
    private final DynamicString bestAccuracy = new DynamicString("", 15, 950, 380);

    /**
     * The number of perfect hits made by the user.
     */
    private final DynamicString perfect = new DynamicString("", 15, 695, 540, 150, 30);

    /**
     * The number of bad hits made by the user.
     */
    private final DynamicString bad = new DynamicString("", 15, 695, 585, 150, 30);

    /**
     * The number of missed hits made by the user.
     */
    private final DynamicString miss = new DynamicString("", 15, 695, 630, 150, 30);

    /**
     * The number of good hits made by the user.
     */
    private final DynamicString good = new DynamicString("", 15, 905, 540, 150, 30);

    /**
     * The number of early hits made by the user.
     */
    private final DynamicString early = new DynamicString("", 15, 905, 585, 150, 30);

    /**
     * The number of late hits made by the user.
     */
    private final DynamicString late = new DynamicString("", 15, 905, 630, 150, 30);

    /**
     * The boundaries of the score display area.
     */
    private final DynamicString scoreBounds = new DynamicString("", 0, 540, 175, 1000, 150);

    /**
     * The boundaries of the accuracy display area.
     */
    private final DynamicString accBounds = new DynamicString("", 0, 810, 375, 480, 150);

    /**
     * The boundaries of the note display area.
     */
    private final DynamicString noteBounds = new DynamicString("", 0, 810, 585, 480, 150);

    /**
     * The name of the song being displayed, with special formatting to replace underscores with spaces.
     */
    private final DynamicString songName = new DynamicString(Selection.songDir.replace('_', ' '), 15, 136, 630, 192, 60);

    /**
     * The level and chart constant for the song, formatted as a string (e.g., "level chartConstant").
     */
    private final DynamicString level = new DynamicString(Selection.level + " " + Selection.chartConstant, 15, 280, 630, 96, 60);

    /**
     * The username of the player, retrieved from the users list by user index.
     */
    private final DynamicString userName = new DynamicString(users.get(userIndex).userName, 15, 424, 630, 192, 60);

    /**
     * The jacket image displayed in the result screen.
     */
    private final DynamicImage jacket = new DynamicImage(new CRect(280, 450, 480, 300));

    /**
     * Adds horizontal motion to the provided DynamicString object to animate its movement.
     *
     * @param ds the DynamicString object to which motion is added.
     */
    private void addMotion(DynamicString ds) {
        ds.addHorizontalMotion(new Motion(
                0, 1.5,
                ds.getBoundedString().getBounds().getX() - ReferenceWindow.REF_WIN_W,
                ds.getBoundedString().getBounds().getX(),
                0.5
        ));
    }

    /**
     * Initializes the horizontal motion for all DynamicString and DynamicImage objects on the result page.
     * This method configures the animations for score text, grade, combo, accuracy, and other statistics.
     */
    private void initMotion() {
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

    /**
     * Initializes the bounds and outline properties for a specific DynamicString object.
     *
     * @param ds the DynamicString object to initialize bounds for.
     */
    private void initBound(DynamicString ds) {
        ds.getBoundedString().getBounds().setOutlineColor(grade.getBoundedString().getStringColor());
        ds.getBoundedString().getBounds().setOutlineThickness(3.0f);
    }

    /**
     * Initializes the bounds for various DynamicString objects, such as song name, level, username, and score bounds.
     * This method also checks if the user is set to autoplay and changes the username color accordingly.
     */
    private void initBounds() {
        initBound(songName);
        initBound(level);
        initBound(userName);
        initBound(scoreBounds);
        initBound(accBounds);
        initBound(noteBounds);
        if (users.get(userIndex).isAutoplay) {
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

    private void setText(){
        double currentAccuracy = (Lane.perfect+Lane.good*goodPercentage)/Lane.total;
        int currentScore = (int) Math.round(
                currentAccuracy*Lane.total/ Chart.noteCount*maxScore*(1-comboScorePercentage)+
                        Lane.maxCombo/ Chart.noteCount*maxScore*comboScorePercentage
        );
        ScoreData thisGameScore = new ScoreData(currentScore, (int) Lane.maxCombo, currentAccuracy, Lane.worstHit, (Lane.maxCombo == Chart.noteCount), false);
        accuracy.getBoundedString().setString(thisGameScore.getAccuracyString());
        scoreText.getBoundedString().setString(thisGameScore.getScoreString());
        thisGameScore.setGradeString(grade.getBoundedString());
        perfect.getBoundedString().setString("Perfect: "+ (int)Lane.perfect);
        good.getBoundedString().setString("Good: "+(int)Lane.good);
        bad.getBoundedString().setString("Bad: "+(int)Lane.bad);
        miss.getBoundedString().setString("Miss: "+(int)Lane.miss);
        early.getBoundedString().setString("Early: "+(int)Lane.early);
        late.getBoundedString().setString("Late: "+(int)Lane.late);
        maxCombo.getBoundedString().setString(String.valueOf((int)Lane.maxCombo));
        worstHit.getBoundedString().setString(thisGameScore.getWorstHitString());
        initBounds();
        if(!users.get(userIndex).isAutoplay){
            if(thisGameScore.score>getScoreData().score){
                newBestScore.getBoundedString().setString("+"+(ScoreData.toScoreString(thisGameScore.score-getScoreData().score)));
                getScoreData().score = thisGameScore.score;
            }
            if(thisGameScore.acc>getScoreData().acc){
                bestAccuracy.getBoundedString().setString("+"+String.format("%.2f", (thisGameScore.acc-getScoreData().acc)*100)+" %");
                getScoreData().acc = thisGameScore.acc;
            }
            if(thisGameScore.maxCombo>getScoreData().maxCombo){
                bestMaxCombo.getBoundedString().setString("+"+(thisGameScore.maxCombo-getScoreData().maxCombo));
                getScoreData().maxCombo = thisGameScore.maxCombo;
            }
            if(thisGameScore.worstHit<getScoreData().worstHit){
                bestWorstHit.getBoundedString().setString(String.format("%.2f", (thisGameScore.worstHit-getScoreData().worstHit)*1000)+" ms");
                getScoreData().worstHit = thisGameScore.worstHit;
            }
            if(!getScoreData().fc){
                getScoreData().fc = thisGameScore.fc;
            }
            getScoreData().newChart = false;
        }
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

        if(jacket.getImage()!=null){
            dmm.addDynamicObject(jacket);
        }
        else{
            DynamicString nullJacket = new DynamicString("No jacket preview available", 15, 280, 448, 480, 299);
            nullJacket.getBoundedString().setStyle(Font.ITALIC);
            nullJacket.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
            nullJacket.getBoundedString().getBounds().setOutlineThickness(2.0f);
            nullJacket.addHorizontalMotion(new Motion(0, 1.5, -800, 280, 0.5));
            dmm.addDynamicObject(nullJacket);
        }
    }
}

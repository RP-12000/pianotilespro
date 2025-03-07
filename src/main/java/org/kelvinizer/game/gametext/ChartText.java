package org.kelvinizer.game.gametext;

import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Chart;
import org.kelvinizer.game.gamewindow.Lane;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;
import static org.kelvinizer.constants.JudgementLimits.*;

/**
 * The {@code ChartText} class is responsible for displaying and updating the various statistics
 * during gameplay on the chart screen. This includes the score, song name, level, combo, accuracy,
 * and the count of different judgement types (Perfect, Good, Bad, Miss, etc.).
 * It implements the {@link Drawable} interface to render the text elements to the screen.
 * @author Boyan Hu
 */
public class ChartText implements Drawable {

    /** Displays the score of the player */
    private final BoundedString score = new BoundedString("0000000", 30, 990, 108);

    /** Displays the song name */
    private final BoundedString songName = new BoundedString(Selection.songDir.replace('_', ' '), 20, 90, 660, 140, 150);

    /** Displays the level and chart constant */
    private final BoundedString level = new BoundedString(Selection.level+" "+Selection.chartConstant, 20, 990, 660);

    /** Displays the combo count */
    private final BoundedString combo = new BoundedString("", 30, 90, 108, 140, 150);

    /** Displays the accuracy percentage */
    private final BoundedString acc = new BoundedString("", 15, 990, 144);

    /** Displays the player's user name */
    private final BoundedString userName = new BoundedString("", 15, 90, 144);

    /** Displays the count of perfect hits */
    private final BoundedString perfect = new BoundedString("", 18, 90, 300, 140, 30);

    /** Displays the count of good hits */
    private final BoundedString good = new BoundedString("", 18, 90, 340, 140, 30);

    /** Displays the count of bad hits */
    private final BoundedString bad = new BoundedString("", 18, 90, 380, 140, 30);

    /** Displays the count of missed hits */
    private final BoundedString miss = new BoundedString("", 18, 90, 420, 140, 30);

    /** Displays the count of early hits */
    private final BoundedString early = new BoundedString("", 18, 990, 300, 140, 30);

    /** Displays the count of late hits */
    private final BoundedString late = new BoundedString("", 18, 990, 340, 140, 30);

    /** Displays the maximum combo count */
    private final BoundedString maxCombo = new BoundedString("", 18, 990, 380, 140, 30);

    /** Displays the worst hit timing */
    private final BoundedString worstHit = new BoundedString("", 18, 990, 420, 140, 30);

    /**
     * Sets the maximum string sizes for each text element to ensure proper display and avoid overflow.
     */
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

    /**
     * Constructs a {@code ChartText} object, initializes the text fields,
     * and sets the maximum string sizes.
     */
    public ChartText(){
        setMaxStringSizes();
    }

    /**
     * Updates the text displayed in the chart, including the player's score,
     * accuracy, combo, and counts for different judgement types (Perfect, Good, etc.).
     * It computes the current score and other statistics based on the data available in {@code Lane}.
     */
    public void updateText(){
        userName.setString(users.get(userIndex).userName);
        double currentAccuracy = (Lane.perfect + Lane.good * goodPercentage) / Lane.total;
        if (Lane.total != 0) {
            acc.setString(String.format("%.2f", currentAccuracy * 100) + " %");
        } else {
            acc.setString("N/A %");
        }
        double minVisibleCombo = 3;
        if (Lane.currentCombo >= minVisibleCombo) {
            combo.setString((int) Lane.currentCombo + " COMBO");
        } else {
            combo.setString("");
        }
        int currentScore = (int) Math.round(
                currentAccuracy * Lane.total / Chart.noteCount * maxScore * (1 - comboScorePercentage) +
                        Lane.maxCombo / Chart.noteCount * maxScore * comboScorePercentage
        );
        int zeroCount = 0;
        int tempScore = currentScore;
        StringBuilder prefix = new StringBuilder();
        while (tempScore < maxScore && zeroCount < Math.log10(maxScore)) {
            prefix.append("0");
            zeroCount++;
            tempScore = tempScore * 10;
        }
        score.setString(prefix.toString() + currentScore);
        perfect.setString("Perfect: " + (int) Lane.perfect);
        good.setString("Good: " + (int) Lane.good);
        bad.setString("Bad: " + (int) Lane.bad);
        miss.setString("Miss: " + (int) Lane.miss);
        early.setString("Early: " + (int) Lane.early);
        late.setString("Late: " + (int) Lane.late);
        maxCombo.setString("Max Combo: " + (int) Lane.maxCombo);
        worstHit.setString("Worst Hit: " + String.format("%.2f", Lane.worstHit * 1000) + " ms");
    }

    /**
     * Renders the text elements (score, song name, level, combo, accuracy, etc.) to the screen.
     *
     * @param g2d the {@link Graphics2D} object used to draw the text
     */
    @Override
    public void render(Graphics2D g2d){
        score.render(g2d);
        songName.render(g2d);
        level.render(g2d);
        combo.render(g2d);
        acc.render(g2d);
        if (users.get(userIndex).isAutoplay) {
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
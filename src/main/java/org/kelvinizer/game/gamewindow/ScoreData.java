package org.kelvinizer.game.gamewindow;

import org.kelvinizer.constants.JudgementLimits;
import org.kelvinizer.support.classes.BoundedString;

import java.awt.*;

import static org.kelvinizer.constants.JudgementLimits.maxScore;

/**
 * Represents the score data for a game session, including the player's score, maximum combo, accuracy,
 * worst hit time, and whether the player achieved a full combo (FC) or is playing a new chart.
 * This class provides methods to format and display the score and performance statistics.
 */
public class ScoreData {

    /**
     * The player's score for the game session.
     */
    public int score;

    /**
     * The maximum combo achieved by the player during the game session.
     */
    public int maxCombo;

    /**
     * The player's accuracy for the game session, expressed as a decimal between 0 and 1.
     */
    public double acc;

    /**
     * The worst hit time (in seconds) experienced by the player during the game session.
     */
    public double worstHit;

    /**
     * A flag indicating if the player achieved a full combo (FC) during the game session.
     */
    public boolean fc;

    /**
     * A flag indicating if the player is playing a new chart.
     */
    public boolean newChart;

    /**
     * Constructs a ScoreData object from a string representing score data in the format:
     * <score> <maxCombo> <accuracy> <worstHit> <fc> <newChart>
     *
     * @param s The string containing the score data.
     * @throws NumberFormatException If the string cannot be parsed into valid data.
     */
    public ScoreData(String s) {
        String[] t = s.split(" ");
        score = Integer.parseInt(t[0]);
        maxCombo = Integer.parseInt(t[1]);
        acc = Double.parseDouble(t[2]);
        worstHit = Double.parseDouble(t[3]);
        fc = Boolean.parseBoolean(t[4]);
        newChart = Boolean.parseBoolean(t[5]);
    }

    /**
     * Default constructor that initializes the score data to default values.
     */
    public ScoreData() {
        this(0, 0, 0, Double.POSITIVE_INFINITY, false, true);
    }

    /**
     * Constructs a ScoreData object with the specified values.
     *
     * @param score     The player's score for the game session.
     * @param maxCombo  The maximum combo achieved during the session.
     * @param acc       The accuracy for the session, as a decimal between 0 and 1.
     * @param worstHit  The worst hit time in milliseconds for the session.
     * @param fc        A flag indicating if the player achieved a full combo (FC).
     * @param newChart  A flag indicating if the player is playing a new chart.
     */
    public ScoreData(int score, int maxCombo, double acc, double worstHit, boolean fc, boolean newChart) {
        this.score = score;
        this.maxCombo = maxCombo;
        this.acc = acc;
        this.worstHit = worstHit;
        this.fc = fc;
        this.newChart = newChart;
    }

    /**
     * Returns the player's score as a formatted string with leading zeros if necessary.
     *
     * @return A string representation of the score with appropriate formatting.
     */
    public String getScoreString() {
        return toScoreString(this.score);
    }

    /**
     * Converts a score into a string representation with leading zeros if the score is less than the maximum score.
     *
     * @param score The score to be formatted.
     * @return A string representation of the score with leading zeros.
     */
    public static String toScoreString(int score) {
        int zeroCount = 0;
        int tempScore = score;
        StringBuilder prefix = new StringBuilder();
        while (tempScore < maxScore && zeroCount < Math.log10(maxScore)) {
            prefix.append("0");
            zeroCount++;
            tempScore = tempScore * 10;
        }
        return prefix.toString() + score;
    }

    /**
     * Returns the player's accuracy as a formatted string, with "N/A %" if the accuracy is not available.
     *
     * @return A string representation of the accuracy in percentage.
     */
    public String getAccuracyString() {
        if (acc == Double.POSITIVE_INFINITY) {
            return "N/A %";
        }
        return String.format("%.2f", acc * 100) + " %";
    }

    /**
     * Returns the worst hit time as a formatted string in milliseconds.
     *
     * @return A string representation of the worst hit time in milliseconds.
     */
    public String getWorstHitString() {
        return String.format("%.2f", worstHit * 1000) + " ms";
    }

    /**
     * Sets the grade string based on the player's performance (score and FC status) and assigns a color to the grade.
     *
     * @param grade A BoundedString object that will hold the grade string and its color.
     */
    public void setGradeString(BoundedString grade) {
        if (fc) {
            grade.setStringColor(Color.BLUE);
        } else {
            grade.setStringColor(Color.WHITE);
        }

        if (score < 700000) {
            grade.setString("F");
        } else if (score < 820000) {
            grade.setString("C");
        } else if (score < 880000) {
            grade.setString("B");
        } else if (score < 920000) {
            grade.setString("A");
        } else if (score < 960000) {
            grade.setString("S");
        } else if (score < 1000000) {
            grade.setString("V");
        } else {
            if (worstHit <= JudgementLimits.THEORETICAL) {
                grade.setString("T");
                grade.setStringColor(Color.MAGENTA);
            } else {
                grade.setString("P");
                grade.setStringColor(Color.GREEN);
            }
        }
    }

    /**
     * Returns a string representation of the score data in the format:
     * <score> <maxCombo> <accuracy> <worstHit> <fc> <newChart>
     *
     * @return A string representation of the score data.
     */
    @Override
    public String toString() {
        return score + " " + maxCombo + " " + acc + " " + worstHit + " " + fc + " " + newChart;
    }
}
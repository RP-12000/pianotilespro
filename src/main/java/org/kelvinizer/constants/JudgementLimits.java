package org.kelvinizer.constants;

import static org.kelvinizer.constants.Control.FPS;

/**
 * The {@code JudgementLimits} class defines constants related to timing and scoring mechanics.
 * It is primarily used for rhythm-based game systems to evaluate player input accuracy.
 * @author Boyan Hu
 */
public class JudgementLimits {
    /** The duration of a single frame in seconds, calculated based on {@link Control#FPS}. */
    private static final double FRAME = 1.0 / FPS;

    /** The theoretical maximum timing limit for note accuracy, expressed as 1.2 times a single frame. */
    public static final double THEORETICAL = 1.2 * FRAME;

    /** The timing window for a "Perfect" judgment, expressed as 5.1 times a single frame. */
    public static final double PERFECT = 5.1 * FRAME;

    /** The timing window for a "Good" judgment, expressed as 9.6 times a single frame. */
    public static final double GOOD = 9.6 * FRAME;

    /** The timing window for a "Bad" judgment, expressed as 12 times a single frame. */
    public static final double BAD = 12 * FRAME;

    /** The timing limit for the end of a held note, expressed as 18 times a single frame. */
    public static final double HOLD_NOTE_END_LIMIT = 18 * FRAME;

    /** The maximum lingering time for notes after they are missed, expressed as 36 times a single frame. */
    public static final double NOTE_LINGERING_TIME = 36 * FRAME;

    /** The percentage of the score allocated to "Good" judgments in accuracy scoring. */
    public static final double goodPercentage = 0.65;

    /** The percentage of the score allocated to combo scoring. */
    public static final double comboScorePercentage = 0.1;

    /** The maximum achievable score for the game. */
    public static final int maxScore = 1_000_000;
}
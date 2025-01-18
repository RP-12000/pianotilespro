package org.kelvinizer.constants;

import static org.kelvinizer.constants.Control.FPS;

public class JudgementLimits {
    private static final double FRAME = 1.0/FPS;
    public static final double THEORETICAL = 1.2*FRAME;
    public static final double PERFECT = 5.1*FRAME;
    public static final double GOOD = 9.6*FRAME;
    public static final double BAD = 12*FRAME;
    public static final double HOLD_NOTE_END_LIMIT = 18*FRAME;
    public static final double NOTE_LINGERING_TIME = 36*FRAME;
    public static final double goodPercentage = 0.65;
    public static final double comboScorePercentage = 0.1;
    public static final int maxScore = 1000000;
}
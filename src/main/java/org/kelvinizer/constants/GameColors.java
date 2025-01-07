package org.kelvinizer.constants;

import java.awt.*;

public class GameColors {
    public static final int PAUSED_OPACITY = 64;
    public static final int IMAGE_OPACITY = 32;
    public static final Color TRANSPARENT = new Color(255,255,255,1);

    public static final Color[] TAP_NOTE_COLOR = {
            new Color(130,222,250),
            new Color(130,222,250,PAUSED_OPACITY)
    };
    public static final Color[] HOLD_NOTE_COLOR = {
            new Color(255,255,255) ,
            new Color(255,255,255, PAUSED_OPACITY)
    };
    public static final Color[] SYNC_COLOR = {
            new Color(223,197,123),
            new Color(223,197,123,PAUSED_OPACITY)
    };
    public static final Color[] PROGRESS_BAR_COLOR = {
            new Color(0,255,255),
            new Color(0,255,255,PAUSED_OPACITY)
    };
    public static final Color[][] PARTICLE_COLOR = {
            {
                    new Color(0,255,0),
                    new Color(0,0,255),
                    new Color(255,0,0)
            },
            {
                    new Color(0,255,0,PAUSED_OPACITY),
                    new Color(0,0,255,PAUSED_OPACITY),
                    new Color(255,0,0,PAUSED_OPACITY)
            }
    };
    public static final Color[][]  JUDGEMENT_LINE_COLOR = {
            {
                    new Color(0,255,0),
                    new Color(0,0,255),
                    new Color(255,255,255)
            },
            {
                    new Color(0,255,0,PAUSED_OPACITY),
                    new Color(0,0,255,PAUSED_OPACITY),
                    new Color(255,255,255,PAUSED_OPACITY)
            }
    };
}
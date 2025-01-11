package org.kelvinizer.constants;

import java.awt.*;

public class GameColors {
    public static final int PAUSED_OPACITY = 64;
    public static final int IMAGE_OPACITY = 32;
    public static final Color TRANSPARENT = new Color(255,255,255,1);

    public static final Color[] PROGRESS_BAR_COLOR = {
            new Color(0,255,255),
            new Color(0,255,255,PAUSED_OPACITY)
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

    public static Color setColorAlpha(Color c, int opacity){
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
    }
}
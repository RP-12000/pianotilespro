package org.kelvinizer.animation;

import java.awt.*;

public class ColorMotion {
    private final double durationInMS;
    private final boolean isAppearance;
    private final Color rgb;

    public ColorMotion(double durationInMS, boolean isAppearance, Color rgb){
        this.durationInMS = durationInMS;
        this.isAppearance = isAppearance;
        this.rgb = rgb;
    }

    public Color getColor(double timePassed) {
        if(isAppearance){
            return new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), (int)(255/durationInMS*timePassed));
        }
        return new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), (int)(255-255/durationInMS*timePassed));
    }
}

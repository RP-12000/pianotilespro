package org.kelvinizer;

import java.awt.*;

public class Constants {
    public static final String VERSION="v0.0.0-a";
    public static final String GameName ="Piano Tiles Pro";
    public static int panel_index = 0;
    public static final Color DEFAULT_COLOR = Color.WHITE;
    public static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);
    public static boolean isAutoplay = false;

    public static class ReferenceWindow{
        public static final double REF_WIN_W = 1080.0;
        public static final double REF_WIN_H = 720.0;
        public static final double HORIZONTAL_JUDGEMENT_SPACING = 108.0;
        public static final double FIRST_JUDGEMENT_LINE_X = 160.0;
        public static final double VERTICAL_JUDGEMENT_SPACING = 80.0;
        public static final double[] HORIZONTAL_JUDGEMENT_LINE_POS = {612.0, 108.0};
        public static final double[] VERTICAL_JUDGEMENT_LINE_POS = {
                160.0,240.0,320.0,400.0,480.0,560.0,640.0,720.0,800.0
        };
        public static final double NOTE_WIDTH = 48.0;
        public static final double TAP_NOTE_HEIGHT = 5.0;
        public static final double HOLD_NOTE_WIDTH = 10.0;
        public static final double NOTE_OUTLINE_THICKNESS = 1.0;
        public static final double PROGRESS_BAR_THICKNESS = 5.0;
    }

    public static class Time{
        public static final long FPS = 60;
        public static final long INTRO_TIME_IN_MS = 500;
        public static final long EXIT_TIME_IN_MS = 500;
        public static final long MS_TO_NS_CONVERSION_FACTOR = 1000000;
        public static double CURRENT_TIME = 0;
    }

    public static class Selection {
        public static int collectionIndex = 0;
        public static int songIndex = 0;
        public static String collectionDir = "";
        public static String songDir = "";
        public static String level = "";
    }

    public static class JudgementLimits{
        public static final double PERFECT_LIMIT = 0.08;
        public static final double GOOD_LIMIT = 0.16;
        public static final double BAD_LIMIT = 0.18;
        public static final double HOLD_NOTE_END_LIMIT = 0.3;
        public static final double NOTE_LINGERING_TIME = 0.6;
        public static double MUSIC_DIFFERENCE = 0;
    }

    public static class GameColors{
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
}

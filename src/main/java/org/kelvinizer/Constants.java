package org.kelvinizer;

import java.awt.*;

public class Constants {
    public static final String VERSION="v0.0.0-a";
    public static final String GameName ="Piano Tiles Pro";
    public static int panel_index = 0;
    public static final Color DEFAULT_COLOR = Color.WHITE;
    public static final Stroke DEFAULT_STROKE = new BasicStroke(1.0f);

    public static class ReferenceWindow{
        public static final int REF_WIN_W = 1080;
        public static final int REF_WIN_H = 720;
    }

    public static class Time{
        public static final long FPS = 120;
        public static final long INTRO_TIME_IN_MS = 500;
        public static final long EXIT_TIME_IN_MS = 500;
        public static final long MS_TO_NS_CONVERSION_FACTOR = 1000000;
    }

    public static class GameWindow{
        public static String songDir = "";
        public static String level = "";
    }
}

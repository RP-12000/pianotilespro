package org.kelvinizer.constants;

import java.util.Objects;

public class Control {
    public static int panel_index = 0;
    public static boolean isAutoplay = true;

    public static boolean syncEnabled = true;
    public static boolean FCAPHintEnabled = true;
    public static boolean handHintEnabled = false;
    public static int MUSIC_DIFFERENCE = 0;
    public static int tolerance = 80;
    public static int FPS = 60;
    public static int newFPS = 60;

    public static String userName = "";

    public static String getResourcePathName(String path){
        return Objects.requireNonNull(Control.class.getResource("/"+path)).getPath();
    }
}
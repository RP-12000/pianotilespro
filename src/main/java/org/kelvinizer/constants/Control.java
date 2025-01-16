package org.kelvinizer.constants;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

public class Control {
    public static final String GAME_VERSION = "v0.0.0-a";

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

    public static void printSettings(PrintWriter pw){
        pw.println(userName);
        pw.println(isAutoplay);
        pw.println(syncEnabled);
        pw.println(FCAPHintEnabled);
        pw.println(handHintEnabled);
        pw.println(newFPS);
        pw.println(MUSIC_DIFFERENCE);
        pw.println(tolerance);
    }

    public static void readSettings(BufferedReader userInfo){
        try{
            userName = userInfo.readLine();
            isAutoplay = Boolean.parseBoolean(userInfo.readLine());
            syncEnabled = Boolean.parseBoolean(userInfo.readLine());
            FCAPHintEnabled = Boolean.parseBoolean(userInfo.readLine());
            handHintEnabled = Boolean.parseBoolean(userInfo.readLine());
            FPS = Integer.parseInt(userInfo.readLine());
            newFPS = FPS;
            MUSIC_DIFFERENCE = Integer.parseInt(userInfo.readLine());
            tolerance = Integer.parseInt(userInfo.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
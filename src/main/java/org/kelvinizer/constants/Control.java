package org.kelvinizer.constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Control {
    public static final String GAME_VERSION = "v0.0.0-a";
    public static int panel_index = 0;
    public static ArrayList<User> users = new ArrayList<>();
    public static int userIndex = 0;
    public static final int FPS = 60;
    public static boolean firstTimeOpen = true;

    public static void getAllUsers(){
        File userFolder = new File("Users");
        if(!userFolder.exists() && !userFolder.mkdir()){
            throw new RuntimeException("Unable to create Users folder");
        }
        File[] files = userFolder.listFiles((dir, name) -> name.endsWith(".ptpuser"));
        if (files != null) {
            for(File f: files){
                users.add(new User(f));
            }
            users.removeIf(u -> !u.isValidUser);
        }
    }

    public static BufferedInputStream getResourceInput(String path){
        return new BufferedInputStream(Objects.requireNonNull(Control.class.getResourceAsStream("/" + path)));
    }
}
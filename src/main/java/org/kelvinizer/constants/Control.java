package org.kelvinizer.constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The {@code Control} class provides constants and utility methods for managing the application's state,
 * user data, and resources.
 * @author Boyan Hu
 */
public class Control {
    /** The current version of the game. */
    public static final String GAME_VERSION = "v0.0.0";
    /** Index of the current panel being displayed. */
    public static int panel_index = 0;
    /** List of registered users. */
    public static ArrayList<User> users = new ArrayList<>();
    /** Index of the currently active user. */
    public static int userIndex = 0;
    /** Frames per second for the application's update loop. */
    public static final int FPS = 60;
    /** Indicates whether the application is being opened for the first time. */
    public static boolean firstTimeOpen = true;

    /**
     * Retrieves all users from the "Users" folder. Users are added to the {@code users} list.
     * If the folder does not exist, it will be created.
     * Only valid user files with the ".ptpuser" extension are loaded.
     */
    public static void getAllUsers() {
        File userFolder = new File("Users");
        if (!userFolder.exists() && !userFolder.mkdir()) {
            throw new RuntimeException("Unable to create Users folder");
        }
        File[] files = userFolder.listFiles((dir, name) -> name.endsWith(".ptpuser"));
        if (files != null) {
            for (File f : files) {
                users.add(new User(f));
            }
            users.removeIf(u -> !u.isValidUser);
        }
    }

    /**
     * Retrieves a resource from the application's classpath as a {@link BufferedInputStream}.
     *
     * @param path the relative path to the resource
     * @return a {@link BufferedInputStream} for reading the resource
     * @throws NullPointerException if the resource cannot be found
     */
    public static BufferedInputStream getResourceInput(String path) {
        return new BufferedInputStream(Objects.requireNonNull(Control.class.getResourceAsStream("/" + path)));
    }
}
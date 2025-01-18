package org.kelvinizer;

import javax.swing.*;

/**
 * Main class of the program
 * just calls the creating of a new App
 * @author Boyan Hu
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
package org.kelvinizer.constants;

/**
 * The {@code ReferenceWindow} class defines constants for the reference window dimensions and
 * positioning in the game. These constants are used to standardize UI element placement
 * and scaling relative to the reference window.
 * <p>
 * The class also includes constants for judgment line positioning and unit measurements
 * for consistent layout design.
 * </p>
 *
 * @author Boyan Hu
 */
public class ReferenceWindow {
    /** The reference window width in pixels. */
    public static final double REF_WIN_W = 1080;

    /** The reference window height in pixels. */
    public static final double REF_WIN_H = 720;

    /** The extra width in pixels for window borders or margins. */
    public static final int extraWidth = 12;

    /** The extra height in pixels for window borders or margins. */
    public static final int extraHeight = 35;

    /** A base unit used for scaling and positioning calculations, typically equal to a single "unit" of the UI grid. */
    public static final double UNIT = 612;

    /** The vertical spacing between judgment lines in pixels. */
    public static final double VERTICAL_JUDGEMENT_SPACING = 90;

    /**
     * The positions of horizontal judgment lines in the reference window.
     * <ul>
     * <li>Index 0: The primary horizontal line position.</li>
     * <li>Index 1: The secondary horizontal line position.</li>
     * </ul>
     */
    public static final double[] HORIZONTAL_JUDGEMENT_LINE_POS = {612, 108};

    /**
     * The positions of vertical judgment lines in the reference window.
     * These are evenly spaced along the height of the window.
     */
    public static final double[] VERTICAL_JUDGEMENT_LINE_POS = {
            180, 270, 360, 450, 540, 630, 720, 810, 900
    };
}
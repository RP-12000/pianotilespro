package org.kelvinizer.menu.songselection;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The {@code SongSelectionButtons} class manages the creation and rendering of buttons for selecting songs
 * and their difficulty levels in the song selection menu. It provides functionality for navigating between
 * levels and moving up or down within the selection menu.
 * <p>
 * This class supports scaling of button sizes and setting their focus, allowing for interaction with the
 * user interface. It also handles the rendering of different song levels (BS, MD, AV, LG) and provides
 * visual feedback when buttons are selected or focused.
 * </p>
 *
 * @author Boyan Hu
 */
public class SongSelectionButtons implements Scalable, Focusable {
    /** Button for basic level (BS) selection. */
    public final CRectButton basic = new CRectButton();

    /** Button for BS level verification. */
    public final CRectButton BS = new CRectButton();

    /** Button for medium level (MD) selection. */
    public final CRectButton medium = new CRectButton();

    /** Button for MD level verification. */
    public final CRectButton MD = new CRectButton();

    /** Button for advanced level (AV) selection. */
    public final CRectButton advanced = new CRectButton();

    /** Button for AV level verification. */
    public final CRectButton AV = new CRectButton();

    /** Button for legendary level (LG) selection. */
    public final CRectButton legendary = new CRectButton();

    /** Button for LG level verification. */
    public final CRectButton LG = new CRectButton();

    /** Button for moving up in the selection menu. */
    public final CTriangleButton moveUp = new CTriangleButton();

    /** Button for moving down in the selection menu. */
    public final CTriangleButton moveDown = new CTriangleButton();

    /**
     * Initializes the button for each level with appropriate properties.
     *
     * @param button the button to be initialized.
     * @param verdict the verdict button to be initialized.
     * @param selectedColor the color to use when the button is selected.
     * @param levelString the level's string representation (e.g., "BS", "MD").
     * @param x the x-coordinate of the button.
     */
    private void setLevelButton(CRectButton button, CRectButton verdict, Color selectedColor, String levelString, double x) {
        BoundedString normal = new BoundedString("", 30, x, 505, 50, 50);
        normal.setRelativeY(0.35);
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        button.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 30, x, 505, 50, 50);
        onFocus.setRelativeY(0.35);
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        button.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 30, x, 505, 50, 50);
        onSelection.setRelativeY(0.35);
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(selectedColor);
        button.setOnSelection(onSelection);

        BoundedString normal2 = new BoundedString(levelString, 13, x, 505, 50, 50);
        normal2.setRelativeY(0.8);
        normal2.getBounds().setOutlineColor(Color.WHITE);
        normal2.getBounds().setOutlineThickness(1.0);
        verdict.setNormal(normal2);

        BoundedString onFocus2 = new BoundedString(levelString, 13, x, 505, 50, 50);
        onFocus2.setRelativeY(0.8);
        onFocus2.getBounds().setOutlineColor(Color.WHITE);
        onFocus2.getBounds().setOutlineThickness(3.0);
        verdict.setOnFocus(onFocus2);

        BoundedString onSelection2 = new BoundedString(levelString, 13, x, 505, 50, 50);
        onSelection2.setRelativeY(0.8);
        onSelection2.getBounds().setOutlineColor(Color.WHITE);
        onSelection2.getBounds().setOutlineThickness(1.0);
        verdict.setOnSelection(onSelection2);
    }

    /**
     * Initializes buttons for each level in the song selection menu.
     */
    private void setLevelButtons() {
        setLevelButton(basic, BS, Color.GREEN, "BS", 545);
        setLevelButton(medium, MD, Color.BLUE, "MD", 595);
        setLevelButton(advanced, AV, Color.RED, "AV", 645);
        setLevelButton(legendary, LG, Color.MAGENTA, "LG", 695);
    }

    /**
     * Initializes the move-up button in the song selection menu.
     */
    private void setMoveUp() {
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(220, 150);
        normal.setSecondPoint(380, 150);
        normal.setThirdPoint(300, 100);
        normal.setFillColor(Color.WHITE);
        moveUp.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(220, 150);
        onFocus.setSecondPoint(380, 150);
        onFocus.setThirdPoint(300, 100);
        onFocus.setFillColor(Color.WHITE);
        onFocus.setOutlineColor(Color.BLUE);
        onFocus.setOutlineThickness(3.0);
        moveUp.setOnFocus(onFocus);
    }

    /**
     * Initializes the move-down button in the song selection menu.
     */
    private void setMoveDown() {
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(220, 550);
        normal.setSecondPoint(380, 550);
        normal.setThirdPoint(300, 600);
        normal.setFillColor(Color.WHITE);
        moveDown.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(220, 550);
        onFocus.setSecondPoint(380, 550);
        onFocus.setThirdPoint(300, 600);
        onFocus.setFillColor(Color.WHITE);
        onFocus.setOutlineColor(Color.BLUE);
        onFocus.setOutlineThickness(3.0);
        moveDown.setOnFocus(onFocus);
    }

    /**
     * Constructor for initializing all buttons in the song selection menu.
     */
    public SongSelectionButtons() {
        setLevelButtons();
        setMoveUp();
        setMoveDown();
    }

    @Override
    public void scale(Dimension d) {
        basic.scale(d);
        BS.scale(d);
        medium.scale(d);
        MD.scale(d);
        advanced.scale(d);
        AV.scale(d);
        legendary.scale(d);
        LG.scale(d);
        moveUp.scale(d);
        moveDown.scale(d);
    }

    /**
     * Converts a level represented by a pair of string and double to a string.
     *
     * @param level the level as a pair.
     * @return the string representation of the level.
     */
    private String levelToString(Pair<String, Double> level) {
        return ((Integer)(int)(double)(level.second)).toString();
    }

    /**
     * Renders the level selection button and its corresponding verdict.
     *
     * @param button the button for the level.
     * @param verdict the verdict for the level.
     * @param g2d the graphics object used for rendering.
     * @param s the {@link Song} object associated with the song selection.
     * @param level the level being rendered.
     */
    private void renderOneLevel(CRectButton button, CRectButton verdict, Graphics2D g2d, Song s, String level) {
        button.select(Selection.level.equals(level));
        verdict.select(Selection.level.equals(level));
        button.getNormal().setString(levelToString(s.getCharterData(level)));
        button.getOnFocus().setString(levelToString(s.getCharterData(level)));
        button.getOnSelection().setString(levelToString(s.getCharterData(level)));
        button.render(g2d);
        verdict.render(g2d);
    }

    /**
     * Renders all level buttons for a given song.
     *
     * @param g2d the graphics object used for rendering.
     * @param s the {@link Song} object associated with the song selection.
     */
    public void renderLevels(Graphics2D g2d, Song s) {
        renderOneLevel(basic, BS, g2d, s, "BS");
        renderOneLevel(medium, MD, g2d, s, "MD");
        renderOneLevel(advanced, AV, g2d, s, "AV");
        if (s.hasLG()) {
            renderOneLevel(legendary, LG, g2d, s, "LG");
        }
    }

    /**
     * Sets the focus for each of the song selection buttons based on the provided mouse event.
     * This method updates the focused state of the level buttons and navigation buttons
     * when the user interacts with them.
     *
     * @param e the {@link MouseEvent} representing the user's mouse interaction,
     *          used to determine which button is focused.
     */
    @Override
    public void setFocused(MouseEvent e) {
        basic.setFocused(e);
        medium.setFocused(e);
        advanced.setFocused(e);
        legendary.setFocused(e);
        moveUp.setFocused(e);
        moveDown.setFocused(e);
    }
}
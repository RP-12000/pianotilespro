package org.kelvinizer.menu.userselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.classes.Triple;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

import static org.kelvinizer.constants.Selection.level;

/**
 * Represents the text information displayed in the user selection menu, including
 * username, total data, cleared data, full combo data, and all perfect data.
 * Implements {@link Drawable} for rendering on the screen.
 * @author Boyan Hu
 */
public class UserSelectionText implements Drawable {

    /** The text object for displaying the user's name. */
    private final BoundedString userName = new BoundedString("", 40, 540, 330, 600, 270);

    /** The text object for displaying the total data statistic. */
    private final BoundedString totalData = new BoundedString("", 20, 312, 495, 140, 50);

    /** The text object for displaying the cleared data statistic. */
    private final BoundedString clearedData = new BoundedString("", 20, 464, 495, 140, 50);

    /** The text object for displaying the full combo (FC) statistic. */
    private final BoundedString fcData = new BoundedString("", 20, 616, 495, 140, 50);

    /** The text object for displaying the all perfect (AP) statistic. */
    private final BoundedString apData = new BoundedString("", 20, 768, 495, 140, 50);

    /**
     * Constructs a {@code UserSelectionText} instance and initializes the appearance
     * of all text objects.
     */
    public UserSelectionText() {
        userName.getBounds().setOutlineColor(Color.WHITE);
        userName.getBounds().setOutlineThickness(5.0);
        setBoundsAndColor(totalData, Color.WHITE);
        setBoundsAndColor(clearedData, Color.YELLOW);
        setBoundsAndColor(fcData, Color.BLUE);
        setBoundsAndColor(apData, Color.GREEN);
    }

    /**
     * Sets the bounds and color properties of a {@link BoundedString} object.
     *
     * @param bs the {@code BoundedString} to configure
     * @param c the color to apply to the bounds and text
     */
    private void setBoundsAndColor(BoundedString bs, Color c) {
        bs.getBounds().setOutlineColor(c);
        bs.getBounds().setOutlineThickness(5.0);
        bs.setMaxStringSize(20);
        bs.setStringColor(c);
    }

    /**
     * Renders the text objects on the provided graphics context. Updates the text
     * content based on the currently selected user and level.
     *
     * @param g2d the {@link Graphics2D} context used for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        if (UserSelection.renderIndex != Control.users.size()) {
            // Update user name and statistics based on the selected user
            userName.setString(Control.users.get(UserSelection.renderIndex).userName);
            Pair<Integer, Triple<Integer, Integer, Integer>> temp =
                    Control.users.get(UserSelection.renderIndex).getWholeGameFCAPData(level);

            // Set statistics text
            totalData.setString(temp.first + " Total");
            clearedData.setString(temp.second.first + " Cleared");
            fcData.setString(temp.second.second + " Full Combo");
            apData.setString(temp.second.third + " All Perfect");

            // Render the text objects
            userName.render(g2d);
            totalData.render(g2d);
            clearedData.render(g2d);
            fcData.render(g2d);
            apData.render(g2d);
        }
    }
}
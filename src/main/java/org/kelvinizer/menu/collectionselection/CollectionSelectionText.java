package org.kelvinizer.menu.collectionselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.classes.Triple;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

import static org.kelvinizer.constants.Selection.*;

/**
 * Represents the text components displayed in the collection selection menu.
 * Includes labels for collection metadata and an optional message for unavailable previews.
 * Implements {@link Drawable} for rendering text on the screen.
 * @author Boyan Hu
 */
public class CollectionSelectionText implements Drawable {

    /** Message displayed when no jacket preview is available for the selected collection. */
    public final BoundedString nullJacket = new BoundedString("No jacket preview available", 15, 540, 330, 360, 360);

    /** Displays the name of the selected collection. */
    public final BoundedString selectionName = new BoundedString("", 50, 540, 128, 400, 80);

    /** Displays the total number of items in the collection. */
    private final BoundedString totalData = new BoundedString("", 20, 315, 580, 140, 50);

    /** Displays the number of cleared items in the collection. */
    private final BoundedString clearedData = new BoundedString("", 20, 465, 580, 140, 50);

    /** Displays the number of items with a full combo in the collection. */
    private final BoundedString fcData = new BoundedString("", 20, 615, 580, 140, 50);

    /** Displays the number of items with an all-perfect status in the collection. */
    private final BoundedString apData = new BoundedString("", 20, 765, 580, 140, 50);

    /**
     * Initializes the visual style and properties for a {@link BoundedString} object.
     *
     * @param bs the {@link BoundedString} to initialize
     * @param c the color to set for the text and its outline
     */
    private void initData(BoundedString bs, Color c) {
        bs.setStringColor(c);
        bs.getBounds().setOutlineColor(c);
        bs.getBounds().setOutlineThickness(5.0);
        bs.setMaxStringSize(20);
    }

    /**
     * Constructs a {@code CollectionSelectionText} instance and initializes its text components.
     */
    public CollectionSelectionText() {
        selectionName.getBounds().setOutlineColor(Color.WHITE);
        selectionName.getBounds().setOutlineThickness(3.0);
        selectionName.setMaxStringSize(50);
        nullJacket.setStyle(Font.ITALIC);
        nullJacket.getBounds().setOutlineColor(Color.WHITE);
        nullJacket.getBounds().setOutlineThickness(1.0);
        initData(totalData, Color.WHITE);
        initData(clearedData, Color.YELLOW);
        initData(fcData, Color.BLUE);
        initData(apData, Color.GREEN);
    }

    /**
     * Renders the collection selection text components on the screen.
     * Updates text content based on the current user's data for the selected collection and level.
     *
     * @param g2d the {@link Graphics2D} object used for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        Pair<Integer, Triple<Integer, Integer, Integer>> temp = Control.users.get(Control.userIndex).getFCAPData(collectionDir, level);
        totalData.setString(temp.first + " Total");
        clearedData.setString(temp.second.first + " Cleared");
        fcData.setString(temp.second.second + " Full Combo");
        apData.setString(temp.second.third + " All Perfect");
        totalData.render(g2d);
        clearedData.render(g2d);
        fcData.render(g2d);
        apData.render(g2d);
    }
}
package org.kelvinizer.menu.settingpage;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

/**
 * The {@code SettingsText} class handles the rendering and management of text and its boundaries
 * in the settings menu. It dynamically updates text content and boundaries based on the current page.
 * Implements {@link Drawable}.
 * @author Boyan Hu
 */
public class SettingsText implements Drawable {

    /** The header text displayed at the top of the settings menu. */
    private final BoundedString header = new BoundedString("", 30, 540, 100, 150, 60);

    /** The first description text displayed in the settings menu. */
    private final BoundedString firstVerdict = new BoundedString("", 25, 300, 240, 150, 60);

    /** The second description text displayed in the settings menu. */
    private final BoundedString secondVerdict = new BoundedString("", 25, 300, 420, 150, 60);

    /** The third description text displayed in the settings menu (only on page 1). */
    private final BoundedString thirdVerdict = new BoundedString("", 25, 300, 600, 150, 60);

    /** The visual boundary associated with the first verdict text. */
    private final CRect firstBoundary = new CRect(540, 240, 720, 160);

    /** The visual boundary associated with the second verdict text. */
    private final CRect secondBoundary = new CRect(540, 420, 720, 160);

    /** The visual boundary associated with the third verdict text (only on page 1). */
    private final CRect thirdBoundary = new CRect(540, 600, 720, 160);

    /**
     * Configures the appearance of the specified verdict text and boundary.
     *
     * @param verdict the {@link BoundedString} to be configured
     * @param bound   the {@link CRect} boundary to be configured
     */
    public void setBoundAndVerdict(BoundedString verdict, CRect bound) {
        verdict.getBounds().setOutlineColor(Color.WHITE);
        verdict.getBounds().setOutlineThickness(3.0);
        bound.setOutlineColor(Color.WHITE);
        bound.setOutlineThickness(5.0);
    }

    /**
     * Constructs a {@code SettingsText} object and initializes the visual boundaries and text properties.
     */
    public SettingsText() {
        setBoundAndVerdict(firstVerdict, firstBoundary);
        setBoundAndVerdict(secondVerdict, secondBoundary);
        setBoundAndVerdict(thirdVerdict, thirdBoundary);
        header.getBounds().setOutlineColor(Color.WHITE);
        header.getBounds().setOutlineThickness(3.0);
    }

    /**
     * Updates the content of the text fields based on the current settings page.
     * - Page 1: Displays hints-related text.
     * - Page 2: Displays time-related text.
     */
    public void updateText() {
        if (Settings.page == 1) {
            firstVerdict.setString("Sync Hint");
            secondVerdict.setString("FC/AP Hint");
            thirdVerdict.setString("Hand Hint");
        } else {
            firstVerdict.setString("Music Delay");
            secondVerdict.setString("Tolerance");
        }
    }

    /**
     * Renders the text and its associated boundaries to the screen.
     * Adjusts the header text based on the current page.
     *
     * @param g2d the {@link Graphics2D} object used for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        header.setString("Time");
        if(Settings.page==1){
            header.setString("Hints");
        }
        header.render(g2d);
        firstVerdict.render(g2d);
        secondVerdict.render(g2d);
        firstBoundary.render(g2d);
        secondBoundary.render(g2d);
        if (Settings.page == 1) {
            thirdVerdict.render(g2d);
            thirdBoundary.render(g2d);
        }
    }
}
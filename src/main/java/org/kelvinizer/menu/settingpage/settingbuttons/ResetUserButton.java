package org.kelvinizer.menu.settingpage.settingbuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.constants.User;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;

/**
 * The {@code ResetUserButton} class handles the button and associated logic
 * for resetting user data. This includes clearing the user's game history
 * and displaying feedback about the operation's success or failure.
 * Implements {@link Scalable}, {@link Drawable}, and {@link Focusable}.
 * @author Boyan Hu
 */
public class ResetUserButton implements Scalable, Drawable, Focusable {

    /** The button that triggers the user data reset. */
    public final CRectButton resetUser = new CRectButton();

    /** The text displayed to the user after performing the reset operation. */
    public final BoundedString verdict1 = new BoundedString("", 0, 540, 675, 800, 400);

    /** The time when the verdict message should start appearing. */
    private long verdictAppearanceTime;

    /**
     * Constructs a {@code ResetUserButton} and initializes the button's appearance
     * in both normal and focused states.
     */
    public ResetUserButton(){
        BoundedString normalOn = new BoundedString("Clear User History", 30);
        normalOn.setBounds(new CRect(540, 585, 300, 100));
        normalOn.getBounds().setOutlineColor(Color.WHITE);
        normalOn.getBounds().setOutlineThickness(1.0);
        normalOn.setStyle(Font.PLAIN);
        resetUser.setNormal(normalOn);

        BoundedString onFocusOn = new BoundedString("Clear User History", 30);
        onFocusOn.setBounds(new CRect(540, 585, 360, 120));
        onFocusOn.getBounds().setOutlineColor(Color.BLUE);
        onFocusOn.getBounds().setOutlineThickness(5.0);
        resetUser.setOnFocus(onFocusOn);

        verdict1.setMaxStringSize(50);
    }

    /**
     * Resets the user data by archiving the old user data and creating a fresh user.
     * If the reset operation is successful, the user is informed that their data has been cleared.
     * Otherwise, an error message is displayed.
     */
    public void resetUserData(){
        String oldUser = users.get(userIndex).userName;
        users.get(userIndex).exportUser();
        File f = new File("Users/"+oldUser+".ptpuser");
        if(!f.renameTo(new File("Users/"+oldUser+".userarchive"))){
            verdict1.setString("Unable to clear "+oldUser+" game history. Reason: unable to create "+oldUser+".userarchive");
            verdict1.setStringColor(Color.RED);
            verdictAppearanceTime = System.nanoTime();
            return;
        }
        users.set(userIndex, null); // Trigger Java garbage collector
        users.set(userIndex, new User(oldUser));
        users.get(userIndex).exportUser();
        verdict1.setString("Successfully cleared "+oldUser+" game history. Old user data is in "+oldUser+".userarchive");
        verdict1.setStringColor(Color.GREEN);
        verdictAppearanceTime = System.nanoTime();
    }

    /**
     * Renders the reset user button and verdict text to the screen. The verdict text fades out
     * gradually after being displayed.
     *
     * @param g2d the {@link Graphics2D} object responsible for drawing
     */
    @Override
    public void render(Graphics2D g2d) {
        resetUser.render(g2d);
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                (float) Math.clamp(1.0 - 1.0 / 3 * (System.nanoTime() - verdictAppearanceTime) / 1e9, 0.0, 1.0)
        ));
        verdict1.render(g2d);
        verdict1.render(g2d);
    }

    /**
     * Sets the button focus when the user interacts with the mouse.
     *
     * @param e the {@link MouseEvent} indicating the focus event
     */
    @Override
    public void setFocused(MouseEvent e) {
        resetUser.setFocused(e);
    }

    /**
     * Scales the button and its associated components based on the new window dimensions.
     *
     * @param d the new dimension of the window
     */
    @Override
    public void scale(Dimension d) {
        resetUser.scale(d);
    }
}
package org.kelvinizer.game.gamewindow;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.game.gamebuttons.ResultPageButtons;
import org.kelvinizer.game.gametext.ResultPageText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents the result page of the game, which displays the outcome of a game session.
 * This panel contains buttons for restarting the game or going back to the previous screen,
 * and it handles user input for interaction such as mouse movements, clicks, and keyboard events.
 * The page also supports dynamic animations and transitions during the appearance and rendering.
 */
public class ResultPage extends AnimatablePanel {

    private final DynamicMotionManager dmm = new DynamicMotionManager();
    private final ResultPageButtons rpb = new ResultPageButtons(dmm);
    private boolean goBack = false;

    /**
     * Constructs a new ResultPage and sets up key bindings for user interaction.
     * It adds keyboard shortcuts for exiting the page or going back.
     */
    public ResultPage() {
        super(1500);
        new ResultPageText(dmm);

        // Add key binding for exiting the result page
        addKeyBinding(KeyEvent.VK_R, true, KeyEvent.CTRL_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        // Add key binding for going back to the previous screen
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack = true;
                exit();
            }
        });

        dmm.activate();
    }

    /**
     * Scales the buttons and elements on the result page according to the given dimension.
     *
     * @param d The new dimension to scale to.
     */
    @Override
    public void scale(Dimension d) {
        rpb.scale(d);
    }

    /**
     * Handles mouse movement over the result page. It updates the focus state of the buttons.
     *
     * @param e The mouse event containing the mouse's current position.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        rpb.setFocused(e);
    }

    /**
     * Handles mouse clicks on the result page. It checks if any button is clicked and triggers the corresponding action.
     *
     * @param e The mouse event triggered by the user's click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (rpb.restart.getCRectButton().isFocused()) {
            exit();
        } else if (rpb.back.getCRectButton().isFocused()) {
            goBack = true;
            exit();
        }
    }

    /**
     * Renders the dynamic motion effects on the result page when the page appears.
     *
     * @param g2d The Graphics2D object used for rendering the page content.
     */
    @Override
    public void onAppearance(Graphics2D g2d) {
        dmm.render(g2d);
    }

    /**
     * Renders the content of the result page, including the animations and visual effects.
     *
     * @param g2d The Graphics2D object used for rendering the page content.
     */
    @Override
    public void render(Graphics2D g2d) {
        dmm.render(g2d);
    }

    /**
     * Transitions to the next panel, either going back to the previous screen or to the results screen
     * based on the user's interaction with the result page.
     */
    @Override
    public void toNextPanel() {
        if (goBack) {
            Control.panel_index = 2;  // Go back to Song Selection
        } else {
            Control.panel_index = 3;  // Go to the previous chart
        }
    }
}
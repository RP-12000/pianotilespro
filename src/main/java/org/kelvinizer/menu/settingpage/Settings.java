package org.kelvinizer.menu.settingpage;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.menu.settingpage.settingbuttons.ResetUserButton;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsCRectButtons;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsGeneralButton;
import org.kelvinizer.menu.settingpage.settingbuttons.SettingsSlidingButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.kelvinizer.constants.Control.*;

/**
 * The {@code Settings} class represents the settings menu for the application.
 * It provides options for toggling user preferences, adjusting sliders, and navigating between pages.
 * Inherits from {@link AnimatablePanel}.
 * @author Boyan Hu
 */
public class Settings extends AnimatablePanel {

    /** Buttons for toggling settings with rectangular buttons. */
    private final SettingsCRectButtons scb = new SettingsCRectButtons();

    /** General navigation buttons for the settings menu. */
    private final SettingsGeneralButton sgb = new SettingsGeneralButton();

    /** Sliding buttons for adjustable settings. */
    private final SettingsSlidingButton ssb = new SettingsSlidingButton();

    /** Button for resetting user data. */
    private final ResetUserButton rub = new ResetUserButton();

    /** Text components for displaying settings information. */
    private final SettingsText st = new SettingsText();

    /** Current page index in the settings menu. */
    public static int page = 1;

    /**
     * Constructs the {@code Settings} panel and initializes its key bindings and behavior.
     */
    public Settings() {
        super();

        // Key bindings for toggling and navigating settings
        addKeyBinding(KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 1) {
                    users.get(userIndex).syncEnabled = !users.get(userIndex).syncEnabled;
                }
            }
        });

        addKeyBinding(KeyEvent.VK_H, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 1) {
                    users.get(userIndex).handHintEnabled = !users.get(userIndex).handHintEnabled;
                }
            }
        });

        addKeyBinding(KeyEvent.VK_F, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 1) {
                    users.get(userIndex).FCAPHintEnabled = !users.get(userIndex).FCAPHintEnabled;
                }
            }
        });

        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 2) {
                    ssb.musicDelay.moveSlider(-1.0);
                }
            }
        });

        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 2) {
                    ssb.musicDelay.moveSlider(1.0);
                }
            }
        });

        addKeyBinding(KeyEvent.VK_LEFT, true, KeyEvent.SHIFT_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 2) {
                    ssb.tolerance.moveSlider(-1.0);
                }
            }
        });

        addKeyBinding(KeyEvent.VK_RIGHT, true, KeyEvent.SHIFT_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 2) {
                    ssb.tolerance.moveSlider(1.0);
                }
            }
        });

        addKeyBinding(KeyEvent.VK_R, true, KeyEvent.CTRL_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page == 2) {
                    rub.resetUserData();
                }
            }
        });

        addKeyBinding(KeyEvent.VK_PAGE_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page = Math.min(2, page + 1);
            }
        });

        addKeyBinding(KeyEvent.VK_PAGE_UP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page = Math.max(1, page - 1);
            }
        });

        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }

    /**
     * Handles mouse movement to update button focus states.
     *
     * @param e the {@link MouseEvent} containing mouse movement data
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        scb.setFocused(e);
        sgb.setFocused(e);
        ssb.setFocused(e);
        rub.setFocused(e);
    }

    /**
     * Handles mouse dragging to adjust sliders on page 2.
     *
     * @param e the {@link MouseEvent} containing mouse drag data
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (page == 2) {
            ssb.musicDelay.moveSlider(e);
            ssb.tolerance.moveSlider(e);
        }
    }

    /**
     * Handles mouse clicks to trigger button actions or page navigation.
     *
     * @param e the {@link MouseEvent} containing mouse click data
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (page == 1) {
            // Handle page 1 button interactions
            if (scb.syncOn.isFocused()) {
                users.get(userIndex).syncEnabled = true;
            } else if (scb.syncOff.isFocused()) {
                users.get(userIndex).syncEnabled = false;
            } else if (scb.FCAP_On.isFocused()) {
                users.get(userIndex).FCAPHintEnabled = true;
            } else if (scb.FCAP_Off.isFocused()) {
                users.get(userIndex).FCAPHintEnabled = false;
            } else if (scb.handHintOn.isFocused()) {
                users.get(userIndex).handHintEnabled = true;
            } else if (scb.handHintOff.isFocused()) {
                users.get(userIndex).handHintEnabled = false;
            }
        } else {
            if (rub.resetUser.isFocused()) {
                rub.resetUserData();
            }
        }

        // Handle navigation and back button
        if (sgb.back.isFocused()) {
            exit();
        } else if (sgb.moveLeft.isFocused()) {
            page = Math.max(1, page - 1);
        } else if (sgb.moveRight.isFocused()) {
            page = Math.min(2, page + 1);
        }
    }

    /**
     * Adjusts the layout of buttons and text based on the provided dimensions.
     *
     * @param d the new {@link Dimension} of the panel
     */
    @Override
    public void scale(Dimension d) {
        scb.scale(d);
        ssb.scale(d);
        sgb.scale(d);
        rub.scale(d);
    }

    /**
     * Renders the settings menu, including buttons and text components.
     *
     * @param g2d the {@link Graphics2D} object used for rendering
     */
    @Override
    public void render(Graphics2D g2d) {
        sgb.back.render(g2d);
        st.updateText();
        st.render(g2d);
        if (page == 1) {
            sgb.moveRight.render(g2d);
            scb.render(g2d);
        }
        if (page == 2) {
            sgb.moveLeft.render(g2d);
            ssb.render(g2d);
            rub.render(g2d);
        }
        users.get(userIndex).MUSIC_DIFFERENCE = (int) ssb.musicDelay.getCurrentVal();
        users.get(userIndex).tolerance = (int) ssb.tolerance.getCurrentVal();
    }

    /**
     * Navigates to the next panel.
     */
    @Override
    public void toNextPanel() {
        Control.panel_index = -Control.panel_index;
    }
}
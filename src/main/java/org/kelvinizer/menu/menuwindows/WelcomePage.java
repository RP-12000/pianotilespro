package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.constants.Control;
import org.kelvinizer.animation.*;
import org.kelvinizer.menu.menutext.WelcomePageText;

import java.awt.*;
import java.awt.event.MouseEvent;

public class WelcomePage extends AnimatablePanel {
    private int click_count=0;
    private final WelcomePageText wpt = new WelcomePageText();
    public WelcomePage(){
        super(2000, 2000);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click_count++;
        if(click_count==2){
            removeMouseListener(this);
            exit();
        }
        repaint();
    }

    @Override
    public void render(Graphics2D g2d){
        wpt.render(g2d);
    }

    @Override
    public void toNextPanel() {
        Control.panel_index=1;
    }
}
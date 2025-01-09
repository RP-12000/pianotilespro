package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.dynamic.DynamicString;

import java.awt.*;
import java.awt.event.MouseEvent;

public class WelcomePage extends AnimatablePanel {
    private int click_count=0;
    private final DynamicString gameName = new DynamicString("Piano Tiles Pro", 81, 540, 200);
    private final DynamicString gameVersion = new DynamicString("v0.0.0-a", 27, 540, 590);
    private final DynamicString startVerdict = new DynamicString("Double click anywhere to start", 20, 540, 630);

    public WelcomePage(){
        super(2000, 2000);
        startVerdict.getBoundedString().setStyle(Font.ITALIC);
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
        gameName.render(g2d);
        gameVersion.render(g2d);
        startVerdict.render(g2d);
    }

    @Override
    public void toNextPanel() {
        Control.panel_index=1;
    }
}
package org.kelvinizer.gui;

import org.kelvinizer.Constants;
import org.kelvinizer.animation.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class WelcomePage extends AnimatablePanel {
    private int click_count=0;

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
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 27));
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.drawString(
                Constants.VERSION,
                540 - (metrics.stringWidth(Constants.VERSION) / 2),
                590 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
        g2d.setFont(new Font("Arial", Font.BOLD, 81));
        metrics = g2d.getFontMetrics();
        g2d.drawString(
                Constants.GameName,
                540 - (metrics.stringWidth(Constants.GameName) / 2),
                200 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        metrics = g2d.getFontMetrics();
        g2d.drawString(
                "Double click anywhere to start",
                540 - (metrics.stringWidth("Double click anywhere to start") / 2),
                630 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
    }

    @Override
    public void toNextPanel() {
        click_count = 0;
        Constants.panel_index=1;
    }
}
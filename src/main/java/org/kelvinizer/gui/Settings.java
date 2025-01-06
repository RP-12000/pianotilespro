package org.kelvinizer.gui;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.support.PolygonButton;
import org.kelvinizer.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Settings extends AnimatablePanel {
    private final PolygonButton normal = new PolygonButton(
            new Rectangle(100, 100, 200, 200)
    );
    private final PolygonButton autoplay = new PolygonButton(
            new Rectangle(500, 100, 200, 200)
    );
    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );

    public Settings(){
        super();
        addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constants.isAutoplay = !Constants.isAutoplay;
            }
        });
        addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constants.isAutoplay = !Constants.isAutoplay;
            }
        });
    }

    @Override
    public void mouseMoved(MouseEvent e){
        autoplay.setFocused(e);
        normal.setFocused(e);
        back.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(autoplay.contains(e.getPoint())){
            Constants.isAutoplay = true;
        }
        else if(normal.contains(e.getPoint())){
            Constants.isAutoplay = false;
        }
        else if(back.contains(e.getPoint())){
            exit();
        }
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(Constants.isAutoplay){
            autoplay.setFocused(true);
        }
        else{
            normal.setFocused(true);
        }
        normal.draw(g2d);
        autoplay.draw(g2d);
        back.draw(g2d);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.drawString(
                "Play",
                540 - (metrics.stringWidth("Play") / 2),
                600 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
        g2d.drawString(
                "Autoplay",
                540 - (metrics.stringWidth("Autoplay") / 2),
                600 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
    }

    @Override
    public void toNextPanel(){
        Constants.panel_index -= 10;
    }
}
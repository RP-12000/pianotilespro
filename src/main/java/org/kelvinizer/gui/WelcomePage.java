package org.kelvinizer.gui;

import org.kelvinizer.Constants;
import org.kelvinizer.animation.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class WelcomePage extends AnimatablePanel {
    private final BufferedImage image;
    private int click_count=0;

    public WelcomePage(){
        super(2000, 2000);
        try{
            image = ImageIO.read(new File("welcome.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        //g2d.drawImage(image, 0, 0, ReferenceWindow.REF_WIN_W, ReferenceWindow.REF_WIN_H, this);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 27));
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.drawString(
                Constants.VERSION,
                540 - (metrics.stringWidth(Constants.VERSION) / 2),
                620 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
        g2d.setFont(new Font("Arial", Font.BOLD, 81));
        metrics = g2d.getFontMetrics();
        g2d.drawString(
                Constants.GameName,
                540 - (metrics.stringWidth(Constants.GameName) / 2),
                200 + ((metrics.getAscent() - metrics.getDescent()) / 2)
        );
    }

    @Override
    public void onAppearance(Graphics2D g2d){
        if(image!=null){
            setAppearingOpacity(g2d);
            renderObjects(g2d);
        }
    }

    @Override
    public void onActive(Graphics2D g2d){
        if(image!=null){
            renderObjects(g2d);
        }
    }

    @Override
    public void onDisappearance(Graphics2D g2d){
        if(image!=null){
            setDisappearingOpacity(g2d);
            renderObjects(g2d);
        }
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
    public void toNextPanel() {
        click_count = 0;
        Constants.panel_index=1;
    }
}
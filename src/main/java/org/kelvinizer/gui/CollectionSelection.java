package org.kelvinizer.gui;

import org.kelvinizer.Animatable;
import org.kelvinizer.Constants.*;

import javax.swing.*;
import java.awt.*;

public class CollectionSelection extends JPanel implements Animatable {
    private int status;

    public CollectionSelection(){
        status = 1;
        setSize(ReferenceWindow.REF_WIN_W, ReferenceWindow.REF_WIN_H);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D) g;
        g2d.scale((double)getWidth()/ ReferenceWindow.REF_WIN_W, (double)getHeight()/ReferenceWindow.REF_WIN_H);
        switch(status){
            case 0:
                callOnAppearance(g2d);
                break;
            case 1:
                callOnActive(g2d);
                break;
            case 2:
                callOnDisappearance(g2d);
                break;
            default:
                break;
        }
    }

    @Override
    public void callOnAppearance(Graphics2D g2d) {

    }

    @Override
    public void callOnActive(Graphics2D g2d) {
        g2d.drawString("OK new panel is here", 100, 100);
    }

    @Override
    public void callOnDisappearance(Graphics2D g2d) {

    }
}

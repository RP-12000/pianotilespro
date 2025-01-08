package org.kelvinizer.buttons;

import org.kelvinizer.support.BoundedString;
import org.kelvinizer.shapes.CRect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RectangleButton extends KButton {
    private BoundedString normalMode;
    private BoundedString onFocus;
    private BoundedString onSelection = null;
    private BufferedImage icon = null;
    private CRect normalSpace, focusSpace, selectionSpace;

    public RectangleButton(){}

    public void setNormalMode(BoundedString bs) {
        normalMode = bs;
        normalSpace = new CRect(
                bs.getBounds().getX(),
                bs.getBounds().getY(),
                bs.getBounds().getWidth(),
                bs.getBounds().getHeight()
        );
        normalSpace.setOrigin(bs.getBounds().getOrigin());
    }

    public void setOnFocus(BoundedString bs) {
        onFocus = bs;
        focusSpace = new CRect(
                bs.getBounds().getX(),
                bs.getBounds().getY(),
                bs.getBounds().getWidth(),
                bs.getBounds().getHeight()
        );
        focusSpace.setOrigin(bs.getBounds().getOrigin());
    }

    public void setOnSelection(BoundedString bs) {
        onSelection = bs;
        selectionSpace = new CRect(
                bs.getBounds().getX(),
                bs.getBounds().getY(),
                bs.getBounds().getWidth(),
                bs.getBounds().getHeight()
        );
        selectionSpace.setOrigin(bs.getBounds().getOrigin());
    }

    @Override
    public void setFocused(MouseEvent e){
        if(selected){
            focused = selectionSpace.contains(e.getPoint());
        }
        else if(focused){
            focused = focusSpace.contains(e.getPoint());
        }
        else{
            focused = normalSpace.contains(e.getPoint());
        }
    }

    @Override
    public void resize(Dimension d){
        normalSpace.scale(d);
        focusSpace.scale(d);
        if(selectionSpace!=null){
            selectionSpace.scale(d);
        }
    }

    private void drawIcon(Graphics2D g2d){
        if(icon != null){
            if(selected){
                g2d.drawImage(icon,
                        (int)onSelection.getBounds().getX(),
                        (int)onSelection.getBounds().getY(),
                        (int)onSelection.getBounds().getWidth(),
                        (int)onSelection.getBounds().getHeight(),
                        null
                );
            }
            else if(focused){
                g2d.drawImage(icon,
                        (int)onFocus.getBounds().getX(),
                        (int)onFocus.getBounds().getY(),
                        (int)onFocus.getBounds().getWidth(),
                        (int)onFocus.getBounds().getHeight(),
                        null
                );
            }
            else{
                g2d.drawImage(icon,
                        (int)normalMode.getBounds().getX(),
                        (int)normalMode.getBounds().getY(),
                        (int)normalMode.getBounds().getWidth(),
                        (int)normalMode.getBounds().getHeight(),
                        null
                );
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d){
        if(selected){
            onSelection.draw(g2d);
        }
        else if(focused){
            onFocus.draw(g2d);
        }
        else{
            normalMode.draw(g2d);
        }
        drawIcon(g2d);
    }

    public void setIcon(BufferedImage bf){
        icon = bf;
    }
}
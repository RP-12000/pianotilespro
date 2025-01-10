package org.kelvinizer.buttons;

import org.kelvinizer.constants.Control;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.shapes.CRect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CRectButton extends KButton {
    private BoundedString normal;
    private BoundedString onFocus;
    private BoundedString onSelection = null;
    private BufferedImage icon = null;
    private CRect normalSpace, focusSpace, selectionSpace;

    public CRectButton(){}

    private void initNormalSpace(){
        normalSpace = new CRect(
                normal.getBounds().getX(),
                normal.getBounds().getY(),
                normal.getBounds().getWidth(),
                normal.getBounds().getHeight()
        );
        normalSpace.setOrigin(normal.getBounds().getOrigin());
    }

    public void setNormal(BoundedString bs) {
        normal = bs;
        initNormalSpace();
    }

    private void initFocusSpace(){
        focusSpace = new CRect(
                onFocus.getBounds().getX(),
                onFocus.getBounds().getY(),
                onFocus.getBounds().getWidth(),
                onFocus.getBounds().getHeight()
        );
        focusSpace.setOrigin(onFocus.getBounds().getOrigin());
    }

    public void setOnFocus(BoundedString bs) {
        onFocus = bs;
        initFocusSpace();
    }

    private void initSelectionSpace(){
        selectionSpace = new CRect(
                onSelection.getBounds().getX(),
                onSelection.getBounds().getY(),
                onSelection.getBounds().getWidth(),
                onSelection.getBounds().getHeight()
        );
        selectionSpace.setOrigin(onSelection.getBounds().getOrigin());
    }

    public void setOnSelection(BoundedString bs) {
        onSelection = bs;
        initSelectionSpace();
    }

    public BoundedString getNormal(){
        return normal;
    }

    public BoundedString getOnFocus(){
        return onFocus;
    }

    public BoundedString getOnSelection(){
        return onSelection;
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
    public void scale(Dimension d){
        initNormalSpace();
        initFocusSpace();
        normalSpace.scale(d);
        focusSpace.scale(d);
        if(selectionSpace!=null){
            initSelectionSpace();
            selectionSpace.scale(d);
        }
    }

    private void drawIcon(Graphics2D g2d){
        if(icon != null){
            if(selected){
                Rectangle r = onSelection.getBounds().toJShape();
                g2d.drawImage(icon, r.x, r.y, r.width, r.height,null);
            }
            else if(focused){
                Rectangle r = onFocus.getBounds().toJShape();
                g2d.drawImage(icon, r.x, r.y, r.width, r.height,null);
            }
            else{
                Rectangle r = normal.getBounds().toJShape();
                g2d.drawImage(icon, r.x, r.y, r.width, r.height,null);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d){
        if(selected){
            onSelection.render(g2d);
            g2d.setColor(Control.DEFAULT_COLOR);
            g2d.setStroke(Control.DEFAULT_STROKE);
            g2d.draw(onSelection.getBounds().toJShape());
        }
        else if(focused){
            onFocus.render(g2d);
        }
        else{
            normal.render(g2d);
        }
        drawIcon(g2d);
    }

    public void setIcon(BufferedImage bf){
        icon = bf;
    }
}
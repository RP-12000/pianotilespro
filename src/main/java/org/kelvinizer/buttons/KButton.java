package org.kelvinizer.buttons;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class KButton {
    protected boolean selected = false;
    protected boolean focused = false;

    public KButton(){}

    public void select(boolean option){
        selected = option;
    }

    public boolean isFocused() {
        return focused;
    }

    public abstract void setFocused(MouseEvent e);
    public abstract void resize(Dimension d);
    public abstract void draw(Graphics2D g2d);
}
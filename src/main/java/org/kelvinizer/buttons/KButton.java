package org.kelvinizer.buttons;

import org.kelvinizer.support.interfaces.*;

public abstract class KButton implements Drawable, Scalable, Focusable {
    protected boolean selected = false;
    protected boolean focused = false;

    public KButton(){}

    public void select(boolean option){
        selected = option;
    }

    public boolean isFocused() {
        return focused;
    }
}
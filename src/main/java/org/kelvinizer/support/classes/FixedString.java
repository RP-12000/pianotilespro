package org.kelvinizer.support.classes;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class FixedString extends BoundedString implements Drawable {
    private final int fixedStrSize;

    public FixedString(String d, int fixedStrSize, double x, double y, double width ,double height){
        super(d, 0, x, y, width, height);
        this.fixedStrSize = fixedStrSize;
    }

    public FixedString(String d, int fixedStrSize, double width, double height){
        this(d, fixedStrSize, 0, 0, width, height);
    }

    public FixedString(String d, int fixedStrSize){
        this(d, fixedStrSize, 0, 0);
    }

    public FixedString(){
        this("", 0);
    }

    @Override
    public void render(Graphics2D g2d) {
        super.getBounds().render(g2d);

    }
}

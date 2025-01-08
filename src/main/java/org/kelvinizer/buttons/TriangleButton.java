package org.kelvinizer.buttons;

import org.kelvinizer.shapes.CTriangle;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TriangleButton extends KButton {
    private CTriangle normal, onFocus;
    private CTriangle normalSpace, focusSpace;

    public TriangleButton(){}

    public void setNormal(CTriangle c){
        normal = c;
        normalSpace = new CTriangle(
                c.getX1(),
                c.getY1(),
                c.getX2(),
                c.getY2(),
                c.getX3(),
                c.getY3()
        );
    }

    public void setOnFocus(CTriangle c){
        onFocus = c;
        focusSpace = new CTriangle(
                c.getX1(),
                c.getY1(),
                c.getX2(),
                c.getY2(),
                c.getX3(),
                c.getY3()
        );
    }

    @Override
    public void setFocused(MouseEvent e) {
        if(focused){
            focused = focusSpace.contains(e.getPoint());
        }
        else{
            focused = normalSpace.contains(e.getPoint());
        }
    }

    @Override
    public void resize(Dimension d) {
        normalSpace.scale(d);
        focusSpace.scale(d);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if(focused){
            onFocus.draw(g2d);
        }
        else{
            normal.draw(g2d);
        }
    }
}

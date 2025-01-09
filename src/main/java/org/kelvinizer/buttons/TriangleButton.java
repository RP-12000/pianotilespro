package org.kelvinizer.buttons;

import org.kelvinizer.shapes.CTriangle;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TriangleButton extends KButton {
    private CTriangle normal, onFocus;
    private CTriangle normalSpace, focusSpace;

    public TriangleButton(){}

    private void initNormal(){
        normalSpace = new CTriangle(
                normal.getX1(),
                normal.getY1(),
                normal.getX2(),
                normal.getY2(),
                normal.getX3(),
                normal.getY3()
        );
    }

    public void setNormal(CTriangle c){
        normal = c;
        initNormal();
    }

    private void initOnFocus(){
        focusSpace = new CTriangle(
                onFocus.getX1(),
                onFocus.getY1(),
                onFocus.getX2(),
                onFocus.getY2(),
                onFocus.getX3(),
                onFocus.getY3()
        );
    }

    public void setOnFocus(CTriangle c) {
        onFocus = c;
        initOnFocus();
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
    public void scale(Dimension d) {
        initNormal();
        initOnFocus();
        normalSpace.scale(d);
        focusSpace.scale(d);
    }

    @Override
    public void render(Graphics2D g2d) {
        if(focused){
            onFocus.render(g2d);
        }
        else{
            normal.render(g2d);
        }
    }
}

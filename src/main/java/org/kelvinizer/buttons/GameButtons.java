package org.kelvinizer.buttons;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.BoundedString;

import java.awt.*;


public class GameButtons {
    public static final RectangleButton back = new RectangleButton();

    private static void backButton(){
        BoundedString normal = new BoundedString("", 1);
        normal.setBounds(new CRect(100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.getBounds().setOrigin(0,0);
        back.setNormalMode(normal);

        BoundedString onFocus = new BoundedString("", 1);
        onFocus.setBounds(new CRect(120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        onFocus.getBounds().setOrigin(0,0);
        back.setOnFocus(onFocus);
    }

    public static void init(){
        backButton();
    }
}

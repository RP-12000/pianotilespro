package org.kelvinizer.game.gamebuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.dynamic.DynamicCRectButton;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ResultPageButtons implements Scalable, Focusable {
    public final DynamicCRectButton back = new DynamicCRectButton();
    public final DynamicCRectButton restart = new DynamicCRectButton();

    private void initBack(){
        CRectButton button = new CRectButton();

        BoundedString normal = new BoundedString("", 0, 36, 36, 72, 72);
        BoundedString onFocus = new BoundedString("", 0, 42, 42, 84, 84);

        if(!button.setIcon("Back.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        button.setNormal(normal);
        button.setOnFocus(onFocus);
        back.setCRectButton(button);
        back.addHorizontalMotion(new Motion(0, 1.5, -36, 36, 1));
    }

    private void initRestart(){
        CRectButton button = new CRectButton();

        BoundedString normal = new BoundedString("", 0, 1044, 36, 72, 72);
        BoundedString onFocus = new BoundedString("", 0, 1032, 48, 84, 84);

        if(!button.setIcon("Restart.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        button.setNormal(normal);
        button.setOnFocus(onFocus);
        restart.setCRectButton(button);
        restart.addHorizontalMotion(new Motion(0, 1.5, 1116, 1044, 1));
    }

    public ResultPageButtons(DynamicMotionManager dmm){
        initBack();
        initRestart();
        dmm.addDynamicObject(back);
        dmm.addDynamicObject(restart);
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        restart.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        restart.scale(d);
    }
}

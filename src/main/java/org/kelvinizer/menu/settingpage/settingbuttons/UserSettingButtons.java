package org.kelvinizer.menu.settingpage.settingbuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UserSettingButtons implements Scalable, Drawable, Focusable {
    public CRectButton exportUser = new CRectButton();
    public CRectButton importUser = new CRectButton();

    private void setButton(CRectButton crb, int x, String s){
        BoundedString normal = new BoundedString(s, 30);
        normal.setBounds(new CRect(x, 360, 200, 120));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        crb.setNormal(normal);

        BoundedString onFocus = new BoundedString(s, 30);
        onFocus.setBounds(new CRect(x, 360, 240, 144));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        crb.setOnFocus(onFocus);
    }

    public UserSettingButtons(){
        setButton(exportUser, 340, "Export User");
        setButton(importUser, 740, "Import User");
    }

    @Override
    public void render(Graphics2D g2d) {
        exportUser.render(g2d);
        importUser.render(g2d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        exportUser.setFocused(e);
        importUser.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        exportUser.scale(d);
        importUser.scale(d);
    }
}
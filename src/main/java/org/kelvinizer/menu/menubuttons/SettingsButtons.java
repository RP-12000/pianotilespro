package org.kelvinizer.menu.menubuttons;

import org.kelvinizer.buttons.RectangleButton;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Resizable;

import java.awt.*;

public class SettingsButtons implements Resizable, Drawable {
    public final RectangleButton back = new RectangleButton();
    public final RectangleButton normalMode = new RectangleButton();
    public final RectangleButton autoplayMode = new RectangleButton();
    public final RectangleButton syncOn = new RectangleButton();
    public final RectangleButton syncOff = new RectangleButton();

    private void setBack(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(51, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        back.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(61, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        back.setOnFocus(onFocus);
    }

    private void setNormalMode(){
        BoundedString normal = new BoundedString("Off", 50);
        normal.setBounds(new CRect(400, 200, 200, 120));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        normalMode.setNormal(normal);

        BoundedString onFocus = new BoundedString("Off", 50);
        onFocus.setBounds(new CRect(400, 200, 220, 140));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        normalMode.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("Off", 50);
        onSelection.setBounds(new CRect(400, 200, 200, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        normalMode.setOnSelection(onSelection);
    }

    private void setAutoplayMode(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(800, 200, 200, 120));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        autoplayMode.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(800, 200, 220, 140));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        autoplayMode.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(800, 200, 200, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        autoplayMode.setOnSelection(onSelection);
    }

    private void setSyncOn(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(800, 400, 200, 120));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        syncOn.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(800, 400, 220, 140));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        syncOn.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(800, 400, 200, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        syncOn.setOnSelection(onSelection);
    }

    private void setSyncOff(){
        BoundedString normal = new BoundedString("Off", 50);
        normal.setBounds(new CRect(400, 400, 200, 120));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        syncOff.setNormal(normal);

        BoundedString onFocus = new BoundedString("Off", 50);
        onFocus.setBounds(new CRect(400, 400, 220, 140));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        syncOff.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("Off", 50);
        onSelection.setBounds(new CRect(400, 400, 200, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        syncOff.setOnSelection(onSelection);
    }

    public SettingsButtons(){
        setBack();
        setNormalMode();
        setAutoplayMode();
        setSyncOn();
        setSyncOff();
    }

    @Override
    public void resize(Dimension d) {
        back.resize(d);
        normalMode.resize(d);
        autoplayMode.resize(d);
        syncOn.resize(d);
        syncOff.resize(d);
    }

    @Override
    public void render(Graphics2D g2d) {
        back.render(g2d);
        normalMode.render(g2d);
        autoplayMode.render(g2d);
        syncOn.render(g2d);
        syncOff.render(g2d);
    }
}
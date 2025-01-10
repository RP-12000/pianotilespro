package org.kelvinizer.menu.menubuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;

public class SettingsButtons implements Scalable, Drawable {
    public final CRectButton back = new CRectButton();
    public final CRectButton normalMode = new CRectButton();
    public final CRectButton autoplayMode = new CRectButton();
    public final CRectButton syncOn = new CRectButton();
    public final CRectButton syncOff = new CRectButton();

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
        normal.setBounds(new CRect(540, 200, 160, 96));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        normalMode.setNormal(normal);

        BoundedString onFocus = new BoundedString("Off", 50);
        onFocus.setBounds(new CRect(540, 200, 180, 108));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        normalMode.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("Off", 50);
        onSelection.setBounds(new CRect(540, 200, 160, 96));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        normalMode.setOnSelection(onSelection);
    }

    private void setAutoplayMode(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(780, 200, 160, 96));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        autoplayMode.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(780, 200, 180, 108));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        autoplayMode.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(780, 200, 160, 96));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        autoplayMode.setOnSelection(onSelection);
    }

    private void setSyncOn(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(780, 380, 160, 96));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        syncOn.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(780, 380, 180, 108));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        syncOn.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(780, 380, 160, 96));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        syncOn.setOnSelection(onSelection);
    }

    private void setSyncOff(){
        BoundedString normal = new BoundedString("Off", 50);
        normal.setBounds(new CRect(540, 380, 160, 96));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        syncOff.setNormal(normal);

        BoundedString onFocus = new BoundedString("Off", 50);
        onFocus.setBounds(new CRect(540, 380, 180, 108));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        syncOff.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("Off", 50);
        onSelection.setBounds(new CRect(540, 380, 160, 96));
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
    public void scale(Dimension d) {
        back.scale(d);
        normalMode.scale(d);
        autoplayMode.scale(d);
        syncOn.scale(d);
        syncOff.scale(d);
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
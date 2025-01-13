package org.kelvinizer.menu.menubuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.SlidingButton;
import org.kelvinizer.constants.Control;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SettingsButtons implements Scalable, Drawable, Focusable {
    public final CRectButton back = new CRectButton();
    public final CRectButton normalMode = new CRectButton();
    public final CRectButton autoplayMode = new CRectButton();
    public final CRectButton syncOn = new CRectButton();
    public final CRectButton syncOff = new CRectButton();
    public final SlidingButton musicDelay = new SlidingButton(-600, 600, Control.MUSIC_DIFFERENCE+1000);

    private void setBack(){
        BoundedString normal = new BoundedString("", 0, 50, 50, 100, 100);
        BoundedString onFocus = new BoundedString("", 0, 60, 60, 120, 120);

        if(!back.setIcon("Back.jpg")) {
            normal.getBounds().setOutlineColor(Color.WHITE);
            normal.getBounds().setOutlineThickness(1.0);
            onFocus.getBounds().setOutlineColor(Color.BLUE);
            onFocus.getBounds().setOutlineThickness(5.0);
        }

        back.setNormal(normal);
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

    private void setMusicDelay(){
        CRectButton slider = new CRectButton();

        BoundedString normal = new BoundedString("", 0);
        normal.setBounds(new CRect(50, 50));
        normal.getBounds().setFillColor(Color.CYAN);
        slider.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 0);
        onFocus.setBounds(new CRect(50, 50));
        onFocus.getBounds().setFillColor(Color.CYAN);
        onFocus.getBounds().setOutlineColor(Color.YELLOW);
        onFocus.getBounds().setOutlineThickness(5.0);
        slider.setOnFocus(onFocus);

        CRect base = new CRect(640, 590, 400, 50);
        base.setFillColor(Color.WHITE);
        BoundedString verdict = new BoundedString("", 25, 640, 530);

        musicDelay.setBaseAndSlider(base, slider);
        musicDelay.setVerdict(verdict);
    }

    public SettingsButtons(){
        setBack();
        setNormalMode();
        setAutoplayMode();
        setSyncOn();
        setSyncOff();
        setMusicDelay();
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        normalMode.scale(d);
        autoplayMode.scale(d);
        syncOn.scale(d);
        syncOff.scale(d);
        musicDelay.scale(d);
    }

    @Override
    public void render(Graphics2D g2d) {
        back.render(g2d);
        normalMode.render(g2d);
        autoplayMode.render(g2d);
        syncOn.render(g2d);
        syncOff.render(g2d);
        musicDelay.setVerdictString((int)musicDelay.getCurrentVal() +" ms");
        musicDelay.render(g2d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        normalMode.setFocused(e);
        autoplayMode.setFocused(e);
        syncOn.setFocused(e);
        syncOff.setFocused(e);
        musicDelay.setFocused(e);
    }
}
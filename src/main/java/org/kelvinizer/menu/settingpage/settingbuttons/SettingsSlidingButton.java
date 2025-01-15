package org.kelvinizer.menu.settingpage.settingbuttons;

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

public class SettingsSlidingButton implements Scalable, Drawable, Focusable {
    public final SlidingButton tolerance = new SlidingButton(0, 160, Control.tolerance);
    public final SlidingButton frameRate = new SlidingButton(30, 144, Control.FPS);
    public final SlidingButton musicDelay = new SlidingButton(-600, 600, Control.MUSIC_DIFFERENCE);

    private void setSlidingButton(SlidingButton sb, double y){
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

        CRect base = new CRect(640, y+60, 400, 50);
        base.setFillColor(Color.WHITE);
        BoundedString verdict = new BoundedString("", 25, 640, y);

        sb.setBaseAndSlider(base, slider);
        sb.setVerdict(verdict);
    }

    public SettingsSlidingButton(){
        setSlidingButton(frameRate, 170);
        setSlidingButton(musicDelay, 350);
        setSlidingButton(tolerance, 530);
    }

    @Override
    public void render(Graphics2D g2d) {
        musicDelay.setVerdictString((int)musicDelay.getCurrentVal() +" ms");
        musicDelay.render(g2d);
        tolerance.setVerdictString((int)tolerance.getCurrentVal() +" ms");
        tolerance.render(g2d);
        frameRate.setVerdictString((int)frameRate.getCurrentVal() +" FPS");
        frameRate.render(g2d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        tolerance.setFocused(e);
        frameRate.setFocused(e);
        musicDelay.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        tolerance.scale(d);
        frameRate.scale(d);
        musicDelay.scale(d);
    }
}

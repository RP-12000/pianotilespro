package org.kelvinizer.menu.settingspage;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.constants.Control;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SettingsCRectButtons implements Scalable, Drawable, Focusable {
    public final CRectButton syncOn = new CRectButton();
    public final CRectButton syncOff = new CRectButton();
    public final CRectButton FCAP_On = new CRectButton();
    public final CRectButton FCAP_Off = new CRectButton();
    public final CRectButton handHintOn = new CRectButton();
    public final CRectButton handHintOff = new CRectButton();

    private void setOnAndOffIndicator(CRectButton on, CRectButton off, double y){
        BoundedString normalOn = new BoundedString("On", 50);
        normalOn.setBounds(new CRect(540, y, 160, 96));
        normalOn.getBounds().setOutlineColor(Color.WHITE);
        normalOn.getBounds().setOutlineThickness(1.0);
        normalOn.setStyle(Font.PLAIN);
        on.setNormal(normalOn);

        BoundedString onFocusOn = new BoundedString("On", 50);
        onFocusOn.setBounds(new CRect(540, y, 180, 108));
        onFocusOn.getBounds().setOutlineColor(Color.BLUE);
        onFocusOn.getBounds().setOutlineThickness(5.0);
        on.setOnFocus(onFocusOn);

        BoundedString onSelectionOn = new BoundedString("On", 50);
        onSelectionOn.setBounds(new CRect(540, y, 160, 96));
        onSelectionOn.getBounds().setOutlineColor(Color.GREEN);
        onSelectionOn.getBounds().setOutlineThickness(3.0);
        onSelectionOn.setStringColor(Color.GREEN);
        on.setOnSelection(onSelectionOn);

        BoundedString normal = new BoundedString("Off", 50);
        normal.setBounds(new CRect(780, y, 160, 96));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        off.setNormal(normal);

        BoundedString onFocus = new BoundedString("Off", 50);
        onFocus.setBounds(new CRect(780, y, 180, 108));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        off.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("Off", 50);
        onSelection.setBounds(new CRect(780, y, 160, 96));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        off.setOnSelection(onSelection);
    }

    public SettingsCRectButtons(){
        setOnAndOffIndicator(syncOn, syncOff, 200);
        setOnAndOffIndicator(FCAP_On, FCAP_Off, 380);
        setOnAndOffIndicator(handHintOn, handHintOff, 560);
    }

    @Override
    public void scale(Dimension d) {
        syncOn.scale(d);
        syncOff.scale(d);
        FCAP_On.scale(d);
        FCAP_Off.scale(d);
        handHintOn.scale(d);
        handHintOff.scale(d);
    }

    @Override
    public void render(Graphics2D g2d) {
        if(Control.syncEnabled){
            syncOff.select(false);
            syncOn.select(true);
        }
        else{
            syncOff.select(true);
            syncOn.select(false);
        }
        if(Control.FCAPHintEnabled){
            FCAP_Off.select(false);
            FCAP_On.select(true);
        }
        else{
            FCAP_Off.select(true);
            FCAP_On.select(false);
        }
        if(Control.handHintEnabled){
            handHintOff.select(false);
            handHintOn.select(true);
        }
        else{
            handHintOff.select(true);
            handHintOn.select(false);
        }
        syncOn.render(g2d);
        syncOff.render(g2d);
        FCAP_On.render(g2d);
        FCAP_Off.render(g2d);
        handHintOn.render(g2d);
        handHintOff.render(g2d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        syncOn.setFocused(e);
        syncOff.setFocused(e);
        FCAP_On.setFocused(e);
        FCAP_Off.setFocused(e);
        handHintOn.setFocused(e);
        handHintOff.setFocused(e);
    }
}
package org.kelvinizer.menu.menutext;

import org.kelvinizer.constants.Control;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SettingsText implements Drawable {
    private final BoundedString autoplayVerdict = new BoundedString("Autoplay", 25, 300, 200, 150, 60);
    private final BoundedString syncVerdict = new BoundedString("Sync Hints", 25, 300, 380, 150, 60);
    private final BoundedString musicVerdict = new BoundedString("Music Delay", 25, 300, 560, 150, 60);

    private final CRect autoplayBoundary = new CRect(540, 200, 720, 160);
    private final CRect syncBoundary = new CRect(540, 380, 720, 160);
    private final CRect musicBoundary = new CRect(540, 560, 720, 160);

    private final BoundedString delayMs = new BoundedString("", 25, 640, 520);
    private final CRect baseRect = new CRect(640, 600, 400, 50);
    private final CRect selectionRect = new CRect(baseRect.getHeight(), baseRect.getHeight());
    private final int minDelay = -600;
    private final int maxDelay = 600;
    private double start, end, k, b;

    private void initSlideButton(){
        baseRect.setFillColor(Color.WHITE);
        selectionRect.setFillColor(Color.CYAN);
        start = baseRect.getX() - baseRect.getWidth()/2.0 + selectionRect.getWidth()/2.0;
        end = baseRect.getX() + baseRect.getWidth()/2.0 - selectionRect.getWidth()/2.0;
        k = (maxDelay - minDelay) / (end - start);
        b = minDelay - k * start;
        selectionRect.setPosition((Control.MUSIC_DIFFERENCE - b)/k, baseRect.getY());
    }

    private void initVerdictsAndBoundaries(){
        autoplayVerdict.getBounds().setOutlineColor(Color.WHITE);
        autoplayVerdict.getBounds().setOutlineThickness(3.0);
        syncVerdict.getBounds().setOutlineColor(Color.WHITE);
        syncVerdict.getBounds().setOutlineThickness(3.0);
        musicVerdict.getBounds().setOutlineColor(Color.WHITE);
        musicVerdict.getBounds().setOutlineThickness(3.0);
        autoplayBoundary.setOutlineColor(Color.WHITE);
        autoplayBoundary.setOutlineThickness(5.0);
        syncBoundary.setOutlineColor(Color.WHITE);
        syncBoundary.setOutlineThickness(5.0);
        musicBoundary.setOutlineColor(Color.WHITE);
        musicBoundary.setOutlineThickness(5.0);
    }

    public SettingsText() {
        initVerdictsAndBoundaries();
        initSlideButton();
    }

    public void moveSelectionRectByMouse(MouseEvent e){
        if(selectionRect.contains(e.getPoint())){
            selectionRect.setX(Math.clamp(e.getX(), start, end));
            Control.MUSIC_DIFFERENCE = (int)(k*selectionRect.getX() + b);
        }
    }

    public void updateSelectionRect(int delta){
        Control.MUSIC_DIFFERENCE = Math.clamp(Control.MUSIC_DIFFERENCE+delta, minDelay, maxDelay);
        selectionRect.setX((Control.MUSIC_DIFFERENCE - b)/k);
    }

    @Override
    public void render(Graphics2D g2d) {
        autoplayVerdict.render(g2d);
        syncVerdict.render(g2d);
        musicVerdict.render(g2d);
        autoplayBoundary.render(g2d);
        syncBoundary.render(g2d);
        musicBoundary.render(g2d);
        baseRect.render(g2d);
        selectionRect.render(g2d);
        delayMs.setString(Control.MUSIC_DIFFERENCE +" ms");
        delayMs.render(g2d);
    }
}
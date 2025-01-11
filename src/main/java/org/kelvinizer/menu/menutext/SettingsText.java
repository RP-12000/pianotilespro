package org.kelvinizer.menu.menutext;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class SettingsText implements Drawable {
    private final BoundedString autoplayVerdict = new BoundedString("Autoplay", 25, 300, 200, 150, 60);
    private final BoundedString syncVerdict = new BoundedString("Sync Hints", 25, 300, 380, 150, 60);
    private final BoundedString musicVerdict = new BoundedString("Music Delay", 25, 300, 560, 150, 60);

    private final CRect autoplayBoundary = new CRect(540, 200, 720, 160);
    private final CRect syncBoundary = new CRect(540, 380, 720, 160);
    private final CRect musicBoundary = new CRect(540, 560, 720, 160);

    public SettingsText() {
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

    @Override
    public void render(Graphics2D g2d) {
        autoplayVerdict.render(g2d);
        syncVerdict.render(g2d);
        musicVerdict.render(g2d);
        autoplayBoundary.render(g2d);
        syncBoundary.render(g2d);
        musicBoundary.render(g2d);
    }
}
package org.kelvinizer.menu.settingspage;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class SettingsText implements Drawable {
    private final BoundedString firstVerdict = new BoundedString("Autoplay", 25, 300, 200, 150, 60);
    private final BoundedString secondVerdict = new BoundedString("Sync Hints", 25, 300, 380, 150, 60);
    private final BoundedString thirdVerdict = new BoundedString("Music Delay", 25, 300, 560, 150, 60);

    private final CRect firstBoundary = new CRect(540, 200, 720, 160);
    private final CRect secondBoundary = new CRect(540, 380, 720, 160);
    private final CRect thirdBoundary = new CRect(540, 560, 720, 160);

    public void setBoundAndVerdict(BoundedString verdict, CRect bound){
        verdict.getBounds().setOutlineColor(Color.WHITE);
        verdict.getBounds().setOutlineThickness(3.0);
        bound.setOutlineColor(Color.WHITE);
        bound.setOutlineThickness(5.0);
    }

    public SettingsText() {
        setBoundAndVerdict(firstVerdict, firstBoundary);
        setBoundAndVerdict(secondVerdict, secondBoundary);
        setBoundAndVerdict(thirdVerdict, thirdBoundary);
    }

    public void updateText(int pageNum){
        if(pageNum==1){
            firstVerdict.setString("Sync Hint");
            secondVerdict.setString("FC/AP Hint");
            thirdVerdict.setString("Hand Hint");
        }
        else{
            firstVerdict.setString("FPS");
            secondVerdict.setString("Music Delay");
            thirdVerdict.setString("Tolerance");
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        firstVerdict.render(g2d);
        secondVerdict.render(g2d);
        thirdVerdict.render(g2d);
        firstBoundary.render(g2d);
        secondBoundary.render(g2d);
        thirdBoundary.render(g2d);
    }
}
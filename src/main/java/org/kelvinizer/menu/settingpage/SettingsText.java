package org.kelvinizer.menu.settingpage;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class SettingsText implements Drawable {
    private final BoundedString header = new BoundedString("", 30, 540, 100, 150, 60);
    private final BoundedString firstVerdict = new BoundedString("", 25, 300, 240, 150, 60);
    private final BoundedString secondVerdict = new BoundedString("", 25, 300, 420, 150, 60);
    private final BoundedString thirdVerdict = new BoundedString("", 25, 300, 600, 150, 60);

    private final CRect firstBoundary = new CRect(540, 240, 720, 160);
    private final CRect secondBoundary = new CRect(540, 420, 720, 160);
    private final CRect thirdBoundary = new CRect(540, 600, 720, 160);

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
        header.getBounds().setOutlineColor(Color.WHITE);
        header.getBounds().setOutlineThickness(3.0);
    }

    public void updateText(){
        if(Settings.page==1){
            firstVerdict.setString("Sync Hint");
            secondVerdict.setString("FC/AP Hint");
            thirdVerdict.setString("Hand Hint");
        }
        else{
            firstVerdict.setString("Music Delay");
            secondVerdict.setString("Tolerance");
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        switch (Settings.page){
            case 1 -> header.setString("Hints");
            case 2 -> header.setString("Time");
        }
        header.render(g2d);
        firstVerdict.render(g2d);
        secondVerdict.render(g2d);
        firstBoundary.render(g2d);
        secondBoundary.render(g2d);
        if(Settings.page==1){
            thirdVerdict.render(g2d);
            thirdBoundary.render(g2d);
        }
    }
}
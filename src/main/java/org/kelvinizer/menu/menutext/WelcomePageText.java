package org.kelvinizer.menu.menutext;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.FixedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class WelcomePageText implements Drawable {
    public final FixedString gameName = new FixedString("Piano Tiles Pro", 81);
    public final FixedString gameVersion = new FixedString("v0.0.0-a", 27);
    public final FixedString startVerdict = new FixedString("Double click anywhere to start", 20);

    private void setGameName(){
        gameName.setBounds(new CRect(
                540, 200, 0, 0
        ));
    }

    private void setGameVersion(){
        gameVersion.setBounds(new CRect(
                540, 590, 0, 0
        ));
    }

    private void setStartVerdict(){
        startVerdict.setBounds(new CRect(
                540, 630, 0, 0
        ));
        startVerdict.setStyle(Font.PLAIN);
    }

    public WelcomePageText(){
        setGameName();
        setGameVersion();
        setStartVerdict();
    }

    @Override
    public void render(Graphics2D g2d) {
        gameVersion.render(g2d);
        gameName.render(g2d);
        startVerdict.render(g2d);
    }
}
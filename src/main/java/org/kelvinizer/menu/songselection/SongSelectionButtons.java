package org.kelvinizer.menu.songselection;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.CTriangleButton;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SongSelectionButtons implements Scalable, Focusable {
    public final CRectButton basic = new CRectButton();
    public final CRectButton BS = new CRectButton();
    public final CRectButton medium = new CRectButton();
    public final CRectButton MD = new CRectButton();
    public final CRectButton advanced = new CRectButton();
    public final CRectButton AV = new CRectButton();
    public final CRectButton legendary = new CRectButton();
    public final CRectButton LG = new CRectButton();
    public final CTriangleButton moveUp = new CTriangleButton();
    public final CTriangleButton moveDown = new CTriangleButton();

    private void setLevelButton(CRectButton button, CRectButton verdict, Color selectedColor, String levelString, double x){
        BoundedString normal = new BoundedString("", 30, x, 505, 50, 50);
        normal.setRelativeY(0.35);
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        button.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 30, x, 505, 50, 50);
        onFocus.setRelativeY(0.35);
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        button.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 30, x, 505, 50, 50);
        onSelection.setRelativeY(0.35);
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(selectedColor);
        button.setOnSelection(onSelection);

        BoundedString normal2 = new BoundedString(levelString, 13, x, 505, 50, 50);
        normal2.setRelativeY(0.8);
        normal2.getBounds().setOutlineColor(Color.WHITE);
        normal2.getBounds().setOutlineThickness(1.0);
        verdict.setNormal(normal2);

        BoundedString onFocus2 = new BoundedString(levelString, 13, x, 505, 50, 50);
        onFocus2.setRelativeY(0.8);
        onFocus2.getBounds().setOutlineColor(Color.WHITE);
        onFocus2.getBounds().setOutlineThickness(3.0);
        verdict.setOnFocus(onFocus2);

        BoundedString onSelection2 = new BoundedString(levelString, 13, x, 505, 50, 50);
        onSelection2.setRelativeY(0.8);
        onSelection2.getBounds().setOutlineColor(Color.WHITE);
        onSelection2.getBounds().setOutlineThickness(1.0);
        verdict.setOnSelection(onSelection2);
    }

    private void setLevelButtons(){
        setLevelButton(basic, BS, Color.GREEN, "BS",545);
        setLevelButton(medium, MD, Color.BLUE, "MD",595);
        setLevelButton(advanced, AV, Color.RED, "AV",645);
        setLevelButton(legendary, LG, Color.MAGENTA, "LG",695);
    }

    private void setMoveUp(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(220, 150);
        normal.setSecondPoint(380, 150);
        normal.setThirdPoint(300, 100);
        normal.setFillColor(Color.WHITE);
        moveUp.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(220, 150);
        onFocus.setSecondPoint(380, 150);
        onFocus.setThirdPoint(300, 100);
        onFocus.setFillColor(Color.WHITE);
        onFocus.setOutlineColor(Color.BLUE);
        onFocus.setOutlineThickness(3.0);
        moveUp.setOnFocus(onFocus);
    }

    private void setMoveDown(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(220, 550);
        normal.setSecondPoint(380, 550);
        normal.setThirdPoint(300, 600);
        normal.setFillColor(Color.WHITE);
        moveDown.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(220, 550);
        onFocus.setSecondPoint(380, 550);
        onFocus.setThirdPoint(300, 600);
        onFocus.setFillColor(Color.WHITE);
        onFocus.setOutlineColor(Color.BLUE);
        onFocus.setOutlineThickness(3.0);
        moveDown.setOnFocus(onFocus);
    }

    public SongSelectionButtons(){
        setLevelButtons();
        setMoveUp();
        setMoveDown();
    }

    @Override
    public void scale(Dimension d) {
        basic.scale(d);
        BS.scale(d);
        medium.scale(d);
        MD.scale(d);
        advanced.scale(d);
        AV.scale(d);
        legendary.scale(d);
        LG.scale(d);
        moveUp.scale(d);
        moveDown.scale(d);
    }

    private String levelToString(Pair<String, Double> level){
        return ((Integer)(int)(double)(level.second)).toString();
    }

    private void renderOneLevel(CRectButton button, CRectButton verdict, Graphics2D g2d, Song s, String level){
        button.select(Selection.level.equals(level));
        verdict.select(Selection.level.equals(level));
        button.getNormal().setString(levelToString(s.getCharterData(level)));
        button.getOnFocus().setString(levelToString(s.getCharterData(level)));
        button.getOnSelection().setString(levelToString(s.getCharterData(level)));
        button.render(g2d);
        verdict.render(g2d);
    }

    public void renderLevels(Graphics2D g2d, Song s){
        renderOneLevel(basic, BS, g2d, s, "BS");
        renderOneLevel(medium, MD, g2d, s, "MD");
        renderOneLevel(advanced, AV, g2d, s, "AV");
        if(s.hasLG()){
            renderOneLevel(legendary, LG, g2d, s, "LG");
        }
    }

    @Override
    public void setFocused(MouseEvent e) {
        basic.setFocused(e);
        medium.setFocused(e);
        advanced.setFocused(e);
        legendary.setFocused(e);
        moveUp.setFocused(e);
        moveDown.setFocused(e);
    }
}
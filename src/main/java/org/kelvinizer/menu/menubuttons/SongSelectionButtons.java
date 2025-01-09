package org.kelvinizer.menu.menubuttons;

import org.kelvinizer.buttons.RectangleButton;
import org.kelvinizer.buttons.TriangleButton;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.interfaces.Focusable;
import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SongSelectionButtons implements Scalable, Focusable {
    public final RectangleButton back = new RectangleButton();
    public final RectangleButton settings = new RectangleButton();
    public final RectangleButton play = new RectangleButton();
    public final RectangleButton basic = new RectangleButton();
    public final RectangleButton medium = new RectangleButton();
    public final RectangleButton advanced = new RectangleButton();
    public final RectangleButton legendary = new RectangleButton();
    public final TriangleButton moveUp = new TriangleButton();
    public final TriangleButton moveDown = new TriangleButton();

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

    private void setSettings(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1015, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        settings.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1005, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        settings.setOnFocus(onFocus);
    }

    private void setPlay(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1015, 633, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        play.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1005, 623, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        play.setOnFocus(onFocus);
    }

    private void setBasic(){
        BoundedString normal = new BoundedString("", 15);
        normal.setBounds(new CRect(545, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        basic.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 15);
        onFocus.setBounds(new CRect(545, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        basic.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 15);
        onSelection.setBounds(new CRect(545, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.GREEN);
        basic.setOnSelection(onSelection);
    }

    private void setMedium(){
        BoundedString normal = new BoundedString("", 15);
        normal.setBounds(new CRect(595, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        medium.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 15);
        onFocus.setBounds(new CRect(595, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        medium.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 15);
        onSelection.setBounds(new CRect(595, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.BLUE);
        medium.setOnSelection(onSelection);
    }

    private void setAdvanced(){
        BoundedString normal = new BoundedString("", 15);
        normal.setBounds(new CRect(645, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        advanced.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 15);
        onFocus.setBounds(new CRect(645, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        advanced.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 15);
        onSelection.setBounds(new CRect(645, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.RED);
        advanced.setOnSelection(onSelection);
    }

    private void setLegendary(){
        BoundedString normal = new BoundedString("", 15);
        normal.setBounds(new CRect(695, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        legendary.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 15);
        onFocus.setBounds(new CRect(695, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        legendary.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 15);
        onSelection.setBounds(new CRect(695, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.MAGENTA);
        legendary.setOnSelection(onSelection);
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
        setBack();
        setSettings();
        setPlay();
        setBasic();
        setMedium();
        setAdvanced();
        setLegendary();
        setMoveUp();
        setMoveDown();
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        settings.scale(d);
        play.scale(d);
        basic.scale(d);
        medium.scale(d);
        advanced.scale(d);
        legendary.scale(d);
        moveUp.scale(d);
        moveDown.scale(d);
    }

    private String levelToString(Pair<String, Double> level){
        return ((Integer)(int)(double)(level.second)).toString();
    }

    public void renderLevels(Graphics2D g2d, Song s){
        basic.select(false);
        medium.select(false);
        advanced.select(false);
        legendary.select(false);

        switch (Selection.level) {
            case "BS" -> basic.select(true);
            case "MD" -> medium.select(true);
            case "AV" -> advanced.select(true);
            case "LG" -> legendary.select(true);
        }

        basic.getNormal().setString(levelToString(s.getBasicData()));
        basic.getOnFocus().setString(levelToString(s.getBasicData()));
        basic.getOnSelection().setString(levelToString(s.getBasicData()));
        medium.getNormal().setString(levelToString(s.getMediumData()));
        medium.getOnFocus().setString(levelToString(s.getMediumData()));
        medium.getOnSelection().setString(levelToString(s.getMediumData()));
        advanced.getNormal().setString(levelToString(s.getAdvancedData()));
        advanced.getOnFocus().setString(levelToString(s.getAdvancedData()));
        advanced.getOnSelection().setString(levelToString(s.getAdvancedData()));
        if(s.hasLG()){
            legendary.getNormal().setString(levelToString(s.getLegendaryData()));
            legendary.getOnFocus().setString(levelToString(s.getLegendaryData()));
            legendary.getOnSelection().setString(levelToString(s.getLegendaryData()));
        }

        basic.render(g2d);
        medium.render(g2d);
        advanced.render(g2d);
        if(s.hasLG()){
            legendary.render(g2d);
        }
    }

    @Override
    public void setFocused(MouseEvent e) {
        back.setFocused(e);
        settings.setFocused(e);
        play.setFocused(e);
        basic.setFocused(e);
        medium.setFocused(e);
        advanced.setFocused(e);
        legendary.setFocused(e);
        moveUp.setFocused(e);
        moveDown.setFocused(e);
    }
}

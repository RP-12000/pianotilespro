package org.kelvinizer.guibuttons;

import org.kelvinizer.buttons.RectangleButton;
import org.kelvinizer.buttons.TriangleButton;
import org.kelvinizer.gamewindow.Song;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.shapes.CTriangle;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Resizable;

import java.awt.*;

public class SongSelectionButtons implements Resizable {
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
        normal.setBounds(new CRect(50, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        back.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(60, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        onFocus.getBounds().setOrigin(0,0);
        back.setOnFocus(onFocus);
    }

    private void setSettings(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1030, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        settings.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1020, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        settings.setOnFocus(onFocus);
    }

    private void setPlay(){
        BoundedString normal = new BoundedString();
        normal.setBounds(new CRect(1030, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        play.setNormal(normal);

        BoundedString onFocus = new BoundedString();
        onFocus.setBounds(new CRect(1020, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        play.setOnFocus(onFocus);
    }

    private void setBasic(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(50, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        basic.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(60, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        basic.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(60, 60, 120, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        basic.setOnSelection(onSelection);
    }

    private void setMedium(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(50, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        medium.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(60, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        medium.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(60, 60, 120, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        medium.setOnSelection(onSelection);
    }

    private void setAdvanced(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(50, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        advanced.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(60, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        advanced.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(60, 60, 120, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        advanced.setOnSelection(onSelection);
    }

    private void setLegendary(){
        BoundedString normal = new BoundedString("On", 50);
        normal.setBounds(new CRect(50, 50, 100, 100));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        legendary.setNormal(normal);

        BoundedString onFocus = new BoundedString("On", 50);
        onFocus.setBounds(new CRect(60, 60, 120, 120));
        onFocus.getBounds().setOutlineColor(Color.BLUE);
        onFocus.getBounds().setOutlineThickness(5.0);
        legendary.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("On", 50);
        onSelection.setBounds(new CRect(60, 60, 120, 120));
        onSelection.getBounds().setOutlineColor(Color.GREEN);
        onSelection.getBounds().setOutlineThickness(3.0);
        onSelection.setStringColor(Color.GREEN);
        legendary.setOnSelection(onSelection);
    }

    private void setMoveUp(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(100, 100);
        normal.setSecondPoint(100, 100);
        normal.setThirdPoint(100, 100);
        normal.setFillColor(Color.WHITE);
        moveUp.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(100, 100);
        onFocus.setSecondPoint(100, 100);
        onFocus.setThirdPoint(100, 100);
        onFocus.setFillColor(Color.WHITE);
        moveUp.setOnFocus(onFocus);
    }

    private void setMoveDown(){
        CTriangle normal = new CTriangle();
        normal.setFirstPoint(100, 100);
        normal.setSecondPoint(100, 100);
        normal.setThirdPoint(100, 100);
        normal.setFillColor(Color.WHITE);
        moveDown.setNormal(normal);

        CTriangle onFocus = new CTriangle();
        onFocus.setFirstPoint(100, 100);
        onFocus.setSecondPoint(100, 100);
        onFocus.setThirdPoint(100, 100);
        onFocus.setFillColor(Color.WHITE);
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
    public void resize(Dimension d) {
        back.resize(d);
        settings.resize(d);
        play.resize(d);
        basic.resize(d);
        medium.resize(d);
        advanced.resize(d);
        legendary.resize(d);
        moveUp.resize(d);
        moveDown.resize(d);
    }

    public void deselectAllLevel(){
        basic.select(false);
        medium.select(false);
        advanced.select(false);
        legendary.select(false);
    }

    public void addLevelToButtons(Song s){
        basic.getNormal().setString(s.getBasicData().second.toString());
        basic.getOnFocus().setString(s.getBasicData().second.toString());
        basic.getOnSelection().setString(s.getBasicData().second.toString());
        medium.getNormal().setString(s.getBasicData().second.toString());
        medium.getOnFocus().setString(s.getBasicData().second.toString());
        medium.getOnSelection().setString(s.getBasicData().second.toString());
        advanced.getNormal().setString(s.getBasicData().second.toString());
        advanced.getOnFocus().setString(s.getBasicData().second.toString());
        advanced.getOnSelection().setString(s.getBasicData().second.toString());
        legendary.getNormal().setString(s.getBasicData().second.toString());
        legendary.getOnFocus().setString(s.getBasicData().second.toString());
        legendary.getOnSelection().setString(s.getBasicData().second.toString());
    }
}

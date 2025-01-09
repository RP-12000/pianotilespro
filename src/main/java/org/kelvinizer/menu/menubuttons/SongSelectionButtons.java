package org.kelvinizer.menu.menubuttons;

import org.kelvinizer.buttons.CRectButton;
import org.kelvinizer.buttons.CTriangleButton;
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
    public final CRectButton back = new CRectButton();
    public final CRectButton settings = new CRectButton();
    public final CRectButton play = new CRectButton();
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
        BoundedString normal = new BoundedString("", 30);
        normal.setRelativeY(0.35);
        normal.setBounds(new CRect(545, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        basic.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 30);
        onFocus.setRelativeY(0.35);
        onFocus.setBounds(new CRect(545, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        basic.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 30);
        onSelection.setRelativeY(0.35);
        onSelection.setBounds(new CRect(545, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.GREEN);
        basic.setOnSelection(onSelection);
    }

    private void setBS(){
        BoundedString normal = new BoundedString("BS", 13);
        normal.setRelativeY(0.8);
        normal.setBounds(new CRect(545, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        BS.setNormal(normal);

        BoundedString onFocus = new BoundedString("BS", 13);
        onFocus.setRelativeY(0.8);
        onFocus.setBounds(new CRect(545, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        BS.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("BS", 13);
        onSelection.setRelativeY(0.8);
        onSelection.setBounds(new CRect(545, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        BS.setOnSelection(onSelection);
    }

    private void setMedium(){
        BoundedString normal = new BoundedString("", 30);
        normal.setRelativeY(0.35);
        normal.setBounds(new CRect(595, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        medium.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 30);
        onFocus.setRelativeY(0.35);
        onFocus.setBounds(new CRect(595, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        medium.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 30);
        onSelection.setRelativeY(0.35);
        onSelection.setBounds(new CRect(595, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.BLUE);
        medium.setOnSelection(onSelection);
    }

    private void setMD(){
        BoundedString normal = new BoundedString("MD", 13);
        normal.setRelativeY(0.8);
        normal.setBounds(new CRect(595, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        MD.setNormal(normal);

        BoundedString onFocus = new BoundedString("MD", 13);
        onFocus.setRelativeY(0.8);
        onFocus.setBounds(new CRect(595, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        MD.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("MD", 13);
        onSelection.setRelativeY(0.8);
        onSelection.setBounds(new CRect(595, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        MD.setOnSelection(onSelection);
    }

    private void setAdvanced(){
        BoundedString normal = new BoundedString("", 30);
        normal.setRelativeY(0.35);
        normal.setBounds(new CRect(645, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        advanced.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 30);
        onFocus.setRelativeY(0.35);
        onFocus.setBounds(new CRect(645, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        advanced.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 30);
        onSelection.setRelativeY(0.35);
        onSelection.setBounds(new CRect(645, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.RED);
        advanced.setOnSelection(onSelection);
    }

    private void setAV(){
        BoundedString normal = new BoundedString("AV", 13);
        normal.setRelativeY(0.8);
        normal.setBounds(new CRect(645, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        AV.setNormal(normal);

        BoundedString onFocus = new BoundedString("AV", 13);
        onFocus.setRelativeY(0.8);
        onFocus.setBounds(new CRect(645, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        AV.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("AV", 13);
        onSelection.setRelativeY(0.8);
        onSelection.setBounds(new CRect(645, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        AV.setOnSelection(onSelection);
    }

    private void setLegendary(){
        BoundedString normal = new BoundedString("", 30);
        normal.setRelativeY(0.35);
        normal.setBounds(new CRect(695, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        normal.setStyle(Font.PLAIN);
        legendary.setNormal(normal);

        BoundedString onFocus = new BoundedString("", 30);
        onFocus.setRelativeY(0.35);
        onFocus.setBounds(new CRect(695, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        legendary.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("", 30);
        onSelection.setRelativeY(0.35);
        onSelection.setBounds(new CRect(695, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        onSelection.getBounds().setFillColor(Color.MAGENTA);
        legendary.setOnSelection(onSelection);
    }

    private void setLG(){
        BoundedString normal = new BoundedString("LG", 13);
        normal.setRelativeY(0.8);
        normal.setBounds(new CRect(695, 505, 50, 50));
        normal.getBounds().setOutlineColor(Color.WHITE);
        normal.getBounds().setOutlineThickness(1.0);
        LG.setNormal(normal);

        BoundedString onFocus = new BoundedString("LG", 13);
        onFocus.setRelativeY(0.8);
        onFocus.setBounds(new CRect(695, 505, 50, 50));
        onFocus.getBounds().setOutlineColor(Color.WHITE);
        onFocus.getBounds().setOutlineThickness(3.0);
        LG.setOnFocus(onFocus);

        BoundedString onSelection = new BoundedString("LG", 13);
        onSelection.setRelativeY(0.8);
        onSelection.setBounds(new CRect(695, 505, 50, 50));
        onSelection.getBounds().setOutlineColor(Color.WHITE);
        onSelection.getBounds().setOutlineThickness(1.0);
        LG.setOnSelection(onSelection);
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
        setBS();
        setMedium();
        setMD();
        setAdvanced();
        setAV();
        setLegendary();
        setLG();
        setMoveUp();
        setMoveDown();
    }

    @Override
    public void scale(Dimension d) {
        back.scale(d);
        settings.scale(d);
        play.scale(d);
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

    public void renderLevels(Graphics2D g2d, Song s){
        basic.select(false);
        BS.select(false);
        medium.select(false);
        MD.select(false);
        advanced.select(false);
        AV.select(false);
        legendary.select(false);
        LG.select(false);

        switch (Selection.level) {
            case "BS" -> {
                basic.select(true);
                BS.select(true);
            }
            case "MD" -> {
                medium.select(true);
                MD.select(true);
            }
            case "AV" -> {
                advanced.select(true);
                AV.select(true);
            }
            case "LG" -> {
                legendary.select(true);
                LG.select(true);
            }
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
        BS.render(g2d);
        medium.render(g2d);
        MD.render(g2d);
        advanced.render(g2d);
        AV.render(g2d);
        if(s.hasLG()){
            legendary.render(g2d);
            LG.render(g2d);
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

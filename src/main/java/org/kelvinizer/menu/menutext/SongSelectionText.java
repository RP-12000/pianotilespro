package org.kelvinizer.menu.menutext;

import org.kelvinizer.constants.Selection;
import org.kelvinizer.dynamic.DynamicImage;
import org.kelvinizer.dynamic.DynamicString;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.interfaces.Activatable;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SongSelectionText implements Activatable, Drawable {
    public DynamicString selectedSong = new DynamicString();
    public DynamicString focusedSongLevel = new DynamicString();
    public DynamicString composer = new DynamicString();
    public DynamicString illustrator = new DynamicString();
    public DynamicString charter = new DynamicString();
    public DynamicImage jacket = new DynamicImage();

    public BoundedString previousSong = new BoundedString();
    public BoundedString previousSongLevel = new BoundedString();
    public BoundedString previousSongComposer = new BoundedString();
    public BoundedString nextSong = new BoundedString();
    public BoundedString nextSongLevel = new BoundedString();
    public BoundedString nextSongComposer = new BoundedString();

    public BoundedString emptyFolder = new BoundedString("Nothing is in here QAQ", 50, 540, 360);
    public BoundedString corruptedFolder = new BoundedString("Collection corrupted QAQ", 50, 540, 360);
    public BoundedString nullJacket = new BoundedString("No jacket preview available", 15, 675, 340);

    private void setSelectedSong(){
        selectedSong.getBoundedString().setMaxStringSize(20);
        selectedSong.getBoundedString().setBounds(new CRect(300, 350, 400, 60));
        selectedSong.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        selectedSong.getBoundedString().getBounds().setOutlineThickness(5.0);
        selectedSong.addVerticalMotion(new Motion(0, 0.5, 350, 200, 0));
        selectedSong.addHorizontalMotion(new Motion(1.5, 2.0, 300, 1400, 1));
    }

    private void setFocusedSongLevel(){
        focusedSongLevel.getBoundedString().setStringSize(20);
        focusedSongLevel.getBoundedString().setBounds(new CRect(470, 350, 60, 60));
        focusedSongLevel.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        focusedSongLevel.getBoundedString().getBounds().setOutlineThickness(5.0);
        focusedSongLevel.addVerticalMotion(new Motion(0, 0.5, 350, 200, 0));
        focusedSongLevel.addHorizontalMotion(new Motion(1.5, 2.0, 470, 1570, 1));
    }

    private void setComposer(){
        composer.getBoundedString().setStringSize(20);
        composer.getBoundedString().setBounds(new CRect(470, 350, 60, 60));
        composer.addVerticalMotion(new Motion(0, 0.5, 350, 200, 0));
        composer.addHorizontalMotion(new Motion(1.5, 2.0, 470, 1570, 1));
    }

    private void setIllustrator(){
        illustrator.getBoundedString().setStringSize(20);
        illustrator.getBoundedString().setBounds(new CRect(470, 350, 60, 60));
        illustrator.addVerticalMotion(new Motion(0, 0.5, 350, 200, 0));
        illustrator.addHorizontalMotion(new Motion(1.5, 2.0, 470, 1570, 1));
    }

    private void setCharter(){
        charter.getBoundedString().setStringSize(20);
        charter.getBoundedString().setBounds(new CRect(470, 350, 60, 60));
        charter.addVerticalMotion(new Motion(0, 0.5, 350, 200, 0));
        charter.addHorizontalMotion(new Motion(1.5, 2.0, 470, 1570, 1));
    }

    private void setJacket(){
        jacket.setBounds(new CRect(760, 330, 480, 300));
        jacket.addHorizontalMotion(new Motion(1.5, 2.0, 760, 1860, 1));
    }

    private void setPreviousSong(){

    }

    private void setPreviousSongLevel(){

    }

    private void setPreviousSongComposer(){

    }

    private void setNextSong(){

    }

    private void setNextSongLevel(){

    }

    private void setNextSongComposer(){

    }

    private void setNullJacket(){
        nullJacket.setStyle(Font.ITALIC);
    }

    public SongSelectionText(){
        setSelectedSong();
        setCharter();
        setComposer();
        setJacket();
        setIllustrator();
        setFocusedSongLevel();
        setPreviousSong();
        setPreviousSongLevel();
        setPreviousSongComposer();
        setNextSong();
        setNextSongLevel();
        setNextSongComposer();
        setNullJacket();
    }

    public void setStrings(Song s, BufferedImage bf){
        selectedSong.getBoundedString().setString(s.getSongName());
        composer.getBoundedString().setString(s.getComposer());
        illustrator.getBoundedString().setString(s.getIllustration());
        switch (Selection.level){
            case "BS" ->{
                charter.getBoundedString().setString(s.getBasicData().first);
                focusedSongLevel.getBoundedString().setString(s.getBasicData().second.toString());
            }
            case "MD" ->{
                charter.getBoundedString().setString(s.getMediumData().first);
                focusedSongLevel.getBoundedString().setString(s.getMediumData().second.toString());
            }
            case "AV" ->{
                charter.getBoundedString().setString(s.getAdvancedData().first);
                focusedSongLevel.getBoundedString().setString(s.getAdvancedData().second.toString());
            }
            case "LG" ->{
                charter.getBoundedString().setString(s.getLegendaryData().first);
                focusedSongLevel.getBoundedString().setString(s.getLegendaryData().second.toString());
            }
        }
        jacket.setImage(bf);
    }

    @Override
    public void activate() {
        selectedSong.activate();
        focusedSongLevel.activate();
        composer.activate();
        illustrator.activate();
        charter.activate();
        jacket.activate();
    }

    @Override
    public void render(Graphics2D g2d){
        selectedSong.render(g2d);
        focusedSongLevel.render(g2d);
        composer.render(g2d);
        illustrator.render(g2d);
        charter.render(g2d);
        jacket.render(g2d);
    }
}
package org.kelvinizer.menu.menutext;

import org.kelvinizer.dynamic.DynamicImage;
import org.kelvinizer.dynamic.DynamicString;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Activatable;

import java.awt.*;

public class SongSelectionText implements Activatable {
    public DynamicString selectedSong = new DynamicString();
    public DynamicString focusedSongLevel = new DynamicString();
    public DynamicString composer = new DynamicString();
    public DynamicString illustrator = new DynamicString();
    public DynamicString charter = new DynamicString();
    public DynamicImage jacket = new DynamicImage();

    public BoundedString previousSong = new BoundedString();
    public BoundedString previousSongLevel = new BoundedString();
    public BoundedString nextSong = new BoundedString();
    public BoundedString nextSongLevel = new BoundedString();

    public BoundedString emptyFolder = new BoundedString("Nothing is in here QAQ", 50, 540, 360);
    public BoundedString corruptedFolder = new BoundedString("Collection corrupted QAQ", 50, 540, 360);
    public BoundedString nullJacket = new BoundedString("No jacket preview available", 20);

    private void setSelectedSong(){

    }

    private void setFocusedSongLevel(){

    }

    private void setComposer(){

    }

    private void setIllustrator(){

    }

    private void setCharter(){

    }

    private void setJacket(){

    }

    private void setPreviousSong(){

    }

    private void setPreviousSongLevel(){

    }

    private void setNextSong(){

    }

    private void setNextSongLevel(){

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
        setNextSong();
        setNextSongLevel();
        setNullJacket();
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
}
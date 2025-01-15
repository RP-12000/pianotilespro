package org.kelvinizer.menu.songselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.dynamic.DynamicImage;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.dynamic.DynamicString;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.JacketMenu;
import org.kelvinizer.support.classes.Motion;
import org.kelvinizer.support.classes.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SongSelectionText {
    public final DynamicMotionManager dm = new DynamicMotionManager();
    public final DynamicString nullJacket = new DynamicString("No jacket preview available", 15, 760, 330, 480, 300);
    private final DynamicString selectedSong = new DynamicString("", 0, 250, 350, 300, 100);
    private final DynamicString selectedSongDifficulty = new DynamicString("", 50, 450, 350, 100, 100);
    private final DynamicString selectedLevel = new DynamicString("", 15, 450, 380, 100, 40);
    private final DynamicString selectedSongComposer = new DynamicString("", 0, 250, 380, 300, 40);
    private final DynamicString illustratorVerdict = new DynamicString("Illustration", 15, -150, 350, 300, 100);
    private final DynamicString illustrator = new DynamicString("", 35, -150, 380, 300, 100);
    private final DynamicString charterVerdict = new DynamicString("Chart", 15, -150, 440, 300, 100);
    private final DynamicString charter = new DynamicString("", 35, -150, 470, 300, 100);
    private final DynamicString autoplayVerdict = new DynamicString("Auto", 20, 450, 320);
    private final DynamicImage jacket = new DynamicImage(new CRect(760, 330, 480, 300));

    private final BoundedString previousSong = new BoundedString("", 0, 250, 230, 300, 100);
    private final BoundedString previousSongLevel = new BoundedString("", 50, 450, 230, 100, 100);
    private final BoundedString previousSongComposer = new BoundedString("", 0, 250, 260, 300, 40);
    private final BoundedString nextSong = new BoundedString("", 0, 250, 470, 300, 100);
    private final BoundedString nextSongLevel = new BoundedString("", 50, 450, 470, 100, 100);
    private final BoundedString nextSongComposer = new BoundedString("", 0, 250, 500, 300, 40);

    private final BoundedString bestScore = new BoundedString("", 30, 875, 505, 250, 50);
    private final BoundedString bestAcc = new BoundedString("", 10, 915, 515);
    private final BoundedString bestWorstHit = new BoundedString("", 10, 915, 495);
    private final BoundedString bestGrade = new BoundedString("", 40, 960, 505);
    private final BoundedString newSong = new BoundedString("NEW", 15, 960, 505);

    public final BoundedString emptyFolder = new BoundedString("Nothing is in here QAQ", 50, 540, 360);
    public final BoundedString corruptedFolder = new BoundedString("Collection corrupted QAQ", 50, 540, 360);

    private final float adjacentOpacity = 0.25f;
    private boolean isNewSong = false;

    private void setSelectedSongComposer(){
        selectedSongComposer.getBoundedString().setMaxStringSize(15);
        selectedSongComposer.addVerticalMotion(new Motion(0, 0.8, 380, 260));
        selectedSongComposer.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setIllustratorVerdict(){
        illustratorVerdict.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        illustratorVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setIllustrator(){
        illustrator.getBoundedString().setRelativeY(0.6);
        illustrator.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        illustrator.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setCharterVerdict(){
        charterVerdict.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        charterVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setCharter(){
        charter.getBoundedString().setRelativeY(0.6);
        charter.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        charter.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setJacket(){
        jacket.addHorizontalMotion(new Motion(2.8, 4.0, 760, 1840, 3.5));
        nullJacket.getBoundedString().setStyle(Font.ITALIC);
        nullJacket.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        nullJacket.getBoundedString().getBounds().setOutlineThickness(1.0f);
        nullJacket.addHorizontalMotion(new Motion(2.8, 4.0, 760, 1840, 3.5));
    }

    private void setSelectedSong(){
        selectedSong.getBoundedString().setMaxStringSize(35);
        selectedSong.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        selectedSong.getBoundedString().getBounds().setOutlineThickness(5.0);
        selectedSong.getBoundedString().setRelativeY(0.4);
        selectedSong.addVerticalMotion(new Motion(0, 0.8, 350, 230));
        selectedSong.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));

        selectedLevel.addVerticalMotion(new Motion(0, 0.8, 380, 260));
        selectedLevel.addHorizontalMotion(new Motion(2.8, 4.0, 450, 1530, 3.5));

        selectedSongDifficulty.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        selectedSongDifficulty.getBoundedString().getBounds().setOutlineThickness(5.0);
        selectedSongDifficulty.addVerticalMotion(new Motion(0, 0.8, 350, 230));
        selectedSongDifficulty.addHorizontalMotion(new Motion(2.8, 4.0, 450, 1530, 3.5));
    }

    private void setPreviousSong(){
        previousSong.setMaxStringSize(30);
        previousSong.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        previousSong.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        previousSong.getBounds().setOutlineThickness(5.0);
        previousSong.setRelativeY(0.4);

        previousSongLevel.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        previousSongLevel.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        previousSongLevel.getBounds().setOutlineThickness(5.0);

        previousSongComposer.setMaxStringSize(15);
        previousSongComposer.setStringColor(new Color(1, 1, 1, adjacentOpacity));
    }

    private void setNextSong(){
        nextSong.setMaxStringSize(30);
        nextSong.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        nextSong.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        nextSong.getBounds().setOutlineThickness(5.0);
        nextSong.setRelativeY(0.4);

        nextSongLevel.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        nextSongLevel.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        nextSongLevel.getBounds().setOutlineThickness(5.0);

        nextSongComposer.setMaxStringSize(15);
        nextSongComposer.setStringColor(new Color(1, 1, 1, adjacentOpacity));
    }

    private void setDm(){
        dm.addDynamicObject(selectedSong);
        dm.addDynamicObject(selectedSongDifficulty);
        dm.addDynamicObject(selectedLevel);
        dm.addDynamicObject(selectedSongComposer);
        dm.addDynamicObject(charterVerdict);
        dm.addDynamicObject(charter);
        dm.addDynamicObject(illustratorVerdict);
        dm.addDynamicObject(illustrator);
    }

    public SongSelectionText(){
        setSelectedSong();
        setCharterVerdict();
        setCharter();
        setSelectedSongComposer();
        setJacket();
        setIllustratorVerdict();
        setIllustrator();
        setPreviousSong();
        setNextSong();
        autoplayVerdict.getBoundedString().setStringColor(Color.GREEN);
        autoplayVerdict.addVerticalMotion(new Motion(0, 0.8, 320, 200));
        autoplayVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 450, 1530, 3.5));
        bestScore.setRelativeX(0.3);
        bestScore.getBounds().setOutlineThickness(1.0);
        newSong.setStyle(Font.ITALIC);
        setDm();
    }

    private String levelToString(Pair<String, Double> level){
        if(level==null){
            return "";
        }
        return ((Integer)(int)(double)(level.second)).toString();
    }

    public void updateSelectionStrings(JacketMenu jm, ArrayList<Song> sd){
        selectedSong.getBoundedString().setString(jm.getSelectionString().replace('_', ' '));
        selectedSongComposer.getBoundedString().setString(sd.get(jm.getMenuIndex()).getComposer());
        selectedLevel.getBoundedString().setString(Selection.level);
        selectedSongDifficulty.getBoundedString().setString(levelToString(sd.get(jm.getMenuIndex()).getCharterData()));
        bestScore.setString(sd.get(jm.getMenuIndex()).historyBest.get(Selection.level).getScoreString());
        bestAcc.setString(sd.get(jm.getMenuIndex()).historyBest.get(Selection.level).getAccuracyString());
        bestWorstHit.setString(sd.get(jm.getMenuIndex()).historyBest.get(Selection.level).getWorstHitString());
        sd.get(jm.getMenuIndex()).historyBest.get(Selection.level).setGradeString(bestGrade);
        bestScore.getBounds().setOutlineColor(bestGrade.getStringColor());
        isNewSong = sd.get(jm.getMenuIndex()).historyBest.get(Selection.level).newChart;
        if(!jm.atBeginning()){
            previousSong.setString(jm.getSelectionString(jm.getMenuIndex()-1));
            previousSongComposer.setString(sd.get(jm.getMenuIndex()-1).getComposer());
            previousSongLevel.setString(levelToString(sd.get(jm.getMenuIndex()-1).getCharterData()));
        }
        if(!jm.atEnd()){
            nextSong.setString(jm.getSelectionString(jm.getMenuIndex()+1));
            nextSongComposer.setString(sd.get(jm.getMenuIndex()+1).getComposer());
            nextSongLevel.setString(levelToString(sd.get(jm.getMenuIndex()+1).getCharterData()));
        }
    }

    public void setEndStrings(Song s, BufferedImage bf){
        selectedSong.getBoundedString().setString(s.getSongName().replace('_', ' '));
        selectedSongComposer.getBoundedString().setString(s.getComposer());
        illustrator.getBoundedString().setString(s.getIllustration());
        selectedLevel.getBoundedString().setString(Selection.level);
        charter.getBoundedString().setString(s.getCharterData().first);
        selectedSongDifficulty.getBoundedString().setString(levelToString(s.getCharterData()));
        if(Control.isAutoplay){
            dm.addDynamicObject(autoplayVerdict);
        }
        if(bf!=null){
            dm.addDynamicObject(jacket);
            jacket.setImage(bf);
        }
        else{
            dm.addDynamicObject(nullJacket);
        }
    }

    public void renderCurrent(Graphics2D g2d){
        if(Control.isAutoplay){
            autoplayVerdict.render(g2d);
        }
        selectedSong.render(g2d);
        selectedSongDifficulty.render(g2d);
        selectedSongComposer.render(g2d);
        selectedLevel.render(g2d);
        bestScore.render(g2d);
        bestAcc.render(g2d);
        bestWorstHit.render(g2d);
        if(isNewSong){
            newSong.render(g2d);
        }
        else{
            bestGrade.render(g2d);
        }
    }

    public void renderPrevious(Graphics2D g2d){
        previousSong.render(g2d);
        previousSongLevel.render(g2d);
        previousSongComposer.render(g2d);
    }

    public void renderNext(Graphics2D g2d){
        nextSong.render(g2d);
        nextSongLevel.render(g2d);
        nextSongComposer.render(g2d);
    }
}
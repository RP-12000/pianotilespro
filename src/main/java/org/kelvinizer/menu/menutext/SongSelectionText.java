package org.kelvinizer.menu.menutext;

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
    private final DynamicString selectedSong = new DynamicString();
    private final DynamicString selectedSongDifficulty = new DynamicString();
    private final DynamicString selectedLevel = new DynamicString();
    private final DynamicString selectedSongComposer = new DynamicString();
    private final DynamicString illustratorVerdict = new DynamicString();
    private final DynamicString illustrator = new DynamicString();
    private final DynamicString charterVerdict = new DynamicString();
    private final DynamicString charter = new DynamicString();
    public final DynamicString nullJacket = new DynamicString("No jacket preview available", 15, 760, 330, 480, 300);
    private final DynamicImage jacket = new DynamicImage();

    private final BoundedString previousSong = new BoundedString();
    private final BoundedString previousSongLevel = new BoundedString();
    private final BoundedString previousSongComposer = new BoundedString();
    private final BoundedString nextSong = new BoundedString();
    private final BoundedString nextSongLevel = new BoundedString();
    private final BoundedString nextSongComposer = new BoundedString();

    private final BoundedString bestScore = new BoundedString("", 30, 875, 505, 250, 50);
    private final BoundedString bestAcc = new BoundedString("", 10, 915, 515);
    private final BoundedString bestGrade = new BoundedString("", 40, 960, 505);
    private final BoundedString newSong = new BoundedString("NEW", 15, 960, 505);

    public final BoundedString emptyFolder = new BoundedString("Nothing is in here QAQ", 50, 540, 360);
    public final BoundedString corruptedFolder = new BoundedString("Collection corrupted QAQ", 50, 540, 360);

    private final float adjacentOpacity = 0.25f;
    private boolean isNewSong = false;

    private void setSelectedSong(){
        selectedSong.getBoundedString().setMaxStringSize(35);
        selectedSong.getBoundedString().setBounds(new CRect(250, 350, 300, 100));
        selectedSong.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        selectedSong.getBoundedString().getBounds().setOutlineThickness(5.0);
        selectedSong.getBoundedString().setRelativeY(0.4);
        selectedSong.addVerticalMotion(new Motion(0, 0.8, 350, 230));
        selectedSong.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setSelectedLevel(){
        selectedLevel.getBoundedString().setStringSize(15);
        selectedLevel.getBoundedString().setBounds(new CRect(450, 380, 100, 40));
        selectedLevel.addVerticalMotion(new Motion(0, 0.8, 380, 260));
        selectedLevel.addHorizontalMotion(new Motion(2.8, 4.0, 450, 1530, 3.5));
    }

    private void setSelectedSongDifficulty(){
        selectedSongDifficulty.getBoundedString().setStringSize(50);
        selectedSongDifficulty.getBoundedString().setBounds(new CRect(450, 350, 100, 100));
        selectedSongDifficulty.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        selectedSongDifficulty.getBoundedString().getBounds().setOutlineThickness(5.0);
        selectedSongDifficulty.addVerticalMotion(new Motion(0, 0.8, 350, 230));
        selectedSongDifficulty.addHorizontalMotion(new Motion(2.8, 4.0, 450, 1530, 3.5));
    }

    private void setSelectedSongComposer(){
        selectedSongComposer.getBoundedString().setMaxStringSize(15);
        selectedSongComposer.getBoundedString().setBounds(new CRect(250, 380, 300, 40));
        selectedSongComposer.addVerticalMotion(new Motion(0, 0.8, 380, 260));
        selectedSongComposer.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setIllustratorVerdict(){
        illustratorVerdict.getBoundedString().setString("Illustration");
        illustratorVerdict.getBoundedString().setStringSize(15);
        illustratorVerdict.getBoundedString().setBounds(new CRect(-150, 350, 300, 100));
        illustratorVerdict.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        illustratorVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setIllustrator(){
        illustrator.getBoundedString().setStringSize(35);
        illustrator.getBoundedString().setBounds(new CRect(-150, 380, 300, 100));
        illustrator.getBoundedString().setRelativeY(0.6);
        illustrator.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        illustrator.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setCharterVerdict(){
        charterVerdict.getBoundedString().setString("Chart");
        charterVerdict.getBoundedString().setStringSize(15);
        charterVerdict.getBoundedString().setBounds(new CRect(-150, 440, 300, 100));
        charterVerdict.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        charterVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setCharter(){
        charter.getBoundedString().setStringSize(35);
        charter.getBoundedString().setBounds(new CRect(-150, 470, 300, 100));
        charter.getBoundedString().setRelativeY(0.6);
        charter.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        charter.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    private void setJacket(){
        jacket.setBounds(new CRect(760, 330, 480, 300));
        jacket.addHorizontalMotion(new Motion(2.8, 4.0, 760, 1840, 3.5));
    }

    private void setPreviousSong(){
        previousSong.setMaxStringSize(30);
        previousSong.setBounds(new CRect(250, 230, 300, 100));
        previousSong.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        previousSong.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        previousSong.getBounds().setOutlineThickness(5.0);
        previousSong.setRelativeY(0.4);
    }

    private void setPreviousSongLevel(){
        previousSongLevel.setStringSize(50);
        previousSongLevel.setBounds(new CRect(450, 230, 100, 100));
        previousSongLevel.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        previousSongLevel.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        previousSongLevel.getBounds().setOutlineThickness(5.0);
    }

    private void setPreviousSongComposer(){
        previousSongComposer.setMaxStringSize(15);
        previousSongComposer.setBounds(new CRect(250, 260, 300, 40));
        previousSongComposer.setStringColor(new Color(1, 1, 1, adjacentOpacity));
    }

    private void setNextSong(){
        nextSong.setMaxStringSize(30);
        nextSong.setBounds(new CRect(250, 470, 300, 100));
        nextSong.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        nextSong.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        nextSong.getBounds().setOutlineThickness(5.0);
        nextSong.setRelativeY(0.4);
    }

    private void setNextSongLevel(){
        nextSongLevel.setStringSize(50);
        nextSongLevel.setBounds(new CRect(450, 470, 100, 100));
        nextSongLevel.setStringColor(new Color(1, 1, 1, adjacentOpacity));
        nextSongLevel.getBounds().setOutlineColor(new Color(1, 1, 1, adjacentOpacity));
        nextSongLevel.getBounds().setOutlineThickness(5.0);
    }

    private void setNextSongComposer(){
        nextSongComposer.setMaxStringSize(15);
        nextSongComposer.setBounds(new CRect(250, 500, 300, 40));
        nextSongComposer.setStringColor(new Color(1, 1, 1, adjacentOpacity));
    }

    private void setNullJacket(){
        nullJacket.getBoundedString().setStyle(Font.ITALIC);
        nullJacket.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        nullJacket.getBoundedString().getBounds().setOutlineThickness(1.0f);
        nullJacket.addHorizontalMotion(new Motion(2.8, 4.0, 760, 1840, 3.5));
    }

    private void setBestScore(){
        bestScore.setRelativeX(0.3);
        bestScore.getBounds().setOutlineThickness(1.0);
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
        setSelectedLevel();
        setCharterVerdict();
        setCharter();
        setSelectedSongComposer();
        setJacket();
        setIllustratorVerdict();
        setIllustrator();
        setSelectedSongDifficulty();
        setPreviousSong();
        setPreviousSongLevel();
        setPreviousSongComposer();
        setNextSong();
        setNextSongLevel();
        setNextSongComposer();
        setNullJacket();
        setBestScore();
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
        sd.get(jm.getMenuIndex()).historyBest.get(Selection.level).setGradeString(bestGrade);
        bestScore.getBounds().setOutlineColor(bestGrade.getStringColor());
        isNewSong = sd.get(jm.getMenuIndex()).isNewSong();
        if(!jm.atBeginning()){
            previousSong.setString(jm.getSelectionString(jm.getMenuIndex()-1));
            previousSongComposer.setString(sd.get(jm.getMenuIndex()-1).getComposer());
            previousSongLevel.setString(levelToString(sd.get(jm.getMenuIndex()).getCharterData()));
        }
        if(!jm.atEnd()){
            nextSong.setString(jm.getSelectionString(jm.getMenuIndex()+1));
            nextSongComposer.setString(sd.get(jm.getMenuIndex()+1).getComposer());
            nextSongLevel.setString(levelToString(sd.get(jm.getMenuIndex()).getCharterData()));
        }
    }

    public void setEndStrings(Song s, BufferedImage bf){
        selectedSong.getBoundedString().setString(s.getSongName().replace('_', ' '));
        selectedSongComposer.getBoundedString().setString(s.getComposer());
        illustrator.getBoundedString().setString(s.getIllustration());
        selectedLevel.getBoundedString().setString(Selection.level);
        charter.getBoundedString().setString(s.getCharterData().first);
        selectedSongDifficulty.getBoundedString().setString(levelToString(s.getCharterData()));
        if(bf!=null){
            dm.addDynamicObject(jacket);
            jacket.setImage(bf);
        }
        else{
            dm.addDynamicObject(nullJacket);
        }
    }

    public void renderCurrent(Graphics2D g2d){
        selectedSong.render(g2d);
        selectedSongDifficulty.render(g2d);
        selectedSongComposer.render(g2d);
        selectedLevel.render(g2d);
        bestScore.render(g2d);
        bestAcc.render(g2d);
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
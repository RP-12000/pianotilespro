package org.kelvinizer.menu.songselection;

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

import static org.kelvinizer.constants.Control.userIndex;
import static org.kelvinizer.constants.Control.users;
import static org.kelvinizer.constants.Selection.getScoreData;

/**
 * This class manages the dynamic elements and text rendering for the song selection menu.
 * It includes the display and animation of song information, scores, and other related data.
 * The class also manages the display of the current, next, and previous songs in the selection menu.
 * @author Boyan Hu
 */
public class SongSelectionText {
    /**
     * {@code DynamicMotionManager} responsible for managing dynamic motion-related tasks.
     */
    public final DynamicMotionManager dm = new DynamicMotionManager();

    /**
     * {@code DynamicString} displaying a message when no jacket preview is available. Positioned at (15, 760), with width 480 and height 300.
     */
    public final DynamicString nullJacket = new DynamicString("No jacket preview available", 15, 760, 330, 480, 300);

    /**
     * {@code DynamicString} representing the currently selected song name. Positioned at (0, 250), with width 300 and height 100.
     */
    private final DynamicString selectedSong = new DynamicString("", 0, 250, 350, 300, 100);

    /**
     * {@code DynamicString} representing the difficulty of the currently selected song. Positioned at (50, 450), with width 100 and height 100.
     */
    private final DynamicString selectedSongDifficulty = new DynamicString("", 50, 450, 350, 100, 100);

    /**
     * {@code DynamicString} representing the selected level. Positioned at (15, 450), with width 100 and height 40.
     */
    private final DynamicString selectedLevel = new DynamicString("", 15, 450, 380, 100, 40);

    /**
     * {@code DynamicString} representing the composer of the currently selected song. Positioned at (0, 250), with width 300 and height 40.
     */
    private final DynamicString selectedSongComposer = new DynamicString("", 0, 250, 380, 300, 40);

    /**
     * {@code DynamicString} displaying the verdict related to the illustrator of the selected song. Positioned at (15, -150), with width 300 and height 100.
     */
    private final DynamicString illustratorVerdict = new DynamicString("Illustration", 15, -150, 350, 300, 100);

    /**
     * {@code DynamicString} representing the illustrator of the selected song. Positioned at (35, -150), with width 300 and height 100.
     */
    private final DynamicString illustrator = new DynamicString("", 35, -150, 380, 300, 100);

    /**
     * {@code DynamicString} displaying the verdict related to the chart of the selected song. Positioned at (15, -150), with width 300 and height 100.
     */
    private final DynamicString charterVerdict = new DynamicString("Chart", 15, -150, 440, 300, 100);

    /**
     * {@code DynamicString} representing the chart author of the selected song. Positioned at (35, -150), with width 300 and height 100.
     */
    private final DynamicString charter = new DynamicString("", 35, -150, 470, 300, 100);

    /**
     * {@code DynamicString} displaying the verdict for autoplay status. Positioned at (20, 450).
     */
    private final DynamicString autoplayVerdict = new DynamicString("Auto", 20, 450, 320);

    /**
     * {@code DynamicImage} representing the jacket image for the selected song. Positioned at (760, 330), with width 480 and height 300.
     */
    private final DynamicImage jacket = new DynamicImage(new CRect(760, 330, 480, 300));

    /**
     * {@code BoundedString} representing the previous song's name. Positioned at (0, 250), with width 300 and height 100.
     */
    private final BoundedString previousSong = new BoundedString("", 0, 250, 230, 300, 100);

    /**
     * {@code BoundedString} representing the level of the previous song. Positioned at (50, 450), with width 100 and height 100.
     */
    private final BoundedString previousSongLevel = new BoundedString("", 50, 450, 230, 100, 100);

    /**
     * {@code BoundedString} representing the composer of the previous song. Positioned at (0, 250), with width 300 and height 40.
     */
    private final BoundedString previousSongComposer = new BoundedString("", 0, 250, 260, 300, 40);

    /**
     * {@code BoundedString} representing the next song's name. Positioned at (0, 250), with width 300 and height 100.
     */
    private final BoundedString nextSong = new BoundedString("", 0, 250, 470, 300, 100);

    /**
     * {@code BoundedString} representing the level of the next song. Positioned at (50, 450), with width 100 and height 100.
     */
    private final BoundedString nextSongLevel = new BoundedString("", 50, 450, 470, 100, 100);

    /**
     * {@code BoundedString} representing the composer of the next song. Positioned at (0, 250), with width 300 and height 40.
     */
    private final BoundedString nextSongComposer = new BoundedString("", 0, 250, 500, 300, 40);

    /**
     * {@code BoundedString} displaying the best score for the song. Positioned at (30, 875), with width 250 and height 50.
     */
    private final BoundedString bestScore = new BoundedString("", 30, 875, 505, 250, 50);

    /**
     * {@code BoundedString} displaying the best accuracy for the song. Positioned at (10, 915).
     */
    private final BoundedString bestAcc = new BoundedString("", 10, 915, 515);

    /**
     * {@code BoundedString} displaying the worst hit score for the song. Positioned at (10, 915).
     */
    private final BoundedString bestWorstHit = new BoundedString("", 10, 915, 495);

    /**
     * {@code BoundedString} displaying the best grade for the song. Positioned at (40, 960).
     */
    private final BoundedString bestGrade = new BoundedString("", 40, 960, 505);

    /**
     * {@code BoundedString} displaying the "NEW" label for new songs. Positioned at (15, 960).
     */
    private final BoundedString newSong = new BoundedString("NEW", 15, 960, 505);

    /**
     * {@code float} representing the opacity for adjacent elements.
     */
    private final float adjacentOpacity = 0.25f;

    /**
     * {@code boolean} flag indicating whether the song is new.
     */
    private boolean isNewSong = false;

    /**
     * Initializes the selected song composer settings, including adding vertical and horizontal motion.
     */
    private void setSelectedSongComposer(){
        selectedSongComposer.getBoundedString().setMaxStringSize(15);
        selectedSongComposer.addVerticalMotion(new Motion(0, 0.8, 380, 260));
        selectedSongComposer.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    /**
     * Initializes the illustrator verdict settings with motion animations.
     */
    private void setIllustratorVerdict(){
        illustratorVerdict.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        illustratorVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    /**
     * Initializes the illustrator settings with motion animations.
     */
    private void setIllustrator(){
        illustrator.getBoundedString().setRelativeY(0.6);
        illustrator.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        illustrator.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    /**
     * Initializes the chart verdict settings with motion animations.
     */
    private void setCharterVerdict(){
        charterVerdict.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        charterVerdict.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    /**
     * Initializes the chart settings with motion animations.
     */
    private void setCharter(){
        charter.getBoundedString().setRelativeY(0.6);
        charter.addHorizontalMotion(new Motion(0, 0.8, -150, 250, 0.2));
        charter.addHorizontalMotion(new Motion(2.8, 4.0, 250, 1330, 3.5));
    }

    /**
     * Initializes the jacket preview settings and adds motion for the jacket image.
     */
    private void setJacket(){
        jacket.addHorizontalMotion(new Motion(2.8, 4.0, 760, 1840, 3.5));
        nullJacket.getBoundedString().setStyle(Font.ITALIC);
        nullJacket.getBoundedString().getBounds().setOutlineColor(Color.WHITE);
        nullJacket.getBoundedString().getBounds().setOutlineThickness(1.0f);
        nullJacket.addHorizontalMotion(new Motion(2.8, 4.0, 760, 1840, 3.5));
    }

    /**
     * Initializes the settings for the selected song, including adding motion animations.
     */
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

    /**
     * Initializes the settings for the previous song display.
     */
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

    /**
     * Sets the display properties for the "Next Song" section, including text size, color, and outline settings.
     * It applies these settings to the song name, level, and composer for the next song.
     */
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

    /**
     * Initializes and adds dynamic objects to the motion manager.
     * These dynamic objects represent the text and image elements for the song selection menu.
     */
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

    /**
     * Constructor for the SongSelectionText class.
     * It initializes the various display elements (text and images) and adds them to the dynamic motion manager for animation.
     */
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
        bestScore.getBounds().setOutlineThickness(3.0);
        newSong.setStyle(Font.ITALIC);
        setDm();
    }

    /**
     * Converts the given level data into a string representation.
     *
     * @param level The level data as a pair of a string and a double.
     * @return A string representation of the level data or an empty string if the level is null.
     */
    private String levelToString(Pair<String, Double> level){
        if(level == null){
            return "";
        }
        return ((Integer)(int)(double)(level.second)).toString();
    }

    /**
     * Updates the song selection screen with the current song's data, including name, composer, level,
     * difficulty, and best scores. It also updates the previous and next songs based on the current selection.
     *
     * @param jm The JacketMenu object containing the current menu state.
     * @param sd The list of songs to display in the selection.
     */
    public void updateSelectionStrings(JacketMenu jm, ArrayList<Song> sd){
        selectedSong.getBoundedString().setString(jm.getSelectionString().replace('_', ' '));
        selectedSongComposer.getBoundedString().setString(sd.get(jm.getMenuIndex()).getComposer());
        selectedLevel.getBoundedString().setString(Selection.level);
        selectedSongDifficulty.getBoundedString().setString(levelToString(sd.get(jm.getMenuIndex()).getCharterData()));

        getScoreData().setGradeString(bestGrade);

        bestScore.setString(getScoreData().getScoreString());
        bestScore.getBounds().setOutlineColor(bestGrade.getStringColor());
        bestScore.setStringColor(bestGrade.getStringColor());
        bestAcc.setString(getScoreData().getAccuracyString());
        bestAcc.setStringColor(bestGrade.getStringColor());
        bestWorstHit.setString(getScoreData().getWorstHitString());
        bestWorstHit.setStringColor(bestGrade.getStringColor());

        isNewSong = getScoreData().newChart;

        if(jm.notAtBeginning()){
            previousSong.setString(jm.getSelectionString(jm.getMenuIndex()-1));
            previousSongComposer.setString(sd.get(jm.getMenuIndex()-1).getComposer());
            previousSongLevel.setString(levelToString(sd.get(jm.getMenuIndex()-1).getCharterData()));
        }
        if(jm.notAtEnd()){
            nextSong.setString(jm.getSelectionString(jm.getMenuIndex()+1));
            nextSongComposer.setString(sd.get(jm.getMenuIndex()+1).getComposer());
            nextSongLevel.setString(levelToString(sd.get(jm.getMenuIndex()+1).getCharterData()));
        }
    }

    /**
     * Sets the final strings for the song selection screen, including the song name, composer, illustrator, level, and charter.
     * It also adds the appropriate jacket image or a placeholder if no image is provided.
     *
     * @param s The Song object containing the song data.
     * @param bf The BufferedImage for the jacket image, or null if no image is available.
     */
    public void setEndStrings(Song s, BufferedImage bf){
        selectedSong.getBoundedString().setString(s.getSongName().replace('_', ' '));
        selectedSongComposer.getBoundedString().setString(s.getComposer());
        illustrator.getBoundedString().setString(s.getIllustration());
        selectedLevel.getBoundedString().setString(Selection.level);
        charter.getBoundedString().setString(s.getCharterData().first);
        selectedSongDifficulty.getBoundedString().setString(levelToString(s.getCharterData()));
        if(users.get(userIndex).isAutoplay){
            dm.addDynamicObject(autoplayVerdict);
        }
        if(bf != null){
            dm.addDynamicObject(jacket);
            jacket.setImage(bf);
        }
        else{
            dm.addDynamicObject(nullJacket);
        }
    }

    /**
     * Renders the current song selection display, including the song name, composer, level, best score, and additional information.
     * It also renders the "New Song" label if applicable.
     *
     * @param g2d The Graphics2D object used for rendering.
     */
    public void renderCurrent(Graphics2D g2d){
        if(users.get(userIndex).isAutoplay){
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

    /**
     * Renders the previous song's information, including the song name, level, and composer.
     *
     * @param g2d The Graphics2D object used for rendering.
     */
    public void renderPrevious(Graphics2D g2d){
        previousSong.render(g2d);
        previousSongLevel.render(g2d);
        previousSongComposer.render(g2d);
    }

    /**
     * Renders the next song's information, including the song name, level, and composer.
     *
     * @param g2d The Graphics2D object used for rendering.
     */
    public void renderNext(Graphics2D g2d){
        nextSong.render(g2d);
        nextSongLevel.render(g2d);
        nextSongComposer.render(g2d);
    }
}
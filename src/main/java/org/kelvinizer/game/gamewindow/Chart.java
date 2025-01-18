package org.kelvinizer.game.gamewindow;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.*;
import org.kelvinizer.constants.Control;
import org.kelvinizer.game.gamebuttons.ChartButtons;
import org.kelvinizer.game.gametext.ChartText;
import org.kelvinizer.note.HoldNote;
import org.kelvinizer.note.Note;
import org.kelvinizer.note.TapNote;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.Motion;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.*;

/**
 * Represents a chart in the game, which contains the song's notes and gameplay elements.
 * The chart is responsible for handling the timing and display of notes, managing keyboard input for note hits,
 * controlling the music playback, and rendering the game environment.
 * It provides functionality for restarting, pausing, and interacting with the gameplay via keybindings.
 */
public class Chart extends AnimatablePanel {
    /**
     * The total number of notes in the chart. This value is used to track the
     * number of notes that need to be processed or displayed during gameplay.
     */
    public static double noteCount;

    /**
     * The current time in the song or chart, used to track and sync the gameplay
     * with the audio and notes. It is updated as the game progresses and is used
     * for positioning the notes in sync with the music.
     */
    public static double CURRENT_TIME = 0;

    /**
     * An instance of the ChartText class, responsible for handling the text display
     * in the chart, such as the song title, artist, and other text elements.
     */
    private final ChartText ct = new ChartText();

    /**
     * An instance of the ChartButtons class, responsible for managing the buttons
     * and their interactions in the chart UI, such as restart or exit buttons.
     */
    private final ChartButtons cb = new ChartButtons();

    /**
     * An array of Lane objects representing the 16 different lanes in the game.
     * Each lane corresponds to a specific area where notes are displayed and interactable.
     */
    private final Lane[] lanes = new Lane[16];

    /**
     * A static timer that tracks the elapsed time in the chart. It is used for timing-related functions.
     */
    private double STATIC_TIMER;

    /**
     * The AudioInputStream used to read audio data for the song associated with the chart.
     */
    private final AudioInputStream inputStream;

    /**
     * The Clip object used to play the audio for the song. It interfaces with the
     * AudioInputStream to produce sound during the gameplay.
     */
    private final Clip music;

    /**
     * A CRect object representing the progress bar in the chart, which visually indicates
     * the current progress in the song. It is positioned at the top of the screen.
     */
    private final CRect progressBar = new CRect(0, 10);

    /**
     * Flag indicating whether the user should go back to the previous screen.
     */
    private boolean goBack = false;

    /**
     * Flag indicating whether the game is currently paused.
     */
    private boolean isPaused = false;

    /**
     * The time point of the game, typically used to track the position in the music or game timeline.
     */
    private double timePoint;

    /**
     * Array of key codes representing the keyboard keys assigned to activate each lane.
     * Corresponds to specific keys (A, S, D, F, J, K, L, etc.) for user input.
     */
    int[] keyboardActivation = {
            KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON,
            KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P,
    };

    /**
     * Array of booleans representing the state of the keys (pressed or not) corresponding to each lane.
     * Each element tracks whether the key for a specific lane is currently pressed (true) or not (false).
     */
    boolean[] isDown = {
            false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false,
    };

    /**
     * Array of integers representing the activation signals for each lane.
     * A signal value of 1 indicates that the key for the lane is pressed, and -1 indicates it is released.
     */
    int[] signal = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    /**
     * Activates the note in the specified lane by setting the signal for the lane to 1.
     * The corresponding key is marked as pressed in the `isDown` array.
     *
     * @param l The lane index for the note being pressed.
     */
    private void press(int l) {
        if (!isDown[l]) {
            signal[l] = 1;
            isDown[l] = true;
        }
    }

    /**
     * Deactivates the note in the specified lane by setting the signal for the lane to -1.
     * The corresponding key is marked as released in the `isDown` array.
     *
     * @param l The lane index for the note being released.
     */
    private void release(int l) {
        signal[l] = -1;
        isDown[l] = false;
    }

    /**
     * Restarts the chart by resetting all lanes, the current time, and the music playback.
     * The game is unpaused and the music starts from the beginning.
     */
    private void restart() {
        for (Lane l : lanes) {
            l.restart();
        }
        CURRENT_TIME = STATIC_TIMER;
        isPaused = false;
        if (inputStream != null) {
            music.setMicrosecondPosition(0);
        }
    }

    /**
     * Constructs a new Chart based on a given song and difficulty level.
     * It loads the song's audio file, reads the chart file for note information,
     * and prepares the chart's notes and lanes.
     *
     * @param song  The song for which the chart is created.
     * @param level The difficulty level for the chart.
     * @throws IOException                   If there is an issue reading the audio or chart files.
     * @throws RuntimeException              If there is an issue with the notes or chart structure.
     * @throws LineUnavailableException      If the audio line is unavailable.
     * @throws UnsupportedAudioFileException If the audio file format is unsupported.
     */
    public Chart(Song song, String level) throws IOException, RuntimeException, LineUnavailableException, UnsupportedAudioFileException {
        super(2000);
        inputStream = AudioSystem.getAudioInputStream(getResourceInput(song.getAbsoluteDir()+"/audio.wav"));
        music = AudioSystem.getClip();
        music.open(inputStream);
        BufferedReader chart = new BufferedReader(new InputStreamReader(getResourceInput(song.getAbsoluteDir()+"/"+level+".txt")));
        ArrayList<Note> tempNotes = new ArrayList<>();
        noteCount = Double.parseDouble(chart.readLine());
        if(noteCount==0){
            throw new RuntimeException("Zero-note chart detected");
        }
        for(int i=0; i<(int)noteCount; i++){
            String string = chart.readLine();
             if(string.isEmpty() || string.startsWith("#")){
                i--;
                continue;
            }
            String[] s = string.split(" ");
            int numMotions = Integer.parseInt(s[1]);
            Note n;
            if(s[0].equals("0")){
                n = TapNote.parseTapNote(chart.readLine(), song.OFFSET);
            }
            else if(s[1].equals("1")){
                n = HoldNote.parseHoldNote(chart.readLine(), song.OFFSET);
            }
            else{
                throw new RuntimeException("Invalid Note Type detected at Note No."+i);
            }
            for(int j=0; j<numMotions; j++){
                n.addMotion(new Motion(chart.readLine()));
            }
            if(n.isValidNote()){
                tempNotes.add(n);
            }
            else{
                throw new RuntimeException("Invalid Note detected at Note No."+i);
            }
        }
        tempNotes.sort(Note::compareTo);
        for(int i=0; i<tempNotes.size()-1; i++){
            if(tempNotes.get(i).equals(tempNotes.get(i+1))){
                tempNotes.get(i).sync();
                tempNotes.get(i+1).sync();
            }
        }
        tempNotes.sort((Note a, Note b) -> (int)((a.getStartTime() - b.getStartTime())));
        STATIC_TIMER = Math.min(0.0, tempNotes.getFirst().getStartTime());
        if(!users.isEmpty()){
            STATIC_TIMER = Math.min(STATIC_TIMER, users.get(userIndex).MUSIC_DIFFERENCE);
        }
        for(int i=0; i<16; i++){
            lanes[i] = new Lane();
        }
        for (Note tempNote : tempNotes) {
            lanes[tempNote.getLaneNum()].addNote(tempNote);
        }
        for(int i=0; i<16; i++){
            lanes[i].sort();
        }
        for(int i=0; i<16; i++){
            int finalI = i;
            addKeyBinding(keyboardActivation[i], false, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    press(finalI);
                }
            });
            addKeyBinding(keyboardActivation[i], new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    release(finalI);
                }
            });
        }
        addKeyBinding(KeyEvent.VK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused=!isPaused;
            }
        });
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isPaused){
                    goBack = true;
                    exit();
                }
            }
        });
        addKeyBinding(KeyEvent.VK_R, false, KeyEvent.CTRL_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isPaused){
                    restart();
                }
            }
        });

        progressBar.setOrigin(0, 0);
        progressBar.setFillColor(Color.CYAN);
        restart();
    }

    /**
     * Constructs a new Chart object using the song data and the selected level.
     * This constructor is used to initialize the chart with the appropriate
     * song data and level, and sets up the necessary audio and chart data.
     *
     * @throws IOException if an I/O error occurs while reading the chart or audio file
     * @throws LineUnavailableException if the audio line is unavailable for playback
     * @throws UnsupportedAudioFileException if the audio file format is not supported
     */
    public Chart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this(getSongData(), Selection.level);
    }

    /**
     * Checks if the chart is valid for the given song and level.
     * This method attempts to create a new Chart object for the specified song and level,
     * and catches any exceptions that may occur during the creation process to determine
     * if the chart is valid.
     *
     * @param s the song for which the chart is being validated
     * @param level the level of the chart to validate
     * @return true if the chart is invalid, false otherwise
     */
    public static boolean invalidChart(Song s, String level){
        try{
            new Chart(s, level);
            return false;
        } catch (IOException | RuntimeException | LineUnavailableException | UnsupportedAudioFileException e){
            return true;
        }
    }

    /**
     * Scales the ChartButtons component based on the given dimension.
     * This method adjusts the size or layout of the chart buttons when the panel is resized.
     *
     * @param d the new dimension to scale the buttons to
     */
    @Override
    public void scale(Dimension d){
        cb.scale(d);
    }

    /**
     * Handles the mouse movement event by updating the focus of the chart buttons.
     * This method checks where the mouse is moved and updates the focused button accordingly.
     *
     * @param e the mouse event containing information about the mouse movement
     */
    @Override
    public void mouseMoved(MouseEvent e){
        cb.setFocused(e);
    }

    /**
     * Handles mouse click events, taking different actions depending on the button clicked
     * and whether the game is paused or not.
     * <p>
     * If the game is paused, the method checks for interactions with the restart, play, or back buttons.
     * If the game is not paused, the method checks for interaction with the pause button.
     *
     * @param e the mouse event containing information about the click
     */
    @Override
    public void mouseClicked(MouseEvent e){
        if(isPaused){
            if(cb.restart.isFocused()){
                restart();
            }
            else if(cb.play.isFocused()){
                isPaused=false;
            }
            else if(cb.back.isFocused()){
                goBack=true;
                exit();
            }
        }
        else if(cb.pause.isFocused()){
            isPaused=true;
        }
    }

    /**
     * Sets the color of the judgement line based on the worst hit value and the user's settings.
     * This method adjusts the color of the judgement line on the chart based on certain thresholds.
     * If the "FCAPHint" setting is enabled for the user, it uses specific colors for different judgement levels.
     *
     * @param g2d the graphics context used to draw the judgement line
     */
    private void setJudgementLineColor(Graphics2D g2d){
        if(Lane.worstHit<=JudgementLimits.PERFECT && users.get(userIndex).FCAPHintEnabled){
            g2d.setColor(Color.GREEN);
        }
        else if(Lane.worstHit<=JudgementLimits.GOOD && users.get(userIndex).FCAPHintEnabled){
            g2d.setColor(Color.BLUE);
        }
        else{
            g2d.setColor(Color.WHITE);
        }
        g2d.setStroke(new BasicStroke(3.0f));
    }

    private void renderVerticalJudgement(Graphics2D g2d, int distFromMiddle){
        setJudgementLineColor(g2d);
        g2d.drawLine(
                (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4]-distFromMiddle, (int) ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[0],
                (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4]+distFromMiddle, (int) ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[0]
        );
        g2d.drawLine(
                (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4]-distFromMiddle, (int) ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[1],
                (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4]+distFromMiddle, (int) ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[1]
        );
        for(int i=1; i<=distFromMiddle/ReferenceWindow.VERTICAL_JUDGEMENT_SPACING; i++){
            g2d.drawLine(
                    (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4-i], -1,
                    (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4-i], (int) ReferenceWindow.REF_WIN_H+1
            );
            g2d.drawLine(
                    (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4+i], -1,
                    (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4+i], (int) ReferenceWindow.REF_WIN_H+1
            );
        }
        if(users.get(userIndex).handHintEnabled){
            g2d.setColor(Color.RED);
        }
        g2d.drawLine(
                (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4], -1,
                (int) ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[4], (int) ReferenceWindow.REF_WIN_H+1
        );
    }

    /**
     * Handles the appearance of the component by updating the time point, setting the global opacity,
     * and rendering the text and judgement line. This method is called when the component is first displayed.
     *
     * @param g2d the graphics context used for rendering
     */
    @Override
    public void onAppearance(Graphics2D g2d){
        timePoint = (System.nanoTime() - start_time)/1e9;
        setGlobalOpacity(g2d, timePoint);
        ct.updateText();
        ct.render(g2d);
        renderVerticalJudgement(g2d, (int) (ReferenceWindow.VERTICAL_JUDGEMENT_SPACING*4*Math.min(timePoint, 1)));
    }

    /**
     * Handles the disappearance of the component by updating the time point, setting the global opacity,
     * rendering the text and judgement line, and adjusting the progress bar. This method is called when the component
     * is being removed or faded out.
     *
     * @param g2d the graphics context used for rendering
     */
    @Override
    public void onDisappearance(Graphics2D g2d){
        timePoint = (System.nanoTime()-end_time)/1e9;
        if(goBack){
            render(g2d);
        }
        else{
            if(timePoint<=1){
                setGlobalOpacity(g2d, 1.0-timePoint);
                ct.updateText();
                ct.render(g2d);
                renderVerticalJudgement(g2d, (int) (ReferenceWindow.VERTICAL_JUDGEMENT_SPACING*4*(1-timePoint)));
                progressBar.setY(-progressBar.getHeight()*timePoint);
                progressBar.render(g2d);
            }
        }
    }

    /**
     * Forces a refresh by saving the current music position to a file and adjusting the music position
     * based on the current time. If the music position deviates too much from the expected time, it is corrected.
     * This method is typically used for synchronizing the music playback with the expected timeline.
     */
    private void forceRefresh(){
        try {
            PrintWriter pw = new PrintWriter(".refresh");
            pw.println(music.getMicrosecondPosition());
            if(Math.abs(music.getMicrosecondPosition()- CURRENT_TIME*1e6)>users.get(userIndex).tolerance*1e3){
                music.setMicrosecondPosition((long) Math.max(0, CURRENT_TIME*1e6));
            }
            pw.close();
        } catch (IOException ignored) {}
    }

    /**
     * Renders the current state of the game screen. It handles the playing and pausing of music,
     * updating and rendering the chart lanes, and displaying UI components such as play, restart, and back buttons.
     * Additionally, it updates and displays the progress bar, judgement lines, and other visual elements.
     *
     * @param g2d the graphics context used for rendering
     */
    @Override
    public void render(Graphics2D g2d){
        if(music!=null && !isPaused && !music.isRunning() && CURRENT_TIME>=users.get(userIndex).MUSIC_DIFFERENCE/1000.0){
            music.start();
        }
        if (music != null && isPaused && music.isRunning()) {
            music.stop();
        }
        forceRefresh();
        if(isPaused){
            if (goBack) {
                setGlobalOpacity(g2d, 1-timePoint);
            }
            cb.play.render(g2d);
            cb.restart.render(g2d);
            cb.back.render(g2d);
            if(goBack){
                setGlobalOpacity(g2d, 0.25*(1-timePoint));
            }
            else{
                setGlobalOpacity(g2d, 0.25);
            }
        }
        for(int i=0; i<16; i++){
            if(!users.get(userIndex).isAutoplay){
                lanes[i].update(signal[i]);
                if(signal[i]!=0){
                    signal[i] = 0;
                }
            }
            else{
                lanes[i].autoplay();
            }
            ArrayList<CRect> render = lanes[i].toRect();
            for(CRect r: render){
                r.render(g2d);
            }
        }
        renderVerticalJudgement(g2d, (int) ReferenceWindow.VERTICAL_JUDGEMENT_SPACING*4);
        ct.updateText();
        ct.render(g2d);
        setGlobalOpacity(g2d, 1);
        if(!isPaused){
            cb.pause.render(g2d);
            CURRENT_TIME += 1.0 / Control.FPS;
        }
        if(music!=null){
            progressBar.setWidth(ReferenceWindow.REF_WIN_W / (music.getMicrosecondLength()/1e6-STATIC_TIMER) * (CURRENT_TIME-STATIC_TIMER));
            progressBar.render(g2d);
        }
        if(progressBar.getWidth() >= ReferenceWindow.REF_WIN_W){
            try {
                Files.delete(Paths.get("music.refresh"));
            } catch (IOException ignored) {}
            exit(2000);
        }
    }

    /**
     * Navigates to the next panel based on the current state. If the user is going back, it navigates
     * to Song Selection, otherwise, it navigates to Result page.
     */
    @Override
    public void toNextPanel(){
        if(goBack){
            Control.panel_index = 2;
        }
        else{
            Control.panel_index = 4;
        }
    }
}
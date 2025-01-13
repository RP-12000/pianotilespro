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
import java.util.ArrayList;

import static org.kelvinizer.constants.Control.isAutoplay;

public class Chart extends AnimatablePanel {
    public static double noteCount;
    private final ChartText ct = new ChartText();
    private final ChartButtons cb = new ChartButtons();
    private final Lane[] lanes = new Lane[16];
    private final double STATIC_TIMER;

    private AudioInputStream inputStream = null;
    private Clip music = null;

    private final CRect progressBar = new CRect(0, 10);

    private boolean goBack = false;
    private boolean isPaused = false;

    int[] keyboardActivation = {
            KeyEvent.VK_A,
            KeyEvent.VK_S,
            KeyEvent.VK_D,
            KeyEvent.VK_F,
            KeyEvent.VK_J,
            KeyEvent.VK_K,
            KeyEvent.VK_L,
            KeyEvent.VK_SEMICOLON,
            KeyEvent.VK_Q,
            KeyEvent.VK_W,
            KeyEvent.VK_E,
            KeyEvent.VK_R,
            KeyEvent.VK_U,
            KeyEvent.VK_I,
            KeyEvent.VK_O,
            KeyEvent.VK_P,
    };

    boolean[] isDown = {
            false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false,
    };

    int[] signal = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    private void press(int l){
        if(!isDown[l]){
            signal[l]=1;
            isDown[l]=true;
        }
    }

    private void release(int l){
        signal[l] = -1;
        isDown[l]=false;
    }

    private void restart() {
        for(Lane l: lanes){
            l.restart();
        }
        Time.CURRENT_TIME = STATIC_TIMER;
        isPaused = false;
        if(inputStream!=null){
            music.setMicrosecondPosition(0);
        }
    }

    private void getMusic(File songDir) throws IOException, LineUnavailableException {
        File[] f = songDir.listFiles();
        if (f != null) {
            for(File thing: f){
                try{
                    inputStream = AudioSystem.getAudioInputStream(thing);
                } catch (UnsupportedAudioFileException ignored){}
            }
        }
        if(inputStream!=null){
            music = AudioSystem.getClip();
            music.open(inputStream);
        }
    }

    private void staggerStart(){
        music.start();
        try {
            Thread.sleep(511);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error when loading music");
        }
        music.stop();
    }

    public Chart(String songDir, String level) throws IOException, RuntimeException, LineUnavailableException {
        super(3000);
        getMusic(new File(songDir));

        BufferedReader chart = new BufferedReader(new FileReader(songDir+"/"+level+".txt"));
        ArrayList<Note> tempNotes = new ArrayList<>();
        noteCount = Double.parseDouble(chart.readLine());
        if(noteCount==0){
            throw new RuntimeException("Zero-note chart detected");
        }

        for(int i=0; i<(int)noteCount; i++){
            String[] s = chart.readLine().split(" ");
            int numMotions = Integer.parseInt(s[1]);
            Note n;
            if(s[0].equals("0")){
                n = TapNote.parseTapNote(chart.readLine());
            }
            else{
                n = HoldNote.parseHoldNote(chart.readLine());
            }
            for(int j=0; j<numMotions; j++){
                n.addMotion(new Motion(chart.readLine()));
            }
            if(n.isValidNote()){
                tempNotes.add(n);
            }
            else{
                throw new RuntimeException("Invalid Note detected");
            }
        }
        tempNotes.sort((Note a, Note b) -> (int)( (a.getStartTime()-b.getStartTime())));
        for(int i=0; i<tempNotes.size()-1; i++){
            if(tempNotes.get(i).equals(tempNotes.get(i+1))){
                tempNotes.get(i).sync();
                tempNotes.get(i+1).sync();
            }
        }
        STATIC_TIMER = Math.min(
                Math.min(0.0, tempNotes.getFirst().getPerfectHitTime() - tempNotes.getFirst().getTotalMovementTime()),
                Control.MUSIC_DIFFERENCE/1000.0
        );

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

    public Chart() throws IOException, LineUnavailableException {
        this("Chart/"+Selection.collectionDir+"/"+Selection.songDir, Selection.level);
        staggerStart();
    }

    public static boolean isValidChart(String dir, String songLevel){
        try{
            new Chart(dir, songLevel);
        } catch (RuntimeException | IOException | LineUnavailableException e) {
            return false;
        }
        return true;
    }

    @Override
    public void mouseMoved(MouseEvent e){
        cb.setFocused(e);
    }

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

    @Override
    public void onAppearance(Graphics2D g2d){

    }

    @Override
    public void onActive(Graphics2D g2d){
        render(g2d);
    }

    @Override
    public void onDisappearance(Graphics2D g2d){
        if(goBack){
            super.onDisappearance(g2d);
        }
        else{

        }
    }

    @Override
    public void render(Graphics2D g2d){
        if(music!=null && !isPaused && !music.isRunning() && Time.CURRENT_TIME>=Control.MUSIC_DIFFERENCE/1000.0){
            music.start();
        }
        if (music != null && isPaused && music.isRunning()) {
            music.stop();
        }
        if(isPaused){
            cb.play.render(g2d);
            cb.restart.render(g2d);
            cb.back.render(g2d);
            setGlobalOpacity(g2d, 0.25f);
        }
        for(int i=0; i<16; i++){
            if(!isAutoplay){
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
        if(Lane.bad+Lane.good+Lane.miss == 0){
            g2d.setColor(Color.GREEN);
        }
        else if(Lane.bad+Lane.miss == 0){
            g2d.setColor(Color.BLUE);
        }
        else{
            g2d.setColor(Color.WHITE);
        }
        for(int i=0; i<9; i++){
            g2d.drawLine(
                    (int)ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[i], -1,
                    (int)ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[i], (int)ReferenceWindow.REF_WIN_H+1
            );
        }
        for(int i=0; i<16; i++){
            g2d.drawLine(
                    (int)ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[i%8],
                    (int)ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[i/8],
                    (int)ReferenceWindow.VERTICAL_JUDGEMENT_LINE_POS[i%8+1],
                    (int)ReferenceWindow.HORIZONTAL_JUDGEMENT_LINE_POS[i/8]
            );
        }
        ct.updateText();
        ct.render(g2d);
        progressBar.setWidth(ReferenceWindow.REF_WIN_W / (music.getMicrosecondLength()/1e6-STATIC_TIMER) * (Time.CURRENT_TIME-STATIC_TIMER));
        progressBar.render(g2d);
        setGlobalOpacity(g2d, 1);
        if(!isPaused){
            cb.pause.render(g2d);
            Time.CURRENT_TIME += 1.0 / Time.FPS;
        }
    }

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
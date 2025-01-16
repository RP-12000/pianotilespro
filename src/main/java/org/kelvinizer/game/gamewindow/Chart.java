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

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.*;

public class Chart extends AnimatablePanel {
    public static double noteCount;
    public static double CURRENT_TIME = 0 ;
    private final ChartText ct = new ChartText();
    private final ChartButtons cb = new ChartButtons();
    private final Lane[] lanes = new Lane[16];
    private final double STATIC_TIMER;

    private AudioInputStream inputStream = null;
    private Clip music = null;
    public static double duration;

    private final CRect progressBar = new CRect(0, 10);

    private boolean goBack = false;
    private boolean isPaused = false;
    private double timePoint;

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
        CURRENT_TIME = STATIC_TIMER;
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
            duration = music.getMicrosecondLength()/1e3;
        }
//        else{
//            throw new RuntimeException("No audio detected");
//        }
    }

    public Chart(Song song, String level) throws IOException, RuntimeException, LineUnavailableException {
        super(2000);
        getMusic(new File(getResourcePathName(song.getAbsoluteDir())));
        BufferedReader chart = new BufferedReader(new FileReader(getResourcePathName(song.getAbsoluteDir()+"/"+level+".txt")));
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
                n = TapNote.parseTapNote(chart.readLine(), song.OFFSET);
            }
            else{
                n = HoldNote.parseHoldNote(chart.readLine(), song.OFFSET);
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
        tempNotes.sort((Note a, Note b) -> (int)( (a.getStartTime()-b.getStartTime())));
        for(int i=0; i<tempNotes.size()-1; i++){
            if(tempNotes.get(i).equals(tempNotes.get(i+1))){
                tempNotes.get(i).sync();
                tempNotes.get(i+1).sync();
            }
        }
        STATIC_TIMER = Math.min(
                Math.min(0.0, tempNotes.getFirst().getPerfectHitTime() - tempNotes.getFirst().getTotalMovementTime()),
                users.get(userIndex).MUSIC_DIFFERENCE/1000.0
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
        this(getSongData(), Selection.level);
    }

    public static boolean isValidChart(Song s, String level){
        try{
            new Chart(s, level);
            return true;
        } catch (IOException | RuntimeException | LineUnavailableException e){
            return false;
        }
    }

    @Override
    public void scale(Dimension d){
        cb.scale(d);
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

    private void setJudgementLineColor(Graphics2D g2d){
        if(Lane.bad+Lane.good+Lane.miss == 0 && users.get(userIndex).FCAPHintEnabled){
            g2d.setColor(Color.GREEN);
        }
        else if(Lane.bad+Lane.miss == 0 && users.get(userIndex).FCAPHintEnabled){
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

    @Override
    public void onAppearance(Graphics2D g2d){
        timePoint = (System.nanoTime() - start_time)/1e9;
        setGlobalOpacity(g2d, timePoint);
        ct.updateText();
        ct.render(g2d);
        setGlobalOpacity(g2d, 1);
        renderVerticalJudgement(g2d, (int) (ReferenceWindow.VERTICAL_JUDGEMENT_SPACING*4*Math.min(timePoint, 1)));
    }

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
                setGlobalOpacity(g2d, 1);
                renderVerticalJudgement(g2d, (int) (ReferenceWindow.VERTICAL_JUDGEMENT_SPACING*4*(1-timePoint)));
                progressBar.setY(-progressBar.getHeight()*timePoint);
                progressBar.render(g2d);
            }
        }
    }

    private void forceRefresh(){
        try {
            PrintWriter pw = new PrintWriter("music.refresh");
            pw.println(music.getMicrosecondPosition());
            if(Math.abs(music.getMicrosecondPosition()- CURRENT_TIME*1e6)>users.get(userIndex).tolerance*1e3){
                music.setMicrosecondPosition((long) Math.max(0, CURRENT_TIME*1e6));
            }
            pw.close();
        } catch (IOException ignored) {}
    }

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
        progressBar.setWidth(ReferenceWindow.REF_WIN_W / (music.getMicrosecondLength()/1e6-STATIC_TIMER) * (CURRENT_TIME-STATIC_TIMER));
        progressBar.render(g2d);
        setGlobalOpacity(g2d, 1);
        if(!isPaused){
            cb.pause.render(g2d);
            CURRENT_TIME += 1.0 / Control.FPS;
        }
        if(progressBar.getWidth() >= ReferenceWindow.REF_WIN_W){
            exit(2000);
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
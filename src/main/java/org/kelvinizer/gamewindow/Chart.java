package org.kelvinizer.gamewindow;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.*;
import org.kelvinizer.motion.Motion;
import org.kelvinizer.note.HoldNote;
import org.kelvinizer.note.Note;
import org.kelvinizer.note.TapNote;
import org.kelvinizer.support.CRect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import static org.kelvinizer.constants.General.isPaused;

public class Chart extends AnimatablePanel {
    double noteCount;
    Lane[] lanes = new Lane[16];
    private final double STATIC_TIMER;
    BufferedImage illustration;
    int[] activation = {
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

    private void getJacket(File f){
        File[] lf = f.listFiles();
        if(lf == null){
            illustration = null;
            return;
        }
        boolean hasJacket = false;
        for(File thing: lf){
            try{
                String path = thing.getCanonicalPath();
                if(path.endsWith(".jpg")||path.endsWith(".png")){
                    if(!hasJacket){
                        illustration = ImageIO.read(thing);
                        hasJacket=true;
                    }
                    else{
                        illustration = null;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(!hasJacket){
            illustration = null;
        }
    }


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

    private void restart(){
        for(Lane l: lanes){
            l.restart();
        }
        Time.CURRENT_TIME = STATIC_TIMER;
    }

    public Chart(String dir) throws IOException, RuntimeException {
        super(1000, 1000);
        BufferedReader chart = new BufferedReader(new FileReader(dir +"/chart.txt"));
        ArrayList<Note> tempNotes = new ArrayList<>();
        noteCount = Double.parseDouble(chart.readLine());
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
                n.addMotion(Motion.parseMotion(chart.readLine()));
            }
            tempNotes.add(n);
        }
        tempNotes.sort(Note::compareTo);
        for(int i=0; i<tempNotes.size()-1; i++){
            if(tempNotes.get(i).equals(tempNotes.get(i+1))){
                tempNotes.get(i).sync();
                tempNotes.get(i+1).sync();
            }
        }
        STATIC_TIMER = Math.min(0.0, tempNotes.getFirst().getPerfectHitTime() - tempNotes.getFirst().getTotalMovementTime());
        for(int i=0; i<16; i++){
            lanes[i] = new Lane();
        }
        for (Note tempNote : tempNotes) {
            lanes[tempNote.getLaneNum()].addNote(tempNote);
        }
        getJacket(new File(dir));
        for(int i=0; i<16; i++){
            int finalI = i;
            addKeyBinding(activation[i], false, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    press(finalI);
                }
            });
            addKeyBinding(activation[i], new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    release(finalI);
                }
            });
        }
        addKeyBinding(KeyEvent.VK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = (isPaused+1)%2;
            }
        });
        restart();
    }

    public Chart() throws IOException{
        this(Selection.collectionDir+"/"+Selection.songDir + "/" + Selection.level);
    }

    public static boolean isValidChart(String dir){
        try{
            new Chart(dir);
        } catch (RuntimeException | IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void onAppearance(Graphics2D g2d){

    }

    @Override
    public void onActive(Graphics2D g2d){
        renderObjects(g2d);
    }

    @Override
    public void onDisappearance(Graphics2D g2d){

    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        for(int i=0; i<16; i++){
            ArrayList<CRect> render = lanes[i].toRect();
            for(CRect r: render){
                r.draw(g2d);
            }
            lanes[i].update(signal[i]);
            if(signal[i]!=0){
                signal[i] = 0;
            }
        }
        Time.CURRENT_TIME += 1.0/Time.FPS;
        if(Lane.bad+Lane.good+Lane.miss == 0){
            g2d.setColor(GameColors.JUDGEMENT_LINE_COLOR[isPaused][0]);
        }
        else if(Lane.bad+Lane.miss == 0){
            g2d.setColor(GameColors.JUDGEMENT_LINE_COLOR[isPaused][1]);
        }
        else{
            g2d.setColor(GameColors.JUDGEMENT_LINE_COLOR[isPaused][2]);
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
    }
}
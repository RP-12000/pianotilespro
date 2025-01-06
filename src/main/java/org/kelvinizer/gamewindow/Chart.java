package org.kelvinizer.gamewindow;

import org.kelvinizer.Constants.*;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.support.CRect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Chart extends AnimatablePanel {
    double noteCount = 0;
    Lane[] lanes = new Lane[16];
    private double STATIC_TIMER;
    BufferedImage illustration;
    int isPaused = 0;
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

    public Chart(String dir){
        super(1000, 1000);
        FileReader f;
        try{
            f = new FileReader(dir +"/chart.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Invalid Chart");
        }
        BufferedReader chart = new BufferedReader(f);
        ArrayList<Note> tempNotes = new ArrayList<>();
        while(true){
            try{
                String temp = chart.readLine();
                if(temp==null){
                    break;
                }
                try{
                    tempNotes.add(new Note(temp));
                } catch (RuntimeException e) {
                    throw new RuntimeException("Invalid Chart");
                }
                noteCount++;
            } catch (IOException e) {
                break;
            }
        }
        tempNotes.sort(Note::compareTo);
        for(int i=0; i<tempNotes.size()-1; i++){
            if(tempNotes.get(i).getPerfect_hit_time() == tempNotes.get(i+1).getPerfect_hit_time()){
                tempNotes.get(i).sync();
                tempNotes.get(i+1).sync();
            }
        }
        STATIC_TIMER = Math.min(0.0, tempNotes.getFirst().getPerfect_hit_time() - tempNotes.getFirst().get_duration());
        for(int i=0; i<16; i++){
            lanes[i] = new Lane(i);
        }
        for (Note tempNote : tempNotes) {
            try{
                lanes[tempNote.getLaneNum()].add_note(tempNote);
            }catch(IndexOutOfBoundsException e){
                throw new RuntimeException("Invalid Chart");
            }
        }
        try{
            f = new FileReader(dir + "/jm.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Invalid Chart");
        }
        chart = new BufferedReader(f);
        while(true){
            try{
                String temp = chart.readLine();
                if(temp==null){
                    break;
                }
                try{
                    String[] strings = temp.split(" ");
                    lanes[(int)Double.parseDouble(strings[0])].add_invisible_interval(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]));
                } catch (RuntimeException e) {
                    throw new RuntimeException("Invalid Chart");
                }
                noteCount++;
            } catch (IOException e) {
                break;
            }
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

    public Chart(){
        this(Selection.collectionDir+"/"+Selection.songDir + "/" + Selection.level);
    }

    public static boolean isValidChart(String dir){
        try{
            new Chart(dir);
        } catch (RuntimeException e) {
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
            ArrayList<CRect> render = lanes[i].to_rect(isPaused);
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
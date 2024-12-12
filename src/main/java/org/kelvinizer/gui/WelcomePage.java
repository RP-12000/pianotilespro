package org.kelvinizer.gui;

import org.kelvinizer.Animatable;
import org.kelvinizer.Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class WelcomePage extends JPanel implements Animatable {
    private BufferedImage image;
    int click_count=0;
    private int status = 0;
    private long init_time, end_time;
    private boolean has_start, has_end;

    private void addListners(){
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click_count++;
                if(click_count==2){
                    removeMouseListener(this);
                    status=2;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public WelcomePage(){
        try{
            image = ImageIO.read(new File("welcome.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setSize(ReferenceWindow.REF_WIN_W, ReferenceWindow.REF_WIN_H);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D) g;
        g2d.scale((double)getWidth()/ ReferenceWindow.REF_WIN_W, (double)getHeight()/ReferenceWindow.REF_WIN_H);
        switch(status){
            case 0:
                callOnAppearance(g2d);
                break;
            case 1:
                callOnActive(g2d);
                break;
            case 2:
                callOnDisappearance(g2d);
                break;
            default:
                break;
        }
    }

    @Override
    public void callOnAppearance(Graphics2D g2d){
        if(!has_start){
            has_start=true;
            init_time=System.nanoTime();
        }
        if(System.nanoTime()-init_time<=Time.intro_time_in_ms*1e6){

        }
        else{
            status=1;
            addListners();
        }
    }

    @Override
    public void callOnActive(Graphics2D g2d){
        if(image!=null){
            g2d.drawImage(image, 0, 0, ReferenceWindow.REF_WIN_W, ReferenceWindow.REF_WIN_H, this);
        }
        g2d.drawString("Double Click to Start", 100, 100);
    }

    @Override
    public void callOnDisappearance(Graphics2D g2d){
        if(!has_end){
            has_end=true;
            end_time=System.nanoTime();
        }
        if(System.nanoTime()-end_time<=Time.exit_time_in_ms*1e6){

        }
        else{
            status=0;
            has_start=false;
            has_end=false;
            PanelControl.panel_index = 1;
        }
    }
}
package org.kelvinizer.gui;

import org.kelvinizer.Constants;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.support.PolygonButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class ChartSelection extends AnimatablePanel {
    private final PolygonButton BS = new PolygonButton(
            new Rectangle(190, 160, 200, 150)
    );
    private final PolygonButton MD = new PolygonButton(
            new Rectangle(620, 160, 200, 150)
    );
    private final PolygonButton AV_WITH_LG = new PolygonButton(
            new Rectangle(190, 390, 200, 150)
    );
    private final PolygonButton AV_WITHOUT_LG = new PolygonButton(
            new Rectangle(410, 390, 200, 150)
    );
    private final PolygonButton LG = new PolygonButton(
            new Rectangle(620, 390, 200, 150)
    );
    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );
    private final PolygonButton settings = new PolygonButton(
            new Rectangle(Constants.ReferenceWindow.REF_WIN_W-100, 0, 100, 100)
    );
    private boolean isValid = true;
    private boolean hasLG = true;
    private boolean goBack = false;

    public ChartSelection(){
        check();
    }

    private void check(){
        File f = new File(Constants.GameWindow.songDir+"/BS");
        if(!f.exists() || !f.isDirectory()){
            isValid=false;
        }
        f = new File(Constants.GameWindow.songDir+"/MD");
        if(!f.exists() || !f.isDirectory()){
            isValid=false;
        }
        f = new File(Constants.GameWindow.songDir+"/AV");
        if(!f.exists() || !f.isDirectory()){
            isValid=false;
        }
        f = new File(Constants.GameWindow.songDir+"/LG");
        if(!f.exists() || !f.isDirectory()){
            hasLG=false;
        }
        f = new File(Constants.GameWindow.songDir+"/BS/chart.txt");
        if(!f.exists()){
            isValid=false;
        }
        f = new File(Constants.GameWindow.songDir+"/MD/chart.txt");
        if(!f.exists()){
            isValid=false;
        }
        f = new File(Constants.GameWindow.songDir+"/AV/chart.txt");
        if(!f.exists()){
            isValid=false;
        }
        f = new File(Constants.GameWindow.songDir+"/LG/chart.txt");
        if(!f.exists()){
            hasLG=false;
        }
    }

    private void exitWithVerdict(String verdict){
        Constants.GameWindow.level=verdict;
        exit();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        BS.setFocused(e);
        MD.setFocused(e);
        if(hasLG){
            AV_WITH_LG.setFocused(e);
            LG.setFocused(e);
        }
        else{
            AV_WITHOUT_LG.setFocused(e);
        }
        back.setFocused(e);
        settings.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(BS.contains(e.getPoint())){
            exitWithVerdict("BS");
        }
        if(MD.contains(e.getPoint())){
            exitWithVerdict("MD");
        }
        if(hasLG){
            if(AV_WITH_LG.contains(e.getPoint())){
                exitWithVerdict("AV");
            }
            if(LG.contains(e.getPoint())){
                exitWithVerdict("LG");
            }
        }
        else{
            if(AV_WITHOUT_LG.contains(e.getPoint())){
                exitWithVerdict("AV");
            }
        }
        if(back.contains(e.getPoint())) {
            goBack = true;
            exit();
        }
    }

    @Override
    public void resizeButtons(Dimension d){
        BS.resize(d);
        MD.resize(d);
        LG.resize(d);
        AV_WITH_LG.resize(d);
        AV_WITHOUT_LG.resize(d);
        back.resize(d);
        settings.resize(d);
    }

    @Override
    protected void renderObjects(Graphics2D g2d){
        if(!isValid){
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    "Song Corrupted QAQ",
                    540 - (metrics.stringWidth("Song Corrupted QAQ") / 2),
                    360 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
        }
        else{
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    "Select Your Difficulty",
                    540 - (metrics.stringWidth("Select Your Difficulty") / 2),
                    80 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString(
                    "Basic",
                    320 - (metrics.stringWidth("Basic") / 2),
                    225 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
            g2d.drawString(
                    "Medium",
                    765 - (metrics.stringWidth("Medium") / 2),
                    225 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
            BS.draw(g2d);
            MD.draw(g2d);
            if(!hasLG){
                AV_WITHOUT_LG.draw(g2d);
                g2d.drawString(
                        "Advanced",
                        565 - (metrics.stringWidth("Advanced") / 2),
                        455 + ((metrics.getAscent() - metrics.getDescent()) / 2)
                );
            }
            else{
                AV_WITH_LG.draw(g2d);
                LG.draw(g2d);
                g2d.drawString(
                        "Advanced",
                        347 - (metrics.stringWidth("Advanced") / 2),
                        455 + ((metrics.getAscent() - metrics.getDescent()) / 2)
                );
                g2d.drawString(
                        "Legendary",
                        782 - (metrics.stringWidth("Legendary") / 2),
                        455 + ((metrics.getAscent() - metrics.getDescent()) / 2)
                );
            }
        }
        back.draw(g2d);
        settings.draw(g2d);
    }

    @Override
    public void toNextPanel(){
        if(goBack){
            Constants.panel_index = 1;
        }
        else{
            Constants.panel_index = 3;
        }
    }
}

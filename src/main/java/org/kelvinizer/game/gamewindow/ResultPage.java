package org.kelvinizer.game.gamewindow;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.dynamic.DynamicMotionManager;
import org.kelvinizer.game.gamebuttons.ResultPageButtons;
import org.kelvinizer.game.gametext.ResultPageText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ResultPage extends AnimatablePanel {
    private final DynamicMotionManager dmm = new DynamicMotionManager();
    private final ResultPageButtons rpb = new ResultPageButtons(dmm);

    boolean goBack = false;

    public ResultPage(){
        super(3000);
        new ResultPageText(dmm);
        addKeyBinding(KeyEvent.VK_R, true, KeyEvent.SHIFT_DOWN_MASK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack=true;
                exit();
            }
        });
        dmm.activate();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(rpb.restart.getCRectButton().isFocused()){
            exit();
        }
        else if(rpb.back.getCRectButton().isFocused()) {
            goBack=true;
            exit();
        }
    }

    @Override
    public void onAppearance(Graphics2D g2d){
        dmm.render(g2d);
    }

    @Override
    public void render(Graphics2D g2d) {
        dmm.render(g2d);
    }

    @Override
    public void toNextPanel(){
        if(goBack){
            Control.panel_index = 2;
        }
        else{
            Control.panel_index = 3;
        }
    }
}

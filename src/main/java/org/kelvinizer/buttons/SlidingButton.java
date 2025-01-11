package org.kelvinizer.buttons;

import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SlidingButton implements Focusable, Drawable, Scalable {
    private CRect base = new CRect();
    private CRectButton slider = new CRectButton();
    private BoundedString verdict = new BoundedString();
    private final double minVal, maxVal;
    private double start, end, k, b, currentVal;
    private double mousePosScaleFactor = 1;

    public SlidingButton(double minVal, double maxVal, double startVal){
        this.maxVal = maxVal;
        this.minVal = minVal;
        this.currentVal = startVal;
    }

    public SlidingButton(double minVal, double maxVal){
        this(minVal, maxVal, (minVal+maxVal)/2);
    }

    public void setBaseAndSlider(CRect base, CRectButton slider) {
        this.base = base;
        this.slider = slider;
        start = base.getX() - base.getWidth()/2.0 + slider.getNormal().getBounds().getWidth()/2.0;
        end = base.getX() + base.getWidth()/2.0 - slider.getNormal().getBounds().getWidth()/2.0;
        k = (maxVal - minVal) / (end - start);
        b = minVal - k * start;
        this.slider.getNormal().getBounds().setPosition((currentVal - b)/k, this.base.getY());
        this.slider.getOnFocus().getBounds().setPosition((currentVal - b)/k, this.base.getY());
    }

    public void setVerdict(BoundedString verdict) {
        this.verdict = verdict;
    }

    public void setVerdictString(String s){
        this.verdict.setString(s);
    }

    public void moveSlider(MouseEvent e){
        if(slider.isFocused()){
            slider.getNormal().getBounds().setX(Math.clamp(e.getX()/mousePosScaleFactor, start, end));
            slider.getOnFocus().getBounds().setX(Math.clamp(e.getX()/mousePosScaleFactor, start, end));
            currentVal = (int)(k*slider.getOnFocus().getBounds().getX() + b);
        }
    }

    public void moveSlider(double delta){
        currentVal = Math.clamp(currentVal+delta, minVal, maxVal);
        slider.getNormal().getBounds().setX((currentVal - b)/k);
        slider.getOnFocus().getBounds().setX((currentVal - b)/k);
    }

    public double getCurrentVal(){
        return currentVal;
    }

    @Override
    public void render(Graphics2D g2d) {
        base.render(g2d);
        slider.render(g2d);
        verdict.render(g2d);
    }

    @Override
    public void setFocused(MouseEvent e) {
        slider.setFocused(e);
    }

    @Override
    public void scale(Dimension d) {
        slider.scale(d);
        mousePosScaleFactor = (double) d.width / ReferenceWindow.REF_WIN_W;
    }
}
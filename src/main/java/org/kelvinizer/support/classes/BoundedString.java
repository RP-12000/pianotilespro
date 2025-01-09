package org.kelvinizer.support.classes;

import org.kelvinizer.constants.Control;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class BoundedString implements Drawable {
    private CRect bounds;
    private String string;

    private String fontFamily = "Arial";
    private int style = Font.BOLD;
    private int stringSize;

    private Color stringColor = Control.DEFAULT_COLOR;
    private double horizontalWhiteSpace = 3, verticalWhiteSpace = 2;
    private int maxStringSize = -1;
    private double relativeX = 0.5, relativeY = 0.5;

    public BoundedString(String d, int stringSize, double x, double y, double width ,double height){
        string = d;
        this.stringSize = stringSize;
        bounds = new CRect(x, y, width, height);
    }

    public BoundedString(String d, int stringSize, double x, double y){
        this(d, stringSize, x, y, 0, 0);
    }

    public BoundedString(String d, int stringSize){
        this(d, stringSize, 0, 0);
    }

    public BoundedString(String d){
        this(d, 0);
    }

    public BoundedString(){
        this("");
    }

    private boolean isValidSize(Graphics2D g2d, Font f, String d){
        FontMetrics fm = g2d.getFontMetrics(f);
        return (bounds.getHeight()*relativeY - (double) (fm.getAscent()+fm.getDescent())/2 > verticalWhiteSpace) &&
               (bounds.getHeight()*(1-relativeY) - (double) (fm.getAscent()+fm.getDescent())/2 > verticalWhiteSpace) &&
               (bounds.getWidth()*relativeX - (double) (fm.stringWidth(d))/2 > horizontalWhiteSpace) &&
               (bounds.getWidth()*(1-relativeX) - (double) (fm.stringWidth(d))/2 > horizontalWhiteSpace);
    }

    protected Pair<Double, Double> getRenderPoint(Graphics2D g2d, String d){
        FontMetrics fm = g2d.getFontMetrics();
        return new Pair<>(
                bounds.getX() - bounds.getOrigin().first + bounds.getWidth()*relativeX - (fm.stringWidth(d) / 2),
                bounds.getY() - bounds.getOrigin().second + bounds.getHeight()*relativeY + ((fm.getAscent() - fm.getDescent()) / 2)
        );
    }

    @Override
    public void render(Graphics2D g2d){
        bounds.render(g2d);
        if(maxStringSize>=0){
            int size = 0;
            while(isValidSize(g2d, new Font(fontFamily, style, size), string) && size<=maxStringSize){
                size++;
            }
            stringSize = size-1;
        }
        g2d.setFont(new Font(fontFamily, style, stringSize));
        g2d.setColor(stringColor);
        Pair<Double, Double> p = getRenderPoint(g2d, string);
        g2d.drawString(string, (float)(double)p.first, (float)(double)p.second);
        g2d.setStroke(Control.DEFAULT_STROKE);
        g2d.setColor(Control.DEFAULT_COLOR);
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setStringSize(int stringSize){
        this.stringSize = stringSize;
    }

    public void setString(String s){
        string = s;
    }

    public String getString(){
        return string;
    }

    public void setHorizontalWhiteSpace(double horizontalWhiteSpace) {
        this.horizontalWhiteSpace = horizontalWhiteSpace;
    }

    public void setVerticalWhiteSpace(double verticalWhiteSpace) {
        this.verticalWhiteSpace = verticalWhiteSpace;
    }

    public void setStringColor(Color stringColor) {
        this.stringColor = stringColor;
    }

    public void setBounds(CRect c){
        bounds = c;
    }

    public CRect getBounds(){
        return bounds;
    }

    public void setBounds(Rectangle r){
        bounds = new CRect(r.x-r.width/2.0, r.y-r.height/2.0, r.width, r.height);
    }

    public String getFontName(){
        return fontFamily;
    }

    public int getFontStyle(){
        return style;
    }

    public void setMaxStringSize(int maxStringSize) {
        this.maxStringSize = maxStringSize;
    }

    public void setRelativeX(double x){
        relativeX = x;
    }

    public void setRelativeY(double y){
        relativeY = y;
    }
}
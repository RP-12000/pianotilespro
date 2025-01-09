package org.kelvinizer.support.classes;

import org.kelvinizer.constants.General;
import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public class BoundedString implements Drawable {
    private CRect bounds;
    private String data;
    private final int maxStringSize;
    private String name = "Arial";
    private Color stringColor = General.DEFAULT_COLOR;
    private int style = Font.BOLD;
    private double horizontalWhiteSpace = 3, verticalWhiteSpace = 2;

    public BoundedString(String d, int maxStringSize, double x, double y, double width ,double height){
        data = d;
        this.maxStringSize = maxStringSize;
        bounds = new CRect(x, y, width, height);
    }

    public BoundedString(String d, int maxStringSize, double width, double height){
        this(d, maxStringSize, 0, 0, width, height);
    }

    public BoundedString(String d, int maxStringSize){
        this(d, maxStringSize, 0, 0);
    }

    public BoundedString(){
        this("", 0);
    }

    private boolean isValidSize(Graphics2D g2d, Font f, String d){
        FontMetrics fm = g2d.getFontMetrics(f);
        return (bounds.getWidth() - 2 * horizontalWhiteSpace > fm.stringWidth(d)) &&
               (bounds.getHeight() - 2 * verticalWhiteSpace > fm.getAscent() - fm.getDescent());
    }

    private Pair<Double, Double> getRenderPoint(Graphics2D g2d, String d){
        FontMetrics fm = g2d.getFontMetrics();
        return new Pair<>(
                bounds.getX() - (fm.stringWidth(d) / 2),
                bounds.getY() + ((fm.getAscent() - fm.getDescent()) / 2)
        );
    }

    @Override
    public void render(Graphics2D g2d){
        bounds.render(g2d);
        int size = 0;
        while(isValidSize(g2d, new Font(name, style, size), data) && size<=maxStringSize){
            size++;
        }
        g2d.setFont(new Font(name, style, size-1));
        g2d.setColor(stringColor);
        Pair<Double, Double> p = getRenderPoint(g2d, data);
        g2d.drawString(data, (float)(double)p.first, (float)(double)p.second);
        g2d.setStroke(General.DEFAULT_STROKE);
        g2d.setColor(General.DEFAULT_COLOR);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setString(String s){
        data = s;
    }

    public String getString(){
        return data;
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
        bounds = new CRect(r.x, r.y, r.width, r.height);
    }
}

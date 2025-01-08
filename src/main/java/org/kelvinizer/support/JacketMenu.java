package org.kelvinizer.support;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JacketMenu{
    private final String dir;
    private final ArrayList<Pair<String, BufferedImage>> menu = new ArrayList<>();
    private CRect textBoundary = new CRect();
    private static final int HORIZONTAL_WHITE_SPACE = 3;
    private static final int VERTICAL_WHITE_SPACE = 2;
    private int menuIndex;
    private final int maxStringSize;

    private BufferedImage getJacket(File f){
        File[] lf = f.listFiles();
        if(lf == null){
            return null;
        }
        for(File thing: lf){
            try{
                String path = thing.getCanonicalPath();
                if(path.endsWith(".jpg")||path.endsWith(".png")){
                    return ImageIO.read(thing);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private void updateMenu() {
        menu.clear();
        File f = new File(dir);
        File[] lf = f.listFiles();
        if(lf == null){
            throw new RuntimeException();
        }
        for(File thing: lf){
            if(thing.isDirectory()){
                try{
                    String path = thing.getCanonicalPath();
                    String[] temp = path.split("\\\\");
                    try{
                        menu.add(new Pair<>(temp[temp.length-1], getJacket(thing)));
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean isValidSize(Graphics2D g2d, Font f, String d){
        FontMetrics fm = g2d.getFontMetrics(f);
        return (textBoundary.getWidth() - 2 * HORIZONTAL_WHITE_SPACE > fm.stringWidth(d)) &&
                (textBoundary.getHeight() - 2 * VERTICAL_WHITE_SPACE > fm.getAscent() - fm.getDescent());
    }

    private Pair<Double, Double> getRenderPoint(Graphics2D g2d, Font f, String d){
        FontMetrics fm = g2d.getFontMetrics(f);
        return new Pair<>(
                textBoundary.getX()-textBoundary.getOrigin().x+textBoundary.getWidth()/2 - (fm.stringWidth(d) / 2),
                textBoundary.getY()-textBoundary.getOrigin().y+ textBoundary.getHeight()/2 + ((fm.getAscent() - fm.getDescent()) / 2)
        );
    }

    public JacketMenu(String dir, int mi, int maxStringSize){
        this.dir = dir;
        updateMenu();
        if(mi>=0 && mi<menu.size()){
            menuIndex = mi;
        }
        else{
            menuIndex = 0;
        }
        this.maxStringSize = maxStringSize;
    }

    public void setTextBoundary(CRect c){
        textBoundary = c;
    }

    public int getMenuIndex(){
        return menuIndex;
    }

    public void moveBackward(){
        menuIndex = Math.max(0, menuIndex-1);
    }

    public void moveForward(){
        menuIndex = Math.min(menu.size()-1, menuIndex+1);
    }

    public void move(int amount){
        menuIndex = Math.min(menu.size()-1, menuIndex+amount);
        menuIndex = Math.max(0, menuIndex);
    }

    public void move(MouseWheelEvent e){
        move(e.getWheelRotation());
    }

    public BufferedImage getSelectionJacket(){
        return getSelectionJacket(menuIndex);
    }

    public BufferedImage getSelectionJacket(int index){
        if(index>=0 && index <menu.size()){
            return menu.get(index).second;
        }
        return null;
    }

    public String getSelectionString(int index){
        return menu.get(index).first;
    }

    public String getSelectionString(){
        return getSelectionString(menuIndex);
    }

    public int size(){
        return menu.size();
    }

    public boolean isEmpty() {
        return menu.isEmpty();
    }

    public boolean atEnd(){
        return menuIndex==menu.size()-1;
    }

    public boolean atBeginning(){
        return menuIndex == 0;
    }

    public CRect getTextBoundary() {
        return textBoundary;
    }

    public Triple<Font, Float, Float> getTextRenderParams(Graphics2D g2d, String name, int style){
        return getTextRenderParams(g2d, name, style, menuIndex);
    }

    public Triple<Font, Float, Float> getTextRenderParams(Graphics2D g2d, String name, int style, int index){
        int size = 0;
        while(isValidSize(g2d, new Font(name, style, size), menu.get(index).first) && size<=maxStringSize){
            size++;
        }
        Font temp = new Font(name, style, size-1);
        Pair<Double, Double> p = getRenderPoint(g2d, temp, menu.get(index).first);
        return new Triple<>(temp, (float)(double)p.first, (float)(double)p.second);
    }
}
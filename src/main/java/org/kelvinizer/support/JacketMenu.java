package org.kelvinizer.support;

import org.kelvinizer.shapes.CRect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JacketMenu{
    private final String dir;
    private final ArrayList<Pair<BoundedString, BufferedImage>> menu = new ArrayList<>();
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
                        menu.add(new Pair<>(
                                new BoundedString(temp[temp.length-1],maxStringSize),
                                getJacket(thing)
                        ));
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public JacketMenu(String dir, int mi, int maxStringSize){
        this.dir = dir;
        this.maxStringSize = maxStringSize;
        updateMenu();
        if(mi>=0 && mi<menu.size()){
            menuIndex = mi;
        }
        else{
            menuIndex = 0;
        }
    }

    public void setBounds(CRect c){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.setBounds(c);
        }
    }

    public void setOutlineColor(Color c){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.getBounds().setOutlineColor(c);
        }
    }

    public void setFillColor(Color c){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.getBounds().setFillColor(c);
        }
    }

    public void setOutlineThickness(double t){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.getBounds().setOutlineThickness(t);
        }
    }

    public void setBounds(Rectangle c){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.setBounds(c);
        }
    }

    public void setStringColor(Color c){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.setStringColor(c);
        }
    }

    public void setHorizontalWhiteSpace(double d){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.setHorizontalWhiteSpace(d);
        }
    }

    public void setVerticalWhiteSpace(double d){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.setVerticalWhiteSpace(d);
        }
    }

    public void setFont(String name, int style){
        for(Pair<BoundedString, BufferedImage> p: menu){
            p.first.setName(name);
            p.first.setStyle(style);
        }
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
        return menu.get(index).first.getString();
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

    public void drawSelectionString(Graphics2D g2d){
        menu.get(menuIndex).first.draw(g2d);
    }
}
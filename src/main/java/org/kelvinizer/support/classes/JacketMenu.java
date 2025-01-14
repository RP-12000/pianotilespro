package org.kelvinizer.support.classes;


import javax.imageio.ImageIO;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.kelvinizer.constants.Control.getResourcePathName;

public class JacketMenu{
    private final String dir;
    private final ArrayList<Pair<String, BufferedImage>> menu = new ArrayList<>();
    private int menuIndex;

    private BufferedImage getJacket(File f){
        File[] lf = f.listFiles();
        if(lf == null){
            return null;
        }
        BufferedImage bf = null;
        for(File thing: lf){
            try{
                bf = ImageIO.read(thing);
                if(bf!=null){
                    return bf;
                }
            } catch (IOException ignored) {}
        }
        return bf;
    }

    private void updateMenu() {
        menu.clear();
        File f = new File(getResourcePathName(dir));
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

    public JacketMenu(String dir, int mi){
        this.dir = dir;
        updateMenu();
        if(mi>=0 && mi<menu.size()){
            menuIndex = mi;
        }
        else{
            menuIndex = 0;
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
}
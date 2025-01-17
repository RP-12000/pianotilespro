package org.kelvinizer.support.classes;


import javax.imageio.ImageIO;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.kelvinizer.constants.Control.getResourceInput;

public class JacketMenu{
    private final String dir;
    private ArrayList<Pair<String, BufferedImage>> menu;
    private int menuIndex;

    private void updateMenu() {
        menu = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResourceInput(dir + "/.ptpmenu")));
        while(true){
            String s;
            try{
                s = Objects.requireNonNull(br.readLine());
            } catch (IOException | NullPointerException e){
                break;
            }

            BufferedImage bi;
            try{
                bi = ImageIO.read(getResourceInput(dir +"/"+s+"/jacket.jpg"));
            } catch (Exception e){
                bi=null;
            }
            menu.add(new Pair<>(s, bi));
        }
        if(menu.isEmpty()){
            throw new RuntimeException("Empty Jacket Menu not allowed");
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
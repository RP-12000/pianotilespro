package org.kelvinizer.support.classes;

import javax.imageio.ImageIO;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static org.kelvinizer.constants.Control.getResourceInput;

/**
 * This class is a menu that stores a list of {@code Pair} objects
 * with a {@code String} name and a {@code BufferedImage} jacket.
 * It is used in the selection of collections and songs.
 * @author Boyan Hu
 */
public class JacketMenu{
    /**
     * An array list of {@code Pair<String, BufferedImage>} objects
     * that serves as the elements of the menu.
     */
    private final ArrayList<Pair<String, BufferedImage>> menu;

    /**
     * Where the menu is right now. The position of
     * the selected {@code Pair<String, BufferedImage>}
     * in {@code menu}.
     */
    private int menuIndex;

    /**
     * Constructs a new {@code JacketMenu} object using the
     * root directory and initial menu index.
     * @param dir the root directory
     * @param mi the initial menu index
     */
    public JacketMenu(String dir, int mi){
        menu = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResourceInput(dir + "/.ptpmenu")));
        while(true){
            //Get the name
            String s;
            try{
                s = Objects.requireNonNull(br.readLine());
            } catch (IOException | NullPointerException e){
                break;
            }
            //Get the image
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
        if(mi>=0 && mi<menu.size()){
            menuIndex = mi;
        }
        else{
            menuIndex = 0;
        }
    }

    /**
     * Get the current menu index
     * @return the current menu index
     */
    public int getMenuIndex(){
        return menuIndex;
    }

    /**
     * Move the menu backwards. This means decrementing the
     * value of {@code menuIndex} by 1 if {@code menuIndex}
     * is not already at 0
     */
    public void moveBackward(){
        menuIndex = Math.max(0, menuIndex-1);
    }

    /**
     * Move the menu forward. This means incrementing the
     * value of {@code menuIndex} by 1 if {@code menuIndex}
     * is not already at {@code menu.size()-1}
     */
    public void moveForward(){
        menuIndex = Math.min(menu.size()-1, menuIndex+1);
    }

    /**
     * Move the menu index by a specific amount and clamp
     * the value between 0 and {@code menu.size()-1}
     * @param amount the change in menuIndex
     */
    public void move(int amount){
        menuIndex = Math.min(Math.max(menuIndex+amount, 0), menu.size()-1);
    }

    /**
     * Move the menu index by a specific rotation of the
     * mouse wheel
     * @param e the {@code MouseWheelEvent} to be processed
     */
    public void move(MouseWheelEvent e){
        move(e.getWheelRotation());
    }

    /**
     * Get the current jacket of the menu element selected.
     * @return the current jacket of the menu element selected
     */
    public BufferedImage getSelectionJacket(){
        return getSelectionJacket(menuIndex);
    }

    /**
     * Get the current jacket of the menu element specified
     * with the index being passed.
     * @param index the index of the element in the menu
     * @return the jacket of the menu element at position {@code index}
     */
    public BufferedImage getSelectionJacket(int index){
        if(index>=0 && index <menu.size()){
            return menu.get(index).second;
        }
        return null;
    }

    /**
     * Get the current name of the menu element selected
     * @return the name of the menu element selected
     */
    public String getSelectionString(){
        return getSelectionString(menuIndex);
    }

    /**
     * Get the current name of the menu element specified
     * wit the index being passed in
     * @param index the index of the element in the menu
     * @return the name of the menu element at position {@code index}
     */
    public String getSelectionString(int index){
        return menu.get(index).first;
    }

    /**
     * Get the size of {@code menu}
     * @return the size of {@code menu}
     */
    public int size(){
        return menu.size();
    }

    /**
     * Check if the menu index is not at the end
     * @return true if it is not at the end, false if it is
     */
    public boolean notAtEnd(){
        return menuIndex != menu.size() - 1;
    }

    /**
     * Check if the menu index is not at the beginning
     * @return true if it is not at the beginning, false if it is
     */
    public boolean notAtBeginning(){
        return menuIndex != 0;
    }
}
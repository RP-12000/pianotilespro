package org.kelvinizer.gui;

import org.kelvinizer.Constants;
import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.support.PolygonButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SelectionPage extends AnimatablePanel {
    private final ArrayList<String> menu = new ArrayList<>();
    private int layer = 0;
    private final ArrayList<Integer> menuIndex = new ArrayList<>();
    private final ArrayList<Image> jackets = new ArrayList<>();
    private boolean goBack = false;
    private final Font NULL_FOLDER_FONT = new Font("Arial", Font.BOLD, 25);
    private final Font MENU_FONT = new Font("Arial", Font.BOLD, 50);
    private String currentSelection = "Chart";
    private final PolygonButton previous = new PolygonButton(
            new Polygon(new int[]{60, 60, 30}, new int[]{310, 410, 360}, 3)
    );
    private final PolygonButton next = new PolygonButton(
            new Polygon(new int[]{1020, 1020, 1050}, new int[]{310, 410, 360}, 3)
    );
    private final PolygonButton back = new PolygonButton(
            new Rectangle(100, 100)
    );
    private final PolygonButton jacket = new PolygonButton(
            new Rectangle(360, 140, 360, 360)
    );
    private final PolygonButton settings = new PolygonButton(
            new Rectangle(Constants.ReferenceWindow.REF_WIN_W-100, 0, 100, 100)
    );

    public SelectionPage(){
        super();
        updateMenu();
        menuIndex.add(0);
        if(!menu.isEmpty()){
            addKeyBinding(KeyEvent.VK_RIGHT, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuIndex.set(layer, (menuIndex.get(layer)+1)%menu.size());
                }
            });
            addKeyBinding(KeyEvent.VK_LEFT, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuIndex.set(layer, (menuIndex.get(layer)-1)%menu.size());
                    if(menuIndex.get(layer)<0){
                        menuIndex.set(layer, menuIndex.get(layer)+menu.size());
                    }
                }
            });
            addKeyBinding(KeyEvent.VK_ENTER, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    exit();
                }
            });
        }
        addKeyBinding(KeyEvent.VK_BACK_SPACE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack=true;
                exit();
            }
        });
    }

    private void getJacket(File f) throws RuntimeException{
        File[] lf = f.listFiles();
        if(lf == null){
            jackets.add(null);
            return;
        }
        boolean hasJacket = false;
        for(File thing: lf){
            try{
                String path = thing.getCanonicalPath();
                if(path.endsWith(".jpg")||path.endsWith(".png")){
                    if(!hasJacket){
                        BufferedImage bi = ImageIO.read(thing);
                        jackets.add(bi);
                        hasJacket=true;
                    }
                    else{
                        throw new RuntimeException("Two jackets detected in one folder.");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(!hasJacket){
            jackets.add(null);
        }
    }

    private void updateMenu() {
        menu.clear();
        jackets.clear();
        File f = new File(currentSelection);
        File[] lf = f.listFiles();
        if(lf == null){
            throw new RuntimeException();
        }
        for(File thing: lf){
            if(thing.isDirectory()){
                try{
                    String path = thing.getCanonicalPath();
                    String[] temp = path.split("\\\\");
                    menu.add(temp[temp.length-1]);
                    try{
                        getJacket(thing);
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e){
        next.setFocused(e);
        previous.setFocused(e);
        jacket.setFocused(e);
        back.setFocused(e);
        settings.setFocused(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(!menu.isEmpty()){
            if(next.contains(e.getPoint())){
                menuIndex.set(layer, (menuIndex.get(layer)+1)%menu.size());
            }
            else if(previous.contains((e.getPoint()))){
                menuIndex.set(layer, (menuIndex.get(layer)-1)%menu.size());
                if(menuIndex.get(layer)<0){
                    menuIndex.set(layer, menuIndex.get(layer)+menu.size());
                }
            }
            else if(jacket.contains(e.getPoint())){
                exit();
            }
        }
        if(back.contains(e.getPoint())){
            goBack = true;
            exit();
        }
    }

    @Override
    public void resizeButtons(Dimension d){
        next.resize(d);
        previous.resize(d);
        back.resize(d);
        jacket.resize(d);
        settings.resize(d);
    }

    @Override
    public void onAppearance(Graphics2D g2d) {
        setAppearingOpacity(g2d);
        renderObjects(g2d);
    }

    @Override
    public void onActive(Graphics2D g2d) {
        renderObjects(g2d);
    }

    @Override
    public void onDisappearance(Graphics2D g2d) {
        setDisappearingOpacity(g2d);
        renderObjects(g2d);
    }

    public void renderObjects(Graphics2D g2d){
        if(menu.isEmpty()){
            g2d.setColor(Color.WHITE);
            g2d.setFont(NULL_FOLDER_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    "Nothing is in here QAQ\n",
                    540 - (metrics.stringWidth("Nothing is in here QAQ\n") / 2),
                    360 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
        }
        else{
            g2d.setColor(Color.WHITE);
            g2d.setFont(MENU_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            g2d.drawString(
                    menu.get(menuIndex.get(layer)),
                    540 - (metrics.stringWidth(menu.get(menuIndex.get(layer))) / 2),
                    600 + ((metrics.getAscent() - metrics.getDescent()) / 2)
            );
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5.0f));
            g2d.drawRect(
                    540 - (metrics.stringWidth(menu.get(menuIndex.get(layer))) / 2) - 5,
                    600 - ((metrics.getAscent() + metrics.getDescent()) / 2) - 5,
                    metrics.stringWidth(menu.get(menuIndex.get(layer))) + 10,
                    metrics.getAscent() + metrics.getDescent()+ 10
            );
            g2d.setStroke(new BasicStroke(1.0f));
            next.fill(g2d);
            previous.fill(g2d);
            jacket.draw(g2d);
            if(jackets.get(menuIndex.get(layer))!=null){
                g2d.drawImage(jackets.get(menuIndex.get(layer)), 360, 140, 360, 360, this);
            }
            else{
                g2d.setFont(new Font("Arial", Font.ITALIC, 15));
                metrics = g2d.getFontMetrics();
                g2d.drawString(
                        "No jacket preview available",
                        540 - (metrics.stringWidth("No jacket preview available") / 2),
                        320 + ((metrics.getAscent() - metrics.getDescent()) / 2)
                );
            }
        }
        settings.draw(g2d);
        back.draw(g2d);
    }

    @Override
    public void toNextPanel() {
        if(goBack){
            if(currentSelection.lastIndexOf('/') == -1){
                Constants.panel_index = 0;
            }
            else{
                currentSelection = currentSelection.substring(0, currentSelection.lastIndexOf('/'));
                updateMenu();
                layer--;
            }
            goBack=false;
        }
        else{
            if(layer == 1){
                Constants.GameWindow.songDir = currentSelection+"/"+menu.get(menuIndex.get(layer));
                Constants.panel_index = 2;
            }
            else{
                currentSelection+="/";
                currentSelection+=menu.get(menuIndex.get(layer));
                updateMenu();
                layer++;
                if(layer == menuIndex.size()){
                    menuIndex.add(0);
                }
            }
        }
    }
}
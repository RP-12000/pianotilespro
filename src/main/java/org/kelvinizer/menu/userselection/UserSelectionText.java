package org.kelvinizer.menu.userselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.classes.Triple;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

import static org.kelvinizer.constants.Selection.level;

public class UserSelectionText implements Drawable {
    private final BoundedString userName = new BoundedString("", 40, 540, 330, 600, 270);
    private final BoundedString totalData = new BoundedString("", 20, 312, 495, 140, 50);
    private final BoundedString clearedData = new BoundedString("", 20, 464, 495, 140, 50);
    private final BoundedString fcData = new BoundedString("", 20, 616, 495, 140, 50);
    private final BoundedString apData = new BoundedString("", 20, 768, 495, 140, 50);

    private void setBoundsAndColor(BoundedString bs, Color c){
        bs.getBounds().setOutlineColor(c);
        bs.getBounds().setOutlineThickness(5.0);
        bs.getBounds().setOutlineColor(c);
        bs.setMaxStringSize(20);
        bs.setStringColor(c);
    }

    public UserSelectionText(){
        userName.getBounds().setOutlineColor(Color.WHITE);
        userName.getBounds().setOutlineThickness(5.0);
        setBoundsAndColor(totalData, Color.WHITE);
        setBoundsAndColor(clearedData, Color.YELLOW);
        setBoundsAndColor(fcData, Color.BLUE);
        setBoundsAndColor(apData, Color.GREEN);
    }

    @Override
    public void render(Graphics2D g2d) {
        if(UserSelection.renderIndex!=Control.users.size()){
            userName.setString(Control.users.get(UserSelection.renderIndex).userName);
            Pair<Integer, Triple<Integer, Integer, Integer>> temp = Control.users.get(UserSelection.renderIndex).getWholeGameFCAPData(level);
            totalData.setString(temp.first+" Total");
            clearedData.setString(temp.second.first+" Cleared");
            fcData.setString(temp.second.second+" Full Combo");
            apData.setString(temp.second.third+" All Perfect");
            userName.render(g2d);
            totalData.render(g2d);
            clearedData.render(g2d);
            fcData.render(g2d);
            apData.render(g2d);
        }
    }
}
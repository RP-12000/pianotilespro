package org.kelvinizer.menu.collectionselection;

import org.kelvinizer.constants.Control;
import org.kelvinizer.menu.userselection.UserSelection;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.Pair;
import org.kelvinizer.support.classes.Triple;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

import static org.kelvinizer.constants.Selection.*;

public class CollectionSelectionText implements Drawable {
    public final BoundedString nullJacket = new BoundedString("No jacket preview available", 15, 540, 330, 360, 360);
    public final BoundedString selectionName = new BoundedString("", 50, 540, 128, 400, 80);
    private final BoundedString totalData = new BoundedString("", 20, 315, 580, 140, 50);
    private final BoundedString clearedData = new BoundedString("", 20, 465, 580, 140, 50);
    private final BoundedString fcData = new BoundedString("", 20, 615, 580, 140, 50);
    private final BoundedString apData = new BoundedString("", 20, 765, 580, 140, 50);

    private void initData(BoundedString bs, Color c){
        bs.setStringColor(c);
        bs.getBounds().setOutlineColor(c);
        bs.getBounds().setOutlineThickness(5.0);
        bs.setMaxStringSize(20);
    }

    public CollectionSelectionText(){
        selectionName.getBounds().setOutlineColor(Color.WHITE);
        selectionName.getBounds().setOutlineThickness(3.0);
        selectionName.setMaxStringSize(50);
        nullJacket.setStyle(Font.ITALIC);
        nullJacket.getBounds().setOutlineColor(Color.WHITE);
        nullJacket.getBounds().setOutlineThickness(1.0);
        initData(totalData, Color.WHITE);
        initData(clearedData, Color.YELLOW);
        initData(fcData, Color.BLUE);
        initData(apData, Color.GREEN);
    }

    @Override
    public void render(Graphics2D g2d){
        Pair<Integer, Triple<Integer, Integer, Integer>> temp = Control.users.get(UserSelection.renderIndex).getFCAPData(collectionDir, level);
        totalData.setString(temp.first+" Total");
        clearedData.setString(temp.second.first+" Cleared");
        fcData.setString(temp.second.second+" Full Combo");
        apData.setString(temp.second.third+" All Perfect");
        totalData.render(g2d);
        clearedData.render(g2d);
        fcData.render(g2d);
        apData.render(g2d);
    }
}

package org.kelvinizer.menu.menutext;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.FixedString;

import java.awt.*;

public class CollectionSelectionText {
    public FixedString emptyFolderVerdict = new FixedString("Nothing is in there QAQ", 50);
    public FixedString nullJacketVerdict = new FixedString("No jacket available", 15);

    private void setEmptyFolderVerdict(){
        emptyFolderVerdict.setBounds(new CRect(
                540, 360, 0, 0
        ));
    }

    private void setNullJacketVerdict(){
        nullJacketVerdict.setBounds(new CRect(
                540, 330, 0, 0
        ));
        nullJacketVerdict.setStyle(Font.ITALIC);
    }

    public CollectionSelectionText(){
        setEmptyFolderVerdict();
        setNullJacketVerdict();
    }
}

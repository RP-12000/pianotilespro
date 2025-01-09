package org.kelvinizer.menu.menuwindows;

import org.kelvinizer.animation.AnimatablePanel;
import org.kelvinizer.constants.Control;
import org.kelvinizer.constants.Selection;
import org.kelvinizer.game.gamewindow.Song;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.classes.JacketMenu;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LoadingPage extends AnimatablePanel {
    private final BoundedString loading = new BoundedString("Checking for corruption, please wait...", 40, 540, 360);
    private boolean isChecked = false;

    private void check(){
        JacketMenu songs = new JacketMenu(
                "Chart/"+ Selection.collectionDir,
                Selection.songIndex.get(Selection.collectionDir),
                20
        );
        ArrayList<Song> songData = new ArrayList<>();
        for(int i = 0; i< songs.size(); i++){
            try{
                songData.add(new Song("Chart/"+ Selection.collectionDir+"/"+ songs.getSelectionString(i)));
            } catch (RuntimeException | IOException e) {
                Selection.isValidCollection=false;
                isChecked=true;
                return;
            }
        }
        Selection.isValidCollection=true;
        isChecked=true;
    }

    public LoadingPage(){
        super(200, 200);
        check();
    }

    @Override
    public void render(Graphics2D g2d){
        loading.render(g2d);
        if(isChecked){
            exit();
        }
    }

    @Override
    public void toNextPanel(){
        Control.panel_index = 3;
    }
}

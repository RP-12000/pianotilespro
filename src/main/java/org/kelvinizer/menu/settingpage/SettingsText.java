package org.kelvinizer.menu.settingpage;

import org.kelvinizer.shapes.CRect;
import org.kelvinizer.support.classes.BoundedString;
import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static org.kelvinizer.constants.Control.*;
import static org.kelvinizer.constants.Selection.collections;
import static org.kelvinizer.constants.Selection.songData;

public class SettingsText implements Drawable {
    private final BoundedString header = new BoundedString("", 30, 540, 100);
    private final BoundedString firstVerdict = new BoundedString("", 25, 300, 200, 150, 60);
    private final BoundedString secondVerdict = new BoundedString("", 25, 300, 380, 150, 60);
    private final BoundedString thirdVerdict = new BoundedString("", 25, 300, 560, 150, 60);
    private final BoundedString userVerdict = new BoundedString("", 25, 540, 560, 800, 60);

    private final CRect firstBoundary = new CRect(540, 200, 720, 160);
    private final CRect secondBoundary = new CRect(540, 380, 720, 160);
    private final CRect thirdBoundary = new CRect(540, 560, 720, 160);
    private final CRect userBoundary = new CRect(540, 360, 720, 200);

    private long userVerdictAppearTime;

    public void setBoundAndVerdict(BoundedString verdict, CRect bound){
        verdict.getBounds().setOutlineColor(Color.WHITE);
        verdict.getBounds().setOutlineThickness(3.0);
        bound.setOutlineColor(Color.WHITE);
        bound.setOutlineThickness(5.0);
    }

    public SettingsText() {
        setBoundAndVerdict(firstVerdict, firstBoundary);
        setBoundAndVerdict(secondVerdict, secondBoundary);
        setBoundAndVerdict(thirdVerdict, thirdBoundary);
        setBoundAndVerdict(userVerdict, userBoundary);
        userVerdict.setMaxStringSize(25);
    }

    public void updateText(int pageNum){
        if(pageNum==1){
            firstVerdict.setString("Sync Hint");
            secondVerdict.setString("FC/AP Hint");
            thirdVerdict.setString("Hand Hint");
        }
        else{
            firstVerdict.setString("FPS");
            secondVerdict.setString("Music Delay");
            thirdVerdict.setString("Tolerance");
        }
    }

    private void throwError(String message){
        userVerdict.setStringColor(Color.RED);
        userVerdict.setString(message);
        userVerdict.getBounds().setOutlineColor(Color.RED);
        userVerdict.getBounds().setOutlineThickness(3.0);
        userVerdictAppearTime = System.nanoTime();
    }

    private void throwSuccess(String message){
        userVerdict.setStringColor(Color.GREEN);
        userVerdict.setString(message);
        userVerdict.getBounds().setOutlineColor(Color.GREEN);
        userVerdict.getBounds().setOutlineThickness(3.0);
        userVerdictAppearTime = System.nanoTime();
    }

    public void exportUser(){
        try{
            PrintWriter user = new PrintWriter(new FileWriter(userName+".user"));
            user.println(GAME_VERSION+" "+collections.size());
            printSettings(user);
            for(int i=0; i<collections.size(); i++){
                String cd = collections.getSelectionString(i);
                user.println(cd+" "+songData.get(cd).size());
                for(int j=0; j<songData.get(cd).size(); j++){
                    user.println(songData.get(cd).get(j).toUserDataString());
                }
            }
            user.close();
            throwSuccess("Successfully exported user data to "+userName+".user");
        } catch (IOException e) {
            throwError("Unable to export user data. Try again later");
        }
    }

    private void importUser(File f){
        BufferedReader br;
        String temp;
        int lineCount = 1;
        try{
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            temp = br.readLine();
            String[] list1 = temp.split(" ");
            if(!list1[0].equals(GAME_VERSION)){
                throwError("Unable to import user data because of this: data version incompatible with game version");
                throw new RuntimeException();
            }
            readSettings(br);
            for(int i=0; i<Integer.parseInt(list1[1]); i++){
                temp = br.readLine();
                String[] list2 = temp.split(" ");
                String collection = list2[0];
                for(int j=0; j<Integer.parseInt(list2[1]); j++){
                    temp = br.readLine();
                    String[] list3 = temp.split(" ");
                    try(PrintWriter pw = new PrintWriter(getResourcePathName("Chart/"+collection+"/"+list3[0]+"/user.txt"))){
                        for(int k=0; k<3; k++){
                            temp = br.readLine();
                            pw.println(temp);
                            lineCount++;
                        }
                        if(Boolean.parseBoolean(list3[1])){
                            temp = br.readLine();
                            pw.println(temp);
                            lineCount++;
                        }
                    }
                }
            }
        } catch (IOException | RuntimeException e) {
            throwError("Unable to import user data because of this: error when reading line No."+lineCount+" in data");
            throw new RuntimeException(e);
        }
    }

    public void importUser(){
        ArrayList<File> availableUsers = new ArrayList<>();
        try{
            File root = new File(".");
            File[] fs = root.listFiles();
            if(fs!=null){
                for(File f: fs){
                    if(f.getCanonicalPath().endsWith(".user")){
                        availableUsers.add(f);
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        if(availableUsers.size()>1){
            throwError("Unable to import user data because of this: More than one .user file detected under this folder");
            return;
        }
        if(availableUsers.isEmpty()){
            throwError("Unable to import user data because of this: No .user file detected under this folder");
            return;
        }
        exportUser();
        try{
            importUser(availableUsers.getFirst());
            String[] s = availableUsers.getFirst().getCanonicalPath().split("\\\\");
            userName = s[s.length-1].split("\\.")[0];
            PrintWriter newInfo = new PrintWriter(new FileWriter(getResourcePathName("Chart")+"/settings.txt"));
            printSettings(newInfo);
            newInfo.close();
            throwSuccess("Successfully imported user with username "+userName+". Restart game to enable changes or you will lose data");
        } catch (RuntimeException e) {
            importUser(new File(userName + ".user"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        switch (Settings.page){
            case 1 -> header.setString("Hints");
            case 2 -> header.setString("Time");
            case 3 -> header.setString("User Info");
        }
        header.render(g2d);
        if(Settings.page!=3){
            firstVerdict.render(g2d);
            secondVerdict.render(g2d);
            thirdVerdict.render(g2d);
            firstBoundary.render(g2d);
            secondBoundary.render(g2d);
            thirdBoundary.render(g2d);
        }
        else{
            userBoundary.render(g2d);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) Math.clamp((3-(System.nanoTime()-userVerdictAppearTime)/1e9), 0.0, 1.0)));
            userVerdict.render(g2d);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
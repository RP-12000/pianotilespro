package org.kelvinizer.animation;

import org.kelvinizer.Constants;
import org.kelvinizer.Constants.*;
import org.kelvinizer.support.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AnimatablePanel extends JPanel implements Animatable, MouseMotionListener, MouseWheelListener, MouseListener{
    private boolean is_start = true, is_end = false;
    private boolean has_start = false, has_end = false;
    protected long start_time, end_time;
    protected final long start_duration, end_duration;
    private final HashMap<Pair, Action> bindings = new HashMap<>();

    public AnimatablePanel(long start_duration_in_ms, long end_duration_in_ms){
        start_duration=start_duration_in_ms*Time.MS_TO_NS_CONVERSION_FACTOR;
        end_duration=end_duration_in_ms*Time.MS_TO_NS_CONVERSION_FACTOR;
        setSize(ReferenceWindow.REF_WIN_W, ReferenceWindow.REF_WIN_H);
    }

    public AnimatablePanel(){
        this(Time.INTRO_TIME_IN_MS, Time.EXIT_TIME_IN_MS);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.scale((double)getWidth()/ReferenceWindow.REF_WIN_W, (double)getHeight()/ReferenceWindow.REF_WIN_H);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, Constants.ReferenceWindow.REF_WIN_W,Constants.ReferenceWindow.REF_WIN_H);
        if(is_start){
            if(!has_start){
                has_start=true;
                start_time=System.nanoTime();
            }
            if(System.nanoTime()-start_time<=start_duration){
                onAppearance(g2d);
            }
            else{
                is_start=false;
                addListeners();
            }
        }
        else if(is_end){
            if(!has_end){
                has_end=true;
                end_time=System.nanoTime();
            }
            if(System.nanoTime()-end_time<=end_duration){
                onDisappearance(g2d);
            }
            else{
                is_start=true;
                is_end=false;
                has_start=false;
                has_end=false;
                toNextPanel();
            }
        }
        else{
            onActive(g2d);
        }
    }

    private void addListeners() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        if(!bindings.isEmpty()){
            for(Map.Entry<Pair, Action> entry: bindings.entrySet()){
                getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                        KeyStroke.getKeyStroke(
                                (int)entry.getKey().first,
                                (int)entry.getKey().second
                        ),
                        entry.getKey()
                );
                getActionMap().put(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void exit(){
        is_end=true;
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeMouseWheelListener(this);
        if(!bindings.isEmpty()){
            for(Map.Entry<Pair, Action> entry: bindings.entrySet()){
                getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(
                        KeyStroke.getKeyStroke(
                                (int)entry.getKey().first,
                                (int)entry.getKey().second
                        )
                );
                getActionMap().remove(entry.getKey());
            }
        }
    }

    protected void setAppearingOpacity(Graphics2D g2d){
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                Math.min(1.0f, 1.0f*(System.nanoTime()-start_time)/start_duration)
        ));
    }

    protected void setDisappearingOpacity(Graphics2D g2d){
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                Math.max(0.0f, 1.0f-1.0f*(System.nanoTime()-end_time)/end_duration)
        ));
    }

    protected void renderObjects(Graphics2D g2d) {}

    protected void addKeyBinding(Integer VK_Code, Integer VK_State, Action a){
        bindings.put(new Pair(VK_Code, VK_State), a);
    }

    protected void addKeyBinding(Integer VK_Code, Action a){
        addKeyBinding(VK_Code, 0, a);
    }

    @Override
    public void onAppearance(Graphics2D g2d) {}

    @Override
    public void onActive(Graphics2D g2d) {}

    @Override
    public void onDisappearance(Graphics2D g2d) {}

    @Override
    public void toNextPanel() {}

    @Override
    public void resizeButtons(Dimension d) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {}

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
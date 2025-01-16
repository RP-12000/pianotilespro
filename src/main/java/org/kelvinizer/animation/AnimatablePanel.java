package org.kelvinizer.animation;

import org.kelvinizer.constants.ReferenceWindow;
import org.kelvinizer.support.classes.Triple;
import org.kelvinizer.support.interfaces.Drawable;
import org.kelvinizer.support.interfaces.Scalable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.kelvinizer.constants.Control.FPS;

public class AnimatablePanel extends JPanel implements Animatable, MouseMotionListener, MouseWheelListener, MouseListener, Scalable, Drawable {
    private boolean is_start = true, is_end = false;
    private boolean has_start = false, has_end = false;
    protected long start_time, end_time;
    protected final long start_duration;
    protected long end_duration;
    private final HashMap<Triple<Integer, Boolean, Integer>, Action> bindings = new HashMap<>();

    public AnimatablePanel(long start_duration_in_ms){
        start_duration = (long) (start_duration_in_ms*1e6);
        setSize((int) ReferenceWindow.REF_WIN_W, (int) ReferenceWindow.REF_WIN_H);
        ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(this::repaint, 0, 1000/FPS, TimeUnit.MILLISECONDS);
    }

    public AnimatablePanel(){
        this(500);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.scale((double)getWidth()/ReferenceWindow.REF_WIN_W, (double)getHeight()/ReferenceWindow.REF_WIN_H);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, (int) ReferenceWindow.REF_WIN_W, (int) ReferenceWindow.REF_WIN_H);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1.0f));
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
            render(g2d);
        }
    }

    private void addListeners() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        if(!bindings.isEmpty()){
            for(Map.Entry<Triple<Integer, Boolean, Integer>, Action> entry: bindings.entrySet()){
                try{
                    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                            KeyStroke.getKeyStroke(
                                    entry.getKey().first,
                                    entry.getKey().third,
                                    entry.getKey().second
                            ),
                            entry.getKey()
                    );
                }catch (IllegalStateException e){
                    throw new RuntimeException(e);
                }
                getActionMap().put(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void exit(long end_duration_in_ms){
        end_duration=(long) (end_duration_in_ms*1e6);
        is_end=true;
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeMouseWheelListener(this);
        if(!bindings.isEmpty()){
            for(Map.Entry<Triple<Integer, Boolean, Integer>, Action> entry: bindings.entrySet()){
                try{
                    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(
                            KeyStroke.getKeyStroke(
                                    entry.getKey().first,
                                    entry.getKey().third,
                                    entry.getKey().second
                            )
                    );
                }catch (IllegalStateException e){
                    throw new RuntimeException(e);
                }
                getActionMap().remove(entry.getKey());
            }
        }
    }

    protected void exit(){
        exit(500);
    }

    protected void setAppearingOpacity(Graphics2D g2d){
        setGlobalOpacity(g2d, 1.0*(System.nanoTime()-start_time)/start_duration);
    }

    protected void setDisappearingOpacity(Graphics2D g2d){
        setGlobalOpacity(g2d, 1.0-1.0*(System.nanoTime()-end_time)/end_duration);
    }

    protected void setGlobalOpacity(Graphics2D g2d, double ratio){
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.clamp((float) ratio, 0.0f, 1.0f)));
    }

    @Override
    public void render(Graphics2D g2d) {}

    public void addKeyBinding(int VK_Code, boolean onRelease, Action a){
        addKeyBinding(VK_Code, onRelease, 0, a);
    }

    public void addKeyBinding(int VK_Code, boolean onRelease, int VK_State, Action a){
        bindings.put(new Triple<>(VK_Code, onRelease, VK_State), a);
    }

    public void addKeyBinding(int VK_Code, Action a){
        addKeyBinding(VK_Code, true, a);
    }

    @Override
    public void onAppearance(Graphics2D g2d) {
        setAppearingOpacity(g2d);
        render(g2d);
    }

    @Override
    public void onDisappearance(Graphics2D g2d) {
        setDisappearingOpacity(g2d);
        render(g2d);
    }

    protected void toNextPanel() {}

    @Override
    public void scale(Dimension d) {}

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
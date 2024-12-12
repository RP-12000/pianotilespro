package org.kelvinizer;

import java.awt.*;

public interface Animatable {
    public void callOnAppearance(Graphics2D g2d);
    public void callOnActive(Graphics2D g2d);
    public void callOnDisappearance(Graphics2D g2d);
}
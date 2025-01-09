package org.kelvinizer.animation;

import java.awt.*;

public interface Animatable {
    void onAppearance(Graphics2D g2d);
    void onActive(Graphics2D g2d);
    void onDisappearance(Graphics2D g2d);
}
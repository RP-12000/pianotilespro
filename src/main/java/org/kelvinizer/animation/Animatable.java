package org.kelvinizer.animation;

import org.kelvinizer.support.interfaces.Drawable;

import java.awt.*;

public interface Animatable extends Drawable {
    void onAppearance(Graphics2D g2d);
    void onDisappearance(Graphics2D g2d);
}
package org.kelvinizer.animation;

import org.kelvinizer.support.interfaces.Scalable;

import java.awt.*;

public interface Animatable extends Scalable {
    void onAppearance(Graphics2D g2d);
    void onActive(Graphics2D g2d);
    void onDisappearance(Graphics2D g2d);
    void toNextPanel();
}
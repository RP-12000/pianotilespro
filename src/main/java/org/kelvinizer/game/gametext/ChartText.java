package org.kelvinizer.game.gametext;

import org.kelvinizer.support.classes.BoundedString;

public class ChartText {
    public final BoundedString score = new BoundedString("0000000", 15, 1000, 100);
    public final BoundedString songName = new BoundedString("", 15, 150, 600);
    public final BoundedString level = new BoundedString("", 15, 1000, 600);
    public final BoundedString combo = new BoundedString("", 15, 150, 100);
}

package org.kelvinizer.support.classes;

import org.kelvinizer.constants.Time;

public class Motion {
    private final double start, end;
    private final double a, b, c;

    public Motion(double s, double e, double sp, double ep, double acc){
        start = s;
        end = e;
        a = acc/2;
        b = -a*(e-s)+(ep-sp)/(e-s);
        c = sp;
    }

    public Motion(String s){
        this(
                Double.parseDouble(s.split(" ")[0]),
                Double.parseDouble(s.split(" ")[1]),
                Double.parseDouble(s.split(" ")[2]),
                Double.parseDouble(s.split(" ")[3]),
                Double.parseDouble(s.split(" ")[4])
        );
    }

    public boolean contains(double time){
        return start<time && time<end;
    }

    public double getPos(double time){
        double t = time - start;
        return a*t*t+b*t+c;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }
}

package org.kelvinizer.support.classes;

public class Motion {
    private final double start, end;
    private final double exp, startPoint, constant;

    public Motion(double s, double e, double sp, double ep, double exp){
        start = s;
        end = e;
        this.exp = exp;
        constant = (ep-sp)/Math.pow(e-s, exp);
        startPoint = sp;
    }

    public Motion(double s, double e, double sp, double ep){
        this(s, e, sp, ep, 1);
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
        return constant*Math.pow(t, exp)+startPoint;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }
}

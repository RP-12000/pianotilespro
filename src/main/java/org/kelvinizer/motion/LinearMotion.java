package org.kelvinizer.motion;

public class LinearMotion extends Motion{
    private final double k, b;

    public LinearMotion(double s, double e, double k, double b){
        super(s, e);
        this.k = k;
        this.b = b;
    }

    public static LinearMotion parseLinearMotion(String s){
        String[] temp = s.split(" ");
        return new LinearMotion(
                Double.parseDouble(temp[1]),
                Double.parseDouble(temp[2]),
                Double.parseDouble(temp[3]),
                Double.parseDouble(temp[4])
        );
    }

    @Override
    public double dist(double time){
        return k*(time-super.getStart())+b;
    }
}
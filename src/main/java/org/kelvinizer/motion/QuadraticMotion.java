package org.kelvinizer.motion;

public class QuadraticMotion extends Motion{
    private final double a, b, c;

    public QuadraticMotion(double s, double e, double a, double b, double c){
        super(s,e);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static QuadraticMotion parseQuadraticMotion(String s){
        String[] temp = s.split(" ");
        return new QuadraticMotion(
                Double.parseDouble(temp[1]),
                Double.parseDouble(temp[2]),
                Double.parseDouble(temp[3]),
                Double.parseDouble(temp[4]),
                Double.parseDouble(temp[5])
        );
    }

    @Override
    public double dist(double time) {
        return a*(time-super.getStart())*(time-super.getStart())+b*(time-super.getStart())+c;
    }
}
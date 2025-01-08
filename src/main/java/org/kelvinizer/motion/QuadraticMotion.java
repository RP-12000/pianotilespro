package org.kelvinizer.motion;

public class QuadraticMotion extends Motion{
    private final double a;
    private double b;
    private double c;

    public QuadraticMotion(String s){
        super(s);
        String[] temp = s.split(" ");
        a = Double.parseDouble(temp[4])/2;
    }

    @Override
    public void initParams(double motionEndTime) {
        b = -a*(motionEndTime-super.getStartTime())+(super.getEndPoint()-super.getStartPoint())/(motionEndTime-super.getStartTime());
        c = super.getStartPoint();
    }

    @Override
    public double dist(double time) {
        return a*time*time+b*time+c;
    }
}

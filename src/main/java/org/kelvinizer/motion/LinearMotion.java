package org.kelvinizer.motion;

public class LinearMotion extends Motion{
    private double k, b;

    public LinearMotion(String s) throws IllegalArgumentException{
        super(s);
    }

    @Override
    public void initParams(double motionEndTime) {
        k = (super.getEndPoint()-super.getStartPoint())/(motionEndTime-super.getStartPoint());
        b = super.getStartPoint();
    }

    @Override
    public double dist(double time) {
        return k*time+b;
    }
}

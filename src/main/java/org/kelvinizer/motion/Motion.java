package org.kelvinizer.motion;

public abstract class Motion{
    private final double startTime;
    private final double startPoint, endPoint;

    public Motion(String s){
        String[] t = s.split(" ");
        startTime = Double.parseDouble(t[1]);
        startPoint = Double.parseDouble(t[2]);
        endPoint = Double.parseDouble(t[3]);
    }

    public static Motion parseMotion(String s){
        String[] t = s.split(" ");
        return switch (t[0]) {
            case "0" -> new LinearMotion(s);
            case "1" -> new QuadraticMotion(s);
            default -> throw new IllegalArgumentException("Invalid motion type detected");
        };
    }

    public double getStartPoint(){
        return startPoint;
    }

    public double getEndPoint(){
        return endPoint;
    }

    public double getStartTime() {
        return startTime;
    }

    public abstract void initParams(double motionEndTime);
    public abstract double dist(double time);
}
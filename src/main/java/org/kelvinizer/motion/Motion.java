package org.kelvinizer.motion;

public class Motion{
    private final double startTime;
    private final double startPoint, endPoint;
    private final int type;

    public Motion(String s){
        String[] t = s.split(" ");
        startTime = Double.parseDouble(t[0]);
        type = Integer.parseInt(t[1]);
        startPoint = Double.parseDouble(t[2]);
        endPoint = Double.parseDouble(t[3]);
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

    public int getType() {
        return type;
    }
}
package org.kelvinizer.motion;

public abstract class Motion {
    private final double start, end;

    public Motion(double s, double e){
        start = s;
        end = e;
    }

    public double getStart(){
        return start;
    }

    public double getEnd(){
        return end;
    }

    public static Motion parseMotion(String s){
        String[] temp = s.split(" ");
        return switch (Integer.parseInt(temp[0])) {
            case 0 -> LinearMotion.parseLinearMotion(s);
            case 1 -> QuadraticMotion.parseQuadraticMotion(s);
            default -> throw new IllegalArgumentException("Invalid motion");
        };
    }
    public abstract double dist(double time);
}

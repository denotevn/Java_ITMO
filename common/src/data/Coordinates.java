package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x;
    private Double y;

    public Coordinates (int x, Double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

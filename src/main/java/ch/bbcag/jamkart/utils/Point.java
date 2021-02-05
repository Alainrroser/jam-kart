package ch.bbcag.jamkart.utils;

public class Point {

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Direction getDirectionTo(Point other) {
        return new Direction(other.x - x, other.y - y);
    }

}

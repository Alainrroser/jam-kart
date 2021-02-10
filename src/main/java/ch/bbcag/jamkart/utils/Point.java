package ch.bbcag.jamkart.utils;

public class Point {

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
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

    public void moveInDirection(Direction direction) {
        this.x += direction.getX();
        this.y += direction.getY();
    }

}

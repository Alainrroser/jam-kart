package ch.bbcag.jamkart.utils;

public class Direction {

    private float x;
    private float y;

    public Direction() {

    }

    public Direction(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLength() {
        return MathUtils.sqrt(MathUtils.sqr(x) + MathUtils.sqr(y));
    }

    public Direction add(Direction other) {
        return new Direction(x + other.x, y + other.y);
    }

    public Direction scale(float scale) {
        return new Direction(x * scale, y * scale);
    }

    public Direction normalized() {
        float length = getLength();
        return new Direction(x / length, y / length);
    }

    public void setFromAngleAndLength(float angleInDegrees, float length) {
        x = MathUtils.sin(angleInDegrees) * length;
        y = -MathUtils.cos(angleInDegrees) * length;
    }

}

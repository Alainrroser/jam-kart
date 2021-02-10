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

    public Direction(Direction other) {
        this.x = other.x;
        this.y = other.y;
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

    public void scale(float scale) {
        this.x *= scale;
        this.y *= scale;
    }

    public void normalize() {
        float length = getLength();
        x /= length;
        y /= length;
    }

    public void setFromAngleAndLength(float angleInDegrees, float length) {
        x = MathUtils.sin(angleInDegrees) * length;
        y = -MathUtils.cos(angleInDegrees) * length;
    }

}

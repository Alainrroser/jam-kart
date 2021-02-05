package ch.bbcag.jamkart.utils;

public class MathUtils {

    public static float sin(float angleInDegrees) {
        return (float) Math.sin(Math.toRadians(angleInDegrees));
    }

    public static float cos(float angleInDegrees) {
        return (float) Math.cos(Math.toRadians(angleInDegrees));
    }

    public static float atan2(float y, float x) {
        return (float) Math.toRadians(Math.atan2(y, x));
    }

    public static float sqrt(float number) {
        return (float) Math.sqrt(number);
    }

    public static float sqr(float number) {
        return number * number;
    }

}

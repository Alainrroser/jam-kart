package ch.bbcag.jamkart.utils;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

    public static double getTextWidth(String text, Font font){
        Text textBox = new Text(text);
        textBox.setFont(font);
        return textBox.getLayoutBounds().getWidth();
    }
}

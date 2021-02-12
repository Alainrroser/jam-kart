package ch.bbcag.jamkart.utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DrawingUtils {

    public static void drawRotated(
            GraphicsContext context, Image image, Point position, float width, float height, float rotation) {
        context.save();

        context.translate(position.getX() + width / 2, position.getY() + height / 2);
        context.rotate(rotation);
        context.translate(-(position.getX() + width / 2), -(position.getY() + height / 2));

        context.drawImage(image, position.getX(), position.getY(), width, height);

        context.restore();
    }
}

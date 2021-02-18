package ch.bbcag.jamkart.utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DrawingUtils {

    public static void drawRotated(
            GraphicsContext context, Image image, Point position, float width, float height, float rotation) {
        // Save the context state to restore it when we're done
        // This should prevent other objects from being rotated around this image
        context.save();

        // Rotate the image around the center of the image
        context.translate(position.getX() + width / 2, position.getY() + height / 2);
        context.rotate(rotation);
        context.translate(-(position.getX() + width / 2), -(position.getY() + height / 2));

        context.drawImage(image, position.getX(), position.getY(), width, height);

        context.restore();
    }
}

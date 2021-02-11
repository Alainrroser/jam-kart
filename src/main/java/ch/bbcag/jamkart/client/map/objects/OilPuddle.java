package ch.bbcag.jamkart.client.map.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OilPuddle extends GameObject {

    public static final float SIZE = 100.0f;

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillOval(getPosition().getX(), getPosition().getY(), SIZE, SIZE);
    }

    @Override
    public void update(float deltaTimeInSeconds) {

    }
}

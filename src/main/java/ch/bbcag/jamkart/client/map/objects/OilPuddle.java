package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OilPuddle extends GameObject {

    public static final float SIZE = 100.0f;

    public OilPuddle(Point position) {
        setPosition(position);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillOval(getPosition().getX(), getPosition().getY(), SIZE, SIZE);
    }

    @Override
    public void update(float deltaTimeInSec) {

    }
}

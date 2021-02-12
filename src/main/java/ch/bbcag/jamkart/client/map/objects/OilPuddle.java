package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OilPuddle extends GameObject {

    public OilPuddle(Point position) {
        setPosition(position);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillOval(getPosition().getX(), getPosition().getY(), Constants.SIZE, Constants.SIZE);
    }

    @Override
    public void update(float deltaTimeInSeconds) {

    }
}

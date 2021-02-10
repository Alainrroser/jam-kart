package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.Direction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Car extends GameObject {

    private static final Image IMAGE = new Image(Car.class.getResourceAsStream("/car_red.png"));

    private float rotation = 0.0f;

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.RED);
        context.translate(getPosition().getX() + 50, getPosition().getY() + 50);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + 50), -(getPosition().getY() + 50));

        context.drawImage(IMAGE, getPosition().getX(), getPosition().getY(), 100, 100);
    }

    @Override
    public void update(float deltaTimeInSec) {
        rotation += 180.0f * deltaTimeInSec;

        Direction direction = new Direction();
        direction.setFromAngleAndLength(rotation, 400.0f * deltaTimeInSec);
        getPosition().moveInDirection(direction);
    }

}
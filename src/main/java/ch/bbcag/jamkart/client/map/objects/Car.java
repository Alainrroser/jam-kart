package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.client.KeyEventHandler;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.utils.Direction;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Car extends GameObject {

    private static final Image IMAGE = new Image(Car.class.getResourceAsStream("/car_red.png"));
    private static final float SIZE = 100.0f;

    private Map map;
    private KeyEventHandler keyEventHandler;

    private float rotation = 0.0f;
    private Direction velocity = new Direction();

    private static final float ROTATION_SPEED = 180.0f;
    private static final float ENGINE_POWER_FORWARD = 40.0f;
    private static final float ENGINE_POWER_BACKWARD = -20.0f;
    private static final float STANDARD_DAMPING = 0.05f;
    private static final float BRAKING_DAMPING = 0.2f;

    public Car(Map map, KeyEventHandler keyEventHandler) {
        this.map = map;
        this.keyEventHandler = keyEventHandler;
    }

    public Point getCenter() {
        return new Point(getPosition().getX() + SIZE / 2, getPosition().getY() + SIZE / 2);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.RED);
        context.fillOval(getCenter().getX() - 5, getCenter().getY() - 5, 10, 10);

        context.translate(getPosition().getX() + SIZE / 2, getPosition().getY() + SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + SIZE / 2), -(getPosition().getY() + SIZE / 2));

        context.drawImage(IMAGE, getPosition().getX(), getPosition().getY(), SIZE, SIZE);
    }

    @Override
    public void update(float deltaTimeInSec) {
        if(keyEventHandler.isRightPressed()) {
            rotation += ROTATION_SPEED * deltaTimeInSec;
        }

        if(keyEventHandler.isLeftPressed()) {
            rotation -= ROTATION_SPEED * deltaTimeInSec;
        }

        float speed = 0.0f;
        if(keyEventHandler.isForwardPressed()) {
            speed = ENGINE_POWER_FORWARD;
        } else if(keyEventHandler.isBackwardPressed()) {
            speed = -ENGINE_POWER_BACKWARD;
        }

        Direction direction = new Direction();
        direction.setFromAngleAndLength(rotation, speed);

        velocity = velocity.add(direction);

        float damping = keyEventHandler.isSpacePressed() ? BRAKING_DAMPING : STANDARD_DAMPING;
        if(!map.getRoad().isInside(getCenter())) {
            damping *= 4.0f;
        }

        velocity = velocity.scale(1.0f - damping);

        Direction movement = velocity.scale(deltaTimeInSec);
        getPosition().moveInDirection(movement);
    }

}
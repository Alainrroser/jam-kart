package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.client.KeyEventHandler;
import ch.bbcag.jamkart.utils.Direction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Car extends GameObject {

    private static final Image IMAGE = new Image(Car.class.getResourceAsStream("/car_red.png"));

    private KeyEventHandler keyEventHandler;

    private float rotation = 0.0f;
    private Direction velocity = new Direction();

    private static final float ROTATION_SPEED = 180.0f;
    private static final float ENGINE_POWER_FORWARD = 40.0f;
    private static final float ENGINE_POWER_BACKWARD = -20.0f;
    private static final float STANDARD_DAMPING = 0.05f;
    private static final float BRAKING_DAMPING = 0.2f;

    public Car(KeyEventHandler keyEventHandler) {
        this.keyEventHandler = keyEventHandler;
    }

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
        velocity = velocity.scale(1.0f - damping);

        Direction movement = velocity.scale(deltaTimeInSec);
        getPosition().moveInDirection(movement);
    }

}
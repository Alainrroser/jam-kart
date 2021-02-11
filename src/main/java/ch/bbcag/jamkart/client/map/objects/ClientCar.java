package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.client.KeyEventHandler;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.Direction;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClientCar extends GameObject {
    public static final float SIZE = 100.0f;

    private int id;
    private Image image;
    private Map map;
    private String name;
    private KeyEventHandler keyEventHandler;
    private float timer = 0;
    private ClientGame clientGame;
    private float rotation = 0.0f;
    private Direction velocity = new Direction();

    private static final float ROTATION_SPEED = 180.0f;
    private static final float ENGINE_POWER_FORWARD = 50.0f;
    private static final float ENGINE_POWER_BACKWARD = 25.0f;
    private static final float STANDARD_DAMPING = 0.05f;
    private static final float BRAKING_DAMPING = 0.2f;

    public ClientCar(Map map, KeyEventHandler keyEventHandler, ClientGame clientGame) {
        this.map = map;
        this.keyEventHandler = keyEventHandler;
        this.clientGame = clientGame;

    }

    public Point getCenter() {
        return new Point(getPosition().getX() + SIZE / 2, getPosition().getY() + SIZE / 2);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.save();

        context.translate(getPosition().getX() + SIZE / 2, getPosition().getY() + SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + SIZE / 2), -(getPosition().getY() + SIZE / 2));

        context.drawImage(image, getPosition().getX(), getPosition().getY(), SIZE, SIZE);

        context.restore();
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void update(float deltaTimeInSec) {
        updateRotation(deltaTimeInSec);
        updateMovement(deltaTimeInSec);

        timer += deltaTimeInSec;
        if (timer >= 0.1) {
            Message message = new Message(MessageType.UPDATE);
            float rotation = getRotation();
            float x = getPosition().getX();
            float y = getPosition().getY();
            message.addValue("x", x);
            message.addValue("y", y);
            message.addValue("rotation", rotation);
            Client client = clientGame.getClient();
            client.sendMessage(message);
            timer = 0;
        }
    }

    private void updateRotation(float deltaTimeInSec) {
        if(keyEventHandler.isRightPressed()) {
            rotation += ROTATION_SPEED * deltaTimeInSec;
        }

        if(keyEventHandler.isLeftPressed()) {
            rotation -= ROTATION_SPEED * deltaTimeInSec;
        }
    }

    private void updateMovement(float deltaTimeInSec) {
        float speed = getSpeed();
        Direction direction = new Direction();
        direction.setFromAngleAndLength(rotation, speed);
        velocity = velocity.add(direction);

        applyDamping();

        Direction movement = velocity.scale(deltaTimeInSec);
        getPosition().moveInDirection(movement);
    }

    private float getSpeed() {
        if(keyEventHandler.isForwardPressed()) {
            return ENGINE_POWER_FORWARD;
        } else if(keyEventHandler.isBackwardPressed()) {
            return -ENGINE_POWER_BACKWARD;
        }

        return 0.0f;
    }

    private void applyDamping() {
        float damping = keyEventHandler.isSpacePressed() ? BRAKING_DAMPING : STANDARD_DAMPING;
        if(!map.getRoad().isInside(getCenter())) {
            damping *= 4.0f;
        }

        velocity = velocity.scale(1.0f - damping);
    }

    public void setId(int id) {
        this.id = id;
        image = new Image(getClass().getResourceAsStream("/car_" + id + ".png"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
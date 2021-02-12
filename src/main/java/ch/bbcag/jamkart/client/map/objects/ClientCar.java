package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.client.KeyEventHandler;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.utils.Direction;
import ch.bbcag.jamkart.utils.MathUtils;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClientCar extends GameObject {

    private Map map;
    private KeyEventHandler keyEventHandler;
    private ClientGame clientGame;

    private Image image;
    private String name;
    private float rotation = 90.0f;
    private Direction velocity = new Direction();

    private float timer = 0.0f;
    private float oilTimer = 0.0f;

    private boolean isControllable = false;

    private static final float ROTATION_SPEED = 180.0f;
    private static final float ROTATION_SPEED_OILED = 1440.0f;

    private static final float SPEED_FORWARD = 50.0f;
    private static final float SPEED_BACKWARD = 25.0f;
    private static final float SPEED_OILED = 1200.0f;
    private static final float SPEED_BOOST_PAD = 3000.0f;

    private static final float DAMPING_STANDARD = 0.05f;
    private static final float DAMPING_BRAKING = 0.2f;
    private static final float DAMPING_FACTOR_GRASS = 3.0f;

    private static final float OIL_TIME = 1.0f;
    private static final float OIL_COLLISION_DISTANCE = Constants.SIZE;

    private static final float BOOST_PAD_COLLISION_DISTANCE = Constants.SIZE / 2 * 0.5f;

    public ClientCar(Map map, KeyEventHandler keyEventHandler, ClientGame clientGame) {
        this.map = map;
        this.keyEventHandler = keyEventHandler;
        this.clientGame = clientGame;
    }

    public Point getCenter() {
        return new Point(getPosition().getX() + Constants.SIZE / 2, getPosition().getY() + Constants.SIZE / 2);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.save();

        context.translate(getPosition().getX() + Constants.SIZE / 2, getPosition().getY() + Constants.SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + Constants.SIZE / 2), -(getPosition().getY() + Constants.SIZE / 2));

        context.drawImage(image, getPosition().getX(), getPosition().getY(), Constants.SIZE, Constants.SIZE);

        context.restore();
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void update(float deltaTimeInSec) {
        if(isControllable) {
            updateRotation(deltaTimeInSec);
            updateMovement(deltaTimeInSec);

            if(oilTimer > 0.0f) {
                oilTimer -= deltaTimeInSec;
            }

            checkCollisions();
        }

        timer += deltaTimeInSec;
        if (timer >= Constants.NETWORK_TICK_TIME) {
            sendMyState();
        }
    }

    private void updateRotation(float deltaTimeInSec) {
        if(oilTimer <= 0.0f) {
            if(keyEventHandler.isRightPressed()) {
                rotation += ROTATION_SPEED * deltaTimeInSec;
            }

            if(keyEventHandler.isLeftPressed()) {
                rotation -= ROTATION_SPEED * deltaTimeInSec;
            }
        } else {
            rotation += ROTATION_SPEED_OILED * (oilTimer / OIL_TIME) * deltaTimeInSec;
        }
    }

    private void updateMovement(float deltaTimeInSec) {
        if(oilTimer <= 0.0f) {
            float speed = getSpeed();
            Direction direction = new Direction();
            direction.setFromAngleAndLength(rotation, speed);
            velocity = velocity.add(direction);

            applyDamping();
        } else {
            velocity = velocity.normalized().scale(SPEED_OILED * (oilTimer / OIL_TIME));
        }

        Direction movement = velocity.scale(deltaTimeInSec);
        getPosition().moveInDirection(movement);
    }

    private float getSpeed() {
        if(keyEventHandler.isForwardPressed()) {
            return SPEED_FORWARD;
        } else if(keyEventHandler.isBackwardPressed()) {
            return -SPEED_BACKWARD;
        }

        return 0.0f;
    }

    private void applyDamping() {
        float damping = DAMPING_STANDARD;

        if(keyEventHandler.isSpacePressed()) {
            damping = DAMPING_BRAKING;
        }

        if(!map.getRoad().isInside(getCenter())) {
            damping *= DAMPING_FACTOR_GRASS;
        }

        velocity = velocity.scale(1.0f - damping);
    }

    private void checkCollisions() {
        for(GameObject object : map.getGameObjects()) {
            float distanceX = object.getPosition().getX() - getPosition().getX();
            float distanceY = object.getPosition().getY() - getPosition().getY();
            float distance = MathUtils.sqrt(distanceX * distanceX + distanceY * distanceY);

            if(object instanceof OilPuddle) {
                if(oilTimer <= 0.0f) {
                    if(distance < OIL_COLLISION_DISTANCE) {
                        oilTimer = OIL_TIME;
                    }
                }
            } else if(object instanceof BoostPad) {
                if(distance < BOOST_PAD_COLLISION_DISTANCE && velocity.getLength() < SPEED_BOOST_PAD) {
                    float angle = ((BoostPad) object).getRotation();
                    velocity.setFromAngleAndLength(angle, SPEED_BOOST_PAD);
                    rotation = angle;
                }
            }
        }
    }

    private void sendMyState() {
        Message message = new Message(MessageType.UPDATE);
        message.addValue("x", getPosition().getX());
        message.addValue("y", getPosition().getY());
        message.addValue("rotation", getRotation());

        clientGame.getClient().sendMessage(message);
    }

    public void setId(int id) {
        image = new Image(getClass().getResourceAsStream("/car_" + id + ".png"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setControllable(boolean isControllable) {
        this.isControllable = isControllable;
    }

    public Image getImage() {
        return image;
    }
}
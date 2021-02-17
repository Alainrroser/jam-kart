package ch.bbcag.jamkart.client.map.objects.car;

import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.client.KeyEventHandler;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.client.map.objects.BoostPad;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.client.map.objects.OilPuddle;
import ch.bbcag.jamkart.utils.Direction;
import ch.bbcag.jamkart.utils.MathUtils;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;

public class ClientMyCar extends ClientCar {

    private Map map;
    private KeyEventHandler keyEventHandler;
    private ClientGame clientGame;

    private Direction velocity = new Direction();

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
    private static final float OIL_COLLISION_DISTANCE = OilPuddle.SIZE;

    private static final float BOOST_PAD_COLLISION_DISTANCE = BoostPad.SIZE;

    public ClientMyCar(Map map, KeyEventHandler keyEventHandler, ClientGame clientGame) {
        this.map = map;
        this.keyEventHandler = keyEventHandler;
        this.clientGame = clientGame;

        setRotation(90.0f);
    }

    public Point getCenter() {
        return new Point(getPosition().getX() + SIZE / 2, getPosition().getY() + SIZE / 2);
    }

    @Override
    public void draw(GraphicsContext context) {
        drawCarImage(context);
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
    }

    private void updateRotation(float deltaTimeInSec) {
        if(oilTimer <= 0.0f) {
            if(keyEventHandler.isRightPressed()) {
                rotate(ROTATION_SPEED * deltaTimeInSec);
            }

            if(keyEventHandler.isLeftPressed()) {
                rotate(-ROTATION_SPEED * deltaTimeInSec);
            }
        } else {
            rotate(ROTATION_SPEED_OILED * (oilTimer / OIL_TIME) * deltaTimeInSec);
        }
    }

    private void updateMovement(float deltaTimeInSec) {
        if(oilTimer <= 0.0f) {
            float speed = getSpeed();
            Direction direction = new Direction();
            direction.setFromAngleAndLength(getRotation(), speed);
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
                    setRotation(angle);
                }
            }
        }
    }

    public void setControllable(boolean isControllable) {
        this.isControllable = isControllable;
    }

}
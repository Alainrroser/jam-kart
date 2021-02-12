package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoostPad extends GameObject {

    private static final Image IMAGE = new Image(BoostPad.class.getResourceAsStream("/boost_pad.png"));

    private float rotation;

    public BoostPad(Point position, float rotation) {
        setPosition(position);
        this.rotation = rotation;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.save();

        context.translate(getPosition().getX() + Constants.SIZE / 2, getPosition().getY() + Constants.SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + Constants.SIZE / 2), -(getPosition().getY() + Constants.SIZE / 2));

        context.drawImage(IMAGE, getPosition().getX(), getPosition().getY(), Constants.SIZE, Constants.SIZE);

        context.restore();
    }

    @Override
    public void update(float deltaTimeInSeconds) {

    }

    public float getRotation() {
        return rotation;
    }

}

package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BoostPad extends GameObject {

    public static final float SIZE = 100.0f;
    private static final Image IMAGE = new Image(BoostPad.class.getResourceAsStream("/boost_pad.png"));

    private float rotation;

    public BoostPad(Point position, float rotation) {
        setPosition(position);
        this.rotation = rotation;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.save();

        context.translate(getPosition().getX() + SIZE / 2, getPosition().getY() + SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + SIZE / 2), -(getPosition().getY() + SIZE / 2));

        context.drawImage(IMAGE, getPosition().getX(), getPosition().getY(), SIZE, SIZE);

        context.restore();
    }

    @Override
    public void update(float deltaTimeInSeconds) {

    }

    public float getRotation() {
        return rotation;
    }

}

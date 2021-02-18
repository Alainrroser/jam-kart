package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.DrawingUtils;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoostPad extends GameObject {

    private static final Image IMAGE = new Image(BoostPad.class.getResourceAsStream("/boost_pad.png"));

    public static final float SIZE = 100.0f;

    private float rotation;

    public BoostPad(Point position, float rotation) {
        setPosition(position);
        this.rotation = rotation;
    }

    @Override
    public void draw(GraphicsContext context) {
        DrawingUtils.drawRotated(context, IMAGE, getPosition(), SIZE, SIZE, rotation);
    }

    @Override
    public void update(float deltaTimeInSec) {

    }

    public float getRotation() {
        return rotation;
    }

}

package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tree extends GameObject {
    private static final Image IMAGE = new Image(BoostPad.class.getResourceAsStream("/tree.png"));
    private static final float SIZE = 250;

    public Tree(Point position) {
        setPosition(position);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(IMAGE, getPosition().getX(), getPosition().getY(), SIZE, SIZE);
    }

    @Override
    public void update(float deltaTimeInSeconds) {

    }
}
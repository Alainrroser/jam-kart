package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.utils.DrawingUtils;
import ch.bbcag.jamkart.utils.MathUtils;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class ClientOtherCar extends GameObject {
    private float rotation;
    private int id;
    private Image image;
    private String name;

    private Point lastPosition;
    private Point nextPosition;
    private float lastRotation;
    private float nextRotation;
    private float interpolation = 0.0f;

    public ClientOtherCar(int id, String name) {
        this.id = id;
        this.name = name;
        image = new Image(getClass().getResourceAsStream("/car_" + id + ".png"));
    }

    @Override
    public void draw(GraphicsContext context) {
        double nameWidth = MathUtils.getTextWidth(name, context.getFont());

        DrawingUtils.drawRotated(context, image, getPosition(), Constants.CAR_SIZE, Constants.CAR_SIZE, rotation);
        context.setFont(new Font(24));
        context.fillText(name, getPosition().getX() + Constants.CAR_SIZE / 2 - nameWidth / 2, getPosition().getY() + Constants.CAR_SIZE * 1.2);
    }

    @Override
    public void update(float deltaTimeInSeconds) {
        interpolation += deltaTimeInSeconds / Constants.NETWORK_TICK_TIME;

        if(interpolation <= 1.0f) {
            float x = interpolate(lastPosition.getX(), nextPosition.getX(), interpolation);
            float y = interpolate(lastPosition.getY(), nextPosition.getY(), interpolation);
            float rotation = interpolate(lastRotation, nextRotation, interpolation);

            setPosition(new Point(x, y));
            setRotation(rotation);
        }
    }

    private float interpolate(float start, float end, float interpolation) {
        return start + (end - start) * interpolation;
    }

    public void interpolateState(Point nextPosition, float nextRotation) {
        interpolation = 0.0f;

        this.lastPosition = new Point(getPosition().getX(), getPosition().getY());
        this.nextPosition = nextPosition;

        this.lastRotation = rotation;
        this.nextRotation = nextRotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}

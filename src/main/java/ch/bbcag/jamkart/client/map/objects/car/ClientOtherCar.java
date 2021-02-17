package ch.bbcag.jamkart.client.map.objects.car;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.utils.MathUtils;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class ClientOtherCar extends ClientCar {

    private Point lastPosition;
    private Point nextPosition;
    private float lastRotation;
    private float nextRotation;
    private float interpolation = 0.0f;

    public ClientOtherCar(int id, String name) {
        setId(id);
        setName(name);
    }

    @Override
    public void draw(GraphicsContext context) {
        double nameWidth = MathUtils.getTextWidth(getName(), context.getFont());
        double textX = getPosition().getX() + SIZE / 2 - nameWidth / 2;
        double textY = getPosition().getY() + SIZE * 1.2;

        drawCarImage(context);
        context.setFont(new Font(24));
        context.fillText(getName(), textX, textY);
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

        this.lastRotation = getRotation();
        this.nextRotation = nextRotation;
    }

}

package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.client.map.objects.car.ClientCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientMyCar;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.Canvas;

public class Camera {
    private Point position;

    public Camera(Point position) {
        this.position = position;
    }

    public float getX() {
        return position.getX();
    }

    public void setX(float x) {
        position.setX(x);
    }

    public float getY() {
        return position.getY();
    }

    public void setY(float y) {
        position.setY(y);
    }

    public void update(ClientMyCar car, Canvas canvas) {
        setX(car.getPosition().getX() - (float) (canvas.getWidth() / 2) + ClientCar.SIZE / 2);
        setY(car.getPosition().getY() - (float) (canvas.getHeight() / 2) + ClientCar.SIZE / 2);
    }
}

package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.client.map.objects.ClientCar;
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

    public void drawCamera(ClientCar car, Canvas canvas){
        setX(car.getPosition().getX() - (float) (canvas.getWidth() / 2) + Constants.CAR_SIZE / 2);
        setY(car.getPosition().getY() - (float) (canvas.getHeight() / 2) + Constants.CAR_SIZE / 2);
    }
}

package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.utils.Point;

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

}

package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.Point;

public abstract class GameObject {

    private Point position = new Point(0.0f, 0.0f);

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public abstract void draw();
    public abstract void update(float deltaTimeInSeconds);

}

package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.utils.Point;

public class RoadPathMarker {

    private Point position;
    private float radius;

    public RoadPathMarker(Point position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Point getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

}

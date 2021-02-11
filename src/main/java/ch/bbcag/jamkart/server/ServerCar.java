package ch.bbcag.jamkart.server;

import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.utils.Point;

public class ServerCar {
    private Point position = new Point(0.0f, 0.0f);
    private float rotation = 0.0f;
    private Connection connection;
    private int id = 0;

    public ServerCar(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

package ch.bbcag.jamkart.server;

import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.utils.Point;

public class ServerCar {
    private Point position = new Point(0.0f, 0.0f);
    private float rotation = 0.0f;
    private Connection connection;
    private int id;
    private String name;
    private int progress;

    public ServerCar(Connection connection, int id, String name) {
        this.connection = connection;
        this.id = id;
        this.name = name;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Connection getConnection() {
        return connection;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package ch.bbcag.jamkart.client.map.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClientOtherCar extends GameObject {
    private float rotation;
    private int id;
    private Image image;

    public ClientOtherCar(int id) {
        this.id = id;
        image = new Image(getClass().getResourceAsStream("/car_" + id + ".png"));
    }

    @Override
    public void draw(GraphicsContext context) {
        context.save();

        context.translate(getPosition().getX() + ClientCar.SIZE / 2, getPosition().getY() + ClientCar.SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + ClientCar.SIZE / 2), -(getPosition().getY() + ClientCar.SIZE / 2));

        context.drawImage(image, getPosition().getX(), getPosition().getY(), ClientCar.SIZE, ClientCar.SIZE);

        context.restore();
    }

    @Override
    public void update(float deltaTimeInSeconds) {

    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getId() {
        return id;
    }
}

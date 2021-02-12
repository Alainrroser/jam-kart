package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClientOtherCar extends GameObject {
    private float rotation;
    private int id;
    private Image image;
    private String name;

    public ClientOtherCar(int id, String name) {
        this.id = id;
        this.name = name;
        image = new Image(getClass().getResourceAsStream("/car_" + id + ".png"));
    }

    @Override
    public void draw(GraphicsContext context) {
        context.save();

        context.translate(getPosition().getX() + Constants.CAR_SIZE / 2, getPosition().getY() + Constants.CAR_SIZE / 2);
        context.rotate(rotation);
        context.translate(-(getPosition().getX() + Constants.CAR_SIZE / 2), -(getPosition().getY() + Constants.CAR_SIZE / 2));

        context.drawImage(image, getPosition().getX(), getPosition().getY(), Constants.CAR_SIZE, Constants.CAR_SIZE);

        context.restore();
    }

    @Override
    public void update(float deltaTimeInSeconds) {

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

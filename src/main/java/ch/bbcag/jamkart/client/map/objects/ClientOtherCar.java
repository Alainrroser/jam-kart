package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.utils.DrawingUtils;
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
        DrawingUtils.drawRotated(context, image, getPosition(), Constants.CAR_SIZE, Constants.CAR_SIZE, rotation);
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

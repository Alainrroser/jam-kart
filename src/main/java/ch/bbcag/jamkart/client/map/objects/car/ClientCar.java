package ch.bbcag.jamkart.client.map.objects.car;

import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.utils.DrawingUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ClientCar extends GameObject {

    private String name;
    private float rotation;

    private int id;
    private Image image;

    private boolean finished = false;
    private float finishTime = 0.0f;

    private int progress = 0;

    public static final float SIZE = 100.0f;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void rotate(float rotation) {
        this.rotation += rotation;
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
        this.image = new Image(getClass().getResourceAsStream("/car_" + id + ".png"));
    }

    public boolean isFinished() {
        return finished;
    }

    public float getFinishTime() {
        return finishTime;
    }

    public void finish(float time) {
        this.finished = true;
        this.finishTime = time;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    protected void drawCarImage(GraphicsContext context) {
        DrawingUtils.drawRotated(context, getImage(), getPosition(), SIZE, SIZE, rotation);
    }

}

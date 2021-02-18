package ch.bbcag.jamkart.client.map.objects;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

    private Point position = new Point(0.0f, 0.0f);

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract void draw(GraphicsContext context);
    public abstract void update(float deltaTimeInSec);

}

package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Road {

    private Polygon polygon = new Polygon();

    public Polygon getPolygon() {
        return polygon;
    }

    public void addPoint(Point point) {
        polygon.getPoints().addAll((double) point.getX(), (double) point.getY());
    }

    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();

        int numberOfPoints = polygon.getPoints().size() / 2;

        for(int i = 0; i < numberOfPoints; i++) {
            float x = polygon.getPoints().get(i * 2).floatValue();
            float y = polygon.getPoints().get(i * 2 + 1).floatValue();
            points.add(new Point(x, y));
        }

        return points;
    }

    public boolean isInside(Point point) {
        return polygon.contains(point.getX(), point.getY());
    }

    public void draw(GraphicsContext context) {
        int numberOfPoints = polygon.getPoints().size() / 2;
        double[] xPoints = new double[numberOfPoints];
        double[] yPoints = new double[numberOfPoints];

        for(int i = 0; i < numberOfPoints; i++) {
            xPoints[i] = polygon.getPoints().get(i * 2);
            yPoints[i] = polygon.getPoints().get(i * 2 + 1);
        }

        context.setFill(Color.GRAY);
        context.fillPolygon(xPoints, yPoints, numberOfPoints);
    }

}

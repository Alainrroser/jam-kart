package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class Road {

    private Polyline polyline = new Polyline();
    private static final float WIDTH = 300.0f;
    private static final float LINE_WIDTH = 20;
    private static final float LINE_DASHES = 100;

    public Road() {
        polyline.setStrokeWidth(WIDTH);
    }

    public void addPoint(Point point) {
        polyline.getPoints().addAll((double) point.getX(), (double) point.getY());
    }

    public boolean isInside(Point point) {
        return polyline.contains(point.getX(), point.getY());
    }

    public void draw(GraphicsContext context) {
        int numberOfPoints = polyline.getPoints().size() / 2;
        double[] xPoints = new double[numberOfPoints];
        double[] yPoints = new double[numberOfPoints];
        int x;

        for(int i = 0; i < numberOfPoints; i++) {
            x = i * 2;
            xPoints[i] = polyline.getPoints().get(x);
            yPoints[i] = polyline.getPoints().get(x + 1);
        }

        context.setStroke(Color.GRAY);
        context.setLineWidth(WIDTH);
        context.setLineDashes(0);
        context.strokePolyline(xPoints, yPoints, numberOfPoints);

        context.setStroke(Color.WHITE);
        context.setLineWidth(LINE_WIDTH);
        context.setLineDashes(LINE_DASHES);
        context.strokePolyline(xPoints, yPoints, numberOfPoints);
    }

}

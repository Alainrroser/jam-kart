package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Road {

    private Polyline polyline = new Polyline();
    private List<RoadPathMarker> pathMarkers = new ArrayList<>();

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

    public void addPathMarker(RoadPathMarker pathMarker) {
        pathMarkers.add(pathMarker);
    }

    public List<RoadPathMarker> getPathMarkers() {
        return pathMarkers;
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

//        context.setFill(Color.color(1.0f, 0.0f, 0.0f, 0.5));
//        for(RoadPathMarker marker : pathMarkers) {
//            float mx = marker.getPosition().getX();
//            float my = marker.getPosition().getY();
//            float mRadius = marker.getRadius();
//            context.fillOval(mx - mRadius, my - mRadius, mRadius * 2, mRadius * 2);
//        }
    }

}

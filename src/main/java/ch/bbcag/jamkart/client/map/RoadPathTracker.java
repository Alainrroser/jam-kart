package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.client.map.objects.ClientCar;

public class RoadPathTracker {

    private Road road;
    private ClientCar car;

    private int nextPathMarkerIndex = 0;
    private int passedLapCounter = 0;

    public RoadPathTracker(Road road, ClientCar car) {
        this.road = road;
        this.car = car;
    }

    public int getPassedLapCounter() {
        return passedLapCounter;
    }

    public void update() {
        RoadPathMarker nextMarker = road.getPathMarkers().get(nextPathMarkerIndex);
        float distance = car.getPosition().getDistanceTo(nextMarker.getPosition());
        if(distance <= nextMarker.getRadius()) {
            nextPathMarkerIndex++;
        }

        if(nextPathMarkerIndex == road.getPathMarkers().size()) {
            passedLapCounter++;
            nextPathMarkerIndex = 0;
        }
    }
}

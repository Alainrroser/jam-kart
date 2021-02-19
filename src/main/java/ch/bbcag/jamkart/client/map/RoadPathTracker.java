package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.client.ClientGame;

public class RoadPathTracker {

    private ClientGame game;

    private int nextPathMarkerIndex = 0;
    private int passedLapCounter = 0;

    public static final int NUMBER_OF_LAPS = 3;

    public RoadPathTracker(ClientGame game) {
        this.game = game;
    }

    public int getPassedLapCounter() {
        return passedLapCounter;
    }

    public void update() {
        // Get the next path marker
        RoadPathMarker nextMarker = game.getMap().getRoad().getPathMarkers().get(nextPathMarkerIndex);

        // Calculate the distance to the next path marker
        float distance = game.getCar().getPosition().getDistanceTo(nextMarker.getPosition());

        // Test whether the distance between the car and the marker is smaller than its radius
        if(distance <= nextMarker.getRadius()) {
            // Move on to the next path marker
            nextPathMarkerIndex++;
            game.getCar().setProgress(game.getCar().getProgress() + 1);
        }

        // Test whether we have passed all markers on the road
        if(nextPathMarkerIndex == game.getMap().getRoad().getPathMarkers().size()) {
            // Move on to the next lap
            passedLapCounter++;
            nextPathMarkerIndex = 0;

            // End the game if we have passed all laps
            if(passedLapCounter >= NUMBER_OF_LAPS) {
                endGame();
            }
        }
    }

    private void endGame() {
        game.getCar().finish(game.getTimer().getTime());

        game.getTimer().stop();
        game.getNetworking().sendTimeMessage();
        game.getCar().setControllable(false);
    }

}

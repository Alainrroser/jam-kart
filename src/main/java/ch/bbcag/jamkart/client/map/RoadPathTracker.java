package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;

public class RoadPathTracker {

    private ClientGame game;

    private int nextPathMarkerIndex = 0;
    private int passedLapCounter = 0;

    public static final int NUMBER_OF_LAPS = 1;

    public RoadPathTracker(ClientGame game) {
        this.game = game;
    }

    public int getPassedLapCounter() {
        return passedLapCounter;
    }

    public void update() {
        RoadPathMarker nextMarker = game.getMap().getRoad().getPathMarkers().get(nextPathMarkerIndex);
        float distance = game.getCar().getPosition().getDistanceTo(nextMarker.getPosition());
        if(distance <= nextMarker.getRadius()) {
            nextPathMarkerIndex++;
        }

        if(nextPathMarkerIndex == game.getMap().getRoad().getPathMarkers().size()) {
            passedLapCounter++;
            nextPathMarkerIndex = 0;

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

package ch.bbcag.jamkart.client;

public class GameTimer {

    private float time = 0.0f;
    private boolean running = false;

    public float getTime() {
        return time;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void update(float deltaTimeInSec) {
        if (running) {
            this.time += deltaTimeInSec;
        }
    }
}

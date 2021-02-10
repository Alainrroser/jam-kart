package ch.bbcag.jamkart.common;

import javafx.animation.AnimationTimer;

public abstract class GameLoop
extends AnimationTimer {

    private long lastTimeInNanoSeconds;

    @Override
    public void start() {
        super.start();
        lastTimeInNanoSeconds = System.nanoTime();
    }

    @Override
    public void handle(long currentTimeInNanoSeconds) {
        long deltaTimeInNanoSeconds = currentTimeInNanoSeconds - lastTimeInNanoSeconds;
        float deltaTimeInSeconds = deltaTimeInNanoSeconds / 1e9f;

        lastTimeInNanoSeconds = currentTimeInNanoSeconds;
        update(deltaTimeInSeconds);
    }

    public abstract void update(float deltaTimeInSec);

}
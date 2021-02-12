package ch.bbcag.jamkart.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Countdown {
    private Canvas canvas;
    private float timer = 3;

    public Countdown(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawCountdown() {
        canvas.getGraphicsContext2D().setFont(new Font("Arial", 150));
        canvas.getGraphicsContext2D().setFill(Color.BLACK);

        int roundedTimer = (int) Math.ceil(timer);

        if (roundedTimer == 0) {
            canvas.getGraphicsContext2D().fillText("GO!", 600, 400);
        } else if(roundedTimer > -1) {
            canvas.getGraphicsContext2D().fillText(String.valueOf(roundedTimer), 700, 400);
        }
    }

    public void update(float deltaTimeInSec) {
        timer -= deltaTimeInSec;
    }
}

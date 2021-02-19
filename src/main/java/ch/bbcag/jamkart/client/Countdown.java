package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.utils.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Countdown {
    private ClientGame game;
    private float timer = 3;
    private boolean finished = false;

    public Countdown(ClientGame game) {
        this.game = game;
    }

    public void drawCountdown(GraphicsContext context) {
        String text = null;
        int roundedTimer = (int) Math.ceil(timer);

        // Display "GO!" if the timer is equal to 0
        // Otherwise we display only if the timer is greater than -1
        if (roundedTimer == 0) {
            text = "GO!";
        } else if(roundedTimer > -1) {
            text = String.valueOf(roundedTimer);
        }

        context.setFont(new Font("Arial", 150));

        double textWidth = MathUtils.getTextWidth(text, context.getFont());
        double textX = context.getCanvas().getWidth() / 2 - textWidth / 2;

        // Draw shadow
        context.setFill(Color.BLACK);
        context.fillText(text, textX, 210);

        // Draw text
        context.setFill(Color.ORANGE);
        context.fillText(text, textX, 200);
    }

    public void update(float deltaTimeInSec) {
        timer -= deltaTimeInSec;

        if(timer <= 0.0f && !finished) {
            game.getCar().setControllable(true);
            game.startTimer();
            finished = true;
        }
    }
}

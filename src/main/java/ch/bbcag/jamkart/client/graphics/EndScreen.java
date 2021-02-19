package ch.bbcag.jamkart.client.graphics;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.client.map.objects.car.ClientCar;
import ch.bbcag.jamkart.utils.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EndScreen {

    private static final String TEXT_RETURN_TO_MAIN = "Leertaste, um zum Menü zurückzukehren";
    private static final String TEXT_FINISHED = "Ziel erreicht!";
    private static final String TEXT_LEADERBOARD = "Rangliste:";

    public void drawEndScreen(GraphicsContext context, List<ClientCar> cars) {
        double imgPosX = 80;
        double imgPosY = 100;
        int rank = 1;
        final double MARGIN = 60;

        // Draw the "finished!" text
        context.setFont(new Font(80));
        double finishedWidth = MathUtils.getTextWidth(TEXT_FINISHED, context.getFont());
        context.setFill(Color.BLACK);
        context.fillText(TEXT_FINISHED, Constants.GAME_WINDOW_WIDTH / 2 - finishedWidth / 2, 100);

        // Draw the leaderboard title
        context.setFont(new Font(34));
        context.fillText(TEXT_LEADERBOARD, 80, 80);

        // Draw the times of all cars
        context.setFont(new Font(28));

        for (ClientCar car : cars) {
            if (car.isFinished()) {
                String time = MathUtils.formatTimer(car.getFinishTime());

                context.fillText(rank + ".", imgPosX - 20, imgPosY + MARGIN / 2);
                context.drawImage(car.getImage(), imgPosX, imgPosY, 50, 50);
                context.fillText(car.getName() + " " + time, imgPosX + MARGIN, imgPosY + MARGIN / 2);
                imgPosY += MARGIN;

                rank ++;
            }
        }

        // Draw the "Press space to return to menu" text
        context.setFont(new Font(60));
        double returnToMainWidth = MathUtils.getTextWidth(TEXT_RETURN_TO_MAIN, context.getFont());
        context.fillText(TEXT_RETURN_TO_MAIN, Constants.GAME_WINDOW_WIDTH / 2 - returnToMainWidth / 2, 700);
    }

}
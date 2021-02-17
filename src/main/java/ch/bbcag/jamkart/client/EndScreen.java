package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.car.ClientCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientMyCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientOtherCar;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EndScreen {
    private JamKartApp app;
    private String backToMain = "Press space to go back to main menu!";
    private String finished = "Finished";

    public EndScreen(JamKartApp app) {
        this.app = app;
    }

    public void drawEndScreen(GraphicsContext context, List<ClientCar> cars) {
        double imgPosX = 100;
        double imgPosY = 200;
        int rank = 1;
        final double MARGIN = 60;

        context.setFont(new Font(80));
        context.setFill(Color.BLACK);
        context.fillText(finished, Constants.GAME_WINDOW_WIDTH / 2 - MathUtils.getTextWidth(finished, context.getFont()) / 2, 100);

        context.setFont(new Font(34));
        context.fillText("Scoreboard:", 100, 180);

        context.setFont(new Font(28));

        for (ClientCar car : cars) {
            if (car.isFinished()) {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                long timeInMillis = (long) (car.getFinishTime() * 1000);
                String time = format.format(new Date(timeInMillis));

                context.fillText(rank + ".", imgPosX - 20, imgPosY + MARGIN / 2);
                context.drawImage(car.getImage(), imgPosX, imgPosY, 50, 50);
                context.fillText(car.getName() + " " + time, imgPosX + MARGIN, imgPosY + MARGIN / 2);
                imgPosY += MARGIN;

                rank ++;
            }
        }

        context.setFont(new Font(60));
        context.fillText(backToMain, Constants.GAME_WINDOW_WIDTH / 2 - MathUtils.getTextWidth(backToMain, context.getFont()) / 2, 700);
    }

}
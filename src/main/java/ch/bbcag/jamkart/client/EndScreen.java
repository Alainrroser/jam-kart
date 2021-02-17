package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.car.ClientCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientMyCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientOtherCar;
import ch.bbcag.jamkart.utils.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class EndScreen {
    private JamKartApp app;
    private String backToMain = "Press space to go back to main menu!";
    private String finished = "Finished";

    public EndScreen(JamKartApp app) {
        this.app = app;
    }

    public void drawEndScreen(GraphicsContext context, List<ClientCar> cars) {
        double imgPosX = 650;
        double imgPosY = 110;
        final double MARGIN = 60;
        //Image image = new Image(getClass().getResourceAsStream("/grass.png"));
       // context.drawImage(image, 0, 0, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        context.setFont(new Font(80));
        context.setFill(Color.BLACK);
        context.fillText(finished, Constants.GAME_WINDOW_WIDTH / 2 - MathUtils.getTextWidth(finished, context.getFont()) / 2, 100);

        context.setFont(new Font(42));

        for (ClientCar car : cars) {
            context.drawImage(car.getImage(), imgPosX, imgPosY, 50, 50);
            context.fillText(car.getName(), imgPosX + MARGIN, imgPosY + MARGIN / 2);
            imgPosY += MARGIN;
        }

        if (app.getServerGame() != null) {
            context.setFont(new Font(60));
            context.fillText(backToMain, Constants.GAME_WINDOW_WIDTH / 2 - MathUtils.getTextWidth(backToMain, context.getFont()) / 2, 700);
        }
    }

}
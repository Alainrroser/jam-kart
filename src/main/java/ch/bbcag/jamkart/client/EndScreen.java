package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.ClientCar;
import ch.bbcag.jamkart.client.map.objects.ClientOtherCar;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;

public class EndScreen {
    private JamKartApp app;

    public EndScreen(JamKartApp app) {
        this.app = app;
    }

    public void drawEndScreen(GraphicsContext context, ClientCar car, List<ClientOtherCar> clientOtherCars) {
        double imgPosX = 650;
        double imgPosY = 110;
        final double MARGIN = 60;
        Image image = new Image(getClass().getResourceAsStream("/grass.png"));
        context.drawImage(image, 0,0, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        context.setFont(new Font(42));
        context.setFill(Color.BLACK);

        context.drawImage(car.getImage(), imgPosX, 50, 50, 50);
        context.fillText(car.getName(), imgPosX + MARGIN, 90);

        for (ClientOtherCar clientOtherCar : clientOtherCars) {
            context.drawImage(clientOtherCar.getImage(), imgPosX, imgPosY, 50, 50);
            context.fillText(clientOtherCar.getName(), imgPosX + MARGIN, imgPosY + MARGIN / 2);
            imgPosY += MARGIN;
        }

        if (app.getServerGame() != null) {
            context.setFont(new Font(80));
            context.fillText("Press space to go back to main menu!", 270, 700);
        }
    }

}
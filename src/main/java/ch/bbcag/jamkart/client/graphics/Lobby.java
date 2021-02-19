package ch.bbcag.jamkart.client.graphics;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.car.ClientMyCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientOtherCar;
import ch.bbcag.jamkart.utils.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class Lobby {
    private JamKartApp app;

    private static final String TEXT_START = "Drücke Leertaste zum Starten!";

    public Lobby(JamKartApp app) {
        this.app = app;
    }

    public void drawLobby(GraphicsContext context, ClientMyCar car, List<ClientOtherCar> clientOtherCars) {
        double imgPosX = 650;
        double imgPosY = 110;
        final double MARGIN = 60;

        context.setFont(new Font(42));
        context.setFill(Color.BLACK);

        // Draw my car
        context.drawImage(car.getImage(), imgPosX, 50, 50, 50);
        context.fillText(car.getName(), imgPosX + MARGIN, 90);

        // Draw the other cars
        for (ClientOtherCar clientOtherCar : clientOtherCars) {
            context.drawImage(clientOtherCar.getImage(), imgPosX, imgPosY, 50, 50);
            context.fillText(clientOtherCar.getName(), imgPosX + MARGIN, imgPosY + MARGIN / 2);
            imgPosY += MARGIN;
        }

        // Draw the start instruction if we are the server
        if (app.getServerGame() != null) {
            context.setFont(new Font(100));
            double textWidth = MathUtils.getTextWidth(TEXT_START, context.getFont());

            context.fillText(TEXT_START, context.getCanvas().getWidth() / 2 - textWidth / 2, 700);
        }
    }

}
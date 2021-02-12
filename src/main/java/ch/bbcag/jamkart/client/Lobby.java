package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.ClientCar;
import ch.bbcag.jamkart.client.map.objects.ClientOtherCar;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class Lobby {
    private JamKartApp app;

    public Lobby(JamKartApp app) {
        this.app = app;
    }

    public void drawLobby(GraphicsContext context, ClientCar car, List<ClientOtherCar> clientOtherCars) {
        double imgPosX = 650;
        double imgPosY = 110;
        final double MARGIN = 60;

        context.setFont(new Font("Arial", 42));
        context.setFill(Color.BLACK);

        context.drawImage(car.getImage(), imgPosX, 50, 50, 50);
        context.fillText(car.getName(), imgPosX + MARGIN, 90);

        for (ClientOtherCar clientOtherCar : clientOtherCars) {
            context.drawImage(clientOtherCar.getImage(), imgPosX, imgPosY, 50, 50);
            context.fillText(clientOtherCar.getName(), imgPosX + MARGIN, imgPosY + MARGIN / 2);
            imgPosY += MARGIN;
        }

        if (app.getServerGame() != null) {
            context.setFont(new Font("Arial", 100));
            context.fillText("Press space to start!", 270, 700);
        }
    }

}
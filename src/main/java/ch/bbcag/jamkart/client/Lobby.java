package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.ClientCar;
import ch.bbcag.jamkart.client.map.objects.ClientOtherCar;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;

public class Lobby {
    private Canvas canvas;
    private JamKartApp app;

    public Lobby(Canvas canvas, JamKartApp app){
        this.canvas = canvas;
        this.app = app;
    }

    public void drawLobby(ClientCar car, List<ClientOtherCar> clientOtherCars){
        double imgPosX = 650;
        double imgPosY = 110;
        final double MARGIN = 60;

        canvas.getGraphicsContext2D().setFont(new Font("Arial", 42));
        canvas.getGraphicsContext2D().setFill(Color.BLACK);

        canvas.getGraphicsContext2D().drawImage(car.getImage(), imgPosX, 50, 50, 50);
        canvas.getGraphicsContext2D().fillText(car.getName(), imgPosX + MARGIN, 90);

        for (ClientOtherCar clientOtherCar : clientOtherCars) {
            canvas.getGraphicsContext2D().drawImage(clientOtherCar.getImage(), imgPosX, imgPosY, 50, 50);
            canvas.getGraphicsContext2D().fillText(clientOtherCar.getName(), imgPosX + MARGIN, imgPosY + MARGIN/2);
            imgPosY += MARGIN;
        }


        if (app.getServerGame() != null){
            canvas.getGraphicsContext2D().setFont(new Font("Arial", 100));
            canvas.getGraphicsContext2D().fillText("Press space to start!", 270, 700);
        }
    }

}
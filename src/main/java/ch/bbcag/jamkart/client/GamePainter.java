package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.objects.ClientOtherCar;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GamePainter {

    private ClientGame game;

    private Lobby lobby;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));

    public GamePainter(ClientGame game, JamKartApp app) {
        this.game = game;
        lobby = new Lobby(app);
    }

    public void draw(GraphicsContext context) {
        context.save();

        context.translate(-game.getCamera().getX(), -game.getCamera().getY());
        for (int y = -10000; y < 10000; y += Constants.GAME_WINDOW_HEIGHT) {
            for (int x = -10000; x < 10000; x += Constants.GAME_WINDOW_WIDTH) {
                context.drawImage(GRASS, x, y, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);
            }
        }

        game.getMap().getRoad().draw(context);

        for (GameObject gameObject : game.getMap().getGameObjects()) {
            gameObject.draw(context);
        }

        context.restore();

        String lapIndicator = "Runde " + (game.getRoadPathTracker().getPassedLapCounter() + 1) + " / 3";
        context.setFont(new Font(40));
        context.setFill(Color.BLACK);
        context.fillText(lapIndicator, 20, 50);

        if (game.getCountdown() != null) {
            game.getCountdown().drawCountdown(context);
        } else {
            lobby.drawLobby(context, game.getCar(), game.getMap().getAllGameObjectsFromType(ClientOtherCar.class));
        }
    }
}

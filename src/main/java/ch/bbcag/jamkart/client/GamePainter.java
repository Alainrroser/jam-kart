package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.map.RoadPathTracker;
import ch.bbcag.jamkart.client.map.objects.ClientOtherCar;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GamePainter {

    private ClientGame game;

    private Lobby lobby;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));
    private static final int HALF_GRASS_FIELD_SIZE = 8000;

    public GamePainter(ClientGame game, JamKartApp app) {
        this.game = game;
        lobby = new Lobby(app);
    }

    public void draw(GraphicsContext context) {
        drawMap(context);
        drawLapIndicator(context);

        if (game.getCountdown() != null) {
            drawCountdown(context);
        } else {
            drawLobby(context);
        }

        drawTimer(context);
    }

    private void drawMap(GraphicsContext context) {
        context.save();

        moveEverythingInNegativeCameraPosition(context);
        drawGrassField(context);
        drawRoad(context);
        drawGameObjects(context);

        context.restore();
    }

    private void moveEverythingInNegativeCameraPosition(GraphicsContext context) {
        context.translate(-game.getCamera().getX(), -game.getCamera().getY());
    }

    private void drawGrassField(GraphicsContext context) {
        for (int y = -HALF_GRASS_FIELD_SIZE; y < HALF_GRASS_FIELD_SIZE; y += Constants.GAME_WINDOW_HEIGHT) {
            for (int x = -HALF_GRASS_FIELD_SIZE; x < HALF_GRASS_FIELD_SIZE; x += Constants.GAME_WINDOW_WIDTH) {
                context.drawImage(GRASS, x, y, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);
            }
        }
    }

    private void drawRoad(GraphicsContext context) {
        game.getMap().getRoad().draw(context);
    }

    private void drawGameObjects(GraphicsContext context) {
        for (GameObject gameObject : game.getMap().getGameObjects()) {
            gameObject.draw(context);
        }
    }

    private void drawLapIndicator(GraphicsContext context) {
        int currentLap = (game.getRoadPathTracker().getPassedLapCounter() + 1);
        String lapIndicator;

        if (currentLap <= RoadPathTracker.NUMBER_OF_LAPS) {
            lapIndicator = "Runde " + currentLap + " / " + RoadPathTracker.NUMBER_OF_LAPS;
        } else {
            lapIndicator = "Im Ziel";
        }

        context.setFont(new Font(40));
        context.setFill(Color.BLACK);
        context.fillText(lapIndicator, 20, 50);
    }

    private void drawTimer(GraphicsContext context) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        long timeInMillis = (long) (game.getTimer().getTime() * 1000);
        String timer = format.format(new Date(timeInMillis));

        context.setFont(new Font(40));
        context.setFill(Color.BLACK);
        context.fillText(timer, 20, 100);
    }

    private void drawCountdown(GraphicsContext context) {
        game.getCountdown().drawCountdown(context);
    }

    private void drawLobby(GraphicsContext context) {
        lobby.drawLobby(context, game.getCar(), game.getMap().getAllGameObjectsFromType(ClientOtherCar.class));
    }

}

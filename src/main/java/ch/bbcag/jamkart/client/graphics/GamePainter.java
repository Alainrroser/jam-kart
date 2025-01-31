package ch.bbcag.jamkart.client.graphics;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.client.map.RoadPathTracker;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.client.map.objects.car.ClientCar;
import ch.bbcag.jamkart.client.map.objects.car.ClientOtherCar;
import ch.bbcag.jamkart.utils.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class GamePainter {

    private ClientGame game;

    private Lobby lobby;
    private EndScreen endScreen;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));
    private static final int HALF_GRASS_FIELD_SIZE = 8000;

    public GamePainter(ClientGame game, JamKartApp app) {
        this.game = game;
        lobby = new Lobby(app);
        endScreen = new EndScreen();
    }

    public void draw(GraphicsContext context) {
        drawMap(context);

        if (game.getCountdown() != null) {
            drawCountdown(context);
        } else {
            drawLobby(context);
        }

        if (game.getCar().isFinished()) {
            drawEndScreen(context);
        } else {
            drawLapIndicator(context);
            drawTimer(context);
            drawRank(context);
        }
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
        String lapIndicator = "Runde " + currentLap + " / " + RoadPathTracker.NUMBER_OF_LAPS;

        context.setFont(new Font(40));
        context.setFill(Color.BLACK);
        context.fillText(lapIndicator, 20, 50);
    }

    private void drawTimer(GraphicsContext context) {
        String timer = MathUtils.formatTimer(game.getTimer().getTime());

        context.setFont(new Font(40));
        context.setFill(Color.BLACK);
        context.fillText(timer, 20, 100);
    }

    private void drawRank(GraphicsContext context) {
        context.setFont(new Font(80));

        // Sort the list of cars by their progress
        List<ClientCar> cars = game.getMap().getAllGameObjectsFromType(ClientCar.class);
        cars.sort((c1, c2) -> Integer.compare(c2.getProgress(), c1.getProgress()));

        // The index in the list + 1 is the rank of my car
        int index = cars.indexOf(game.getCar());
        int rank = index + 1;

        String text = rank + " / " + cars.size();
        double textWidth = MathUtils.getTextWidth(text, context.getFont());

        context.setFill(Color.BLACK);
        context.fillText(text, context.getCanvas().getWidth() - textWidth - 20, 100);
    }

    private void drawCountdown(GraphicsContext context) {
        game.getCountdown().drawCountdown(context);
    }

    private void drawLobby(GraphicsContext context) {
        lobby.drawLobby(context, game.getCar(), game.getMap().getAllGameObjectsFromType(ClientOtherCar.class));
    }

    private void drawEndScreen(GraphicsContext context){
        // Sort the list of cars by their finish time
        List<ClientCar> cars = game.getMap().getAllGameObjectsFromType(ClientCar.class);
        cars.sort((o1, o2) -> Float.compare(o1.getFinishTime(), o2.getFinishTime()));

        endScreen.drawEndScreen(context, cars);
    }

}

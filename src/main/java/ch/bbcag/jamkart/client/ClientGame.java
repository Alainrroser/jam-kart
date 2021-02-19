package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.NetErrorMessages;
import ch.bbcag.jamkart.client.graphics.GamePainter;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.client.map.MapLoader;
import ch.bbcag.jamkart.client.map.RoadPathTracker;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.client.map.objects.car.ClientMyCar;
import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.scenes.SceneBackToStart;
import ch.bbcag.jamkart.scenes.SceneType;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.Canvas;

import java.io.IOException;

public class ClientGame {

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;

    private GameNetworking networking;

    private Map map;
    private Camera camera;
    private ClientMyCar car;

    private RoadPathTracker roadPathTracker;

    private GameLoop loop;
    private GamePainter painter;

    private Countdown countdown;
    private GameTimer timer;

    private JamKartApp app;

    public ClientGame(Canvas canvas, KeyEventHandler keyEventHandler, JamKartApp app) {
        this.canvas = canvas;
        this.keyEventHandler = keyEventHandler;
        this.app = app;
     }

    public void load() {
        map = new Map();

        MapLoader loader = new MapLoader(map);
        loader.load();

        car = new ClientMyCar(map, keyEventHandler);
        map.getGameObjects().add(car);
    }

    public void start(String ip, int port, String name) {
        car.setName(name);
        createClient(ip, port);

        // Only create all other objects if the GameNetworking has been created
        // Otherwise the client has no connection to the server and no game is possible
        if (networking != null) {
            keyEventHandler.reset();
            painter = new GamePainter(this, app);
            camera = new Camera(new Point(0, 0));
            timer = new GameTimer();
            roadPathTracker = new RoadPathTracker(this);

            createLoop();
        }
    }

    private void createClient(String ip, int port) {
        try {
            networking = new GameNetworking(this);
            networking.startClient(ip, port);
        } catch (IOException e) {
            networking = null;

            String message = NetErrorMessages.COULD_NOT_INIT_CONNECTION;
            ((SceneBackToStart) app.getNavigator().getScene(SceneType.BACK_TO_START)).setMessage(message);
            app.getNavigator().navigateTo(SceneType.BACK_TO_START, true);

            System.err.println("couldn't connect to server");
        }
    }

    private void createLoop() {
        loop = new GameLoop() {
            @Override
            public void update(float deltaTimeInSec) {
                ClientGame.this.update(deltaTimeInSec);
                painter.draw(canvas.getGraphicsContext2D());
            }
        };
        loop.start();
    }

    public void startCountdown() {
        countdown = new Countdown(this);
    }

    public void startTimer() {
        timer.start();
    }

    private void update(float deltaTimeInSec) {
        // Start the game if we are the server, the countdown hasn't been started
        // and the space key is pressed
        if (app.getServerGame() != null && countdown == null) {
            if (keyEventHandler.isSpacePressed()) {
                app.getServerGame().sendMessageStartGame();
            }
        }

        if (countdown != null) {
            countdown.update(deltaTimeInSec);
        }

        timer.update(deltaTimeInSec);

        for (GameObject gameObject : map.getGameObjects()) {
            gameObject.update(deltaTimeInSec);
        }
        roadPathTracker.update();

        camera.update(car, canvas);

        if (networking.isDisconnected()) {
            // Stop the game and navigate back to the main menu
            stop();

            String message = NetErrorMessages.CONNECTION_LOST;
            ((SceneBackToStart) app.getNavigator().getScene(SceneType.BACK_TO_START)).setMessage(message);
            app.getNavigator().navigateTo(SceneType.BACK_TO_START, true);

            System.err.println("connection to server lost");
        } else {
            networking.update(deltaTimeInSec);
        }

        if (car.isFinished()) {
            // Return back to the main menu if the car has finished the game
            // and the space key is pressed
            if (keyEventHandler.isSpacePressed()) {
                // Don't forget to close the server when leaving if we are the server
                if (app.getServerGame() != null) {
                    app.getServerGame().stop();
                }

                stop();
                app.getNavigator().navigateTo(SceneType.START, true);
            }
        }
    }

    public void stop() {
        app.setClientGame(null);
        loop.stop();
        networking.closeClient();
    }

    public ClientMyCar getCar() {
        return car;
    }

    public Camera getCamera() {
        return camera;
    }

    public Map getMap() {
        return map;
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public GameTimer getTimer() {
        return timer;
    }

    public RoadPathTracker getRoadPathTracker() {
        return roadPathTracker;
    }

    public GameNetworking getNetworking() {
        return networking;
    }
}

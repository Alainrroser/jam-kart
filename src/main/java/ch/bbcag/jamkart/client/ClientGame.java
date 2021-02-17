package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.NetErrorMessages;
import ch.bbcag.jamkart.client.map.MapLoader;
import ch.bbcag.jamkart.client.scenes.Navigator;
import ch.bbcag.jamkart.client.scenes.SceneBackToStart;
import ch.bbcag.jamkart.client.scenes.SceneType;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.client.map.RoadPathMarker;
import ch.bbcag.jamkart.client.map.RoadPathTracker;
import ch.bbcag.jamkart.client.map.objects.*;
import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.Point;
import javafx.animation.Interpolator;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.Time;

public class ClientGame {

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;
    private Navigator navigator;

    private GameNetworking networking;

    private Map map;
    private Camera camera;
    private ClientCar car;

    private RoadPathTracker roadPathTracker;

    private GameLoop loop;
    private GamePainter painter;

    private Countdown countdown;
    private GameTimer timer;

    private JamKartApp app;

    public ClientGame(Canvas canvas, KeyEventHandler keyEventHandler, Navigator navigator, JamKartApp app) {
        this.canvas = canvas;
        this.keyEventHandler = keyEventHandler;
        this.navigator = navigator;
        this.app = app;

        /*
        TODO: This class needs some serious refactoring.
        My hope is that this TODO won't be forgotten
        and thus won't appear in the final source code.

        Please.
        */
     }

    public void load() {
        map = new Map();

        MapLoader loader = new MapLoader(map);
        loader.load();

        car = new ClientCar(map, keyEventHandler, this);
        map.getGameObjects().add(car);
    }

    public void start(String ip, int port, String name) {
        car.setName(name);
        createClient(ip, port);

        if (networking != null) {
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
            String message = NetErrorMessages.COULD_NOT_INIT_CONNECTION;
            ((SceneBackToStart) navigator.getScene(SceneType.BACK_TO_START)).setMessage(message);
            navigator.navigateTo(SceneType.BACK_TO_START, true);

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

        camera.setX(car.getPosition().getX() - (float) (canvas.getWidth() / 2) + Constants.CAR_SIZE / 2);
        camera.setY(car.getPosition().getY() - (float) (canvas.getHeight() / 2) + Constants.CAR_SIZE / 2);

        if (networking.isDisconnected()) {
            stop();

            String message = NetErrorMessages.CONNECTION_LOST;
            ((SceneBackToStart) navigator.getScene(SceneType.BACK_TO_START)).setMessage(message);
            navigator.navigateTo(SceneType.BACK_TO_START, true);

            System.err.println("connection lost");
        } else {
            networking.update(deltaTimeInSec);
        }
    }


    public void stop() {
        loop.stop();
        networking.closeClient();
    }

    public ClientCar getCar() {
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

}

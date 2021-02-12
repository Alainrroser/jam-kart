package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.NetErrorMessages;
import ch.bbcag.jamkart.client.graphics.scenes.Navigator;
import ch.bbcag.jamkart.client.graphics.scenes.SceneBackToStart;
import ch.bbcag.jamkart.client.graphics.scenes.SceneType;
import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.client.map.RoadPathMarker;
import ch.bbcag.jamkart.client.map.RoadPathTracker;
import ch.bbcag.jamkart.client.map.objects.*;
import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

public class ClientGame {

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;
    private Navigator navigator;

    private Client client;

    private Lobby lobby;
    private Countdown countdown;
    private Map map;
    private Camera camera;
    private ClientCar car;

    private RoadPathTracker roadPathTracker;

    private GameLoop loop;

    private JamKartApp app;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));

    public ClientGame(Canvas canvas, KeyEventHandler keyEventHandler, Navigator navigator, JamKartApp app) {
        this.canvas = canvas;
        this.keyEventHandler = keyEventHandler;
        this.navigator = navigator;
        this.app = app;
    }

    public void load() {
        map = new Map();

        map.getRoad().addPathMarker(new RoadPathMarker(new Point(3400, -2050), 500));
        map.getRoad().addPathMarker(new RoadPathMarker(new Point(-300, -200), 300));

        map.getRoad().addPoint(new Point(200, 200));
        map.getGameObjects().add(new BoostPad(new Point(700, 150), 90));
        map.getRoad().addPoint(new Point(2000, 200));
        map.getRoad().addPoint(new Point(2050, 175));
        map.getGameObjects().add(new OilPuddle(new Point(2160, 122)));
        map.getRoad().addPoint(new Point(2300, 0));
        map.getRoad().addPoint(new Point(2450, -350));
        map.getRoad().addPoint(new Point(2450, -450));
        map.getRoad().addPoint(new Point(2300, -850));
        map.getRoad().addPoint(new Point(2050, -1050));
        map.getRoad().addPoint(new Point(2000, -1075));
        map.getRoad().addPoint(new Point(1700, -1075));
        map.getRoad().addPoint(new Point(1650, -1100));
        map.getRoad().addPoint(new Point(1600, -1150));
        map.getRoad().addPoint(new Point(1550, -1250));
        map.getRoad().addPoint(new Point(1550, -1350));
        map.getRoad().addPoint(new Point(1600, -1450));
        map.getGameObjects().add(new OilPuddle(new Point(2967, -1980)));
        map.getRoad().addPoint(new Point(3000, -1600));
        map.getRoad().addPoint(new Point(3050, -1625));
        map.getRoad().addPoint(new Point(3250, -1750));
        map.getRoad().addPoint(new Point(3400, -2050));
        map.getRoad().addPoint(new Point(3400, -2150));
        map.getRoad().addPoint(new Point(3250, -2450));
        map.getRoad().addPoint(new Point(3000, -2450));
        map.getRoad().addPoint(new Point(2950, -2500));
        map.getRoad().addPoint(new Point(2900, -2600));
        map.getRoad().addPoint(new Point(2900, -2700));
        map.getRoad().addPoint(new Point(2950, -2800));
        map.getRoad().addPoint(new Point(3000, -2900));
        map.getRoad().addPoint(new Point(3400, -3000));
        map.getRoad().addPoint(new Point(3450, -3050));
        map.getRoad().addPoint(new Point(3500, -3100));
        map.getRoad().addPoint(new Point(3550, -3200));
        map.getRoad().addPoint(new Point(3600, -3400));
        map.getRoad().addPoint(new Point(3650, -3650));
        map.getRoad().addPoint(new Point(3650, -3750));
        map.getRoad().addPoint(new Point(3600, -4000));
        map.getRoad().addPoint(new Point(3550, -4250));
        map.getRoad().addPoint(new Point(3500, -4450));
        map.getRoad().addPoint(new Point(3450, -4600));
        map.getRoad().addPoint(new Point(3400, -4650));
        map.getGameObjects().add(new BoostPad(new Point(3150, -4700), 270));
        map.getRoad().addPoint(new Point(2000, -4650));
        map.getRoad().addPoint(new Point(1950, -4600));
        map.getRoad().addPoint(new Point(1900, -4550));
        map.getGameObjects().add(new OilPuddle(new Point(1860, -4480)));
        map.getRoad().addPoint(new Point(1850, -4475));
        map.getRoad().addPoint(new Point(1800, -4400));
        map.getRoad().addPoint(new Point(1750, -4300));
        map.getRoad().addPoint(new Point(1750, -4100));
        map.getRoad().addPoint(new Point(1700, -4075));
        map.getRoad().addPoint(new Point(1650, -4050));
        map.getRoad().addPoint(new Point(1600, -4025));
        map.getGameObjects().add(new BoostPad(new Point(1360, -4440), 180));
        map.getGameObjects().add(new BoostPad(new Point(1360, -4540), 180));
        map.getGameObjects().add(new BoostPad(new Point(1360, -4640), 180));
        map.getGameObjects().add(new BoostPad(new Point(1410, -4440), 180));
        map.getGameObjects().add(new BoostPad(new Point(1410, -4540), 180));
        map.getGameObjects().add(new BoostPad(new Point(1410, -4640), 180));
        map.getGameObjects().add(new BoostPad(new Point(1460, -4440), 180));
        map.getGameObjects().add(new BoostPad(new Point(1460, -4540), 180));
        map.getGameObjects().add(new BoostPad(new Point(1460, -4640), 180));
        map.getGameObjects().add(new Tree(new Point(1360, -4640)));
        map.getRoad().addPoint(new Point(1350, -4025));
        map.getRoad().addPoint(new Point(1250, -4075));
        map.getGameObjects().add(new BoostPad(new Point(790, -4100), 0));
        map.getRoad().addPoint(new Point(1200, -4100));
        map.getRoad().addPoint(new Point(1100, -4600));
        map.getRoad().addPoint(new Point(1050, -5000));
        map.getRoad().addPoint(new Point(1000, -5050));
        map.getRoad().addPoint(new Point(950, -5100));
        map.getRoad().addPoint(new Point(900, -5150));
        map.getRoad().addPoint(new Point(700, -5150));
        map.getRoad().addPoint(new Point(650, -5100));
        map.getRoad().addPoint(new Point(600, -5050));
        map.getRoad().addPoint(new Point(550, -5000));
        map.getGameObjects().add(new BoostPad(new Point(410, -4865), 180));
        map.getGameObjects().add(new OilPuddle(new Point(510, -4235)));
        map.getGameObjects().add(new OilPuddle(new Point(275, -3380)));
        map.getRoad().addPoint(new Point(350, -2700));
        map.getRoad().addPoint(new Point(300, -2550));
        map.getRoad().addPoint(new Point(250, -2450));
        map.getRoad().addPoint(new Point(100, -2400));
        map.getRoad().addPoint(new Point(-100, -2350));
        map.getRoad().addPoint(new Point(-300, -2250));
        map.getGameObjects().add(new BoostPad(new Point(230, -2145), 180));
        map.getRoad().addPoint(new Point(-300, -2050));
        map.getRoad().addPoint(new Point(-250, -2000));
        map.getRoad().addPoint(new Point(100, -1750));
        map.getRoad().addPoint(new Point(150, -1700));
        map.getRoad().addPoint(new Point(175, -1650));
        map.getGameObjects().add(new BoostPad(new Point(-460, -1700), 180));
        map.getRoad().addPoint(new Point(175, -1600));
        map.getRoad().addPoint(new Point(150, -1550));
        map.getRoad().addPoint(new Point(100, -1500));
        map.getRoad().addPoint(new Point(-300, -1275));
        map.getRoad().addPoint(new Point(-315, -1265));
        map.getRoad().addPoint(new Point(-330, -1250));
        map.getRoad().addPoint(new Point(-345, -1225));
        map.getGameObjects().add(new OilPuddle(new Point(-275, -1230)));
        map.getRoad().addPoint(new Point(-345, -1200));
        map.getRoad().addPoint(new Point(-325, -1150));
        map.getRoad().addPoint(new Point(-290, -1100));
        map.getRoad().addPoint(new Point(-225, -1050));
        map.getRoad().addPoint(new Point(100, -1000));
        map.getRoad().addPoint(new Point(125, -990));
        map.getRoad().addPoint(new Point(145, -970));
        map.getRoad().addPoint(new Point(160, -940));
        map.getRoad().addPoint(new Point(160, -800));
        map.getRoad().addPoint(new Point(135, -750));
        map.getRoad().addPoint(new Point(110, -720));
        map.getRoad().addPoint(new Point(90, -700));
        map.getRoad().addPoint(new Point(-300, -500));
        map.getRoad().addPoint(new Point(-325, -490));
        map.getRoad().addPoint(new Point(-350, -450));
        map.getRoad().addPoint(new Point(-350, 100));
        map.getRoad().addPoint(new Point(-325, 150));
        map.getRoad().addPoint(new Point(-300, 200));
        map.getRoad().addPoint(new Point(200, 200));

        car = new ClientCar(map, keyEventHandler, this);
        car.setPosition(new Point(200, 200));
        map.getGameObjects().add(car);

    }

    public void start(String ip, int port, String name) {
        car.setName(name);
        createClient(ip, port);

        lobby = new Lobby(canvas, app);

        if (client != null) {
            camera = new Camera(new Point(100, 0));
            roadPathTracker = new RoadPathTracker(map.getRoad(), car);
            createLoop();
        }
    }

    private void createClient(String ip, int port) {
        try {
            client = new Client(ip, port);
            client.setMessageHandler(this::processMessage);
            client.start();
            sendJoinMessage();
        } catch (IOException e) {
            String message = NetErrorMessages.COULD_NOT_INIT_CONNECTION;
            ((SceneBackToStart) navigator.getScene(SceneType.BACK_TO_START)).setMessage(message);
            navigator.navigateTo(SceneType.BACK_TO_START, true);

            System.err.println("couldn't connect to server");
        }
    }

    private void sendJoinMessage() {
        Message message = new Message(MessageType.JOIN_GAME);
        message.addValue("name", car.getName());
        client.sendMessage(message);
    }

    private void processMessage(Message message) {
        switch (message.getMessageType()) {
            case INITIAL_STATE:
                car.setId(Integer.parseInt(message.getValue("id")));
                car.setPosition(new Point(Integer.parseInt(message.getValue("x")), Integer.parseInt(message.getValue("y"))));
                break;
            case UPDATE:
                int id = Integer.parseInt(message.getValue("id"));
                ClientOtherCar otherCar = null;

                for (GameObject gameObject : map.getGameObjects()) {
                    if (gameObject instanceof ClientOtherCar) {
                        if (((ClientOtherCar) gameObject).getId() == id) {
                            otherCar = (ClientOtherCar) gameObject;
                        }
                    }
                }

                if (otherCar == null) {
                    otherCar = new ClientOtherCar(id, message.getValue("name"));
                    System.out.println(otherCar.getName());
                    map.getGameObjects().add(otherCar);
                }

                otherCar.setPosition(new Point(Float.parseFloat(message.getValue("x")), Float.parseFloat(message.getValue("y"))));
                otherCar.setRotation(Float.parseFloat(message.getValue("rotation")));
                break;
            case DISCONNECTED:
                int disconnectedId = Integer.parseInt(message.getValue("id"));
                for (GameObject gameObject : map.getGameObjects()) {
                    if (gameObject instanceof ClientOtherCar) {
                        if (((ClientOtherCar) gameObject).getId() == disconnectedId) {
                            map.getGameObjects().remove(gameObject);
                        }
                    }
                }
                break;
            case START_GAME:
                countdown = new Countdown(this);
            default:
                break;
        }
    }

    private void createLoop() {
        loop = new GameLoop() {
            @Override
            public void update(float deltaTimeInSec) {
                ClientGame.this.update(deltaTimeInSec);
                draw();
            }
        };
        loop.start();
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

        for (GameObject gameObject : map.getGameObjects()) {
            gameObject.update(deltaTimeInSec);
        }
        roadPathTracker.update();

        camera.setX(car.getPosition().getX() - (float) (canvas.getWidth() / 2) + Constants.CAR_SIZE / 2);
        camera.setY(car.getPosition().getY() - (float) (canvas.getHeight() / 2) + Constants.CAR_SIZE / 2);

        if (client.isDisconnected()) {
            stop();

            String message = NetErrorMessages.CONNECTION_LOST;
            ((SceneBackToStart) navigator.getScene(SceneType.BACK_TO_START)).setMessage(message);
            navigator.navigateTo(SceneType.BACK_TO_START, true);

            System.err.println("connection lost");
        }
    }

    private void draw() {
        canvas.getGraphicsContext2D().save();

        canvas.getGraphicsContext2D().translate(-camera.getX(), -camera.getY());
        for (int y = -10000; y < 10000; y += Constants.GAME_WINDOW_HEIGHT) {
            for (int x = -10000; x < 10000; x += Constants.GAME_WINDOW_WIDTH) {
                canvas.getGraphicsContext2D().drawImage(GRASS, x, y, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);
            }
        }
        map.getRoad().draw(canvas.getGraphicsContext2D());

        for (GameObject gameObject : map.getGameObjects()) {
            gameObject.draw(canvas.getGraphicsContext2D());
        }

        canvas.getGraphicsContext2D().restore();

        String lapIndicator = "Runde " + (roadPathTracker.getPassedLapCounter() + 1) + " / 3";
        canvas.getGraphicsContext2D().setFont(new Font(40));
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillText(lapIndicator, 20, 50);

        if (countdown != null) {
            countdown.drawCountdown(canvas.getGraphicsContext2D());
        } else {
            lobby.drawLobby(car, map.getAllGameObjectsFromType(ClientOtherCar.class));
        }
    }

    public void stop() {
        loop.stop();
        client.close();
    }

    public Client getClient() {
        return client;
    }

    public ClientCar getCar() {
        return car;
    }

}

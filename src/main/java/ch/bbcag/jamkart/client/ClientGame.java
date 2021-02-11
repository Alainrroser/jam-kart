package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.client.map.objects.Car;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ClientGame {

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;

    private Client client;

    private Map map;

    private Camera camera;

    private Car car;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));

    public ClientGame(Canvas canvas, KeyEventHandler keyEventHandler) {
        this.canvas = canvas;
        this.keyEventHandler = keyEventHandler;
    }

    public void load() {
        map = new Map();

        car = new Car(map, keyEventHandler);
        car.setPosition(new Point(200, 200));
        map.getGameObjects().add(car);

        float yCenter = (float) canvas.getHeight() / 2;

        map.getRoad().addPoint(new Point(0, yCenter - 200));
        map.getRoad().addPoint(new Point((float) canvas.getWidth(), yCenter - 200));
        map.getRoad().addPoint(new Point((float) canvas.getWidth(), yCenter + 200));
        map.getRoad().addPoint(new Point(0, yCenter + 200));
    }

    public void start(String ip, int port, String name) {
        createClient(ip, port, name);
        camera = new Camera(new Point(100, 0));
        createLoop();
    }

    private void createClient(String ip, int port, String name) {
        try {
            client = new Client(ip, port);
            client.setMessageHandler(this::processMessage);
            client.start();

            sendJoinMessage(name);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void sendJoinMessage(String name) {
        Message message = new Message(MessageType.JOIN_GAME);
        message.addValue("name", name);
        client.sendMessage(message);
    }

    private void processMessage(Message message) {
        switch (message.getMessageType()) {
            default:
                break;
        }
    }

    private void createLoop() {
        new GameLoop() {
            @Override
            public void update(float deltaTimeInSec) {
                ClientGame.this.update(deltaTimeInSec);
                draw();
            }
        }.start();
    }

    private void update(float deltaTimeInSec) {
        for(GameObject gameObject : map.getGameObjects()) {
            gameObject.update(deltaTimeInSec);
        }
        camera.setX(car.getPosition().getX()-(float) (canvas.getWidth()/2) + Car.SIZE/2);
        camera.setY(car.getPosition().getY()-(float) (canvas.getHeight()/2) + Car.SIZE/2);
    }

    private void draw() {
        canvas.getGraphicsContext2D().save();

        canvas.getGraphicsContext2D().translate(-camera.getX(), -camera.getY());
        for(int y = -10000; y < 10000; y += 800) {
            for (int x = -10000; x < 10000; x += 1500) {
                canvas.getGraphicsContext2D().drawImage(GRASS, x, y, 1500, 800);
            }
        }
        map.getRoad().draw(canvas.getGraphicsContext2D());

        for(GameObject gameObject : map.getGameObjects()) {
            gameObject.draw(canvas.getGraphicsContext2D());
        }

        canvas.getGraphicsContext2D().restore();
    }

    public void stop() {
        client.close();
    }

}

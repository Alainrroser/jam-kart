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

    private Car car;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));

    public ClientGame(Canvas canvas, KeyEventHandler keyEventHandler) {
        this.canvas = canvas;
        this.keyEventHandler = keyEventHandler;
    }

    public void load() {
        map = new Map();

        car = new Car(keyEventHandler);
        car.setPosition(new Point(200, 200));
        map.getGameObjects().add(car);
    }

    public void start(String ip, int port, String name) {
        createClient(ip, port, name);
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
    }

    private void draw() {
        canvas.getGraphicsContext2D().save();

        canvas.getGraphicsContext2D().drawImage(GRASS, 0, 0, 1500, 800);

        for(GameObject gameObject : map.getGameObjects()) {
            gameObject.draw(canvas.getGraphicsContext2D());
        }

        canvas.getGraphicsContext2D().restore();
    }

    public void stop() {
        client.close();
    }

}

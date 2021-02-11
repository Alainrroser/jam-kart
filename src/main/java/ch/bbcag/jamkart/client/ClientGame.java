package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.client.map.Map;
import ch.bbcag.jamkart.client.map.objects.ClientCar;
import ch.bbcag.jamkart.client.map.objects.ClientOtherCar;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.IOException;

public class ClientGame {

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;
    private Client client;
    private Map map;
    private Camera camera;
    private ClientCar car;

    private static final Image GRASS = new Image(ClientGame.class.getResourceAsStream("/grass.png"));

    public ClientGame(Canvas canvas, KeyEventHandler keyEventHandler) {
        this.canvas = canvas;
        this.keyEventHandler = keyEventHandler;
    }

    public void load() {
        map = new Map();

        car = new ClientCar(map, keyEventHandler, this);
        car.setPosition(new Point(200, 200));
        map.getGameObjects().add(car);

        map.getRoad().addPoint(new Point(0, 300));
        map.getRoad().addPoint(new Point(1000, 300));
        map.getRoad().addPoint(new Point(1500, 0));
        map.getRoad().addPoint(new Point(1500, -500));
        map.getRoad().addPoint(new Point(1000, -700));
        map.getRoad().addPoint(new Point(1000, -1000));
        map.getRoad().addPoint(new Point(1800, -1500));
        map.getRoad().addPoint(new Point(2500, -1300));
        map.getRoad().addPoint(new Point(2500, -500));
    }

    public void start(String ip, int port, String name) {
        car.setName(name);
        createClient(ip, port);
        camera = new Camera(new Point(100, 0));
        createLoop();
    }

    private void createClient(String ip, int port) {
        try {
            client = new Client(ip, port);
            client.setMessageHandler(this::processMessage);
            client.start();
            sendJoinMessage();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void sendJoinMessage() {
        Message message = new Message(MessageType.JOIN_GAME);
        message.addValue("name", car.getName());
        client.sendMessage(message);
    }

    private void processMessage(Message message) {
        switch (message.getMessageType()) {
            case ID:
                car.setId(Integer.parseInt(message.getValue("id")));
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
                    otherCar = new ClientOtherCar(id);
                    map.getGameObjects().add(otherCar);
                }

                otherCar.setPosition(new Point(Float.parseFloat(message.getValue("x")), Float.parseFloat(message.getValue("y"))));
                otherCar.setRotation(Float.parseFloat(message.getValue("rotation")));
                break;
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
        camera.setX(car.getPosition().getX()-(float) (canvas.getWidth()/2) + ClientCar.SIZE/2);
        camera.setY(car.getPosition().getY()-(float) (canvas.getHeight()/2) + ClientCar.SIZE/2);
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

    public Client getClient() {
        return client;
    }
}

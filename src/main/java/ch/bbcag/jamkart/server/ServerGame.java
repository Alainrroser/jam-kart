package ch.bbcag.jamkart.server;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.NetErrorMessages;
import ch.bbcag.jamkart.scenes.Navigator;
import ch.bbcag.jamkart.scenes.SceneBackToStart;
import ch.bbcag.jamkart.scenes.SceneType;
import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.server.Server;
import ch.bbcag.jamkart.utils.Point;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerGame {

    private JamKartApp app;
    private Navigator navigator;

    private Server server;
    private boolean startMessageSent;

    private List<ServerCar> carList = new CopyOnWriteArrayList<>();

    private List<Integer> availableIdList = new CopyOnWriteArrayList<>();
    private static final int NUMBER_OF_PLAYERS = 4;

    private float networkTickTimer = 0;

    public ServerGame(JamKartApp app, Navigator navigator) {
        this.app = app;
        this.navigator = navigator;
    }

    public void start(int port) {
        startMessageSent = false;
        carList.clear();

        availableIdList.clear();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            availableIdList.add(i);
        }

        createServer(port);

        if(server != null) {
            app.setServerGame(this);
            createLoop();
        } else {
            stop();
        }
    }

    private void createServer(int port) {
        try {
            server = new Server(port);
            server.setServerMessageHandler(this::processMessage);
            server.start();

            System.out.println("server started");
        } catch (IOException e) {
            String message = NetErrorMessages.COULD_NOT_CREATE_SERVER;
            ((SceneBackToStart) navigator.getScene(SceneType.BACK_TO_START)).setMessage(message);
            navigator.navigateTo(SceneType.BACK_TO_START, true);

            System.err.println("couldn't start server");
        }
    }

    private void createLoop() {
        new GameLoop() {
            @Override
            public void update(float deltaTimeInSec) {
                networkTickTimer += deltaTimeInSec;
                if (networkTickTimer >= Constants.NETWORK_TICK_TIME) {
                    networkTick();
                    networkTickTimer = 0;
                }
            }
        }.start();
    }

    private void networkTick() {
        for (ServerCar car : carList) {
            if (car.getConnection().isDisconnected()) {
                Message disconnectedMessage = new Message(MessageType.DISCONNECTED);
                disconnectedMessage.addValue("id", car.getId());
                sendMessageToOtherCars(car, disconnectedMessage);

                carList.remove(car);

                // Make the car's id available for other cars
                availableIdList.add(0, car.getId());
            }
        }
    }

    private void processMessage(Message message, Connection connection) {
        switch (message.getMessageType()) {
            case JOIN_GAME:
                processJoinGame(message, connection);
                break;
            case UPDATE:
                processUpdate(message, connection);
                break;
            case TIME:
                processTime(message, connection);
                break;
            default:
                break;
        }
    }

    private void processJoinGame(Message message, Connection connection) {
        if (startMessageSent || availableIdList.isEmpty()) {
            // We don't allow connections if the game has already started or
            // if there are no available ids left
            connection.close();
            System.out.println("new player rejected, there are no ids left or the game has already started");
            System.out.println(startMessageSent);
            System.out.println(availableIdList);
        } else {
            int y = availableIdList.get(0) * Constants.CAR_SPAWN_DISTANCE + Constants.CAR_SPAWN_FIRST_Y;

            Message idMessage = new Message(MessageType.INITIAL_STATE);
            idMessage.addValue("id", availableIdList.get(0));
            idMessage.addValue("x", 0);
            idMessage.addValue("y", y);
            connection.sendMessage(idMessage);

            carList.add(new ServerCar(connection, availableIdList.get(0), message.getValue("name")));
            availableIdList.remove(0);

            System.out.println("new player joined the game!");
            System.out.println("name: " + message.getValue("name"));
        }
    }

    private void processUpdate(Message message, Connection connection) {
        for (ServerCar car : carList) {
            if (car.getConnection() == connection) {
                car.setPosition(new Point(Float.parseFloat(message.getValue("x")), Float.parseFloat(message.getValue("y"))));
                car.setRotation(Float.parseFloat(message.getValue("rotation")));
                car.setProgress(Integer.parseInt(message.getValue("progress")));

                // Send the cars state to the other players
                Message serverUpdateMessage = createUpdateMessageForCar(car);
                sendMessageToOtherCars(car, serverUpdateMessage);
            }
        }
    }

    private void processTime(Message message, Connection connection) {
        for (ServerCar car : carList) {
            if (car.getConnection() == connection) {
                Message timeMessage = new Message(MessageType.TIME);
                timeMessage.addValue("id", car.getId());
                timeMessage.addValue("time", message.getValue("time"));
                sendMessageToOtherCars(car, timeMessage);
            }
        }
    }

    private Message createUpdateMessageForCar(ServerCar car) {
        Message message = new Message(MessageType.UPDATE);
        message.addValue("x", car.getPosition().getX());
        message.addValue("y", car.getPosition().getY());
        message.addValue("rotation", car.getRotation());
        message.addValue("name", car.getName());
        message.addValue("id", car.getId());
        message.addValue("progress", car.getProgress());

        return message;
    }

    public void stop() {
        System.out.println("closing server...");
        app.setServerGame(null);

        if (server != null) {
            server.close();
        }
    }

    public void sendMessageStartGame() {
        if (!startMessageSent) {
            for (ServerCar car : carList) {
                Message message = new Message(MessageType.START_GAME);
                car.getConnection().sendMessage(message);
            }
            startMessageSent = true;
        }
    }

    private void sendMessageToOtherCars(ServerCar car, Message message) {
        for (ServerCar otherCar : carList) {
            if (otherCar.getConnection() != car.getConnection()) {
                otherCar.getConnection().sendMessage(message);
            }
        }
    }

    public Server getServer() {
        return server;
    }

}

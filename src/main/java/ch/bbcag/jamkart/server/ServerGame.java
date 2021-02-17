package ch.bbcag.jamkart.server;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.NetErrorMessages;
import ch.bbcag.jamkart.client.scenes.Navigator;
import ch.bbcag.jamkart.client.scenes.SceneBackToStart;
import ch.bbcag.jamkart.client.scenes.SceneType;
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

    private Navigator navigator;

    private Server server;
    private boolean startMessageSent = false;

    private List<ServerCar> carList = new CopyOnWriteArrayList<>();

    private List<Integer> availableIdList = new CopyOnWriteArrayList<>();
    private static final int NUMBER_OF_PLAYERS = 4;

    private float networkTickTimer = 0;

    public ServerGame(Navigator navigator) {
        this.navigator = navigator;

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            availableIdList.add(i);
        }
    }

    public void start(int port) {
        createServer(port);

        if(server != null) {
            createLoop();
        }
    }

    private void createServer(int port) {
        try {
            server = new Server(port);
            server.setServerMessageHandler(this::processMessage);
            server.start();
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
                if (startMessageSent || availableIdList.isEmpty()) {
                    // We don't allow connections if the game has already started or
                    // if there are no available ids left
                    connection.close();
                } else {
                    int y = availableIdList.get(0) * 50 + 65;

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
                break;
            case UPDATE:
                for (ServerCar car : carList) {
                    if (car.getConnection() == connection) {
                        car.setPosition(new Point(Float.parseFloat(message.getValue("x")), Float.parseFloat(message.getValue("y"))));
                        car.setRotation(Float.parseFloat(message.getValue("rotation")));

                        // Send the cars state to the other players
                        Message serverUpdateMessage = createUpdateMessageForCar(car);
                        sendMessageToOtherCars(car, serverUpdateMessage);
                    }
                }
                break;
            case TIME:
                for (ServerCar car : carList) {
                    if (car.getConnection() == connection) {
                        String time = message.getValue("time");
                        Message timeMessage = new Message(MessageType.TIME);
                        timeMessage.addValue("time", time);
                        sendMessageToOtherCars(car, timeMessage);
                    }
                }
            default:
                break;
        }
    }

    private Message createUpdateMessageForCar(ServerCar car) {
        Message message = new Message(MessageType.UPDATE);
        message.addValue("x", car.getPosition().getX());
        message.addValue("y", car.getPosition().getY());
        message.addValue("rotation", car.getRotation());
        message.addValue("name", car.getName());
        message.addValue("id", car.getId());

        return message;
    }

    public void stop() {
        server.close();
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

}

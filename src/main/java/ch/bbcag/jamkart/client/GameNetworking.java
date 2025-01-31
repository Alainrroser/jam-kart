package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.client.map.objects.GameObject;
import ch.bbcag.jamkart.client.map.objects.car.ClientOtherCar;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import ch.bbcag.jamkart.utils.Point;

import java.io.IOException;

public class GameNetworking {

    private ClientGame game;

    private Client client;

    private float networkTickTimer = 0.0f;

    public GameNetworking(ClientGame game) {
        this.game = game;
    }

    public void startClient(String ip, int port) throws IOException {
        client = new Client(ip, port);
        client.setMessageHandler(this::processMessage);
        client.start();
        sendJoinMessage();
    }

    private void sendJoinMessage() {
        Message message = new Message(MessageType.JOIN_GAME);
        message.addValue("name", game.getCar().getName());
        client.sendMessage(message);
    }

    public void sendTimeMessage() {
        Message timeMessage = new Message(MessageType.TIME);
        timeMessage.addValue("time", game.getTimer().getTime());
        client.sendMessage(timeMessage);
    }

    private void sendMyState() {
        Message message = new Message(MessageType.UPDATE);
        message.addValue("x", game.getCar().getPosition().getX());
        message.addValue("y", game.getCar().getPosition().getY());
        message.addValue("rotation", game.getCar().getRotation());
        message.addValue("progress", game.getCar().getProgress());

        client.sendMessage(message);
    }

    private void processMessage(Message message) {
        switch (message.getMessageType()) {
            case INITIAL_STATE:
                processInitialState(message);
                break;
            case UPDATE:
                processUpdate(message);
                break;
            case DISCONNECTED:
                processDisconnected(message);
                break;
            case START_GAME:
                processStartGame();
                break;
            case TIME:
                processTime(message);
                break;
            default:
                break;
        }
    }

    private void processInitialState(Message message) {
        float x = Float.parseFloat(message.getValue("x"));
        float y = Float.parseFloat(message.getValue("y"));

        game.getCar().setId(Integer.parseInt(message.getValue("id")));
        game.getCar().setPosition(new Point(x, y));
    }

    private void processUpdate(Message message) {
        int id = Integer.parseInt(message.getValue("id"));

        // Find the other car that this id belongs to
        ClientOtherCar otherCar = getOtherCarFromId(id);

        // Create the other car if the id couldn't be found
        if (otherCar == null) {
            otherCar = new ClientOtherCar(id, message.getValue("name"));
            System.out.println(otherCar.getName());
            game.getMap().getGameObjects().add(otherCar);
        }

        // Update the other car's state
        float x = Float.parseFloat(message.getValue("x"));
        float y = Float.parseFloat(message.getValue("y"));
        float rotation = Float.parseFloat(message.getValue("rotation"));
        int progress = Integer.parseInt(message.getValue("progress"));
        otherCar.interpolateState(new Point(x, y), rotation);
        otherCar.setProgress(progress);
    }

    private void processDisconnected(Message message) {
        int disconnectedId = Integer.parseInt(message.getValue("id"));
        ClientOtherCar otherCar = getOtherCarFromId(disconnectedId);

        if (otherCar != null) {
            game.getMap().getGameObjects().remove(otherCar);
        }
    }

    private void processStartGame() {
        game.startCountdown();
    }

    private void processTime(Message message) {
        int id = Integer.parseInt(message.getValue("id"));
        ClientOtherCar otherCar = getOtherCarFromId(id);

        if(otherCar != null) {
            float time = Float.parseFloat(message.getValue("time"));
            otherCar.finish(time);
            System.out.println("other car finished");
        }
    }

    private ClientOtherCar getOtherCarFromId(int id) {
        for (GameObject gameObject : game.getMap().getGameObjects()) {
            if (gameObject instanceof ClientOtherCar) {
                if (((ClientOtherCar) gameObject).getId() == id) {
                    return (ClientOtherCar) gameObject;
                }
            }
        }

        return null;
    }

    public void update(float deltaTimeInSec) {
        networkTickTimer += deltaTimeInSec;
        if (networkTickTimer >= Constants.NETWORK_TICK_TIME) {
            sendMyState();
            networkTickTimer = 0.0f;
        }
    }

    public boolean isDisconnected() {
        return client == null || client.isDisconnected();
    }

    public void closeClient() {
        if (client != null) {
            client.close();
        }
    }

}

package ch.bbcag.jamkart.client;

import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ClientGame {

    private Canvas canvas;
    private Client client;

    public ClientGame(Canvas canvas) {
        this.canvas = canvas;

        canvas.getGraphicsContext2D().setFill(Color.GREEN);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void load() {

    }

    public void start(String ip, int port, String name) {
        createClient(ip, port, name);
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

    public void stop() {
        client.close();
    }

    private void processMessage(Message message) {
        switch (message.getMessageType()) {
            default:
                break;
        }
    }

}

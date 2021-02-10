package ch.bbcag.jamkart.server;

import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.server.Server;
import ch.bbcag.jamkart.net.server.ServerMessageHandler;

import java.io.IOException;

public class ServerGame {

    private Server server;

    public void start(int port) {
        try {
            server = new Server(port);
            server.setServerMessageHandler(this::processMessage);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        server.close();
    }

    private void processMessage(Message message, Connection connection) {
        switch (message.getMessageType()) {
            case JOIN_GAME:
                System.out.println("new player joined the game!");
                System.out.println("name: " + message.getValue("name"));
                break;
            default:
                break;
        }
    }
}

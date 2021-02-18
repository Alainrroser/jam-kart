package ch.bbcag.jamkart.net.server;

import ch.bbcag.jamkart.net.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ServerMessageHandler serverMessageHandler;
    private List<Connection> connections = new CopyOnWriteArrayList<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        // Remove all disconnected clients from the list
        new Thread(() -> {
            while (!serverSocket.isClosed()) {
                for (Connection connection : connections) {
                    if (connection.isDisconnected()) {
                        System.out.println("client disconnected from server");
                        connections.remove(connection);
                    }
                }
            }
        }).start();

        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket);
                connection.setMessageHandler(message -> {
                    if (serverMessageHandler != null) {
                        serverMessageHandler.handle(message, connection);
                    }
                });
                connection.start();
                connections.add(connection);
            }
        } catch (IOException e) {
            System.err.println("couldn't accept new client: " + e.getMessage());
        }
    }

    public void setServerMessageHandler(ServerMessageHandler serverMessageHandler) {
        this.serverMessageHandler = serverMessageHandler;
    }

    public void close() {
        for (Connection connection : connections) {
            connection.close();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

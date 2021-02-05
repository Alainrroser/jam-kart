package ch.bbcag.jamkart.net.server;

import ch.bbcag.jamkart.net.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private List<Connection> connections;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            connections = new ArrayList<>();

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket);
                connections.add(connection);
                connection.sendMessage("Hallo");
                connection.sendMessage("Wi geits dir");
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }
}

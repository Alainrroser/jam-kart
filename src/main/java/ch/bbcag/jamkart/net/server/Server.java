package ch.bbcag.jamkart.net.server;

import ch.bbcag.jamkart.net.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ServerMessageHandler serverMessageHandler;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
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
            }
        } catch (IOException e) {
            System.err.println("Couldn't accept new client: " + e.getMessage());
        }
    }

    public ServerMessageHandler getServerMessageHandler() {
        return serverMessageHandler;
    }

    public void setServerMessageHandler(ServerMessageHandler serverMessageHandler) {
        this.serverMessageHandler = serverMessageHandler;
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

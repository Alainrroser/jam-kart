package ch.bbcag.jamkart;

import ch.bbcag.jamkart.net.server.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        Server server = new Server(1234);
        server.start();
    }
}

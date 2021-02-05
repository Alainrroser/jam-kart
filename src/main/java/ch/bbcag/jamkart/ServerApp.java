package ch.bbcag.jamkart;

import ch.bbcag.jamkart.net.server.Server;

public class ServerApp {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

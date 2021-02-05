package ch.bbcag.jamkart.net.client;

import ch.bbcag.jamkart.net.Connection;

import java.io.IOException;
import java.net.Socket;

public class Client extends Connection {
    public Client(String ip, int port) throws IOException {
        super(new Socket(ip, port));
    }
}

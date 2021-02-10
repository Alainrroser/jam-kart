package ch.bbcag.jamkart.net.server;

import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.net.Message;

@FunctionalInterface
public interface ServerMessageHandler {
    void handle(Message message, Connection connection);
}

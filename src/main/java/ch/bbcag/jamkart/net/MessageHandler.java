package ch.bbcag.jamkart.net;

@FunctionalInterface
public interface MessageHandler {
    void handle(Message message);
}

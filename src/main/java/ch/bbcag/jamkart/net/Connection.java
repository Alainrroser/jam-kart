package ch.bbcag.jamkart.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private MessageHandler messageHandler;

    private boolean disconnected = false;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) {
        try {
            String data = message.getStringFromMap();
            if (message.getData().containsKey(Message.TYPE)) {
                dataOutputStream.writeUTF(data);
                dataOutputStream.flush();
            } else {
                throw new IllegalArgumentException("The message has to contain a type");
            }
        } catch (IOException e) {
            System.err.println("couldn't write to socket: " + e.getMessage());
            disconnected = true;
        }
    }

    private void readMessage() throws IOException {
        if (socket.getInputStream().available() > 0) { // Are there more than 0 bytes available?
            Message message = new Message();
            String data = dataInputStream.readUTF();
            message.setMapFromString(data);

            if (messageHandler != null) {
                messageHandler.handle(message);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                readMessage();
            }
        } catch (IOException e) {
            System.err.println("couldn't read from socket: " + e.getMessage());
            disconnected = true;
        }
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

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

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) {
        try {
            String data = message.getStringFromMap();
            dataOutputStream.writeUTF(data);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessage() throws IOException {
        if (socket.getInputStream().available() > 0) {
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
            e.printStackTrace();
        }
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}

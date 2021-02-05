package ch.bbcag.jamkart.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
    }

    public void sendMessage(String text) throws IOException {
        dataOutputStream.writeUTF(text);
        dataOutputStream.flush();
    }

    private void readMessage() throws IOException {
        if (socket.getInputStream().available() > 0) {
            System.out.println(dataInputStream.readUTF());
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
}

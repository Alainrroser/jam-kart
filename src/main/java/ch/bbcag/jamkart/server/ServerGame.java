package ch.bbcag.jamkart.server;

import ch.bbcag.jamkart.common.GameLoop;
import ch.bbcag.jamkart.net.Connection;
import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.server.Server;
import ch.bbcag.jamkart.utils.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerGame {
    private Server server;
    private float timer = 0;
    private List<ServerCar> carList = new ArrayList<>();
    private int id = 0;

    public void start(int port) {
        try {
            server = new Server(port);
            server.setServerMessageHandler(this::processMessage);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new GameLoop() {
            @Override
            public void update(float deltaTimeInSec) {
                timer += deltaTimeInSec;
                if (timer >= 0.1) {
                    for (ServerCar car : carList) {
                        Message message = new Message(MessageType.UPDATE);
                        float x = car.getPosition().getX();
                        float y = car.getPosition().getY();
                        message.addValue("x", x);
                        message.addValue("y", y);
                        message.addValue("rotation", car.getRotation());
                        message.addValue("id", car.getId());
                        for (ServerCar otherCar : carList) {
                            if (otherCar.getConnection() != car.getConnection()) {
                                otherCar.getConnection().sendMessage(message);
                            }
                        }
                    }
                    timer = 0;
                }
            }
        }.start();
    }

    public void stop() {
        server.close();
    }

    private void processMessage(Message message, Connection connection) {
        switch (message.getMessageType()) {
            case JOIN_GAME:
                Message idMessage = new Message(MessageType.ID);
                idMessage.addValue("id", id);
                connection.sendMessage(idMessage);

                carList.add(new ServerCar(connection, id));
                id++;
                System.out.println("new player joined the game!");
                System.out.println("name: " + message.getValue("name"));
                break;
            case UPDATE:
                for (ServerCar car : carList) {
                    if (car.getConnection() == connection) {
                        float x = Float.parseFloat((String) message.getValue("x"));
                        float y = Float.parseFloat((String) message.getValue("y"));
                        float rotation = Float.parseFloat((String) message.getValue("rotation"));
                        Point position = new Point(x, y);
                        car.setPosition(position);
                        car.setRotation(rotation);
                    }
                }
                break;
            default:
                break;
        }
    }
}

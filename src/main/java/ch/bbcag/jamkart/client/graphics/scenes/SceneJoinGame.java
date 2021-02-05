package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.net.Message;
import ch.bbcag.jamkart.net.MessageType;
import ch.bbcag.jamkart.net.client.Client;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SceneJoinGame extends Scene {

    private Navigator navigator;
    private static Group rootNode = new Group();

    private TextField inputIp;
    private TextField inputPort;
    private TextField inputName;

    public SceneJoinGame(Navigator navigator) {
        super(rootNode);
        this.navigator = navigator;

        BorderPane pane = new BorderPane();
        pane.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-position: center center;"
        );

        VBox contentBox = new VBox(25);
        contentBox.setPadding(new Insets(50, 0, 0, 70));

        Button mainMenuBtn = new Button("Hauptmenü");
        mainMenuBtn.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        inputName = new TextField();
        inputName.setPromptText("Name eingeben:");
        inputName.setFocusTraversable(false);
        inputName.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        inputIp = new TextField();
        inputIp.setPromptText("IP eingeben:");
        inputIp.setFocusTraversable(false);
        inputIp.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        inputPort = new TextField();
        inputPort.setPromptText("Port eingeben:");
        inputPort.setFocusTraversable(false);
        inputPort.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        Button joinGame = new Button("Spiel beitreten");
        joinGame.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        contentBox.getChildren().addAll(mainMenuBtn, inputName, inputIp, inputPort, joinGame);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        mainMenuBtn.setOnAction(e -> navigator.navigateTo(SceneType.START)); // ip & Port
        joinGame.setOnAction(e -> joinGame()); // ip & Port
        pane.setMinSize(800, 600);
    }

    // @Jan -> noch port Prüfer, Ip Prüfer implementieren + Name einbinden
    private void joinGame() {
        try {
            Client client = new Client(inputIp.getText(), Integer.parseInt(inputPort.getText()));
            client.start();

            Message message = new Message(MessageType.JOIN_LOBBY);
            message.addData("name", "SomeClient");
            client.sendMessage(message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        navigator.navigateTo(SceneType.GAME);
    }
}

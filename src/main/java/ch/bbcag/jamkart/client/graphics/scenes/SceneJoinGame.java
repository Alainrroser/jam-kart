package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.client.graphics.scenes.validation.Validator;
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
import javafx.scene.text.Text;

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

        VBox ipBox = new VBox();
        inputIp = new TextField();
        Text ipError = new Text();
        ipError.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;"
        );
        VBox.setMargin(ipError, new Insets(-10, 0, 0, 15));
        inputIp.setPromptText("IP eingeben:");
        inputIp.setFocusTraversable(false);
        inputIp.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );
        ipBox.getChildren().addAll(ipError, inputIp);

        VBox portBox = new VBox();
        inputPort = new TextField();
        Text portError = new Text();
        portError.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;"
        );
        VBox.setMargin(portError, new Insets(-10, 0, 0, 15));
        inputPort.setPromptText("Port eingeben:");
        inputPort.setFocusTraversable(false);
        inputPort.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );
        portBox.getChildren().addAll(portError, inputPort);

        Button joinGame = new Button("Spiel beitreten");
        joinGame.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        contentBox.getChildren().addAll(mainMenuBtn, inputName, ipBox, portBox, joinGame);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        mainMenuBtn.setOnAction(e -> navigator.navigateTo(SceneType.START)); // ip & Port
        joinGame.setOnAction(e -> joinGame(ipError, portError)); // ip & Port
        pane.setMinSize(800, 600);
    }

    public void setIPError(Text errorMessage) {
        if (!Validator.validateIP(inputIp.getText())) {
            errorMessage.setText("invalide IP-Adresse");
        } else {
            errorMessage.setText("");
        }
    }

    public void setPortError(Text errorMessage) {
        if (Validator.validatePort(inputPort.getText())) {
            errorMessage.setText("");
        } else {
            errorMessage.setText("invalider Port");
        }
    }

    private void joinGame(Text ipError, Text portError) {
        if (Validator.validateIP(inputIp.getText()) && Validator.validatePort(inputPort.getText())) {
            try {
                Client client = new Client(inputIp.getText(), Integer.parseInt(inputPort.getText()));
                client.start();

                Message message = new Message(MessageType.JOIN_LOBBY);
                message.addValue("name", "SomeClient");
                client.sendMessage(message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            navigator.navigateTo(SceneType.GAME);
        } else {
            setIPError(ipError);
            setPortError(portError);
        }
    }
}

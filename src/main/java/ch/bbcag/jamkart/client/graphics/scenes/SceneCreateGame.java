package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.net.server.Server;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SceneCreateGame extends Scene {

    private Navigator navigator;
    private static Group rootNode = new Group();

    private TextField inputPort;
    private TextField inputName;

    public SceneCreateGame(Navigator navigator) {
        super(rootNode);
        this.navigator = navigator;

        BorderPane pane = new BorderPane();
        pane.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-position: center center;"
        );

        VBox contentBox = new VBox(45);
        contentBox.setPadding(new Insets(75, 0, 0, 70));

        Button mainMenuBtn = new Button("Hauptmenü");
        mainMenuBtn.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        inputName = new TextField();
        inputName.setPromptText("Name eingeben");
        inputName.setFocusTraversable(false);
        inputName.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        inputPort = new TextField();
        inputPort.setPromptText("Port eingeben");
        inputPort.setFocusTraversable(false);
        inputPort.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        Button createGame = new Button("Spiel erstellen");
        createGame.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        contentBox.getChildren().addAll(mainMenuBtn, inputName, inputPort, createGame);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        mainMenuBtn.setOnAction(e -> navigator.navigateTo(SceneType.START)); // ip & Port
        createGame.setOnAction(e -> createGame()); // ip & Port
        pane.setMinSize(800, 600);
    }


    // @Jan -> noch port Prüfer implementieren + Name einbinden
    private void createGame() {
        try {
            Server server = new Server(Integer.parseInt(inputPort.getText()));
            server.start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        navigator.navigateTo(SceneType.GAME);
    }
}

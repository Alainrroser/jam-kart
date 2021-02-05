package ch.bbcag.jamkart.client.graphics.scenes;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SceneJoinGame extends Scene {

    private Navigator navigator;
    private static Group rootNode = new Group();

    public SceneJoinGame(Navigator navigator) {
        super(rootNode);
        this.navigator = navigator;

        BorderPane pane = new BorderPane();
        pane.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-position: center center;"
        );

        VBox contentBox = new VBox(45);
        contentBox.setPadding(new Insets(50, 0, 0, 70));

        Button mainMenuBtn = new Button("Hauptmenü");
        mainMenuBtn.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        TextField inputIp = new TextField();
        inputIp.setPromptText("IP eingeben:");
        inputIp.setFocusTraversable(false);
        inputIp.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        TextField inputPort = new TextField();
        inputPort.setPromptText("Port eingeben");
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

        contentBox.getChildren().addAll(mainMenuBtn, inputIp, inputPort, joinGame);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        mainMenuBtn.setOnAction(e -> navigator.navigateTo(SceneType.START)); // ip & Port
        joinGame.setOnAction(e -> navigator.navigateTo(SceneType.GAME)); // ip & Port
        pane.setMinSize(800, 600);
    }
}

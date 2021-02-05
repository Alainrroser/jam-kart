package ch.bbcag.jamkart.client.graphics.scenes;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SceneStart extends Scene {
    private Navigator navigator;
    private static Group rootNode = new Group();

    public SceneStart(Navigator navigator) {
        super(rootNode);
        this.navigator = navigator;

        BorderPane pane = new BorderPane();
        pane.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-position: center center;"
        );

        VBox contentBox = new VBox(45);

        HBox header = new HBox();
        Text headerTxt = new Text("Hauptmenü");
        headerTxt.setFont(Font.font ("Verdana", 42));
        headerTxt.setStyle(
                "-fx-font-weight: bolder;" +
                        "-fx-fill: linear-gradient(#e61f00, #3b0d06);" +
                        "-fx-stroke: black;" +
                        "-fx-stroke-width: 1px;"
        );

        header.getChildren().add(headerTxt);
        header.setPadding(new Insets(0,0,0,10));

        contentBox.setPadding(new Insets(140, 0, 0, 70));
        Button createGame = new Button("Spiel erstellen");
        createGame.setStyle(
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

        contentBox.getChildren().addAll(header, createGame, joinGame);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        createGame.setOnAction(e -> navigator.navigateTo(SceneType.CREATE));
        joinGame.setOnAction(e -> navigator.navigateTo(SceneType.JOIN));
        pane.setMinSize(800, 600);
    }
}
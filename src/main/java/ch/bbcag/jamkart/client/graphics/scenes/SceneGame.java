package ch.bbcag.jamkart.client.graphics.scenes;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SceneGame extends Scene {
    private Navigator navigator;
    private static Group rootNode = new Group();

    public SceneGame(Navigator navigator) {
        super(rootNode);
        this.navigator = navigator;

        BorderPane pane = new BorderPane();
        pane.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-position: center center;"
        );

        VBox btnBox = new VBox(45);
        btnBox.setPadding(new Insets(180, 0, 0, 70));
        Text header = new Text("Game");
        Button createGame = new Button("Zurück");
        createGame.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        btnBox.getChildren().add(createGame);

        pane.setLeft(btnBox);
        rootNode.getChildren().add(pane);
        createGame.setOnAction(e -> navigator.navigateTo(SceneType.START)); // Port
        pane.setMinSize(800, 600);
    }
}

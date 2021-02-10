package ch.bbcag.jamkart.client.graphics.scenes;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SceneBackToStart extends Scene {
    private Navigator navigator;
    private static Group rootNode = new Group();

    public SceneBackToStart(Navigator navigator) {
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

        VBox errorBox = new VBox();
        Text errorMessage = new Text("Falsche Eingabe");
        Button mainMenuBtn = new Button("Hauptmenü");
        mainMenuBtn.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );
        errorBox.getChildren().addAll(errorMessage, mainMenuBtn);

        contentBox.getChildren().add(errorBox);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        mainMenuBtn.setOnAction(e -> navigator.navigateTo(SceneType.START));
        pane.setMinSize(800, 600);
    }
}

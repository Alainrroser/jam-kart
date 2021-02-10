package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.JamKartApp;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SceneBackToStart extends Scene {
    private JamKartApp app;
    private static Group rootNode = new Group();

    public SceneBackToStart(JamKartApp app) {
        super(rootNode);
        this.app = app;

        BorderPane pane = new BorderPane();
        pane.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-position: center center;"
        );

        VBox contentBox = new VBox(45);
        contentBox.setPadding(new Insets(75, 0, 0, 70));

        VBox errorBox = new VBox();
        Text errorMessage = new Text("Konnte nicht mit dem Server verbinden");
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
        mainMenuBtn.setOnAction(e -> app.getNavigator().navigateTo(SceneType.START));
        pane.setMinSize(800, 600);
    }
}

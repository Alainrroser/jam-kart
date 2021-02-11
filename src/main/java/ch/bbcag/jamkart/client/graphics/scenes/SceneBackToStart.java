package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.JamKartApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SceneBackToStart extends Scene {
    private JamKartApp app;
    private static VBox rootNode = new VBox(10.0f);

    private static final String MESSAGE =
            "Konnte nicht mit dem Server verbinden!\n" +
            "Bitte überprüfe die IP-Adresse und den Port";

    public SceneBackToStart(JamKartApp app) {
        super(rootNode);
        this.app = app;

        rootNode.setAlignment(Pos.CENTER);
        rootNode.setStyle(
                "-fx-background-image: url('main_menu.png');" +
                "-fx-background-repeat: stretch;" +
                "-fx-background-position: center center;" +
                "-fx-font-size: 20pt;"
        );

        VBox background = new VBox(20);
        background.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.75), null, null)));
        background.setPadding(new Insets(50, 0, 50, 0));
        background.setAlignment(Pos.CENTER);

        Text errorMessage = new Text(MESSAGE);
        errorMessage.setTextAlignment(TextAlignment.CENTER);
        background.getChildren().add(errorMessage);

        Button mainMenuBtn = new Button("Hauptmenü");
        mainMenuBtn.setStyle(
                "-fx-background-radius: 3em;" +
                "-fx-min-width: 300px;" +
                "-fx-min-height: 45px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 18px;"
        );
        background.getChildren().add(mainMenuBtn);

        rootNode.getChildren().add(background);

        mainMenuBtn.setOnAction(e -> app.getNavigator().navigateTo(SceneType.START, false));
        rootNode.setMinSize(800, 600);
    }
}

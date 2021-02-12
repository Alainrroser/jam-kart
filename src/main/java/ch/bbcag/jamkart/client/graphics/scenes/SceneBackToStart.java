package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SceneBackToStart extends Scene {
    private static VBox rootNode = new VBox(10.0f);

    private Text errorMessage;

    public SceneBackToStart(JamKartApp app) {
        super(rootNode);

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

        errorMessage = new Text();
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
        rootNode.setMinSize(Constants.START_WINDOW_WIDTH, Constants.START_WINDOW_HEIGHT);
    }

    public void setMessage(String message) {
        errorMessage.setText(message);
    }
}

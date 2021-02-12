package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.client.graphics.scenes.validation.Validator;
import ch.bbcag.jamkart.server.ServerGame;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SceneCreateGame extends Scene {

    private JamKartApp app;
    private static Group rootNode = new Group();

    private TextField inputPort;
    private TextField inputName;

    public SceneCreateGame(JamKartApp app) {
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

        Button mainMenuBtn = new Button("Hauptmenü");
        mainMenuBtn.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        VBox nameBox = new VBox();
        inputName = new TextField();
        Text nameError = new Text();
        nameError.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;"
        );
        VBox.setMargin(nameError, new Insets(-10, 0, 0, 15));
        inputName.setPromptText("Name eingeben:");
        inputName.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );
        nameBox.getChildren().addAll(nameError, inputName);

        VBox portBox = new VBox();
        inputPort = new TextField();
        Text portError = new Text();
        portError.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;"
        );
        VBox.setMargin(portError, new Insets(-10, 0, 0, 15));
        inputPort.setPromptText("Port eingeben:");
        inputPort.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );
        portBox.getChildren().addAll(portError, inputPort);

        Button createGame = new Button("Spiel erstellen");
        createGame.setStyle(
                "-fx-background-radius: 3em;" +
                        "-fx-min-width: 300px;" +
                        "-fx-min-height: 45px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18px;"
        );

        contentBox.getChildren().addAll(mainMenuBtn, nameBox, portBox, createGame);

        pane.setLeft(contentBox);
        rootNode.getChildren().add(pane);
        mainMenuBtn.setOnAction(e -> app.getNavigator().navigateTo(SceneType.START, false));
        createGame.setOnAction(e -> validateInputAndCreateGame(portError, nameError));
        pane.setMinSize(Constants.START_WINDOW_WIDTH, Constants.START_WINDOW_HEIGHT);
    }

    private void setErrorMessage(boolean isCorrect, Text errorMessage) {
        if (isCorrect) {
            errorMessage.setText("");
        } else {
            errorMessage.setText("invalide Eingabe");
        }
    }

    private void validateInputAndCreateGame(Text portError, Text nameError) {
        if (Validator.validatePort(inputPort.getText()) && Validator.validateName(inputName.getText())) {
            setErrorMessage(true, portError);
            setErrorMessage(true, nameError);
            createGame();
        } else {
            setErrorMessage(Validator.validatePort(inputPort.getText()), portError);
            setErrorMessage(Validator.validateName(inputName.getText()), nameError);
        }
    }


    private void createGame() {
        int port = Integer.parseInt(inputPort.getText());

        ServerGame serverGame = new ServerGame();
        serverGame.start(port);
        app.setServerGame(serverGame);

        SceneGame newScene = (SceneGame) app.getNavigator().getScene(SceneType.GAME);
        ClientGame clientGame = new ClientGame(newScene.getCanvas(), newScene.getKeyEventHandler(), app.getNavigator(), app);
        clientGame.load();

        app.getNavigator().navigateTo(SceneType.GAME, true);

        clientGame.start("localhost", port, inputName.getText());
        app.setClientGame(clientGame);
    }
}

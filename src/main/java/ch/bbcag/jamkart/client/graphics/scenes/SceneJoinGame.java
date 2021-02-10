package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.graphics.scenes.validation.Validator;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SceneJoinGame extends Scene {

    private JamKartApp app;
    private static Group rootNode = new Group();

    private TextField inputIp;
    private TextField inputPort;
    private TextField inputName;

    public SceneJoinGame(JamKartApp app) {
        super(rootNode);
        this.app = app;

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
        mainMenuBtn.setOnAction(e -> app.getNavigator().navigateTo(SceneType.START)); // ip & Port
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
            int port = Integer.parseInt(inputPort.getText());

            SceneGame sceneGame = (SceneGame) app.getNavigator().getScene(SceneType.GAME);
            sceneGame.enter(inputIp.getText(), port, inputName.getText());
            app.getNavigator().navigateTo(SceneType.GAME);
        } else {
            setIPError(ipError);
            setPortError(portError);
        }
    }
}

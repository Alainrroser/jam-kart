package ch.bbcag.jamkart;

import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.client.graphics.scenes.*;
import ch.bbcag.jamkart.server.ServerGame;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JamKartApp extends Application {

    private Navigator navigator;
    private ClientGame clientGame;
    private ServerGame serverGame;

    @Override
    public void start(Stage primaryStage) {
        navigator = new Navigator(primaryStage);
        navigator.registerScene(SceneType.START, new SceneStart(this));
        navigator.registerScene(SceneType.GAME, new SceneGame(this));
        navigator.registerScene(SceneType.JOIN, new SceneJoinGame(this));
        navigator.registerScene(SceneType.CREATE, new SceneCreateGame(this));
        navigator.navigateTo(SceneType.START);

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/icon.png"));
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public void startClientGame(ClientGame game) {
        this.clientGame = game;
    }

    public void startServerGame(ServerGame game) {
        this.serverGame = game;
    }

    @Override
    public void stop() {
        if(clientGame != null) {
            clientGame.stop();
            System.out.println("stopped client");
        }

        if(serverGame != null) {
            serverGame.stop();
            System.out.println("stopped server");
        }

        System.exit(0);
    }

}

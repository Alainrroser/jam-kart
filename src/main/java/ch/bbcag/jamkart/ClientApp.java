package ch.bbcag.jamkart;

import ch.bbcag.jamkart.client.graphics.scenes.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Navigator navigator = new Navigator(primaryStage);
        navigator.registerScene(SceneType.START, new SceneStart(navigator));
        navigator.registerScene(SceneType.GAME, new SceneGame(navigator));
        navigator.registerScene(SceneType.JOIN, new SceneJoinGame(navigator));
        navigator.registerScene(SceneType.CREATE, new SceneCreateGame(navigator));
        navigator.navigateTo(SceneType.START);

        primaryStage.setResizable(false);
    }
}

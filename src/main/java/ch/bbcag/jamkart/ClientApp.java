package ch.bbcag.jamkart;

import ch.bbcag.jamkart.client.graphics.scenes.Navigator;
import ch.bbcag.jamkart.client.graphics.scenes.SceneGame;
import ch.bbcag.jamkart.client.graphics.scenes.SceneStart;
import ch.bbcag.jamkart.client.graphics.scenes.SceneType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Navigator navigator = new Navigator(primaryStage);
        navigator.registerScene(SceneType.START, new SceneStart(navigator));
        navigator.registerScene(SceneType.GAME, new SceneGame(navigator));
        navigator.navigateTo(SceneType.START);
    }
}

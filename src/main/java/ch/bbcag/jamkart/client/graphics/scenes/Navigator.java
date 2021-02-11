package ch.bbcag.jamkart.client.graphics.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Navigator {

    private Stage stage;
    private Map<SceneType, Scene> sceneMap = new HashMap<>();

    public Navigator(Stage stage){
        this.stage = stage;
    }

    public void navigateTo(SceneType sceneType, boolean centerStage){
        stage.setScene(sceneMap.get(sceneType));
        stage.show();

        if(centerStage) {
            stage.centerOnScreen();
        }
    }

    public void registerScene(SceneType sceneType, Scene scene){
        sceneMap.put(sceneType, scene);
    }

    public Scene getScene(SceneType type) {
        return sceneMap.get(type);
    }
}

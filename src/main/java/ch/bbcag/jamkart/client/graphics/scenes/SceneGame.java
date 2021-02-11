package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.KeyEventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class SceneGame extends Scene {
    private JamKartApp app;
    private static Group rootNode = new Group();

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;

    public SceneGame(JamKartApp app) {
        super(rootNode);
        this.app = app;

        canvas = new Canvas(1500, 800);
        rootNode.getChildren().add(canvas);

        keyEventHandler = new KeyEventHandler();
        setOnKeyPressed(keyEventHandler);
        setOnKeyReleased(keyEventHandler);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public KeyEventHandler getKeyEventHandler() {
        return keyEventHandler;
    }
}

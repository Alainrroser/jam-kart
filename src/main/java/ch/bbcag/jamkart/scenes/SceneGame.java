package ch.bbcag.jamkart.scenes;

import ch.bbcag.jamkart.Constants;
import ch.bbcag.jamkart.client.KeyEventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class SceneGame extends Scene {
    private static Group rootNode = new Group();

    private Canvas canvas;
    private KeyEventHandler keyEventHandler;

    public SceneGame() {
        super(rootNode);

        canvas = new Canvas(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);
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

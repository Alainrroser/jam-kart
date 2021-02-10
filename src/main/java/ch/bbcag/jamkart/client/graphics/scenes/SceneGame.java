package ch.bbcag.jamkart.client.graphics.scenes;

import ch.bbcag.jamkart.JamKartApp;
import ch.bbcag.jamkart.client.ClientGame;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class SceneGame extends Scene {
    private JamKartApp app;
    private static Group rootNode = new Group();

    private Canvas canvas;
    private ClientGame game;

    public SceneGame(JamKartApp app) {
        super(rootNode);
        this.app = app;

        canvas = new Canvas(1500, 800);
        rootNode.getChildren().add(canvas);
    }

    public void enter(String ip, int port, String name) {
        game = new ClientGame(canvas);
        game.load();
        game.start(ip, port, name);

        app.startClientGame(game);
    }
}

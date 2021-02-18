package ch.bbcag.jamkart;

import ch.bbcag.jamkart.client.ClientGame;
import ch.bbcag.jamkart.scenes.*;
import ch.bbcag.jamkart.server.ServerGame;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class JamKartApp extends Application {

    private Navigator navigator;
    private ClientGame clientGame;
    private ServerGame serverGame;

    private MediaPlayer mediaPlayer; // Must be added here so it doesn't get collected by garbage collector

    @Override
    public void start(Stage primaryStage) {
        navigator = new Navigator(primaryStage);
        navigator.registerScene(SceneType.START, new SceneStart(this));
        navigator.registerScene(SceneType.GAME, new SceneGame());
        navigator.registerScene(SceneType.JOIN, new SceneJoinGame(this));
        navigator.registerScene(SceneType.CREATE, new SceneCreateGame(this));
        navigator.registerScene(SceneType.BACK_TO_START, new SceneBackToStart(this));
        navigator.navigateTo(SceneType.START, true);

        primaryStage.setTitle("JAM-Kart");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/icon.png")); // Setting the window icon

        startMusic();
    }

    private void startMusic() {
        try {
            Media music = new Media(getClass().getResource("/music.wav").toURI().toString());
            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Enable looping
            mediaPlayer.play();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ClientGame getClientGame() {
        return clientGame;
    }

    public void setClientGame(ClientGame game) {
        this.clientGame = game;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void setServerGame(ServerGame game) {
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

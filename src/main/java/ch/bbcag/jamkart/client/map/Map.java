package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.client.map.objects.GameObject;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Map {

    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private Road road = new Road();

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Road getRoad() {
        return road;
    }

}
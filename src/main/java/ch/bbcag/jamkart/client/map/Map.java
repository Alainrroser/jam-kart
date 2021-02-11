package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.client.map.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<GameObject> gameObjects = new ArrayList<>();
    private Road road = new Road();

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Road getRoad() {
        return road;
    }

}
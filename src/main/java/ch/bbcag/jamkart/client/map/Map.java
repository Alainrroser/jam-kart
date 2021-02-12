package ch.bbcag.jamkart.client.map;

import ch.bbcag.jamkart.client.map.objects.GameObject;

import java.util.ArrayList;
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

    @SuppressWarnings("unchecked")
    public <T> List<T> getAllGameObjectsFromType(Class<T> classToFind){
        List<T> resultList = new ArrayList<T>();
        for (GameObject gameobject : gameObjects) {
            if (classToFind.equals(gameobject.getClass()))
                resultList.add((T)gameobject);
        }

        return resultList;
    }
}
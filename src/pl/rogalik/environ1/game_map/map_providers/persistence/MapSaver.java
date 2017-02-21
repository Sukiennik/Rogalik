package pl.rogalik.environ1.game_map.map_providers.persistence;

import pl.rogalik.environ1.game_map.GameMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class MapSaver {

    private String fileName = "test.bin";

    public MapSaver(){

    }

    public void saveMapToFile(GameMap gameMap) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeObject(gameMap);
        objectOutputStream.close();
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
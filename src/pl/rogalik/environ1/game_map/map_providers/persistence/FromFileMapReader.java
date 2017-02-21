package pl.rogalik.environ1.game_map.map_providers.persistence;

import pl.rogalik.environ1.game_map.map_providers.MapProviding;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;

import java.io.*;
import java.util.List;

public class FromFileMapReader implements MapProviding {

	private String fileName = "test.bin";
	private Tile[][] map;
	private List<Entity> entitiesList;
	
	public FromFileMapReader() {
		
        ObjectInputStream objectInputStream = null;
        GameMap gameMapRead = null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
		} catch (IOException e) {
			/** TODO **/
			e.printStackTrace();
		}

        try {
			gameMapRead = (GameMap) objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			/** TODO **/
			e1.printStackTrace();
		}

        try {
			objectInputStream.close();
		} catch (IOException e) {
			/** TODO **/
			e.printStackTrace();
		}
		this.map = gameMapRead.getMap();
		this.entitiesList = gameMapRead.getEntities();
	}


	@Override
	public Tile[][] getMap() {
		return this.map;
	}


	@Override
	public List<Entity> getEntities() {
		return this.entitiesList;
	}


	public static class MapSaver {

        private String fileName = "test.bin";

        public MapSaver(){

        }

        public void saveMapToFile(GameMap gameMap) throws IOException, ClassNotFoundException {

            ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(fileName));


            objectOutputStream.writeObject(gameMap);
            objectOutputStream.close();


        }
    }
}

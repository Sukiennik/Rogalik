package pl.rogalik.environ1.game_map.map_providers;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;

public interface MapProviding {

	Tile[][] getMap();
	java.util.List<Entity> getEntities();

	
}
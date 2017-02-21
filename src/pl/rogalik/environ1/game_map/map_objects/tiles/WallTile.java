package pl.rogalik.environ1.game_map.map_objects.tiles;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;

public class WallTile extends Tile{

	private static final long serialVersionUID = 1L;

	public WallTile(){
		super();
		this.type = Type.WALL;
	}

	@Override
	public String toString() {
		if(this.isDoor()){
			return ">";}
		return "#";
	}


    public boolean isDoor(){
		if (this.getEntity().isPresent()){
            if (this.getEntity().get().getType() == EntityType.DOOR)
                return true;
		}
		return false;
	}

}

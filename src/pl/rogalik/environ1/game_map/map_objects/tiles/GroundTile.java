package pl.rogalik.environ1.game_map.map_objects.tiles;

public class GroundTile extends Tile{


	private static final long serialVersionUID = 1L;

	public GroundTile(){
		super();
		this.type = Type.GROUND;
	}

	@Override
	public String toString() {

		if (this.getEntity().isPresent()) {
			if (this.getEntity().get().getType().isCharacterType())
				return "C";
			else if (this.getEntity().get().getType().isItemType())
				return "I";
		}
        return ".";

	}
}
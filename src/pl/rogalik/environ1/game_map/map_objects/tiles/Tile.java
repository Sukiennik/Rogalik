package pl.rogalik.environ1.game_map.map_objects.tiles;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;

import java.io.Serializable;
import java.util.Optional;

public abstract class Tile implements Serializable{

	private static final long serialVersionUID = 1L;

	protected boolean shadowed = true;
	protected Entity entity;
	protected Type type;

	public enum Type{
	    GROUND(false),
        WALL(true);

	    private boolean isWall;

	    Type(boolean isWall){
	        this.isWall = isWall;
        }

        public boolean isWall(){
	        return this.isWall;
        }
    }

	public Tile(){
		this.entity = null;
	}

	public void setEntity(Entity entity){
        this.entity = entity;

    }

    public Optional<Entity> getEntity(){
        return Optional.ofNullable(this.entity);
    }

    public void removeEntity(){
        this.entity = null;
    }

    public void unshadow(){
        this.shadowed = false;
    }

    public boolean isWall(){
        return this.type.isWall();
    }

    public boolean isEmpty() {
        return entity == null;
    }
}

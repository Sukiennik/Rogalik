package pl.rogalik.environ1.game_map.map_objects.entities;

import pl.rogalik.environ1.game_map.GameMap;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Entity implements Serializable{
	
	private static final long serialVersionUID = 1L;

    private int ID;
    private int xPosition;
    private int yPosition;
    private EntityType type;
    private GameMap gameMap = null;

	
	public Entity(int x, int y, EntityType type, int ID){
		this.xPosition = x;
		this.yPosition = y;
		this.type = type;
        this.ID = ID;
	}

	public void setGameMap(GameMap gameMap){
        this.gameMap = gameMap;
    }

    public GameMap getGameMap(){
        return this.gameMap;
    }

    public int getxPosition(){
        return this.xPosition;
    }

    public int getyPosition(){
        return this.yPosition;
    }

    public EntityType getType(){
        return this.type;
    }



    public boolean move(int x, int y){

        if (this.gameMap.getObjectAt(x,y).isPresent()) {
            this.gameMap.getObjectAt(this.xPosition, this.yPosition).get().removeEntity();
            this.gameMap.getObjectAt(x, y).get().setEntity(this);

            this.xPosition = x;
            this.yPosition = y;

            if (this.getType().equals(EntityType.HERO)) {
                this.getGameMap().setHeroPosition(x, y);
            }

            return true;
        }
        else
            return false;

    }

    public boolean removeThisEntity(){
        List<Entity> entities = this.gameMap.getEntities();
        boolean killed = false;

        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()){
            Entity e = iterator.next();
            if(this.equals(e)) {
                iterator.remove();
                killed = true;
            }
        }


        if (this.gameMap.getObjectAt(this.xPosition, this.yPosition).isPresent()) {
            this.gameMap.getObjectAt(this.xPosition, this.yPosition).get().removeEntity();
        }

        this.gameMap = null;

        return killed;
    }


    public int getID(){
        return this.ID;
    }


}

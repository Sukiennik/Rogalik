package pl.rogalik.environ1.game_map;

import pl.rogalik.environ1.game_map.map_providers.persistence.FromFileMapReader;
import pl.rogalik.environ1.game_map.map_providers.MapProviding;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public class GameMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Entity> entities;
	private Tile[][] map;

	private Tile heroPosition;
    private static MapFabric fabric = new MapFabric();

    public GameMap(){ this(fabric.getGenerator()); }
	public GameMap(GameMap gameMap){
		this(gameMap.getEntities(), gameMap.getMap());
	}

	public GameMap(MapProviding mapGenerator){
		this(mapGenerator.getEntities(), mapGenerator.getMap());
	}
	
	public GameMap(FromFileMapReader f){
		this(f.getEntities(), f.getMap());
	}

	private GameMap(List<Entity> entities, Tile[][] map) {
		this.entities = entities;
		this.map = map;
        for (Entity entity : this.entities){
            entity.setGameMap(this);
            if (entity.getType() == EntityType.HERO){
                this.setHeroPosition(entity.getxPosition(), entity.getyPosition());
            }
        }
	}

    public List<Entity> getEntities(){
        return this.entities;
    }

	public Tile[][] getMap(){
		return this.map;
	}

	
	/***** Na potrzeby testowania *****/

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[0].length; j++){
				s.append(map[i][j].toString());
			}
			s.append("\n");

		}
		return s.toString();
	}

	/*** Funkcje dla Logiki ***/

    public Optional<Tile> getObjectAt(int x, int y){
        Tile tile;
        try{
            tile = map[x][y];
        }
        catch (ArrayIndexOutOfBoundsException e){
            tile = null;
        }
        return Optional.ofNullable(tile);
    }

    /** Zwraca obszar włącznie z polami o współrzędnych (x1, y1) oraz (x2, y2) **/
    public Optional<Tile>[][] getArea (int x1, int y1, int x2, int y2){

        Optional<Tile>[][] area = new Optional[Math.abs(x2-x1)+1][Math.abs(y2-y1)+1];

        for(int i = ((x1 < x2) ? x1 : x2); i <= ((x1 > x2) ? x1 : x2); i++){
            for (int j = ((y1 < y2) ? y1 : y2); j <= ((y1 > y2) ? y1 : y2); j++) {
                area[i - ((x1 < x2) ? x1 : x2)][j - ((y1 < y2) ? y1 : y2)] = this.getObjectAt(i, j);
            }
        }
        return area;
    }

    public Tile getHeroPosition(){
        return this.heroPosition;
    }

    public void setHeroPosition(int x, int y){
        this.heroPosition = this.getObjectAt(x, y).get();
    }

    public void getNewMap(){
        MapProviding mapGen = fabric.getGenerator();
        this.entities = mapGen.getEntities();
        this.map = mapGen.getMap();
        for (Entity entity : this.entities){
            entity.setGameMap(this);
            if (entity.getType() == EntityType.HERO){
                this.setHeroPosition(entity.getxPosition(), entity.getyPosition());
            }
        }
    }

}


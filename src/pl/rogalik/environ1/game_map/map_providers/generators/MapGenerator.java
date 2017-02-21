package pl.rogalik.environ1.game_map.map_providers.generators;

import pl.rogalik.environ1.game_map.map_providers.MapProviding;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.GroundTile;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.environ1.game_map.map_objects.tiles.WallTile;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;

import java.util.*;

public abstract class MapGenerator implements MapProviding {

	protected int width;
	protected int height;
	protected double wallsPercent;
	protected double entityPercent;
	private String seed = String.valueOf(System.currentTimeMillis());
	protected Random random = new Random(seed.hashCode());

	protected int[][] map;
	protected List<Entity> entityList;


    public MapGenerator(int width, int height){

        if (width < 15)
            width = 200;
        if (height < 15)
            height = 100;
		
		this.width = width;
		this.height = height;
		this.wallsPercent = 0.4;
		this.entityPercent = 0.12; // wartość zwiększona w celu optymalizacji algorytmu
                                  // (później brany jest kwadrat tej wartości)
		
		generateMap();
        generateEntities();

	}

	protected void generateMap(){}
	
	
	/** Generowanie postaci i obiektów - trzymane są w liście, a odnośniki do nich - w danych Tiles **/

	protected void generateEntities(){
		
		entityList = new ArrayList<Entity>();
		
		int heightStep =  (int)(this.height * this.entityPercent);
        int widthStep =  (int)(this.width * this.entityPercent);
        int ID = 0;

        /** Umiejscowienie bohatera **/

        entityList.add(generateHero(ID++));
        entityList.add(setDoor(ID++));

        for (int loop = 1; loop <= (int)(1 / this.entityPercent); loop++){

            if (loop == 1){
                /** Na początku najbliżej bohatera znajduje się wyłącznie kilka małych Itemów **/
                for (int i = 0; i <= loop*widthStep; i++){
                    for (int j = 0; j <= loop*heightStep; j++){
                        if (map[i][j] == 0){
                            if (random.nextDouble() <= this.entityPercent) {
                                Entity item = new Entity(i, j, EntityType.SMALL_ITEM, ID++);
                                entityList.add(item);
                                map[i][j] = 3;
                            }
                        }
                    }
                }
            }

            else if (loop == (int)(1 / this.entityPercent)){
                /** Najbliżej drzwi znajdują się wyłącznie duże potwory **/
                for (int i = (loop-2)*widthStep; i < loop*widthStep; i++){
                    for (int j = (loop-2)*heightStep; j < loop*heightStep; j++){
                        if (map[i][j] == 0){
                            if (random.nextDouble() <= this.entityPercent * this.entityPercent) {
                                Entity character = new Entity(i, j, EntityType.BIG_MONSTER, ID++);
                                entityList.add(character);
                                map[i][j] = 3;
                            }
                        }
                    }
                }
            }

            else {
                /** Nie ma znaczenia dobór Itemów i Potworów **/
                if (3*loop <= (int)(1 / this.entityPercent)){
                    for (int i = (loop-1)*widthStep; i < (loop+1)*widthStep; i++){
                        for (int j = (loop-1)*heightStep; j < (loop+1)*heightStep; j++){
                            if (map[i][j] == 0){
                                if (random.nextDouble() <= this.entityPercent * this.entityPercent) {
                                    if (random.nextDouble() < 0.875){
                                        Entity character = new Entity(i, j, EntityType.SMALL_MONSTER, ID++);
                                        entityList.add(character);
                                        map[i][j] = 3;
                                    }
                                    else{
                                        Entity item = new Entity(i, j, EntityType.SMALL_ITEM, ID++);
                                        entityList.add(item);
                                        map[i][j] = 3;
                                    }
                                }
                            }
                        }
                    }
                }

                else if (3*loop <= (int)(2 / this.entityPercent)){
                    /** MEDIUM ITEMS & MONSTERS **/
                    for (int i = (loop-1)*widthStep; i < (loop+1)*widthStep; i++){
                        for (int j = (loop-1)*heightStep; j < (loop+1)*heightStep; j++){
                            if (map[i][j] == 0){
                                if (random.nextDouble() <= this.entityPercent * this.entityPercent) {
                                    if (random.nextDouble() < 0.875){
                                        Entity character = new Entity(i, j, EntityType.MEDIUM_MONSTER, ID++);
                                        entityList.add(character);
                                        map[i][j] = 3;
                                    }
                                    else{
                                        Entity item = new Entity(i, j, EntityType.MEDIUM_ITEM, ID++);
                                        entityList.add(item);
                                        map[i][j] = 3;
                                    }
                                }
                            }
                        }
                    }
                }

                else{
                    /** BIG ITEMS & MONSTERS **/
                    for (int i = (loop-1)*widthStep; i < (loop+1)*widthStep; i++){
                        for (int j = (loop-1)*heightStep; j < (loop+1)*heightStep; j++){
                            if (map[i][j] == 0){
                                if (random.nextDouble() <= this.entityPercent * this.entityPercent) {
                                    if (random.nextDouble() < 0.875){
                                        Entity character = new Entity(i, j, EntityType.BIG_MONSTER, ID++);
                                        entityList.add(character);
                                        map[i][j] = 3;
                                    }
                                    else{
                                        Entity item = new Entity(i, j, EntityType.BIG_ITEM, ID++);
                                        entityList.add(item);
                                        map[i][j] = 3;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    /** Dodatkowy potwór przy drzwiach **/
        setFinalMonster(ID++);
	}

	protected Entity generateHero(int ID){
        for (int i = 1; i <= this.width-1; i++){
            for (int j = 1; j <= this.height-1; j++){
                if (map[i][j] == 0){
                    if (map[i-1][j]+map[i][j-1]+map[i][j+1] < 3) {
                        map[i][j] = 3;
                        Entity character = new Entity(i, j, EntityType.HERO, ID++);
                        return character;
                    }
                }
            }
        }

        return null;
    }


    protected Entity setDoor(int ID){
        for (int i = this.width-1; i >= 1; i--){
            for (int j = this.height-1; j >= 1; j--){
                if ((map[i][j] == 1 && map[i][j-1] == 0) || (map[i][j] == 1 && map[i-1][j] == 0)){
                    if (map[i-1][j]+map[i][j-1]+map[i][j+1] < 3) {
                        map[i][j] = 2;
                        Entity door = new Entity(i, j, EntityType.DOOR, ID++);
                        return door;
                    }
                }
            }
        }
        return null;
    }

    protected void setFinalMonster(int ID){

        for (int i = this.width-1; i >= 1; i--){
            for (int j = this.height-1; j >= 1; j--){
                if (map[i][j] == 0) {
                    Entity character = new Entity(i, j, EntityType.BIG_MONSTER, ID++);
                    entityList.add(character);
                    map[i][j] = 3;
                    return;
                }
            }
        }
    }
	

	
	public Tile[][] getMap() {
		
		Tile[][] tilesMap = new Tile[width][height];

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (map[i][j] == 0 || map[i][j] == 3)
					tilesMap[i][j] = new GroundTile();
				else if (map[i][j] == 1)
					tilesMap[i][j] = new WallTile();
				else if (map[i][j]== 2) {
					WallTile doorTile = new WallTile();
					Entity door = this.entityList.stream().filter(e -> e.getType() == EntityType.DOOR).findFirst().get();
                    doorTile.setEntity(door);
					tilesMap[i][j] = doorTile;
                }
            }
        }

        for (Entity entity : this.entityList){
            tilesMap[entity.getxPosition()][entity.getyPosition()].setEntity(entity);
        }

		return tilesMap;
		
	}

    public List<Entity> getEntities(){
        return this.entityList;
    }
	
}

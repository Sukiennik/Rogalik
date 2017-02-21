package pl.rogalik.environ1.game_map;

import pl.rogalik.environ1.game_map.*;
import pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator;
import pl.rogalik.environ1.game_map.map_providers.generators.MapGenerator;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.GroundTile;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.environ1.game_map.map_objects.tiles.WallTile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EntitiesTest {

    private int width;
    private int height;
    private String MapGeneratorClassName;
    private GameMap map;
    private Random randomGenerator = new Random();


    @Before
    public void initialize() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = Class.forName(MapGeneratorClassName).getConstructor(int.class, int.class);
        MapGenerator mapGenerator = (MapGenerator) constructor.newInstance(this.width, this.height);
        this.map = new GameMap(mapGenerator);
    }

    public EntitiesTest(int width, int height, String MapGeneratorClassName){
        this.width = width;
        this.height = height;
        this.MapGeneratorClassName = MapGeneratorClassName;
    }


    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(new Object [][]{
                {200, 100, "pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator"},
                {200, 100, "pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator"},
                {150, 75, "pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator"},
                {150, 75, "pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator"}
        });
    }

    @Test
    public void testMoveCharacters(){
        int groundTileX = 50;
        int groundTileY = 50;
        if (map.getMap()[groundTileX][groundTileY] instanceof WallTile) {
            for (int i = 0; i < map.getMap().length; i++) {
                for (int j = 0; j < map.getMap()[0].length; j++) {
                    if (map.getMap()[i][j] instanceof GroundTile) {
                        groundTileX = i;
                        groundTileY = j;
                        break;
                    }
                }
            }
        }
        for (Entity entity: map.getEntities()){

                Tile tile1 = map.getMap()[groundTileX][groundTileY];

                entity.move(groundTileX,groundTileY);

                Tile tile2 = map.getMap()[entity.getxPosition()][entity.getyPosition()];
                Entity entity2 = map.getMap()[groundTileX][groundTileY].getEntity().get();

                assertEquals(entity, entity2);
                assertEquals(tile1, tile2);


        }

    }


    @Test
    public void testEntityRemove() {

        int numberOfEntitiesInTheBeginning = map.getEntities().size();

        int index = randomGenerator.nextInt(map.getEntities().size());
        Entity randomEntity = map.getEntities().get(index);

        randomEntity.removeThisEntity();

        assertEquals(numberOfEntitiesInTheBeginning - 1, map.getEntities().size());

    }
    @Test
    public void testEntityMap(){
        int index = randomGenerator.nextInt(map.getEntities().size());
        assertEquals(map, map.getEntities().get(index).getGameMap());
    }
}

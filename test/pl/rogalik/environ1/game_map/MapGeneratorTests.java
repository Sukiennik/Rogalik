package pl.rogalik.environ1.game_map;

import pl.rogalik.environ1.game_map.*;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class MapGeneratorTests {

    private int[][] matrix;

    private int width;
    private int height;
    private String MapGeneratorClassName;
    private MapGenerator mapGenerator;

    @Before
    public void initialize() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = Class.forName(MapGeneratorClassName).getConstructor(int.class, int.class);
        this.mapGenerator = (MapGenerator) constructor.newInstance(this.width, this.height);
    }

    public MapGeneratorTests(int width, int height, String MapGeneratorClassName){
        this.width = width;
        this.height = height;
        this.MapGeneratorClassName = MapGeneratorClassName;
    }


    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(new Object [][]{
                {200, 100, "pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator"},
                {200, 100, "pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator"},
                {150, 50, "pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator"},
                {150, 50, "pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator"}
        });
    }
    
    
    /** Sprawdzenie czy istnieją na mapie zarówno ściany jak i podłoga **/
    @Test
    public void checkIfGroundAndWallExist(){
        Tile[][] map = mapGenerator.getMap();
        boolean groundExists = Stream.of(map).flatMap(Stream::of).anyMatch(tile -> tile instanceof GroundTile);
        boolean wallExists = Stream.of(map).flatMap(Stream::of).anyMatch(tile -> tile instanceof WallTile);

        assertTrue(groundExists);
        assertTrue(wallExists);
    }


    /** Sprawdzenie czy istnieją dokladnie jedne drzwi **/
    @Test
    public void checkDoor(){
        Tile[][] map = mapGenerator.getMap();

        long doorAmount = Stream.of(map).flatMap(Stream::of).filter(tile -> tile instanceof WallTile).
                filter(tile -> ((WallTile) tile).isDoor()).count();

        assertEquals(doorAmount, 1);
    }


    /** Czy istnieje droga łącząca bohatera i drzwi **/
    @Test
    public void checkIfPathExistsHeroDoor(){
        Tile[][] map = mapGenerator.getMap();

        Optional<Tile> doorTile = Stream.of(map).flatMap(Stream::of).filter(tile -> (tile instanceof WallTile)).
                filter(tile -> ((WallTile) tile).isDoor()).findAny();

        Optional<Tile> heroTile = Stream.of(map).flatMap(Stream::of).filter(tile -> tile.getEntity().isPresent()).
                filter(tile -> tile.getEntity().get().getType() == EntityType.HERO).findAny();

        assertTrue(doorTile.isPresent());
        assertNotNull(doorTile.isPresent());

        boolean pathExists = ifPathExists(heroTile.get(), doorTile.get(), map);

        assertTrue(pathExists);

    }

    @Test
    public void checkIfItemExist(){
        List<Entity> enlist = mapGenerator.getEntities();
        List<Entity> itlist = enlist.stream().filter(e -> e.getType().isItemType()).map(e -> e).collect(Collectors.toList());
        assertFalse(itlist.isEmpty());

    }
    @Test
    public void checkIfCharacterExist(){
        List<Entity> enlist = mapGenerator.getEntities();
        List<Entity> itlist = enlist.stream().filter(e -> e.getType().isCharacterType()).map(e -> e).collect(Collectors.toList());
        assertFalse(itlist.isEmpty());

    }

    @Test
    public void checkIfOneHeroExist(){
        List<Entity> enlist = mapGenerator.getEntities();
        List<Entity> hero = enlist.stream().filter(e -> e.getType() == EntityType.HERO).collect(Collectors.toList());
        assertEquals(hero.size(),1);
    }
    @Test
    public void testEntityNumber(){
        GameMap map = new GameMap(mapGenerator);
        long count = Stream.of(map.getMap()).flatMap(Stream::of).filter(tile -> tile.getEntity().isPresent()).count();
        assertEquals(count,map.getEntities().size());
    }
    @Test
    public void testIfHeroPosition(){
        GameMap map = new GameMap(mapGenerator);

        Tile hero1 = null;

        Optional<Entity> hero = map.getEntities().stream().filter(e -> e.getType() == EntityType.HERO).findAny();
        Optional<Tile> heroTile = Stream.of(map.getMap()).flatMap(Stream::of)
                .filter(tile -> tile.getEntity().isPresent())
                .filter(tile -> tile.getEntity().get().getType() == EntityType.HERO).findAny();


        assertTrue(hero.isPresent());
        assertTrue(heroTile.isPresent());

        assertEquals(map.getHeroPosition().getEntity().get(), hero.get());
        assertEquals(map.getHeroPosition(), heroTile.get());
    }

    /**************** FUNKCJE POMOCNICZE *******************/

    /** Funkcja sprawdzająca czy istnieje ścieżka składająca się wyłącznie z podłogi łącząca dwa punkty na mapie **/
    public boolean ifPathExists (Tile source, Tile destination, Tile[][] map){
        int sourceX = 0;
        int sourceY = 0;

        int destinationX = 0;
        int destinationY = 0;

        int width = map.length;
        int height = map[0].length;

        this.matrix = new int[width][height];

        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){

                if (map[i][j] == source){
                    sourceX = i;
                    sourceY = j;
                }
                else if (map[i][j] == destination){
                    if (map[i-1][j] instanceof GroundTile) {
                        destinationX = i - 1;
                        destinationY = j;
                    }

                    else if (map[i][j-1] instanceof GroundTile) {
                        destinationX = i ;
                        destinationY = j - 1;
                    }
                }

                if (map[i][j] instanceof WallTile)
                    matrix[i][j] = 1;
                else
                    matrix[i][j] = 0;
            }
        }

        floodFill(sourceX, sourceY);

        if (matrix[destinationX][destinationY] == 2)
            return true;
        else
            return false;

    }

    private void floodFill(int x, int y){

        int current = matrix[x][y];
        if(current == 0){
            this.matrix[x][y] = 2;
            floodFill(x+1, y);
            floodFill(x-1, y);
            floodFill(x, y+1);
            floodFill(x, y-1);

        }
    }



}
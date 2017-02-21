package pl.rogalik.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.function.Executable;
import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;
import pl.rogalik.environ1.game_map.map_objects.tiles.GroundTile;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.environ1.game_map.map_objects.tiles.WallTile;
import pl.rogalik.environ1.game_map.map_providers.MapProviding;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bblaszkow on 05.01.17.
 */
class GameObjectTest {
/*    private GameMap gameMap;
    private Hero hero;

    @BeforeEach
    void setUp() {
        MapProviding gen = new MapProviding() {
            private Tile[][] tiles = new Tile[4][3];
            private List<Entity> entities = new ArrayList<>();

            public MapProviding init() {
                initEntities();
                initMap();
                return this;
            }

            @Override
            public Tile[][] getMap() {
                return tiles;
            }
            private Tile[][] initMap() {
                for (int x = 0; x < tiles.length; x++) {
                    for (int y = 0; y < tiles[x].length; y++) {
                        if (y == 1 && (x == 1 || x == 2))
                            tiles[x][y] = new GroundTile();
                        else
                            tiles[x][y] = new WallTile();
                    }
                }
                for (Entity entity : this.entities){
                    tiles[entity.getxPosition()][entity.getyPosition()].setEntity(entity);
                }
                return tiles;
            }

            @Override
            public List<Entity> getEntities() {
                return entities;
            }
            private List<Entity> initEntities() {
                entities.add(new Entity(1,1, EntityType.HERO, 44));
                return entities;
            }
        }.init();


        gameMap = new GameMap(gen);
        hero = new Hero(gameMap.getHeroPosition().getEntity().get(), "Tester");
    }

    @Test
    void move() {
        hero.setDir(Direction.N);
        assertFalse(hero.move(), "Impossible move should return false");
        assertAll("Position after move",
                () -> assertEquals(hero.getX(), 1),
                () -> assertEquals(hero.getY(), 1)
        );

        hero.setDir(Direction.E);
        assertTrue(hero.move(), "Possible move should return true");
        assertAll("Position after move",
                () -> assertEquals(hero.getX(), 2),
                () -> assertEquals(hero.getY(), 1)
        );
    }

    @Test
    void serialization() throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("hero-serialized.bin"));
        outputStream.writeObject(hero);
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("hero-serialized.bin"));
        Hero newHero = (Hero) inputStream.readObject();
        newHero.setEntity(gameMap.getHeroPosition().getEntity().get());

        Function<Function<Hero, Object>, Executable> assertEqualResult = f -> () -> assertEquals(f.apply(hero), f.apply(newHero));
        assertAll("Deserialized object should be equivalent to original",
            assertEqualResult.apply(Hero::getArmor),
            assertEqualResult.apply(Hero::getDamage),
            assertEqualResult.apply(Hero::getName),
            assertEqualResult.apply(Hero::getHp),
            assertEqualResult.apply(Hero::getX),
            assertEqualResult.apply(Hero::getY),
            assertEqualResult.apply(Hero::getDir)
        );
    }*/
}
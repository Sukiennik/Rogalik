package pl.rogalik.environ1.game_map;

import pl.rogalik.environ1.game_map.*;
import pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator;
import pl.rogalik.environ1.game_map.map_providers.persistence.FromFileMapReader;
import pl.rogalik.environ1.game_map.map_providers.generators.MapGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MapTest {

    private int width;
    private int height;
    private String MapGeneratorClassName;
    private GameMap map;

    @Before
    public void initialize() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = Class.forName(MapGeneratorClassName).getConstructor(int.class, int.class);
        MapGenerator mapGenerator = (MapGenerator) constructor.newInstance(this.width, this.height);
        this.map = new GameMap(mapGenerator);
    }

    public MapTest(int width, int height, String MapGeneratorClassName){
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
    public void testSaveLoad(){
        FromFileMapReader.MapSaver s = new FromFileMapReader.MapSaver();
		try {
			s.saveMapToFile(map);
		}
        catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		GameMap map2 = new GameMap(new FromFileMapReader());
        assertEquals(map2.toString(),map.toString());

    }

}
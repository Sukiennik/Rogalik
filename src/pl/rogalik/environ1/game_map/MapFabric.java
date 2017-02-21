package pl.rogalik.environ1.game_map;

import pl.rogalik.environ1.game_map.map_providers.MapProviding;
import pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator;
import pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator;

import java.util.Random;


 public class MapFabric {
    private static Random random = new Random(String.valueOf(System.currentTimeMillis()).hashCode());

    public  MapProviding getGenerator(){
        MapProviding mapGen;
        int w = random.nextInt(180)+20;
        int h = random.nextInt(180)+20;
        if(random.nextDouble()<0.5)
            mapGen = new DungeonMapGenerator(w,h);
        else
            mapGen =  new CaveMapGenerator(w,h);

        return mapGen;
    }
}

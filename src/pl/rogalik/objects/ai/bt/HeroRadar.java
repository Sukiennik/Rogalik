package pl.rogalik.objects.ai.bt;

import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;

import java.util.Optional;


public class HeroRadar implements Routine {

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        int x = mob.getX();
        int y = mob.getY();
        int range = 4;

        Optional<Tile>[][] area = engine.getGameMap().getArea(x - range, y - range, x + range, y + range);
        for(int i = 0; i <= 2*range; ++i){
            for(int j = 0; j < 2*range; ++j) {
                if (area[i][j].flatMap(Tile::getEntity).map(Entity::getType).filter(EntityType.HERO::equals).isPresent()) {
                    mob.setDir(Direction.fromCoordinates(i - x, j - y));
                    return true;
                }
            }
        }
        return false;
    }
}

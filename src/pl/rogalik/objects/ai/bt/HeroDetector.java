package pl.rogalik.objects.ai.bt;


import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;

import java.util.Optional;

public class HeroDetector implements Routine{

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        for(Direction dir: Direction.values()){
            Optional<Tile> entity = engine.getGameMap().getObjectAt(mob.getX() + dir.dx(), mob.getY() + dir.dy());
            if(entity.flatMap(Tile::getEntity).map(Entity::getType).filter(EntityType.HERO::equals).isPresent()){
                mob.setDir(dir);
                return true;
            }
        }
        return false;
    }
}

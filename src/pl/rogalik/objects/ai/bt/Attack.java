package pl.rogalik.objects.ai.bt;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Hero;
import pl.rogalik.objects.Monster;

import java.util.Optional;


public class Attack implements Routine{
        @Override
        public boolean execute(Monster mob, GameEngine engine) {
            Hero hero = engine.getHero();
            Optional<Entity> attackedObject = engine
                    .getGameMap()
                    .getObjectAt(mob.getFacingX(), mob.getFacingY())
                    .flatMap(Tile::getEntity)
                    .filter(entity -> entity == hero.getEntity());

            if (attackedObject.isPresent()) {
                hero.dealDamage(mob.getDamage());
            }
            return attackedObject.isPresent();
        }
}

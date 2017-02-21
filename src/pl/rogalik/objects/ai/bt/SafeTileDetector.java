package pl.rogalik.objects.ai.bt;


import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;

import java.util.Optional;


public class SafeTileDetector implements Routine {

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        Boolean danger = new HeroRadar().execute(mob, engine);
        if (danger) {
            int x = mob.getX();
            int y = mob.getY();
            int heroDirX = mob.getDir().dx();
            int heroDirY = mob.getDir().dy();

            for (int dirX : new int[]{-heroDirX, 0, heroDirX}) {
                for (int dirY : new int[]{-heroDirY, 0, heroDirY}) {

                    if ((dirX == 0 && dirY == 0) || (dirX == heroDirX && dirY == heroDirY))
                        continue;

                    Optional<Tile> tile = engine.getGameMap().getObjectAt(x + dirX, y + dirY);
                    if (!tile.flatMap(Tile::getEntity).isPresent()) {
                        mob.setDir(Direction.fromCoordinates(dirX, dirY));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

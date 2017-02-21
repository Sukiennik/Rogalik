package pl.rogalik.objects.ai.bt;

import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;

import java.util.Random;


public class Wander implements Routine {

    private static final Random random = new Random();

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        Direction[] possibleDirections = Direction.values();
        int pick = random.nextInt(possibleDirections.length);
        mob.setDir(possibleDirections[pick]);
        return new Movement().execute(mob, engine);
    }
}

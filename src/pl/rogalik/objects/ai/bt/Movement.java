package pl.rogalik.objects.ai.bt;


import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;

public class Movement implements Routine {

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        int movementPointsRequired = 5;
        if(mob.getMovementPoints() >= movementPointsRequired) {
            if (mob.move()) {
                mob.setMovementPoints(mob.getMovementPoints() - movementPointsRequired);
                return true;
            }
        }
        return false;
    }
}

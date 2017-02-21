package pl.rogalik.objects.ai.bt;

import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;


public class EscapeIndicator implements Routine{

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        return mob.getHp() < mob.getFearEstimator();
    }
}

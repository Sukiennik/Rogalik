package pl.rogalik.objects.ai.bt;

import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;


public interface Routine {
    boolean execute(Monster mob, GameEngine engine);
}

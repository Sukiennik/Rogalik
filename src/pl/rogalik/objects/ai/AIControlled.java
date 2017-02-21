package pl.rogalik.objects.ai;


import pl.rogalik.mechanics.GameEngine;

/**
 * Interface implemented by every Creature,
 * that should be controlled by AI algorithms.
 */
public interface AIControlled {
    void makeTurn(GameEngine ge);
    boolean isAlive();
}

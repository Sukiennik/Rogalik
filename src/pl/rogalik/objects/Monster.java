package pl.rogalik.objects;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.ai.AIControlled;
import pl.rogalik.objects.ai.bt.Routine;


public class Monster extends Creature implements AIControlled {

    private Routine brain;
    private final String name;
    private final int level;

    private final double fearEstimator;
    private final int movementPointsPerTurn;
    private int movementPoints;

    public Monster(Entity pos, Routine brain, String name, int level, int hp, int baseDamage, int baseArmor, double fearEstimator, int movementPoints) {
        super(pos, hp, baseDamage, baseArmor);
        this.brain = brain;
        this.name = name;
        this.level = level;
        this.fearEstimator = fearEstimator;
        this.movementPointsPerTurn = movementPoints;
        this.movementPoints = movementPoints;
    }

    @Override
    public void makeTurn(GameEngine engine) {
        brain.execute(this, engine);
        this.movementPoints = this.movementPointsPerTurn;
    }

    public double getFearEstimator() { return fearEstimator; }

    public int getMovementPoints() {
        return movementPoints;
    }

    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public String getName() {
        return name;
    }

    Routine getBrain() {
        return brain;
    }

    public int getLevel() {
        return level;
    }
}

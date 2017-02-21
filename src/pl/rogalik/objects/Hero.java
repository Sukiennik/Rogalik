package pl.rogalik.objects;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;

/**
 * Class representing hero controlled by player
 */
public class Hero extends Creature {
    private int hunger = 100;
    private final String name;

    public Hero(Entity pos, String name) {
        super(pos);
        this.name = name;
    }

    public Hero(Entity pos, int hp, int baseDamage, int baseArmor, String name) {
        super(pos, hp, baseDamage, baseArmor);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getDamage() {
        //TODO: Add modifier based on stats and/or equipment
        return super.getDamage();
    }

    @Override
    public int getArmor() {
        //TODO: Add modifier based on stats and/or equipment
        return super.getArmor();
    }
}

package pl.rogalik.objects;

import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;

/**
 * Living being in game world.
 * Common parent class for all mobs, npcs and hero.
 */
public class Creature extends GameObject {
    private int hp;
    private int baseDamage;
    private int baseArmor;
    private Direction facingDir;

    public Creature(Entity pos) {
        this(pos, 10, 1, 1);
    }

    public Creature(Entity pos, int hp, int baseDamage, int baseArmor) {
        super(pos);
        this.hp = hp;
        this.baseDamage = baseDamage;
        this.baseArmor = baseArmor;
        this.facingDir = Direction.N;
    }

    public int getHp(){return this.hp;}

    public boolean isAlive() { return hp > 0; }

    public int getDamage() {
        return baseDamage;
    }

    public int getArmor() {
        return baseArmor;
    }

    public int getFacingX() {
        return this.getX() + facingDir.dx();
    }
    public int getFacingY() {
        return this.getY() + facingDir.dy();
    }

    public void dealDamage(int dmg) {
        int calculatedDamage = dmg - getArmor();
        if(calculatedDamage > 0)
            hp -= calculatedDamage;
        else
            hp -= 1;
    }

    public Direction getDir(){ return facingDir; }

    public void setDir(Direction dir){this.facingDir=dir;}

    public boolean move() {
        return move(facingDir);
    }

}

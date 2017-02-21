package pl.rogalik.objects;

import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;

import java.io.Serializable;
import java.util.Optional;

/**
 * Basic abstraction of Object in game mechanics
 * Common parent class for creatures, items, interactive objects.
 */
public abstract class GameObject implements Serializable {
    /**
     * Object in environment that maps to object in Game Mechanics
     * It needs to hold entity (x, y) on gameMap and type
     */
    private transient Entity entity;

    public GameObject(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public int getX(){ return entity.getxPosition(); }

    public int getY(){ return entity.getyPosition(); }

    public boolean move(int x, int y) {
        Optional<Tile> tile = entity.getGameMap().getObjectAt(x, y);
        return tile.map(t -> !t.isWall() && t.isEmpty()).orElse(false)
                && this.entity.move(x, y);
    }

    public boolean move(Direction dir) {
        int x = getX() + dir.dx();
        int y = getY() + dir.dy();
        return move(x, y);
    }
}

package pl.rogalik.mechanics;

import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.environ1.game_map.map_objects.direction.Direction;
import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.tiles.Tile;
import pl.rogalik.environ1.game_map.map_providers.generators.CaveMapGenerator;
import pl.rogalik.environ1.game_map.map_providers.generators.DungeonMapGenerator;
import pl.rogalik.objects.Hero;
import pl.rogalik.objects.Monster;
import pl.rogalik.objects.MonsterFactory;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main class of GameMechanics module.
 * It will serve as entry point for Client module
 */
public class GameEngine {
    private Hero hero;
    private Map<Entity, Monster> monsterMap = new HashMap<>();
    private GameMap gameMap = new GameMap(new CaveMapGenerator(50, 50));
    private MonsterFactory monsterFactory = new MonsterFactory("resources/mobs");

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public GameEngine() throws FileNotFoundException, ScriptException, NoSuchMethodException {
        this(null);
        this.hero = new Hero(gameMap.getHeroPosition().getEntity().get(), 100, 20, 50, "Annonymous");
    }
    public GameEngine(Hero hero) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        this.hero = hero;
        gameMap.getEntities().stream()
                .filter(entity -> entity.getType().isCharacterType())
                .forEach(entity -> monsterMap.put(entity, monsterFactory.getMonster(entity, 1)));
    }

    private void makeTurn() {
        monsterMap = monsterMap.entrySet().stream()
                .filter(e -> e.getValue().isAlive())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        monsterMap.forEach((e, m) -> m.makeTurn(this));
    }

    public boolean moveHero(Direction dir) {
        if (hero.move(dir)) {
            makeTurn();
            return true;
        } else {
            return false;
        }
    }

    public boolean attack(Direction dir) {
        hero.setDir(dir);
        Optional<Tile> attackedObject = gameMap.getObjectAt(hero.getFacingX(), hero.getFacingY());
        Optional<Monster> attackedMonster = attackedObject
                .flatMap(Tile::getEntity)
                .filter(e -> e.getType().isCharacterType())
                .flatMap(entity -> Optional.ofNullable(monsterMap.get(entity)));

        attackedMonster.ifPresent(monster -> {
            monster.dealDamage(hero.getDamage());
            if (!monster.isAlive()) {
                monster.getEntity().removeThisEntity();
                monsterMap.remove(monster.getEntity());
            }
            makeTurn();
        });
        return attackedMonster.isPresent();
    }
}

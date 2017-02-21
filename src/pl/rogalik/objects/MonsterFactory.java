package pl.rogalik.objects;


import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.GameMap;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MonsterFactory {

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    private Map<EntityType, List<Tuple<Monster, Double>>> monsterPrototypes;
    private static Random rand = new Random();

    public MonsterFactory(String dirPath) throws ScriptException, FileNotFoundException, NoSuchMethodException {
        engine.eval(new FileReader("src/pl/rogalik/objects/MonstersPrototypeBuilder.js"));
        Invocable invocable = (Invocable) engine;
        this.monsterPrototypes = (Map<EntityType, List<Tuple<Monster, Double>>>) invocable.invokeFunction("buildMonstersPrototypes", dirPath);
    }

    public Monster getMonster(Entity pos, int level){
        EntityType monsterType = pos.getType();
        List<Tuple<Monster, Double>> monsters = monsterPrototypes.get(monsterType);

        int pick = rand.nextInt(monsters.size());
        Tuple<Monster, Double> monsterCharacteristics = monsters.get(pick);
        Monster monsterPrototype = monsterCharacteristics.x;

        double growthRate = monsterCharacteristics.y;
        double rate = Math.pow(growthRate, level);
        int hp = (int) (rate * monsterPrototype.getHp());
        int baseArmor = (int) (rate * monsterPrototype.getArmor());
        int baseDamage = (int) (rate * monsterPrototype.getDamage());
        double fearEstimator = rate * monsterPrototype.getFearEstimator();
        int movementPoints = (int) (rate * monsterPrototype.getMovementPoints());

        return new Monster(pos, monsterPrototype.getBrain(), monsterPrototype.getName(),
                level, hp, baseDamage, baseArmor, fearEstimator, movementPoints);
    }
}

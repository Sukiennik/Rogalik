var String = Java.type("java.lang.String");

var Tuple = Java.type("pl.rogalik.objects.Tuple");
var Monster = Java.type("pl.rogalik.objects.Monster");
var MonsterType = Java.type("pl.rogalik.environ1.game_map.map_objects.entities.EntityType");

var Files = Java.type("java.nio.file.Files");
var File = Java.type("java.io.File");

var ArrayList = Java.type("java.util.ArrayList");
var Hashmap = Java.type("java.util.HashMap");


function buildMonster(monsterDescription){
    var monsterBrain = buildBrain(monsterDescription.brain);
    var monster = new Monster(null, monsterBrain, monsterDescription.name, 1, monsterDescription.hp,
                              monsterDescription.baseDamage, monsterDescription.baseArmor,
                              monsterDescription.fearEstimator, monsterDescription.movementPoints);
    var growthRate = monsterDescription.growthRate;
    return new Tuple(monster, growthRate)
}


function buildMonstersPrototypes(directoryPath) {
    var monsterPrototypes = new Hashmap();
    var directory = new File(directoryPath);
    var filesList = directory.listFiles();

    for (var i = 0; i < filesList.length; ++i) {
        var currentFile = filesList[i];
        if (currentFile.isFile()) {
            var content = new String(Files.readAllBytes(currentFile.toPath()));
            var monsterDescription = JSON.parse(content);

            var monsterType = MonsterType.monsterFromString(monsterDescription.type);
            var monster = buildMonster(monsterDescription);

            if(!monsterPrototypes.containsKey(monsterType)){
                monsterPrototypes.put(monsterType, new ArrayList());
            }
            monsterPrototypes.get(monsterType).add(monster);
        }
    }
    return monsterPrototypes;
}


var buildBrain = (function() {
    var cache = {};

    function getClass(cls_name) {
        var cls = cache[cls_name];
        if(cls == undefined)
            cls = cache[cls_name] = Java.type("pl.rogalik.objects.ai.bt." + cls_name);
        return cls;
    }

    function fun(brainStructure) {
        if(brainStructure instanceof String)
            return new (getClass(brainStructure))();
        else {
            for (var property in brainStructure) {
                var propertyInitListStructure = brainStructure[property];
                var propertyInitList = new ArrayList();
                for(var i = 0; i < propertyInitListStructure.length; ++i){
                    propertyInitList.add(fun(propertyInitListStructure[i]));
                }
                return new (getClass(property))(propertyInitList);
            }
        }
    }

    return fun;
})();

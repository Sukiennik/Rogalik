package pl.rogalik.environ1.game_map.map_objects.entities;


public enum EntityType {

    HERO(TYPE.OTHER),
    SMALL_MONSTER(TYPE.CHARACTER),
    MEDIUM_MONSTER(TYPE.CHARACTER),
    BIG_MONSTER(TYPE.CHARACTER),
    SMALL_ITEM(TYPE.ITEM),
    MEDIUM_ITEM(TYPE.ITEM),
    BIG_ITEM(TYPE.ITEM),
    DOOR(TYPE.OTHER),
    UNKNOWN(TYPE.OTHER);

    private enum TYPE {
        CHARACTER,
        OTHER,
        ITEM
    }

    private TYPE type;

    EntityType(TYPE type) {
        this.type = type;
    }

    public boolean isCharacterType() {
        return (this.type == TYPE.CHARACTER);
    }

    public boolean isItemType() {
        return (this.type == TYPE.ITEM);

    }

    public static EntityType monsterFromString(String text){
        for (EntityType t : EntityType.values()){
            if (t.name().equalsIgnoreCase(text))
                return t;
        }
        return UNKNOWN;
    }


}

package mhyhre.lightrabbit.game;

public enum EnemyType {
    UNDEFINED(""), SHARK("Shark"),
    PIRATE_BOAT("PirateBoat"), PIRATE_SHIP("PirateShip"),
    DIRIGIBLE("Dirigible"), WAR_PLANE("WarPlane"), ZOMBIE_PLANE("ZombiePlane");
    
    private final String name;
    
    private EnemyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static EnemyType getByName(String name) {
        
        for(EnemyType type: values()) {
            if(type.getName().equals(name)) {
                return type;
            }
        }
        return UNDEFINED;
    }
}

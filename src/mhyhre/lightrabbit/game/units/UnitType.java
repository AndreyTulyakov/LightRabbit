package mhyhre.lightrabbit.game.units;

public enum UnitType {
    UNDEFINED(""), SHARK("Shark"),
    PIRATE_BOAT("PirateBoat"), PIRATE_SHIP("PirateShip"),
    DIRIGIBLE("Dirigible"), WAR_PLANE("WarPlane"), ZOMBIE_PLANE("ZombiePlane");
    
    private final String name;
    
    private UnitType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static UnitType getByName(String name) {
        
        for(UnitType type: values()) {
            if(type.getName().equals(name)) {
                return type;
            }
        }
        return UNDEFINED;
    }
}

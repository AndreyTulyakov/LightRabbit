package mhyhre.lightrabbit.game.units;

public enum UnitIdeology {
    NEUTRAL, FRIEDLY, ENEMY_FOR_ALL, PIRATE, IMPERIAL;
    
    public boolean isEnemy(UnitIdeology arg) {
        
        if(this == UnitIdeology.ENEMY_FOR_ALL || arg == UnitIdeology.ENEMY_FOR_ALL) {
            return true;
        }
        
        if(this == arg) {
            return false;
        }
        
        if(this == UnitIdeology.FRIEDLY || this == UnitIdeology.NEUTRAL) {
            if(arg == UnitIdeology.FRIEDLY || arg == UnitIdeology.NEUTRAL) {
                return false;
            }
        }
        
        return true;
    }
}

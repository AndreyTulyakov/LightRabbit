package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.MainActivity;
import android.util.Log;

public enum UnitMoveDirection {
    NONE(0), LEFT(-1), RIGHT(1);
    
    private int direct;
    
    private UnitMoveDirection(int direct) {
        this.direct = direct;
    }
    
    public int getDirect() {
        return direct;
    }
    
    public static UnitMoveDirection getByName(String name) {
        
        UnitMoveDirection result;
        
        try {
            result = UnitMoveDirection.valueOf(name);
        } catch (IllegalArgumentException e) {
            result = UnitMoveDirection.NONE;
            Log.w(MainActivity.DEBUG_ID, "UnitMoveDirection::getByName: wrong arg");
        }
        
        return result;
    }

}

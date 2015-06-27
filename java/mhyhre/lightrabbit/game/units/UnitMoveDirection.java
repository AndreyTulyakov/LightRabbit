/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.utils.Vector2i;
import android.util.Log;

public enum UnitMoveDirection {
    NONE(0,0), LEFT(-1,0), RIGHT(1,0), UP(0,1), DOWN(0,-1);
    
    final Vector2i direction;
    
    private UnitMoveDirection(int directX, int directY) {
        direction = new Vector2i(directX, directY);
    }
    
    public Vector2i getVector() {
        return direction;
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

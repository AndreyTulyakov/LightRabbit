/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky.lighting;

import android.graphics.Point;
import android.util.Log;

import org.andengine.entity.Entity;

import java.util.Random;

import mhyhre.lightrabbit.MainActivity;

public class LightingManager extends Entity  {

    private static final int MAXIMUM_OF_LIGHTING = 5;
    private static final int RANDOM_START_OFFSET = 100;
    private Random random;

    
    public LightingManager() { 
        random = new Random();
    }
    
    public void lightingHit(int positionX, int positionY){

        if(getChildCount() >= MAXIMUM_OF_LIGHTING) {
            Log.w(MainActivity.DEBUG_ID, "LightingManager: can't make more lights");
            return;
        }
        
        Point startPoint = new Point(positionX + random.nextInt(RANDOM_START_OFFSET)/2 - RANDOM_START_OFFSET, (int)MainActivity.getHeight());
        Point endPoint = new Point(positionX, positionY);
               
        LightingUnit unit = new LightingUnit(startPoint, endPoint, MainActivity.getVboManager());
        attachChild(unit);
    }
    
    @Override protected void onManagedUpdate(float pSecondsElapsed) {
        
        for(int i = 0; i < this.getChildCount(); i++) {
            if(getChildByIndex(i).getAlpha() == 0.0f) {
                detachChild(getChildByIndex(i));
                i--;
            }
        }
        super.onManagedUpdate(pSecondsElapsed);
    }
}

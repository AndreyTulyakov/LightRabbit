/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky.lighting;

import java.util.Random;

import mhyhre.lightrabbit.MainActivity;
import org.andengine.entity.Entity;
import android.graphics.Point;
import android.util.Log;

public class LightingManager extends Entity  {

    private static final int MAXIMUM_OF_LIGHTING = 5;
    private static final int RANDOM_START_OFFSET = 100;
    private Random rand;
    
    public LightingManager() { 
        rand = new Random();
    }
    
    public void lightingHit(int positionX, int positionY){
        

        if(getChildCount() >= MAXIMUM_OF_LIGHTING) {
            Log.w(MainActivity.DEBUG_ID, "LightingManager: can't make more lights");
            return;
        }
        
        Point startPoint = new Point(positionX + rand.nextInt(RANDOM_START_OFFSET)/2 - RANDOM_START_OFFSET, (int)MainActivity.getHeight());
        Point endPoint = new Point(positionX, positionY);
               
        LightingUnit unit = new LightingUnit(startPoint, endPoint, MainActivity.getVboManager());
        attachChild(unit);
    }
    
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        
        for(int i = 0; i < this.getChildCount(); i++) {
            if(getChildByIndex(i).getAlpha() == 0.0f) {
                detachChild(getChildByIndex(i));
                i--;
            }
        }
        
        /* TODO:
         * Расcчитываем размер сегмента. Центр, и угол поворота.
         */
        /*
        Point centerSegmentPoint = new Point();
        ITextureRegion lightingRegion = MainActivity.resources.getTextureRegion("lighing");

        for(LightingUnit unit: units) {
            for(Pair<Point,Point> segment: unit.getSegments()) {
                
                final int x1 =  segment.first.x;
                final int x2 =  segment.second.x;
 
                final int y1 =  segment.first.y;
                final int y2 =  segment.second.y;
                
                // Вычислим угол поворота сегмента:
                int z1 = x1 * x2;
                int z2 = y1 * y2;
                
                float angleRes = (float) Math.acos(((x1*x2+y1*y2)/(Math.sqrt(x1*x1+y1*y1)*Math.sqrt(x2*x2+y2*y2))));
                
                // Вычислим центр сегмента.
                centerSegmentPoint.set((segment.first.x + segment.second.x)/2, (segment.first.y + segment.second.y)/2);
                
                // Вычисляем размер сегмента.
                int a = (segment.first.x - segment.second.x);
                int b = (segment.first.y - segment.second.y);
                float lenth = (float) Math.sqrt(a*a+b*b);
                
                
                spriteBatch.draw(lightingRegion,
                        centerSegmentPoint.x, centerSegmentPoint.y,
                        lenth, lenth, (float)Math.toDegrees(angleRes), 1, 1, 1, 1);
            }
        }
        
        spriteBatch.submit();
        */
        super.onManagedUpdate(pSecondsElapsed);
    }
}

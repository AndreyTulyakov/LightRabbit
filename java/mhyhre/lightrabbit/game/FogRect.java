/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;

import org.andengine.entity.primitive.Rectangle;

public class FogRect extends Rectangle {
    
    public FogRect(int r, int g, int b, int a) {
        super(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),MainActivity.getWidth(),MainActivity.getHeight(),MainActivity.getVboManager());
        setColor(r/255.0f, g/255.0f, b/255.0f);
        setAlpha(a/255.0f);
    }
    
    
    public boolean showFogEvent(Event event) {
        
     // Передавать в int силу тумана в 0-100%
        int fogValue = (int)(getAlpha()*100.0f);
        if(fogValue == event.getIntegerArg()) {
            return true;
            
        } else {
            if(fogValue < event.getIntegerArg()) {
                setAlpha(getAlpha()+0.01f);
            } else {
                setAlpha(getAlpha()-0.01f);
            }
            
            if(getAlpha() > 1.0f) {
                setAlpha(1.0f);
            }
            
            if(getAlpha() < 0.0f) {
                setAlpha(0.0f);
            }
        }
        return false;
    }
}

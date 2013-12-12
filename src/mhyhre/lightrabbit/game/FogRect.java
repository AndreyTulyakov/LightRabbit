package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.Levels.Event;

import org.andengine.entity.primitive.Rectangle;

public class FogRect extends Rectangle {
    
    public FogRect() {
        super(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),MainActivity.getWidth(),MainActivity.getHeight(),MainActivity.getVboManager());
        setColor(0.5f, 0.6f, 0.9f);
        setAlpha(0);
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

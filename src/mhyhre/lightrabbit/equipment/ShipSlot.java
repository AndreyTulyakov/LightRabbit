package mhyhre.lightrabbit.equipment;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;

public class ShipSlot extends Entity {

    private static final int CELLS_CONFIGURATION_COUNT = 4;
    private static final int CELLS_AMMUNITION = 4;

    public ShipSlot() {
        Rectangle rect = new Rectangle( MainActivity.getHalfWidth(),  MainActivity.getHalfHeight(),
                MainActivity.getWidth()-20, MainActivity.getHeight()-20,
                MainActivity.getVboManager());
        rect.setColor(0.1f, 0.2f, 0.1f);
        attachChild(rect);

    }
    
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
  
        super.onManagedUpdate(pSecondsElapsed);
    }
}

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;

/*
 * Type: Pirate Ship
 * Speed: 0.3f
 * Health: 220
 * Armor: 50
 */

public class PirateShipUnit extends UnitModel {

    public static final float sSinkSpeed = -1.0f;
    private float targetRotation = -30;
    
    float bright;
    float mWaterLevel;

    public PirateShipUnit(int id, UnitMoveDirection dir) {
        super(id, UnitType.PIRATE_SHIP, 220, 50, 0.3f, 0.2f, dir);
        setIdeology(UnitIdeology.PIRATE);
        
        setSize(128, 75);
        bright = 1;
        setRadius(45);
    }
    
    @Override
    public void setDied(boolean mDied) {
        super.setDied(mDied);
        if(mDied == true) {
            MainActivity.resources.playSound("shipDie");
        }
    };

    public float getBright() {
        return bright;
    }

    public float getWaterLevel() {
        return mWaterLevel;
    }

    public void setWaterLevel(float mWaterLevel) {
        this.mWaterLevel = mWaterLevel;
    }

    @Override
    public void update() {

        moveHorizontalByDirection();
        
        if (isDied == true) {
            yPosition += sSinkSpeed;
            
            if (rotation > targetRotation)
                rotation-=0.5f;
            
            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {
            yPosition = (float) (mWaterLevel);
            updateAgents();
        }
    }

}

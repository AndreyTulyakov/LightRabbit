package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
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

    float mWaterLevel;

    public PirateShipUnit(int id) {
        super(id, UnitType.PIRATE_SHIP, 220, 50, 0.3f, 1.0f);
        setIdeology(UnitIdeology.PIRATE);    
        setSize(128, 75);
        setRadius(45);
    }
    
    @Override
    public void setDied(boolean mDied) {
        super.setDied(mDied);
        if(mDied == true) {
            MainActivity.resources.playSound("shipDie");
        }
    };

    public float getWaterLevel() {
        return mWaterLevel;
    }

    public void setWaterLevel(float mWaterLevel) {
        this.mWaterLevel = mWaterLevel;
    }

    @Override
    public void update(WaterPolygon water) {
        
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
            
            // Update jumping
            if(canJump == false && jumpAcceleration > - JUMP_ACCELERATION_LIMIT) {
                jumpAcceleration -= 0.8f;
            }
            
            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            setWaterLevel(5 + water.getObjectYPosition(getX()) + 20);
            
            float waveYPositionUnderPlayer = 10 + water.getObjectYPosition(getX());

            if(getY() < waveYPositionUnderPlayer - 10 && canJump == false) {
                setY(waveYPositionUnderPlayer);
                canJump = true;
            } else {
                if(canJump == false) {
                    setY(getY() + jumpAcceleration);
                } else {
                    setY(waveYPositionUnderPlayer);
                }
            }

            yPosition = (float) (mWaterLevel);
            updateAgents();
        }
    }

    @Override
    public void accelerate(float acceleration) {
    }

    @Override
    public void jump() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void fireByGun(int gunIndex) {
        // TODO Auto-generated method stub
        
    }

}

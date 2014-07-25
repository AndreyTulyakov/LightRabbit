package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;

/*
 * Type: Pirate Boat
 * Speed: 0.5f
 * Health: 50
 * Armor: 30
 */

public class PirateBoatUnit extends UnitModel {

    public static final float sSinkSpeed = -1.0f;
    private float targetRotation = 45;

    float mWaterLevel;

    public PirateBoatUnit(int id) {
        super(id, UnitType.PIRATE_BOAT, 60, 30, 0.5f, 1.5f);
        setIdeology(UnitIdeology.PIRATE);
        setSize(64, 22);
        setRadius(20);
    }

    @Override
    public void update(WaterPolygon water) {

        if (isDied == true) {
            
            mWaterLevel = 2 + water.getObjectYPosition(getX()) + 2;
            
            if(getY() < mWaterLevel - 10 && canJump == false) {
                setY(mWaterLevel);
            } else {
                setY(getY() + sSinkSpeed);
            }
            
            if (rotation < targetRotation) {
                rotation++;
            }
              
            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.05f)
                    bright = 0.0f;
            }
        } else {
            
            // Update jumping
            if(canJump == false && jumpAcceleration > - JUMP_ACCELERATION_LIMIT) {
                jumpAcceleration -= 0.8f;
            }
            
            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            mWaterLevel = 2 + water.getObjectYPosition(getX()) + 2;
            
            
            if(getY() < mWaterLevel - 10 && canJump == false) {
                setY(mWaterLevel);
                canJump = true;
            } else {
                if(canJump == false) {
                    setY(getY() + jumpAcceleration);
                } else {
                    setY(mWaterLevel);
                }
            }
            updateAgents();
        }
    }
}

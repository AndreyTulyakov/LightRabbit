/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.BarrelsKicker;
import mhyhre.lightrabbit.utils.ControllType;

public class BargeUnit extends UnitModel {

    private float targetRotation = -30;

    public BargeUnit(int id) {
        super(id, UnitType.BARGE, 600, 40, 0.5f, 1.0f, ControllType.LEFT_RIGHT);
        setIdeology(UnitIdeology.NEUTRAL);    
        setSize(128, 48);
        setRadius(35);
        
        
        setGun(new BarrelsKicker(this, 50));
    }
    
    @Override
    public void setDied(boolean mDied) {
        if(mDied == true && this.isDied == false) {
            MainActivity.resources.playSound("shipDie");
        }
        super.setDied(mDied);

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
            
            canJump = false;
            
            // Update jumping
            if(canJump == false && jumpAcceleration > - JUMP_ACCELERATION_LIMIT) {
                jumpAcceleration -= 0.8f;
            }
            
            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            setWaterLevel(5 + water.getObjectYPosition(getX()) + 14);
            
            float waveYPositionUnderPlayer = -20 + water.getObjectYPosition(getX());

            if(getY() < waveYPositionUnderPlayer - 15 && canJump == false) {
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
}


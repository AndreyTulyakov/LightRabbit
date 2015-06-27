/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.utils.ControllType;

public class IndustrialShipUnit extends UnitModel {

    private float targetRotation = -30;

    public IndustrialShipUnit(int id) {
        super(id, UnitType.INDUSTRIAL_SHIP, 80, 40, 0.5f, 1.0f, ControllType.LEFT_RIGHT);
        setIdeology(UnitIdeology.IMPERIAL);    
        setSize(80, 60);
        setRadius(35);
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
            setWaterLevel(5 + water.getObjectYPosition(getX()) + 10);
            
            float waveYPositionUnderPlayer = -2 + water.getObjectYPosition(getX());

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
}


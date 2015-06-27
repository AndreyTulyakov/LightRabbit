/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.utils.ControllType;

public class MerchantShip extends UnitModel {

    private float targetRotation = 45;

    public MerchantShip(int id) {
        super(id, UnitType.MERCHANT_SHIP, 120, 40, 0.5f, 0.8f, ControllType.LEFT_RIGHT);
        setIdeology(UnitIdeology.NEUTRAL);
        setSize(64, 22);
        setRadius(25);
    }

    @Override
    public void update(WaterPolygon water) {

        if (isDied == true) {
            
            mWaterLevel = 12 + water.getObjectYPosition(getX());
            
            if(getY() < mWaterLevel - 5 && canJump == false) {
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
            mWaterLevel = 12 + water.getObjectYPosition(getX());
            
            
            if(getY() < mWaterLevel - 5 && canJump == false) {
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

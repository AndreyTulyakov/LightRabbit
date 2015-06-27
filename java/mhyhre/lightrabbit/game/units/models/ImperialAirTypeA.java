/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.FastBomber;
import mhyhre.lightrabbit.utils.ControllType;

public class ImperialAirTypeA extends UnitModel {

    private float targetRotation = 90;
    private float heightLevel = 450;

    public ImperialAirTypeA(int id) {
        super(id, UnitType.IMPERIAL_AIR_TYPE_A, 60, 30, 4.0f, 1.0f, ControllType.ALL_DIRECTIONS);
        setIdeology(UnitIdeology.IMPERIAL);    
        setSize(100, 20);
        setRadius(15);
        sSinkSpeed = -2.0f;
        setGun(new FastBomber(this, 50));
    }
    
    @Override
    public void setDied(boolean mDied) {
        super.setDied(mDied);
        if(mDied == true) {
            // TODO: Airplane down
            //MainActivity.resources.playSound("shipDie");
        }
    };


    @Override
    public void update(WaterPolygon water) {
        
        if (isDied == true) {
            
            yPosition += sSinkSpeed;
            
            if (rotation < targetRotation)
                rotation+=1.5f;
            
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

            float waveYPosition = (float) (heightLevel + 20 * Math.sin((getX()/200.0)%(Math.PI*2)));
            setY(waveYPosition);
            
            updateAgents();
        }
    }
}


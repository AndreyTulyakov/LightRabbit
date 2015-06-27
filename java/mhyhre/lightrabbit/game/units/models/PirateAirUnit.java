/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.Bomber;
import mhyhre.lightrabbit.utils.ControllType;

public class PirateAirUnit extends UnitModel {

    private float targetRotation = -30;

    public PirateAirUnit(int id) {
        super(id, UnitType.PIRATE_AIR, 50, 40, 0.5f, 1.0f, ControllType.ALL_DIRECTIONS);
        setIdeology(UnitIdeology.PIRATE);    
        setSize(80, 30);
        setRadius(25); 
        sSinkSpeed = -2.0f;
        setGun(new Bomber(this, 50));
        heightLevel = 450;
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

            float waveYPosition = (float) (heightLevel + 20 * Math.sin((getX()/25)%(Math.PI*2)));
            setY(waveYPosition);
            
            if(getX() < -50) {
                setMoveDirection(UnitMoveDirection.RIGHT);
            }
            
            if(getX() > 1000) {
                setMoveDirection(UnitMoveDirection.LEFT);
            }
            
            
            updateAgents();
        }
    }
}


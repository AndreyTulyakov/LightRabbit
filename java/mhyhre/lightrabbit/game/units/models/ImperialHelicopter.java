/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.AutoGun;
import mhyhre.lightrabbit.game.weapons.guns.RocketLauncher;
import mhyhre.lightrabbit.utils.ControllType;

public class ImperialHelicopter extends UnitModel {

    private float targetRotation = 90;

    
    public ImperialHelicopter(int id) {
        super(id, UnitType.IMPERIAL_HELICOPTER, 120, 20, 5.0f, 4.0f, ControllType.ALL_DIRECTIONS);
        setIdeology(UnitIdeology.IMPERIAL);    
        setSize(100, 20);
        setRadius(13);
        setGun(new AutoGun(this, 120));
        sSinkSpeed = -2.0f;
        setSecondGun(new RocketLauncher(this,20));
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
            
            if (rotation < targetRotation)
                rotation+=1.5f;
            
            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
            
        } else {

         // Update jumping
            if(canJump == false && jumpAcceleration == JUMP_ACCELERATION_LIMIT) {
                if(getY() > 400) {
                    jumpAcceleration = -JUMP_ACCELERATION_LIMIT*0.5f;   
                } else {
                    jumpAcceleration = JUMP_ACCELERATION_LIMIT*0.5f;   
                }
            }


                if(canJump == false) {
                    setY(getY() + jumpAcceleration);
                    jumpAcceleration *= 0.9f;
                    if(Math.abs(jumpAcceleration) < 0.1f) {
                        jumpAcceleration = 0;
                        canJump = true;
                    }
                }
            
            float waveYPosition = water.getObjectYPosition(getX());
            if((getY()-15) <= waveYPosition) {
                setDied(true);
            }
              
            updateAgents();
        }
    }

    @Override
    public void setX(float mX) {
        if(getX() == mX) {
            setRotation(0);
        } else {
            if(getX() > mX) {
                setRotation(3);
            } else {
                setRotation(-3);
            }
        }
        super.setX(mX);
    }
}



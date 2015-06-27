/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.Gun150;
import mhyhre.lightrabbit.utils.ControllType;

public class NovaSteamShip extends UnitModel{

    private float targetRotation = 45;

    public NovaSteamShip(int id) {
        super(id, UnitType.NOVA_STEAM_SHIP, 200, 80, 2.0f, 3.0f, ControllType.LEFT_RIGHT);   
        setIdeology(UnitIdeology.FRIEDLY);
        setSize(70, 30);
        setRadius(25);
        
        setGun(new Gun150(this, 50));
    }
    
    @Override
    public void setDied(boolean mDied) {
        if(mDied == true && this.isDied == false) {
            MainActivity.resources.playSound("shipDie");
        }
        super.setDied(mDied);
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

            // Update jumping
            if(canJump == false && jumpAcceleration > - JUMP_ACCELERATION_LIMIT) {
                jumpAcceleration -= 0.8f;
            }
                 
            float waveYPositionUnderPlayer = 14 + water.getObjectYPosition(getX());

            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            
            if(getY() < waveYPositionUnderPlayer - 3 && canJump == false) {
                setY(waveYPositionUnderPlayer);
                canJump = true;
            } else {
                if(canJump == false) {
                    setY(getY() + jumpAcceleration);
                } else {
                    setY(waveYPositionUnderPlayer);
                }
            }
            updateAgents();
        }
    }

}


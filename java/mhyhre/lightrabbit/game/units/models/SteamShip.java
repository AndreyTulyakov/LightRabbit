/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.Gun90;
import mhyhre.lightrabbit.utils.ControllType;

public class SteamShip extends UnitModel{

    private float targetRotation = 45;
    
    public SteamShip(int id) {
        super(id, UnitType.STEAM_SHIP, 100, 30, 1.0f, 3.0f, ControllType.LEFT_RIGHT);   
        setIdeology(UnitIdeology.FRIEDLY);
        setSize(64, 22);
        setRadius(20);
        
        setGun(new Gun90(this, 50));
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
                jumpAcceleration -= 0.70f;
            }
                 
            float waveYPositionUnderPlayer = 10 + water.getObjectYPosition(getX());

            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            
            if(getY() < waveYPositionUnderPlayer - 5 && canJump == false) {
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
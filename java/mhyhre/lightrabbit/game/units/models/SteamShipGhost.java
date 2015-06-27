/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.weapons.guns.GhostGun;
import mhyhre.lightrabbit.utils.ControllType;

public class SteamShipGhost extends UnitModel{

    public final float JUMP_ACCELERATION_LIMIT = 23;
    
    public SteamShipGhost(int id) {
        super(id, UnitType.STEAM_SHIP_GHOST, 800, 1, 7.0f, 5.0f, ControllType.LEFT_RIGHT);   
        setIdeology(UnitIdeology.ENEMY_FOR_ALL);
        setSize(64, 22);
        setRadius(20);
        
        bright = 0.80f;
        
        setGun(new GhostGun(this, 50));
    }
    
    @Override
    public void jump() {
        if (this.isCanJump()) {
            this.setCanJump(false);
            this.setJumpAcceletation(JUMP_ACCELERATION_LIMIT);
        }
    }
    
    @Override
    public void update(WaterPolygon water) {

        if (isDied == true) {
            
            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
            
        } else {

            // Update jumping
            if(canJump == false && jumpAcceleration > - JUMP_ACCELERATION_LIMIT) {
                jumpAcceleration -= 1.2f;
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

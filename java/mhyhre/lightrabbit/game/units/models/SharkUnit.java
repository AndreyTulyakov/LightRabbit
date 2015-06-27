/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.utils.ControllType;


public class SharkUnit extends UnitModel {

    float bright;
    private float targetRotation = 180;

    public SharkUnit(int id) {
        super(id, UnitType.SHARK, 40, 20, 1.0f, 0.5f, ControllType.LEFT_RIGHT);
        setIdeology(UnitIdeology.ENEMY_FOR_ALL);
        bright = 1;
        setSize(64, 40);
        setRadius(24);
    }

    public float getBright() {
        return bright;
    }

    public float getWaterLevel() {
        return mWaterLevel;
    }

    public void setWaterLevel(float mWaterLevel) {
        this.mWaterLevel = mWaterLevel;
    }
    

    @Override
    public void update(WaterPolygon water) {
        
        if (isDied == true) {
            yPosition += sSinkSpeed;;

            if (rotation < targetRotation)
                rotation++;

            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {

            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            setWaterLevel(water.getObjectYPosition(getX()) - 40);
            
            yPosition = (float) (mWaterLevel + 30 * Math.sin(xPosition / (Math.PI * 4)));
            updateAgents();
        }
    }
}

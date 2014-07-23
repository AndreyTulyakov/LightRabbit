package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;

/*
 * Type: Shark
 * Speed: 1.0f
 * Health: 40
 * Armor: 20
 */

public class SharkUnit extends UnitModel {

    public static final float sSinkSpeed = -1.0f;

    float bright;
    float mWaterLevel;
    private float targetRotation = 180;

    public SharkUnit(int id) {
        super(id, UnitType.SHARK, 40, 20, 1.0f, 0.5f);
        setIdeology(UnitIdeology.ENEMY_FOR_ALL);
        bright = 1;
        setSize(64, 40);
        setRadius(25);
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
            moveHorizontalByDirection();
            yPosition += sSinkSpeed;

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

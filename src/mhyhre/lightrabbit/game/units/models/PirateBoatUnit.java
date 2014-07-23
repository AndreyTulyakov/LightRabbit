package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;

/*
 * Type: Pirate Boat
 * Speed: 0.5f
 * Health: 50
 * Armor: 30
 */

public class PirateBoatUnit extends UnitModel {

    public static final float sSinkSpeed = -1.0f;
    private float targetRotation = 45;

    float bright;
    float mWaterLevel;

    public PirateBoatUnit(int id) {
        super(id, UnitType.PIRATE_BOAT, 60, 30, 0.5f, 0.1f);
        setIdeology(UnitIdeology.PIRATE);

        setSize(64, 22);
        bright = 1;
        setRadius(20);
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
            setWaterLevel(2 + water.getObjectYPosition(getX()) + 2);
            
            moveHorizontalByDirection();
            yPosition = (float) (mWaterLevel);
            updateAgents();
        }
    }
/*
    @Override
    public void accelerate(float acceleration) {
        this.setX(getX() + acceleration);
    }
*/

}

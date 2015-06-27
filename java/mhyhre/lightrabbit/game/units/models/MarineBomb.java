package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.utils.ControllType;

public class MarineBomb extends UnitModel {


    float bright;
    private float targetRotation = 180;

    public MarineBomb(int id) {
        super(id, UnitType.MARINE_BOMB, 200, 10, 1.0f, 0.5f, ControllType.LEFT_RIGHT);
        setIdeology(UnitIdeology.ENEMY_FOR_ALL);
        bright = 1;
        setSize(36, 36);
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
            super.type = UnitType.MARINE_BOMB_BANG;
            yPosition += sSinkSpeed;;

            if (rotation < targetRotation)
                rotation++;

            if (bright > 0) {
                bright -= 0.02f;
                if (bright < 0.05f)
                    bright = 0.0f;
            }
            
        } else {

            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            setWaterLevel(water.getObjectYPosition(getX()) - 40);
            
            yPosition = (float) (mWaterLevel+32);
            updateAgents();
        }
    }

}

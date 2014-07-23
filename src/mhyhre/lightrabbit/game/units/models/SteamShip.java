package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;

public class SteamShip extends UnitModel{

    public static final float sSinkSpeed = -1.0f;
    private float targetRotation = 45;
    float mWaterLevel;
    
    public SteamShip(int id) {
        super(id, UnitType.STEAM_SHIP, 150, 30, 1.0f, 3.0f);   
        setIdeology(UnitIdeology.NEUTRAL);
        setSize(64, 22);
        setRadius(20);
    }
    
    @Override
    public void setDied(boolean mDied) {
        super.setDied(mDied);
        if(mDied == true) {
            MainActivity.resources.playSound("shipDie");
        }
    };

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
                 

            float waveYPositionUnderPlayer = 10 + water.getObjectYPosition(getX());

            setRotation(-water.getObjectAngle(getX()) / 2.0f);
            
            if(getY() < waveYPositionUnderPlayer - 10 && canJump == false) {
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

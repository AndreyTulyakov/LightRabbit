package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;

public class PirateBoatUnit extends Enemy {

    public static final float sSinkSpeed = -1.0f;
    public static final float sSpeed = 0.5f;
    private float targetRotation = 45;

    float bright;
    float mWaterLevel;

    public PirateBoatUnit() {
        super(EnemyType.PIRATE_BOAT, 60, 30);

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
    public void update() {

        if (isDied == true) {
            xPosition -= sSpeed;
            yPosition += sSinkSpeed;
            
            if (rotation < targetRotation)
                rotation++;
            
            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {
            xPosition -= sSpeed;
            yPosition = (float) (mWaterLevel);
        }
    }

}

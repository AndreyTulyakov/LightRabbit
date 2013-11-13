package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;

public class PirateShipUnit extends Enemy {

    public static final float sSinkSpeed = -1.0f;
    public static final float sSpeed = 0.3f;

    float bright;
    float mWaterLevel;

    public PirateShipUnit() {
        super(EnemyType.PIRATE_SHIP, 300);

        setSize(128, 75);
        bright = 1;
        setRadius(45);
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

        if (mDied == true) {
            mX -= sSpeed;
            mY += sSinkSpeed;
            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {
            mX -= sSpeed;
            mY = (float) (mWaterLevel);
        }
    }

}

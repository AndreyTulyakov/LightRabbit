package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;

public class SharkUnit extends Enemy {

    public static final float sSinkSpeed = -1.0f;
    public static final float sSpeed = 1;

    float bright;
    float mWaterLevel;
    private float currentRotation = 0, targetRotation = 180;

    public SharkUnit() {
        super(EnemyType.SHARK, 40, 20);
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
    public void update() {
        
        if (isDied == true) {
            xPosition -= sSpeed;
            yPosition += sSinkSpeed;

            if (currentRotation < targetRotation)
                currentRotation++;

            if (bright > 0) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {
            xPosition -= sSpeed;
            yPosition = (float) (mWaterLevel + 30 * Math.sin(xPosition / (Math.PI * 4)));
        }
    }

    public float getCurrentRotation() {
        return currentRotation;
    }

}

package mhyhre.lightrabbit.game.units;

/*
 * Type: Shark
 * Speed: 1.0f
 * Health: 40
 * Armor: 20
 */

public class SharkUnit extends Unit {

    public static final float sSinkSpeed = -1.0f;

    float bright;
    float mWaterLevel;
    private float targetRotation = 180;

    public SharkUnit(int id, UnitMoveDirection dir) {
        super(id, UnitType.SHARK, 40, 20, 1.0f, dir);
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
    public void update() {
        
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
            moveHorizontalByDirection();
            yPosition = (float) (mWaterLevel + 30 * Math.sin(xPosition / (Math.PI * 4)));
        }
    }


}

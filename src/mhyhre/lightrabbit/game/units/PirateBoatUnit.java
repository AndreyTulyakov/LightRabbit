package mhyhre.lightrabbit.game.units;

/*
 * Type: Pirate Boat
 * Speed: 0.5f
 * Health: 50
 * Armor: 30
 */

public class PirateBoatUnit extends Unit {

    public static final float sSinkSpeed = -1.0f;
    private float targetRotation = 45;

    float bright;
    float mWaterLevel;

    public PirateBoatUnit(int id, UnitMoveDirection dir) {
        super(id, UnitType.PIRATE_BOAT, 60, 30, 0.5f, dir);
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
            yPosition = (float) (mWaterLevel);
            updateAgents();
        }
    }

}

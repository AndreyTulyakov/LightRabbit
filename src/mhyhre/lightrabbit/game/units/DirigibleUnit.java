package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.MainActivity;

/*
 * Type: Dirigible
 * Speed: 0.3f
 * Health: 300
 * Armor: 10
 */

public class DirigibleUnit extends Unit {

    public static final float sSinkSpeed = -1.0f;
    private static final float targetRotation = -180;
    float bright;
    

    
    public DirigibleUnit(int id, UnitMoveDirection dir) {
        super(id, UnitType.DIRIGIBLE, 300, 10, 0.3f, dir);
        setIdeology(UnitIdeology.IMPERIAL);
        
        bright = 1;

        setSize(120, 60);
        setRadius(45);
    }

    
    @Override
    public void update() {
        
        if (isDied == true) {
            moveHorizontalByDirection();
            yPosition += sSinkSpeed;
            
            if (rotation > targetRotation)
                rotation--;

            if (bright > 0 && rotation == targetRotation) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {
            moveHorizontalByDirection();
            yPosition = MainActivity.getHeight() * 0.66f;
        }
    }


    public float getBright() {
        return bright;
    }


}

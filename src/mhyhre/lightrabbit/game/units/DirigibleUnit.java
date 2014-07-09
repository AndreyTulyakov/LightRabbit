package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;

public class DirigibleUnit extends Enemy {

    public static final float sSpeed = 0.3f;
    public static final float sSinkSpeed = -1.0f;
    float bright;
    
    private float targetRotation = -180;
    
    public DirigibleUnit() {
        super(EnemyType.DIRIGIBLE, 300, 10);
        bright = 1;

        setSize(120, 60);
        setRadius(45);
    }

    
    @Override
    public void update() {
        
        if (isDied == true) {
            xPosition -= sSpeed;
            yPosition += sSinkSpeed;
            
            if (rotation > targetRotation)
                rotation--;

            if (bright > 0 && rotation == targetRotation) {
                bright -= 0.01f;
                if (bright < 0.1f)
                    bright = 0.0f;
            }
        } else {
            xPosition -= sSpeed;
            yPosition = MainActivity.getHeight() * 0.66f;
        }
    }


    public float getBright() {
        return bright;
    }


}

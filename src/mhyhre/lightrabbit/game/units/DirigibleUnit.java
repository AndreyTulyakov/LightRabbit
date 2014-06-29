package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;

public class DirigibleUnit extends Enemy {

    public static final float sSpeed = 0.3f;

    public DirigibleUnit() {
        super(EnemyType.DIRIGIBLE, 300, 10);
        

        setSize(120, 60);
        setRadius(45);
    }

    @Override
    public void update() {
        xPosition -= sSpeed;
        yPosition = MainActivity.getHeight() * 0.66f;
        
        //setY(MainActivity.getHeight() * 0.66f);
        
    }

}

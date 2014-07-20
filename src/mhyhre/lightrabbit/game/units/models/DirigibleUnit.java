package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;

/*
 * Type: Dirigible
 * Speed: 0.3f
 * Health: 300
 * Armor: 10
 */

public class DirigibleUnit extends UnitModel {

    public static final float sSinkSpeed = -1.0f;
    private static final float targetRotation = -180;


    
    public DirigibleUnit(int id) {
        super(id, UnitType.DIRIGIBLE, 300, 10, 0.3f, 0.1f);
        setIdeology(UnitIdeology.IMPERIAL);

        setSize(120, 60);
        setRadius(45);
    }

    
    @Override
    public void update(WaterPolygon water) {
        
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
            updateAgents();
        }

    }



    @Override
    public void accelerate(float acceleration) {
        this.setX(getX() + acceleration);
    }


    @Override
    public void jump() {
        // None
    }


    @Override
    public void fireByGun(int gunIndex) {
        // Not now
    }



}

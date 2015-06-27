/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.utils.ControllType;

/*
 * Type: Dirigible
 * Speed: 0.3f
 * Health: 300
 * Armor: 10
 */

public class ImperialDirigibleUnit extends UnitModel {

    private static final float targetRotation = -180;
 
    public ImperialDirigibleUnit(int id) {
        super(id, UnitType.IMPERIAL_DIRIGIBLE, 200, 10, 0.3f, 0.1f, ControllType.ALL_DIRECTIONS);
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
            yPosition = MainActivity.getHeight() * 0.75f;
            updateAgents();
        }

    }

}

package mhyhre.lightrabbit.game.units.models;

import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;

public class SteamShip extends UnitModel{

    public static final float sSinkSpeed = -1.0f;
    private float targetRotation = 45;
    
    public SteamShip(int id, UnitMoveDirection dir) {
        super(id, UnitType.STEAM_SHIP, 150, 30, 3.0f, 0.1f, UnitMoveDirection.NONE);
        
        setIdeology(UnitIdeology.NEUTRAL);

        setSize(64, 22);
        setRadius(20);
    }

    @Override
    public void update() {
        
        // Update jumping
        if(canJump == false && jumpAcceleration > -JUMP_ACCELERATION_LIMIT) {
            jumpAcceleration -= 0.8f;
        }
        
    }


}

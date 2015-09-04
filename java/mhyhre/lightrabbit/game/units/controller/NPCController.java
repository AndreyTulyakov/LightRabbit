/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.controller;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.levels.events.EventType;
import mhyhre.lightrabbit.game.units.NonPlayerUnitState;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.BulletsManager;
import mhyhre.lightrabbit.game.weapons.guns.Gun;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;

public class NPCController extends UnitController {

    private NonPlayerUnitState state;
    private static final float SPEED_FACTOR = 0.2f;
    private GunIndex activeGun;

    public NPCController() {
        super(null);
        state = NonPlayerUnitState.NONE;
        currentTime = 0;
        activeGun = GunIndex.FIRST_GUN;
    }

    public NPCController(UnitModel inModel, String unitState) {
        super(inModel);

        try {
            state = NonPlayerUnitState.valueOf(unitState);
        } catch (IllegalArgumentException e) {
            state = NonPlayerUnitState.NONE;
        }
    }

    public void setStateByEvent(Event event) {

        if (event.getType() == EventType.NPC_CHANGE_STATE) {

            try {
                state = NonPlayerUnitState.valueOf(event.getStringArg());

                switch (state) {

                case ATTACK:
                    // None
                    break;

                case ATTACK_AND_MOVE:
                    model.setSpeed(event.getIntegerArg() * SPEED_FACTOR);
                    model.setMoveDirection(UnitMoveDirection.getByName(event.getSecondStringArg()));
                    break;

                case MOVE:
                    model.setSpeed(event.getIntegerArg() * SPEED_FACTOR);
                    model.setMoveDirection(UnitMoveDirection.getByName(event.getSecondStringArg()));
                    break;

                case NONE:
                    // None
                    break;

                default:
                    break;

                }

                update(currentTime);

            } catch (IllegalArgumentException e) {
                // Do nothing
            }

        } else {
            Log.w(MainActivity.DEBUG_ID, "NPC Controller::setStateByEvent: wrong event - " + event.getType().name());
        }
    }

    @Override
    public void update(float time) {
        currentTime = time;

        if (model.isDied()) {
            return;
        } else {
            Gun gun = model.getGun();
            if (gun != null) {
                gun.update(time);
            }
        }

        switch (state) {

        case ATTACK:
            if (aim()) {
                fireByGun(activeGun);
            }
            break;

        case ATTACK_AND_MOVE:
            if (aim()) {
                fireByGun(activeGun);
            }
            model.moveHorizontalByDirection();
            break;

        case MOVE:
            model.moveHorizontalByDirection();
            break;

        case NONE:
            // None
            break;

        default:
            break;

        }
    };

    @Override
    public void jump() {
    }

    @Override
    public void fireByGun(GunIndex index) {

        Gun gun = null;

        switch (index) {
        case FIRST_GUN:
            gun = model.getGun();
            break;
        case SECOND_GUN:
            gun = model.getSecondGun();
            break;
        default:
            break;

        }

        if (gun != null) {
            if (gun.canFireNow()) {

                Projectile projectile = gun.fire(model.getMoveDirection());

                if (projectile != null) {
                    BulletsManager.getInstance().addBullet(projectile);
                }
            }
        }
    }

    private boolean aim() {

        return true;
    }

    @Override
    public boolean isPlayerController() {
        return false;
    }

    public NonPlayerUnitState getState() {
        return state;
    }

    public void setState(NonPlayerUnitState state) {
        this.state = state;
    }

    @Override
    public void accelerate(int xAcceleration, int yAcceleration) {
        // TODO Auto-generated method stub

    }

}

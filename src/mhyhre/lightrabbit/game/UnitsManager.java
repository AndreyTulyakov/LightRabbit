package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.levels.events.EventType;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.UnitGenerator;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.units.agents.UnitStopAgent;
import mhyhre.lightrabbit.game.units.controller.NPCController;
import mhyhre.lightrabbit.game.units.models.UnitModel;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class UnitsManager extends SpriteBatch {

    private static final float COLLISIONS_DAMAGE_FACTOR = 0.1f;
    public static final int UNITS_MAX_COUNT = 100;
    public static final int MINIMAX_UNIT_X = -5000;
    WaterPolygon water;
    List<Unit> units;

    public UnitsManager(WaterPolygon pWater, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(MainActivity.resources.getTextureAtlas("texture01"), UNITS_MAX_COUNT, pVertexBufferObjectManager);

        water = pWater;
        units = new ArrayList<Unit>(UNITS_MAX_COUNT);
    }

    private void calculateCollisionsBetweenUnits() {

        List<Unit> units = getUnitsList();

        for (int targetIndex = 0; targetIndex < units.size(); targetIndex++) {
            for (int otherIndex = targetIndex + 1; otherIndex < units.size(); otherIndex++) {

                UnitModel targetModel = units.get(targetIndex).getModel();
                UnitModel otherModel = units.get(otherIndex).getModel();

                if (targetModel.getIdeology() != otherModel.getIdeology()) {

                    if (targetModel.isDied() || otherModel.isDied()) {
                        // Do nothing
                    } else {
                        if (Collisions.modelAndModel(targetModel, otherModel)) {
                            MainActivity.vibrate(30);
                            applyCollisionDamage(COLLISIONS_DAMAGE_FACTOR, targetModel, otherModel);
                        }
                    }
                }
            }
        }
    }

    private void applyCollisionDamage(float damageFactor, UnitModel first, UnitModel second) {

        int firstDamagePower = (int) Math.sqrt(first.getDamagePower() * damageFactor);
        int secondDamagePower = (int) Math.sqrt(second.getDamagePower() * damageFactor);

        first.setHealth(first.getHealth() - secondDamagePower);
        second.setHealth(second.getHealth() - firstDamagePower);
        
        if(first.getHealth() <= 0) {
            Log.i(MainActivity.DEBUG_ID, "Unit:" + first.getType().getName() + "died by collision.");
            first.setDied(true);
        }
        
        if(second.getHealth() <= 0) {
            Log.i(MainActivity.DEBUG_ID, "Unit:" + second.getType().getName() + "died by collision.");
            second.setDied(true);
        }
        
    }

    public void update() {

        calculateCollisionsBetweenUnits();
   
        for (int i = 0; i < units.size(); i++) {

            Unit unit = units.get(i);
            UnitModel model = unit.getModel();
            model.update(water);
            unit.getController().update();
            drawUnit(unit);

            // if out of scene
            if (model.getY() < 0 || model.getX() < MINIMAX_UNIT_X) {
                units.remove(i);
                i--;
                continue;
            }
            
            // for more fast unit termination
            if(model.isDied() && model.getBright() == 0.00f) {
                units.remove(i);
                i--;
                continue;
            }
            
        }

        submit();

    }

    private void drawUnit(Unit unit) {
        
        float directionScale;
        
        UnitModel model = unit.getModel();
        
        if(model.getMoveDirection() == UnitMoveDirection.RIGHT) {
            directionScale = -MainActivity.PIXEL_MULTIPLIER;
        } else {
            directionScale = MainActivity.PIXEL_MULTIPLIER;
        }
        
        UnitType type = model.getType();
        ITextureRegion unitRegion = MainActivity.resources.getTextureRegion(type.getName());
        
        float bright = model.getBright();
        
        draw(unitRegion,
                model.getX() - unitRegion.getWidth() / 2,
                model.getY() - unitRegion.getHeight() / 2, 
                unitRegion.getWidth(), unitRegion.getHeight(), 
                model.getRotation(),
                directionScale,
                MainActivity.PIXEL_MULTIPLIER, bright, bright, bright, bright);
    }

    public void addUnit(Unit unit) {
        if (unit != null) {
            Log.i(MainActivity.DEBUG_ID, "UnitManager:addUnit:" + unit.getModel().getType());
            units.add(unit);
        }
    }

    public void addUnit(Event event) {

        if (event.getType() == EventType.UNIT_ADD) {
            if (units.size() < UNITS_MAX_COUNT) {

                float xpos = event.getIntegerArg() + MainActivity.getWidth();
                UnitType type = UnitType.getByName(event.getStringArg());

                UnitMoveDirection moveDirection = UnitMoveDirection.LEFT;

                try {
                    moveDirection = UnitMoveDirection.valueOf(event.getSecondStringArg());

                } catch (IllegalArgumentException e) {
                    moveDirection = UnitMoveDirection.LEFT;
                }
                
                Unit unit = UnitGenerator.generate(false, type, event.getId());
                unit.getModel().setPosition(xpos, 0);
                unit.getModel().setMoveDirection(moveDirection);               
                units.add(unit);
                
                Log.i(MainActivity.DEBUG_ID, "UnitManager:addUnit:" + unit.getModel().getType());
            }
        }
    }

    public void unitSetIdeology(Event event) {
        if (event.getType() == EventType.UNIT_SET_IDEOLOGY) {

            for (Unit unit : units) {
                UnitModel model = unit.getModel();

                if (model.getId() == event.getId()) {

                    try {
                        model.setIdeology(UnitIdeology.valueOf(event.getStringArg()));

                    } catch (IllegalArgumentException e) {
                        Log.w(MainActivity.DEBUG_ID, "UnitManager: can't set unit ideology");
                    }

                }
            }
        }
    }

    public void unitSetStopPosition(Event event) {

        if (event.getType() == EventType.UNIT_SET_STOP_POSITION) {

            for (Unit unit : units) {
                if (unit.getModel().getId() == event.getId()) {
                    UnitStopAgent stopAgent = new UnitStopAgent(unit, event.getIntegerArg(), 5);
                    unit.getModel().addAgent(stopAgent);
                }
            }
        }
    }

    public int getEnemyCount(UnitIdeology unitIdeology) {
        return units.size();
    }

    public List<Unit> getUnitsList() {
        return units;
    }

    public boolean existEnemiesFor(Unit targetUnit) {
        
        UnitIdeology targetIdeology = targetUnit.getModel().getIdeology();
        
        for (Unit unit : units) {
            UnitIdeology otherIdeology = unit.getModel().getIdeology();
            
            if (targetIdeology.isEnemy(otherIdeology)) {
                return true;
            }
        }
        
        return false;
    }

    public void changeNPCState(Event gameEvent) {
        
        for (Unit unit : units) {
            
            if(unit.getModel().getId() == gameEvent.getId()) {
                if(unit.getController().isPlayerController() == false) {

                    // Need fix it.
                    NPCController controller = (NPCController) unit.getController();
                    controller.setStateByEvent(gameEvent);
                }
            }
        }
        
    }

}

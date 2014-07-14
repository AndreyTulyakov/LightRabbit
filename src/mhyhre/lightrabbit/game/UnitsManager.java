package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.levels.events.EventType;
import mhyhre.lightrabbit.game.units.DirigibleUnit;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.units.PirateBoatUnit;
import mhyhre.lightrabbit.game.units.PirateShipUnit;
import mhyhre.lightrabbit.game.units.Player;
import mhyhre.lightrabbit.game.units.SharkUnit;
import mhyhre.lightrabbit.game.units.agents.UnitStopAgent;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class UnitsManager extends SpriteBatch {

    public static final int UNITS_MAX_COUNT = 100;
    public static final int MINIMAX_UNIT_X = -5000;
    WaterPolygon water;
    List<Unit> units;

    public UnitsManager(WaterPolygon pWater, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(MainActivity.resources.getTextureAtlas("texture01"), UNITS_MAX_COUNT, pVertexBufferObjectManager);

        water = pWater;
        units = new ArrayList<Unit>(UNITS_MAX_COUNT);
    }

    private void calculateCollisionsWithEnemies(Player player) {

        for (Unit enemy : getEnemiesList()) {

            // If player collides with enemy.
            if (Collisions.spriteByCircle(player, enemy.getX(), enemy.getY(), 36)) {

                if (enemy.isDied() == false) {
                    MainActivity.vibrate(30);
                    player.decrementHealth();
                    enemy.setDied(true);
                }
            }
        }
    }

    public void update(Player player) {

        calculateCollisionsWithEnemies(player);

        ITextureRegion sharkRegion = MainActivity.resources.getTextureRegion("shark_body");
        ITextureRegion pirateBoatRegion = MainActivity.resources.getTextureRegion("pirate_boat");
        ITextureRegion pirateShipRegion = MainActivity.resources.getTextureRegion("pirate_ship");
        ITextureRegion dirigibleRegion = MainActivity.resources.getTextureRegion("dirigible");

        float bright;

        for (int i = 0; i < units.size(); i++) {

            Unit enemy = units.get(i);
            

            switch (enemy.getEnemyType()) {

            
            case PIRATE_BOAT:
                PirateBoatUnit pirateBoat = (PirateBoatUnit) enemy;

                pirateBoat.setWaterLevel(2 + water.getObjectYPosition(pirateBoat.getX()) + 2);
                pirateBoat.update();

                if (enemy.getY() < 0 || enemy.getX() < MINIMAX_UNIT_X) {
                    units.remove(i);
                    i--;
                    continue;
                }
          
                if(enemy.isDied()) {
                } else {
                    enemy.setRotation(-water.getObjectAngle(enemy.getX()) / 2.0f);
                }
                
                drawEnemy(pirateBoatRegion, pirateBoat, pirateBoat.getBright(), enemy.getRotation());
                break;

                
            case PIRATE_SHIP:
                PirateShipUnit pirateShip = (PirateShipUnit) enemy;

                pirateShip.setWaterLevel(5 + water.getObjectYPosition(pirateShip.getX()) + 20);
                pirateShip.update();

                if (enemy.getY() < 0 || enemy.getX() < MINIMAX_UNIT_X) {
                    units.remove(i);
                    i--;
                    continue;
                }
              
                
                if(enemy.isDied()) {
                } else {
                    enemy.setRotation(-water.getObjectAngle(enemy.getX()) / 2.0f);
                }
                           
                drawEnemy(pirateShipRegion, pirateShip, pirateShip.getBright(), enemy.getRotation());
                break;
                

            case SHARK:
                SharkUnit shark = (SharkUnit) enemy;

                shark.setWaterLevel(water.getObjectYPosition(shark.getX()) - 40);
                shark.update();

                boolean needRemoveEnemy = (shark.getY() > MainActivity.getHeight() || shark.getX() < MINIMAX_UNIT_X);
                needRemoveEnemy |= shark.isDied() && shark.getBright() == 0;

                if (needRemoveEnemy) {
                    units.remove(i);
                    i--;
                    continue;
                }
                
                if(enemy.isDied()) {
                } else {
                    enemy.setRotation(-water.getObjectAngle(enemy.getX()) / 2.0f);
                }
                   
                bright = shark.getBright();
                drawEnemy(sharkRegion, shark, bright, enemy.getRotation());
                break;

                
            case DIRIGIBLE:
                DirigibleUnit dirigible = (DirigibleUnit) enemy;
                dirigible.update();

                boolean needRemoveDirigible = dirigible.getX() < MINIMAX_UNIT_X;
                needRemoveDirigible |= dirigible.isDied() && dirigible.getBright() == 0;

                if (needRemoveDirigible) {
                    units.remove(i);
                    i--;
                    continue;
                }
                
                drawEnemy(dirigibleRegion, dirigible, dirigible.getBright(),  enemy.getRotation());

                
            case UNDEFINED:
                break;
                
            default:
                break;
            }
        }

        submit();

    }

    private void drawEnemy(ITextureRegion region, Unit enemy, float bright, float currentRotation) {
        float directionScale;
        switch(enemy.getMoveDirection()) {
        case LEFT:
            directionScale = MainActivity.PIXEL_MULTIPLIER;
            break;
        case RIGHT:
            directionScale = -MainActivity.PIXEL_MULTIPLIER;
            break;
        default:
            directionScale = MainActivity.PIXEL_MULTIPLIER;
            break;
           
        }
        draw(region, enemy.getX() - region.getWidth() / 2, enemy.getY() - region.getHeight() / 2, region.getWidth(), region.getHeight(), currentRotation,
                directionScale, MainActivity.PIXEL_MULTIPLIER, bright, bright, bright, bright);
    }

    public void addNewEnemy(Event event) {

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
                    
                    int id = event.getId();

                switch (type) {

                case SHARK:
                    SharkUnit shark = new SharkUnit(id, moveDirection);
                    shark.setPosition(xpos, 0);
                    units.add(shark);
                    break;

                case DIRIGIBLE:
                    DirigibleUnit dirigible = new DirigibleUnit(id, moveDirection);
                    dirigible.setPosition(xpos, 0);
                    units.add(dirigible);
                    break;

                case PIRATE_SHIP:
                    PirateShipUnit pirateShip = new PirateShipUnit(id, moveDirection);
                    pirateShip.setPosition(xpos, 0);
                    units.add(pirateShip);
                    break;

                case PIRATE_BOAT:
                    PirateBoatUnit pirateBoat = new PirateBoatUnit(id, moveDirection);
                    pirateBoat.setPosition(xpos, 0);
                    units.add(pirateBoat);
                    break;

                default:
                    break;
                }

            }
        }
    }
    
    public void unitSetIdeology(Event event) {
        if(event.getType() == EventType.UNIT_SET_IDEOLOGY) {
            
            for(Unit unit: units) {
                if(unit.getId() == event.getId()) {
                    
                    try {
                        unit.setIdeology(UnitIdeology.valueOf(event.getStringArg()));

                    } catch (IllegalArgumentException e) {
                        Log.w(MainActivity.DEBUG_ID, "UnitManager: can't set unit ideology");
                    }
                    
                }
            }   
        }
    }
    
    public void unitSetStopPosition(Event event) {
        
            if(event.getType() == EventType.UNIT_SET_STOP_POSITION) {

                for(Unit unit: units) {
                    if(unit.getId() == event.getId()) {
                        UnitStopAgent stopAgent = new UnitStopAgent(unit, event.getIntegerArg(), 5);
                        unit.addAgent(stopAgent);
                    }
                }   
            }
    }

    public int getEnemyCount() {
        return units.size();
    }

    public List<Unit> getEnemiesList() {
        return units;
    }

}

package mhyhre.lightrabbit.game.units.models;

import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.units.agents.UnitAgent;
import mhyhre.lightrabbit.game.weapons.guns.Gun;
import mhyhre.lightrabbit.utils.Vector2;


public abstract class UnitModel {
    
    public static final float JUMP_ACCELERATION_LIMIT = 16;

    protected int id;

    protected UnitType type;
    protected UnitIdeology ideology;
    protected int gold;
    

    // Demons, works in background
    protected List<UnitAgent> agents;
    protected List<Gun> guns;
    
    protected int health;
    protected int armor;
    protected float speed;
    protected UnitMoveDirection moveDirection;
    protected float moveAcceleration;


    protected boolean canJump;


    protected float jumpAcceleration;
    
    protected boolean isDied;
    protected boolean isStopped;
    
    protected float xPosition, yPosition;
    protected float rotation;
    protected float width, height;
    protected float radius = 0;

    protected float bright;
    

    public UnitModel(int id, UnitType pType, int pMaxHealth, int pArmor, float speed, float acceleration) {
        
        
        
        this.id = id;
        health = pMaxHealth;
        type = pType;
        armor = pArmor;
        this.speed = speed;
        this.moveAcceleration = acceleration;
        
        isDied = false;
        bright = 1;
        
        moveDirection = UnitMoveDirection.LEFT;
        ideology = UnitIdeology.NEUTRAL;
        
        agents = new LinkedList<UnitAgent>();

        xPosition = 0;
        yPosition = 0;
        width = 0;
        height = 0;
    }
    
    public void moveHorizontalByDirection() {
        
        if(isStopped) {
            return;
        }
        
        switch(moveDirection) {
        case LEFT:
            xPosition -= speed;
            break;
        case RIGHT:
            xPosition += speed;
            break;
        default:
            break;
        
        }

    }
    
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int pHealth) {
        this.health = pHealth;
    }

    public boolean isDied() {
        return isDied;
    }

    public void setDied(boolean mDied) {
        this.isDied = mDied;
    }

    public abstract void update(WaterPolygon water);
    
    public void updateAgents() {
        for(UnitAgent agent: agents) {
            agent.update();
            if(agent.isActive() == false) {
                agents.remove(agent);
            }
        }
    }

    public void setSize(float w, float h) {
        width = w;
        height = h;
    }

    public float getX() {
        return xPosition;
    }

    public void setX(float mX) {
        this.xPosition = mX;
    }

    public float getY() {
        return yPosition;
    }

    public void setY(float mY) {
        this.yPosition = mY;
    }

    public void setPosition(float pX, float pY) {
        xPosition = pX;
        yPosition = pY;
    }

    public void setCenteredPosition(float pX, float pY) {
        xPosition = pX - width / 2;
        yPosition = pY - height / 2;
    }

    public void setPosition(Vector2 pos) {
        xPosition = pos.x;
        yPosition = pos.y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public UnitIdeology getIdeology() {
        return ideology;
    }

    public void setIdeology(UnitIdeology ideology) {
        this.ideology = ideology;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UnitType getType() {
        return type;
    }

    public float getRadius() {
        return radius;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public UnitMoveDirection getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(UnitMoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }

    public float getRotation() {
        return rotation;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void addAgent(UnitAgent agent) {
        agents.add(agent);
    }
    
    public boolean isCanJump() {
        return canJump;
    }
    
    public int getId() {
        return id;
    }
    
    
    public float getMoveAcceleration() {
        return moveAcceleration;
    }

    public void setMoveAcceleration(float moveAcceleration) {
        this.moveAcceleration = moveAcceleration;
    }
    
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public abstract void accelerate(float acceleration);
    public abstract void jump();
    public abstract void fireByGun(int gunIndex);
    
    public int getDamagePower() {
        return armor * health;
    }

    public float getBright() {
        return bright;
    }

    public void setJumpAcceletation(float jumpAcceleration) {
        this.jumpAcceleration = jumpAcceleration;
    }
    
}

/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game;

import android.util.Log;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.scenes.utils.EaseScene;
import mhyhre.lightrabbit.utils.ControllType;

public class GameUserInterface extends EaseScene {

    public enum Buttons {
        LEFT, RIGHT, UP, DOWN, JUMP, FIRE, SECOND_FIRE;
    }
    
    private static final float BUTTON_RADIUS = 60;
    private static final int MAXIMAL_TOUCH_ELEMENTS = 16;
    
    private ControllType controllType;

    private Sprite healthSprite;
    private Sprite projectilesCountSprite;
    private Sprite projectilesSecondCountSprite;
    private Sprite spriteMoveRight, spriteMoveLeft, spriteMoveUp, spriteMoveDown;
    private Sprite spriteFire, spriteSecondFire, spriteJump;

    private Text healthPoints;
    private Text projectilesCount;
    private Text projectilesSecondCount;

    private boolean states[];
    private boolean blinking[];
    private float blinkingAlpha = 0.0f;
    private float blinkingAlphaCounter = 0.0f;
    private boolean activated;

    private TouchPoint[] touchPoints;

    
    public GameUserInterface() {

        setBackgroundEnabled(false);
        show();
        
        controllType = ControllType.LEFT_RIGHT;

        states = new boolean[MAXIMAL_TOUCH_ELEMENTS];
        blinking = new boolean[MAXIMAL_TOUCH_ELEMENTS];
        
        touchPoints = new TouchPoint[10];
        for (int i = 0; i < touchPoints.length; i++) {
            touchPoints[i] = new TouchPoint();
        }

        createIndicators();
        createButtonSprites();
        setControllType(ControllType.LEFT_RIGHT);
    }

    private void createIndicators() {
        
        float wStep = MainActivity.getWidth()/5.0f;
        
        healthSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("heart"), MainActivity.Me.getVertexBufferObjectManager());
        healthSprite.setPosition(wStep - 50, MainActivity.getHeight() - 28);    
        healthSprite.setScale(MainActivity.PIXEL_MULTIPLIER);

        projectilesCountSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("bullet150"), MainActivity.Me.getVertexBufferObjectManager());
        projectilesCountSprite.setPosition(wStep*2 - 50, MainActivity.getHeight() - 28);
        projectilesCountSprite.setScale(MainActivity.PIXEL_MULTIPLIER);
        
        projectilesSecondCountSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("x-barrel"), MainActivity.Me.getVertexBufferObjectManager());
        projectilesSecondCountSprite.setPosition(wStep*3 - 50, MainActivity.getHeight() - 28);
        projectilesSecondCountSprite.setScale(MainActivity.PIXEL_MULTIPLIER);
        

        
        healthPoints = new Text(340, 10, MainActivity.resources.getFont("White Furore"), String.valueOf(0), 20, MainActivity.Me.getVertexBufferObjectManager());
        updateHealthIndicator(0);
        
        projectilesCount = new Text(0, 0, MainActivity.resources.getFont("White Furore"), "", 16, MainActivity.Me.getVertexBufferObjectManager());
        updateProjectilesIndicator(0);
        
        projectilesSecondCount = new Text(0, 0, MainActivity.resources.getFont("White Furore"), "", 16, MainActivity.Me.getVertexBufferObjectManager());
        updateSecondProjectilesIndicator(0);
        
        
        attachChild(healthSprite);
        attachChild(healthPoints);

        attachChild(projectilesCountSprite);
        attachChild(projectilesSecondCountSprite);

        attachChild(projectilesCount);
        attachChild(projectilesSecondCount);
    }
    
    private void createButtonSprites() {
        
        spriteMoveUp = new Sprite(0, 0, MainActivity.resources.getTextureRegion("ButtonUp"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveUp.setPosition(64 + 64 + 30, 50+64+30);
        spriteMoveUp.setVisible(true);
        spriteMoveUp.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteMoveUp);
        
        spriteMoveDown = new Sprite(0, 0, MainActivity.resources.getTextureRegion("ButtonDown"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveDown.setPosition(64 + 64 + 30, 50);
        spriteMoveDown.setVisible(true);
        spriteMoveDown.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteMoveDown);
        
        spriteMoveLeft = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Left"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveLeft.setPosition(64, 70);
        spriteMoveLeft.setVisible(true);
        spriteMoveLeft.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteMoveLeft);

        spriteMoveRight = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Right"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveRight.setPosition(spriteMoveDown.getX() + 64 + 30, 70);
        spriteMoveRight.setVisible(true);
        spriteMoveRight.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteMoveRight);

        spriteFire = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Fire"), MainActivity.Me.getVertexBufferObjectManager());
        spriteFire.setPosition(MainActivity.getWidth() - 64, 70);
        spriteFire.setVisible(true);
        spriteFire.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteFire);
        
        spriteSecondFire = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Fire"), MainActivity.Me.getVertexBufferObjectManager());
        spriteSecondFire.setPosition(MainActivity.getWidth() - 64, 60 + 64 + 30);
        spriteSecondFire.setVisible(true);
        spriteSecondFire.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteSecondFire);
        
        spriteJump = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Jump"), MainActivity.Me.getVertexBufferObjectManager());
        spriteJump.setPosition((spriteFire.getX() - 64) - 30, 70);
        spriteJump.setVisible(true);
        spriteJump.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteJump);
    }
    
    public void hideTopPanel() {
        healthSprite.setVisible(false);
        projectilesCountSprite.setVisible(false);
        healthPoints.setVisible(false);
        projectilesCount.setVisible(false);
        projectilesSecondCount.setVisible(false);
        projectilesSecondCountSprite.setVisible(false);
    }
    
    public void showTopPanle() {
        healthSprite.setVisible(true);
        projectilesCountSprite.setVisible(true);
        healthPoints.setVisible(true);
        projectilesCount.setVisible(true);
    }

    public void updateProjectilesIndicator(int value) {     
            projectilesCount.setText(String.valueOf(value));
            projectilesCount.setPosition(projectilesCountSprite.getX() + 30 + projectilesCount.getWidth() / 2, MainActivity.getHeight() - 24);    
    }
    
    public void updateSecondProjectilesIndicator(int value) {     
        projectilesSecondCount.setText(String.valueOf(value));
        projectilesSecondCount.setPosition(projectilesSecondCountSprite.getX() + 30 + projectilesSecondCount.getWidth() / 2, MainActivity.getHeight() - 24);    
    }

    public void updateHealthIndicator(int currentHealth) {
        healthPoints.setText(String.valueOf(currentHealth));
        healthPoints.setPosition(healthSprite.getX() + 30 + healthPoints.getWidth() / 2, MainActivity.getHeight() - 24);
    }

    private class TouchPoint {
        boolean used;
        float x, y;
    }

    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

        if(activated) {
        
            int pId = pSceneTouchEvent.getPointerID();
    
            if (pId < 0 && pId >= touchPoints.length)
                return false;
    
            if (pSceneTouchEvent.isActionCancel() || pSceneTouchEvent.isActionOutside() || pSceneTouchEvent.isActionUp()) {
                touchPoints[pId].used = false;
                updateCollisionStates();
            }
    
            if (pSceneTouchEvent.isActionMove() || pSceneTouchEvent.isActionDown()) {
    
                touchPoints[pId].used = true;
                touchPoints[pId].x = pSceneTouchEvent.getX();
                touchPoints[pId].y = pSceneTouchEvent.getY();
    
                updateCollisionStates();
            }
        }

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    private void updateCollisionStates() {
        
        resetStates();

        for (int i = 0; i < touchPoints.length; i++) {

            if (touchPoints[i].used == true) {
                
                states[Buttons.UP.ordinal()] |= Collisions.spriteByCircle(spriteMoveUp, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.UP.ordinal()] &= spriteMoveUp.isVisible();
                
                states[Buttons.DOWN.ordinal()] |= Collisions.spriteByCircle(spriteMoveDown, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.DOWN.ordinal()] &= spriteMoveDown.isVisible();
                
                states[Buttons.LEFT.ordinal()] |= Collisions.spriteByCircle(spriteMoveLeft, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.LEFT.ordinal()] &= spriteMoveLeft.isVisible();
                
                states[Buttons.RIGHT.ordinal()] |= Collisions.spriteByCircle(spriteMoveRight, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.RIGHT.ordinal()] &= spriteMoveLeft.isVisible();
                
                states[Buttons.FIRE.ordinal()] |= Collisions.spriteByCircle(spriteFire, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.FIRE.ordinal()] &= spriteFire.isVisible();
 
                states[Buttons.SECOND_FIRE.ordinal()] |= Collisions.spriteByCircle(spriteSecondFire, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.SECOND_FIRE.ordinal()] &= spriteSecondFire.isVisible();
                
                states[Buttons.JUMP.ordinal()] |= Collisions.spriteByCircle(spriteJump, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.JUMP.ordinal()] &= spriteJump.isVisible();
            }
        }
    }
    
    public void resetTouches() {
        for (int touchId = 0; touchId < touchPoints.length; touchId++) {
            touchPoints[touchId].used = false;
        }
    }

    public void resetStates() {
        for (int keyId = 0; keyId < states.length; keyId++) {
            states[keyId] = false;
        }
    }

    public boolean isKeyDown(Buttons code) {
        
        if(activated == false) {
            return false;
        }

        return states[code.ordinal()];
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public void show() {
        setActivated(true);
        super.show();
    }

    @Override
    public void hide() {
        setActivated(false);
        super.hide();
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        
        blinkingAlphaCounter += pSecondsElapsed * 5.0f;
        if(blinkingAlphaCounter >= Math.PI) {
            blinkingAlphaCounter = 0.0f;
        }
        blinkingAlpha =  0.3f + (float)(0.7f * Math.sin(blinkingAlphaCounter));
        
        updateBlinkingElements();
        
        super.onManagedUpdate(pSecondsElapsed);
    }

    public void showElement(String elementName) {
        try {
            Buttons button = Buttons.valueOf(elementName);
            blinking[button.ordinal()] = false;
            showElement(button);
        } catch(IllegalArgumentException e) {
            Log.e(MainActivity.DEBUG_ID, "GameUserInterface::showElement: wrong element name");
        }
    }
    
    public void showElement(Buttons button) {

        switch(button) {
        case FIRE:
            spriteFire.setVisible(true);
            spriteFire.setAlpha(1);
            spriteFire.setIgnoreUpdate(false);
            break;
            
        case SECOND_FIRE:
            spriteSecondFire.setVisible(true);
            spriteSecondFire.setAlpha(1);
            spriteSecondFire.setIgnoreUpdate(false);
            break;
            
        case JUMP:
            spriteJump.setVisible(true);
            spriteJump.setAlpha(1);
            spriteJump.setIgnoreUpdate(false);
            break;
            
        case LEFT:
            spriteMoveLeft.setVisible(true);
            spriteMoveLeft.setAlpha(1);
            spriteMoveLeft.setIgnoreUpdate(false);
            break;
            
        case RIGHT:
            spriteMoveRight.setVisible(true);
            spriteMoveRight.setAlpha(1);
            spriteMoveRight.setIgnoreUpdate(false);
            break;
            
        case UP:
            spriteMoveUp.setVisible(true);
            spriteMoveUp.setAlpha(1);
            spriteMoveUp.setIgnoreUpdate(false);
            break;
            
        case DOWN:
            spriteMoveDown.setVisible(true);
            spriteMoveDown.setAlpha(1);
            spriteMoveDown.setIgnoreUpdate(false);
            break;               
        default:
            break;           
        }
    }

    public void hideAllElements() {
        
        for(int index = 0; index < blinking.length; index++) {
            blinking[index] = false;
        }
        
        spriteFire.setVisible(false);
        spriteFire.setIgnoreUpdate(true);
        
        spriteSecondFire.setVisible(false);
        spriteSecondFire.setIgnoreUpdate(true);

        spriteJump.setVisible(false);
        spriteJump.setIgnoreUpdate(true);

        spriteMoveLeft.setVisible(false);
        spriteMoveLeft.setIgnoreUpdate(true);

        spriteMoveRight.setVisible(false);
        spriteMoveRight.setIgnoreUpdate(true);
        
        spriteMoveUp.setVisible(false);
        spriteMoveUp.setIgnoreUpdate(true);
        
        spriteMoveDown.setVisible(false);
        spriteMoveDown.setIgnoreUpdate(true);
    }
    

    public void blinkElement(String elementName) {
        showElement(elementName);
        
        try {
            Buttons button = Buttons.valueOf(elementName);
            blinking[button.ordinal()] = true;
            
        } catch(IllegalArgumentException e) {
            Log.e(MainActivity.DEBUG_ID, "GameUserInterface::showElement: wrong element name");
        }
    }
    
    
    private void updateBlinkingElements() {
        
        for(int index = 0; index < Buttons.values().length && index < blinking.length; index++) {
            
            if(blinking[index] == true) {
                
                Buttons button = Buttons.values()[index];
                
                switch(button) {
                case FIRE:
                    spriteFire.setAlpha(blinkingAlpha);  
                    break;
                    
                case SECOND_FIRE:
                    spriteSecondFire.setAlpha(blinkingAlpha);
                    break;
                    
                case JUMP:
                    spriteJump.setAlpha(blinkingAlpha);
                    break;
                    
                case LEFT:
                    spriteMoveLeft.setAlpha(blinkingAlpha);
                    break;
                    
                case RIGHT:
                    spriteMoveRight.setAlpha(blinkingAlpha);
                    break;     
                    
                case UP:
                    spriteMoveUp.setAlpha(blinkingAlpha);
                    break;
                    
                case DOWN:
                    spriteMoveDown.setAlpha(blinkingAlpha);
                    break;
                default:
                    break;   
                }
            }
        }
    }

    public void hideElement(String elementName) {
        try {
            Buttons button = Buttons.valueOf(elementName);
            blinking[button.ordinal()] = false;
            hideElement(button);

        } catch(IllegalArgumentException e) {
            Log.e(MainActivity.DEBUG_ID, "GameUserInterface::showElement: wrong element name");
        }
    }
    
    public void hideElement(Buttons button) {
        switch(button) {
        
        case FIRE:
            spriteFire.setVisible(false);
            spriteFire.setAlpha(0);
            spriteFire.setIgnoreUpdate(true);
            break;
            
        case SECOND_FIRE:
            spriteSecondFire.setVisible(false);
            spriteSecondFire.setAlpha(0);
            spriteSecondFire.setIgnoreUpdate(true);
            break;
            
        case JUMP:
            spriteJump.setVisible(false);
            spriteJump.setAlpha(0);
            spriteJump.setIgnoreUpdate(true);
            break;
            
        case LEFT:
            spriteMoveLeft.setVisible(false);
            spriteMoveLeft.setAlpha(0);
            spriteMoveLeft.setIgnoreUpdate(true);
            break;
            
        case RIGHT:
            spriteMoveRight.setVisible(false);
            spriteMoveRight.setAlpha(0);
            spriteMoveRight.setIgnoreUpdate(true);
            break;
                           
        case UP:
            spriteMoveUp.setVisible(false);
            spriteMoveUp.setAlpha(0);
            spriteMoveUp.setIgnoreUpdate(true);
            break;  
            
        case DOWN:
            spriteMoveDown.setVisible(false);
            spriteMoveDown.setAlpha(0);
            spriteMoveDown.setIgnoreUpdate(true);
            break;
        default:
            break;  
        }
        
    }

    public void reloadGun() {
        projectilesCount.setColor(0.8f,0.5f,0.5f,0.5f);
    }

    public void readyGun() {
        projectilesCount.setColor(1.0f,1.0f,1.0f,1.0f);
    }
    
    public void reloadSecondGun() {
        projectilesSecondCount.setColor(0.8f,0.5f,0.5f,0.5f);
    }
    
    public void readySecondGun() {
        projectilesSecondCount.setColor(1.0f,1.0f,1.0f,1.0f);
    }

    
    public void enableSecondGunIndicators(boolean value) {
        
        if(value) {
            showElement(Buttons.SECOND_FIRE);
            projectilesSecondCount.setVisible(true);
            projectilesSecondCountSprite.setVisible(true);
        } else {
            hideElement(Buttons.SECOND_FIRE);
            projectilesSecondCount.setVisible(false);
            projectilesSecondCountSprite.setVisible(false);
        }
    }
    
    public ControllType getControllType() {
        return controllType;
    }
    

    
    public void setControllType(ControllType type) {
        // Change visibility and position;
        this.controllType = type;
        
        switch(type) {
        
        case ALL_DIRECTIONS:
            showElement(Buttons.UP);
            showElement(Buttons.DOWN);
            spriteMoveUp.setPosition(64 + 64 + 30, 50+64+30);
            spriteMoveDown.setPosition(64 + 64 + 30, 50);
            spriteMoveRight.setPosition(spriteMoveDown.getX() + 64 + 30, 97);
            spriteMoveLeft.setPosition(64, 97);
            break;
            
        case LEFT_RIGHT:
            hideElement(Buttons.UP);
            hideElement(Buttons.DOWN);
            spriteMoveLeft.setPosition(64, 70);
            spriteMoveRight.setPosition(spriteMoveLeft.getX() + 64 + 30, 70);
            break;
            
        default:
            break;
        
        }
    }
}

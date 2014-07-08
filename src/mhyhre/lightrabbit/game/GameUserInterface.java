package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.scene.utils.EaseScene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class GameUserInterface extends EaseScene {

    public enum Buttons {
        LEFT, RIGHT, JUMP, FIRE;
    }

    Sprite spriteGold;
    Sprite healthSprite;
    Sprite spriteMoveRight, spriteMoveLeft, spriteFire, spriteJump;
    Text textGold;
    Text healthPoints;

    boolean states[];
    private static final float BUTTON_RADIUS = 60;
    
    private boolean activated;

    TouchPoint[] touchPoints;

    public GameUserInterface() {

        setBackgroundEnabled(false);
        show();

        states = new boolean[16];

        touchPoints = new TouchPoint[10];
        for (int i = 0; i < touchPoints.length; i++) {
            touchPoints[i] = new TouchPoint();
        }

        healthSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("heart"), MainActivity.Me.getVertexBufferObjectManager());
        healthSprite.setPosition(60, MainActivity.getHeight() - 28);    
        healthSprite.setScale(MainActivity.PIXEL_MULTIPLIER);
        
        spriteGold = new Sprite(0, 0, MainActivity.resources.getTextureRegion("gold"), MainActivity.Me.getVertexBufferObjectManager());
        spriteGold.setPosition(MainActivity.getWidth() - 60, MainActivity.getHeight() - 28);
        spriteGold.setScale(MainActivity.PIXEL_MULTIPLIER);
        
        textGold = new Text(340, 10, MainActivity.resources.getFont("White Furore"), String.valueOf(0), 20, MainActivity.Me.getVertexBufferObjectManager());
        updateGoldIndicator(0);
        
        healthPoints = new Text(340, 10, MainActivity.resources.getFont("White Furore"), String.valueOf(0), 20, MainActivity.Me.getVertexBufferObjectManager());
        updateHealthIndicator(0);

        attachChild(healthSprite);
        attachChild(healthPoints);
        attachChild(spriteGold);
        attachChild(textGold);

        spriteMoveLeft = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Left"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveLeft.setPosition(64, 50);
        spriteMoveLeft.setVisible(true);
        spriteMoveLeft.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteMoveLeft);

        spriteMoveRight = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Right"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveRight.setPosition(spriteMoveLeft.getX() + 64 + 30, 50);
        spriteMoveRight.setVisible(true);
        spriteMoveRight.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteMoveRight);

        spriteFire = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Fire"), MainActivity.Me.getVertexBufferObjectManager());
        spriteFire.setPosition(MainActivity.getWidth() - 64, 50);
        spriteFire.setVisible(true);
        spriteFire.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteFire);
        
        spriteJump = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Jump"), MainActivity.Me.getVertexBufferObjectManager());
        spriteJump.setPosition((spriteFire.getX() - 64) - 30, 50);
        spriteJump.setVisible(true);
        spriteJump.setScale(MainActivity.PIXEL_MULTIPLIER*2);
        attachChild(spriteJump);

    }

    public void updateGoldIndicator(int value) {
        textGold.setText(String.valueOf(value));
        textGold.setPosition(spriteGold.getX() - 30 - textGold.getWidth() / 2, MainActivity.getHeight() - 24);
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

                states[Buttons.LEFT.ordinal()] |= Collisions.spriteByCircle(spriteMoveLeft, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.RIGHT.ordinal()] |= Collisions.spriteByCircle(spriteMoveRight, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.FIRE.ordinal()] |= Collisions.spriteByCircle(spriteFire, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.JUMP.ordinal()] |= Collisions.spriteByCircle(spriteJump, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
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
    
    

}

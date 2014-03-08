package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.scene.utils.EaseScene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class GameHUD extends EaseScene {

    public enum Buttons {
        LEFT, RIGHT, JUMP, FIRE;
    }

    Sprite spriteGold;
    Sprite healthSprite;
    Sprite spriteMoveRight, spriteMoveLeft, spriteFire;
    Text textGold;
    Text healthPoints;

    boolean states[];
    private static final float BUTTON_RADIUS = 50;

    TouchPoint[] touchPoints;

    public GameHUD() {

        setBackgroundEnabled(false);
        show();

        states = new boolean[16];

        touchPoints = new TouchPoint[10];
        for (int i = 0; i < touchPoints.length; i++) {
            touchPoints[i] = new TouchPoint();
        }

        healthSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("heart"), MainActivity.Me.getVertexBufferObjectManager());
        healthSprite.setPosition(60, MainActivity.getHeight() - 28);    
        
        spriteGold = new Sprite(0, 0, MainActivity.resources.getTextureRegion("gold"), MainActivity.Me.getVertexBufferObjectManager());
        spriteGold.setPosition(250, MainActivity.getHeight() - 28);

        textGold = new Text(340, 10, MainActivity.resources.getFont("White Furore"), String.valueOf(0), 20, MainActivity.Me.getVertexBufferObjectManager());
        updateGoldIndicator(0);
        
        healthPoints = new Text(340, 10, MainActivity.resources.getFont("White Furore"), String.valueOf(0), 20, MainActivity.Me.getVertexBufferObjectManager());
        updateHealthIndicator(0);

        attachChild(healthSprite);
        attachChild(healthPoints);
        attachChild(spriteGold);
        attachChild(textGold);

        spriteMoveLeft = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Left"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveLeft.setPosition(60, 50);
        spriteMoveLeft.setVisible(true);
        attachChild(spriteMoveLeft);

        spriteMoveRight = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Right"), MainActivity.Me.getVertexBufferObjectManager());
        spriteMoveRight.setPosition(spriteMoveLeft.getX() + spriteMoveLeft.getWidth() + 20, 50);
        spriteMoveRight.setVisible(true);
        attachChild(spriteMoveRight);

        spriteFire = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Fire"), MainActivity.Me.getVertexBufferObjectManager());
        spriteFire.setPosition(MainActivity.getWidth() - (spriteFire.getWidth() + 40), 50);
        spriteFire.setVisible(true);
        attachChild(spriteFire);

    }

    public void updateGoldIndicator(int value) {
        textGold.setText(String.valueOf(value));
        textGold.setPosition(spriteGold.getX() + spriteGold.getWidth() / 2 + 10 + textGold.getWidth() / 2, MainActivity.getHeight() - 24);
    }

    public void updateHealthIndicator(int currentHealth) {
        healthPoints.setText(String.valueOf(currentHealth));
        healthPoints.setPosition(healthSprite.getX() + healthSprite.getWidth() / 2 + 10 + healthPoints.getWidth() / 2, MainActivity.getHeight() - 24);
    }

    private class TouchPoint {
        boolean used;
        float x, y;
    }

    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

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

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    private void updateCollisionStates() {

        resetStates();

        for (int i = 0; i < touchPoints.length; i++) {

            if (touchPoints[i].used == true) {

                states[Buttons.LEFT.ordinal()] |= Collisions.spriteByCircle(spriteMoveLeft, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.RIGHT.ordinal()] |= Collisions.spriteByCircle(spriteMoveRight, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
                states[Buttons.FIRE.ordinal()] |= Collisions.spriteByCircle(spriteFire, touchPoints[i].x, touchPoints[i].y, BUTTON_RADIUS);
            }
        }
    }

    public void resetStates() {
        for (int i = 0; i < states.length; i++) {
            states[i] = false;
        }
    }

    public boolean isKeyDown(Buttons code) {
        return states[code.ordinal()];
    }

}

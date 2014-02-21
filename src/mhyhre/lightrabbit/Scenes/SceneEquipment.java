package mhyhre.lightrabbit.Scenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.equipment.ShipConfigurator;

public class SceneEquipment extends MhyhreScene {
    
    ShipConfigurator equipmentPanel;
    
    public SceneEquipment() {
        
        setBackgroundEnabled(true);
        setBackground(new Background(0.9f, 0.9f, 1.0f));
        
        float borders = 10;
        
        Rectangle rect1 = new Rectangle(MainActivity.getHalfWidth(), 150, MainActivity.getWidth()-borders*2, 100, MainActivity.getVboManager());
        rect1.setColor(0.8f, 0.8f, 0.9f);
        attachChild(rect1);
        
        equipmentPanel = new ShipConfigurator();
        attachChild(equipmentPanel);
        
        
        float firstHorizontalLine = 50;
        
        // Back button
        Sprite mBackButtonSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button2"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.LevelSelector);
                }
                return true;
            }
        };

        mBackButtonSprite.setPosition(mBackButtonSprite.getWidth(), firstHorizontalLine);
        String strBack = MainActivity.Me.getString(R.string.textBack);
        Text textBack = new Text(0, 0, MainActivity.resources.getFont("Furore"), strBack, MainActivity.getVboManager());
        textBack.setPosition(mBackButtonSprite);

        attachChild(mBackButtonSprite);
        registerTouchArea(mBackButtonSprite);
        attachChild(textBack);
        
        
        // Start
        Sprite mStartButtonSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button2"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.GameLoading);
                }
                return true;
            }
        };
        
        mStartButtonSprite.setPosition(MainActivity.getWidth() - mStartButtonSprite.getWidth(), firstHorizontalLine);
        String strStart = MainActivity.Me.getString(R.string.textStart);
        Text textStart = new Text(0, 0, MainActivity.resources.getFont("Furore"), strStart, MainActivity.getVboManager());
        textStart.setPosition(mStartButtonSprite);
        
        attachChild(mStartButtonSprite);
        registerTouchArea(mStartButtonSprite);
        attachChild(textStart);
        
        
        float secondHorizontalLine = 150;
        
        
        // Prev ship
        Sprite mPrevShipButtonSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Left"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    
                }
                return true;
            }
        };
        
        mPrevShipButtonSprite.setPosition(mPrevShipButtonSprite.getWidth(), secondHorizontalLine);
        attachChild(mPrevShipButtonSprite);
        registerTouchArea(mPrevShipButtonSprite);
        
        
        // Next ship
        Sprite mNextShipButtonSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Right"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    
                }
                return true;
            }
        };
        
        mNextShipButtonSprite.setPosition(MainActivity.getWidth() - mNextShipButtonSprite.getWidth(), secondHorizontalLine);
        attachChild(mNextShipButtonSprite);
        registerTouchArea(mNextShipButtonSprite);
        
        
    }

}

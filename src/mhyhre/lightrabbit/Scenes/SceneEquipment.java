/*
 * Copyright (C) 2013 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.scene.utils.EaseScene;
import mhyhre.lightrabbit.scene.utils.ShipSlotSelector;

public class SceneEquipment extends EaseScene {
  
    ShipSlotSelector shipSlotSelector;
    
    
    public SceneEquipment() {
        
        shipSlotSelector = new ShipSlotSelector();
        attachChild(shipSlotSelector);
        
        
        
        setBackgroundEnabled(true);
        setBackground(new Background(0.7f, 0.7f, 0.7f));

        addBackAndStartButtons();
        addNextPreviousShipSlotButtons();
          
    }
    
    
    private void addBackAndStartButtons() {
        
        float buttionsVerticalLine = 50;
        
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

        mBackButtonSprite.setPosition(mBackButtonSprite.getWidth(), buttionsVerticalLine);
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
        
        mStartButtonSprite.setPosition(MainActivity.getWidth() - mStartButtonSprite.getWidth(), buttionsVerticalLine);
        String strStart = MainActivity.Me.getString(R.string.textStart);
        Text textStart = new Text(0, 0, MainActivity.resources.getFont("Furore"), strStart, MainActivity.getVboManager());
        textStart.setPosition(mStartButtonSprite);
        
        attachChild(mStartButtonSprite);
        registerTouchArea(mStartButtonSprite);
        attachChild(textStart);
    }

    private void addNextPreviousShipSlotButtons() {
        
        float buttonsVerticalLine = 150;
        
        // Previous ship
        Sprite mPrevShipButtonSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Left"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    
                }
                return true;
            }
        };
        
        mPrevShipButtonSprite.setPosition(mPrevShipButtonSprite.getWidth(), buttonsVerticalLine);
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
        
        mNextShipButtonSprite.setPosition(MainActivity.getWidth() - mNextShipButtonSprite.getWidth(), buttonsVerticalLine);
        attachChild(mNextShipButtonSprite);
        registerTouchArea(mNextShipButtonSprite);
    }
}

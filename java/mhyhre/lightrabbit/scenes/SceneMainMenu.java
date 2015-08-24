/*
 * Copyright (C) 2014-2015 Andrey Tulyakov
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


import com.google.android.gms.analytics.HitBuilders;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.IFont;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.StringsBase;
import mhyhre.lightrabbit.scenes.utils.BubbleGenerator;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

public class SceneMainMenu extends EaseScene {

    public final float INACTIVE_ALPHA = 0.45f;

    private BubbleGenerator bubbleGenerator;

    public Text mCaptionItem1; // New Game
    public Text mCaptionItem2; // About
    public Text mCaptionItem3; // Exit
    private Sprite background;

    
    public SceneMainMenu() {
        
        setBackgroundEnabled(false);
        background = new Sprite(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),
                MainActivity.resources.getTextureRegion("backgroundLevelSelector"),
                MainActivity.Me.getVertexBufferObjectManager());
        background.setScale(2);
        attachChild(background);

        bubbleGenerator = new BubbleGenerator();
        attachChild(bubbleGenerator);

        // Text captions
        String TextItem1 = StringsBase.getInstance().getValue("MenuItem_Play");
        String TextItem3 = StringsBase.getInstance().getValue("MenuItem_About");
        String TextItem4 = StringsBase.getInstance().getValue("MenuItem_Exit");

        // Creating text
        final IFont usedFont = MainActivity.resources.getFont("Furore");
        
        mCaptionItem1 = new Text(0, 0, usedFont, TextItem1, MainActivity.Me.getVertexBufferObjectManager());
        mCaptionItem2 = new Text(0, 0, usedFont, TextItem3, MainActivity.Me.getVertexBufferObjectManager());
        mCaptionItem3 = new Text(0, 0, usedFont, TextItem4, MainActivity.Me.getVertexBufferObjectManager());
       
        mCaptionItem1.setColor(0.1f,0.6f,1.0f);
        mCaptionItem2.setColor(0.1f,0.6f,1.0f);
        mCaptionItem3.setColor(0.1f,0.6f,1.0f);

        // Creating sprites
        Sprite buttonSpriteGame = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.resources.playSound("click");
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.LevelSelector);

                    MainActivity.Me.tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Main menu: Start")
                            .build());
                }
                return true;
            }
        };
        buttonSpriteGame.setScale(MainActivity.PIXEL_MULTIPLIER);

        Sprite buttonSpriteAbout = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.resources.playSound("click");
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.About);

                    MainActivity.Me.tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Main menu: About")
                            .build());
                }
                return true;
            }
        };
        buttonSpriteAbout.setScale(MainActivity.PIXEL_MULTIPLIER);

        Sprite buttonSpriteExit = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.resources.playSound("click");
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.Exit);

                    MainActivity.Me.tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Main menu: Exit")
                            .build());
                }
                return true;
            }
        };
        buttonSpriteExit.setScale(MainActivity.PIXEL_MULTIPLIER);

        // Calculating positions
        float OffsetX = MainActivity.getHalfWidth();
        float OffsetY = MainActivity.getHeight() / 9.0f;

        buttonSpriteGame.setPosition(OffsetX, OffsetY * 6.5f);
        buttonSpriteAbout.setPosition(OffsetX, MainActivity.getHalfHeight());
        buttonSpriteExit.setPosition(OffsetX, OffsetY * 2.5f);

        mCaptionItem1.setPosition(buttonSpriteGame);
        mCaptionItem2.setPosition(buttonSpriteAbout);
        mCaptionItem3.setPosition(buttonSpriteExit);

        // Attaching
        attachChild(buttonSpriteGame);
        attachChild(buttonSpriteAbout);
        attachChild(buttonSpriteExit);

        attachChild(mCaptionItem1);
        attachChild(mCaptionItem2);
        attachChild(mCaptionItem3);

        // Register Touch Listeners
        registerTouchArea(buttonSpriteGame);
        registerTouchArea(buttonSpriteAbout);
        registerTouchArea(buttonSpriteExit);

        // Vibro, Sound enable/disable
        Sprite mSpriteVibro = new Sprite(0, 0, MainActivity.resources.getTextureRegion("ButtonVibration"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    if (MainActivity.isVibroEnabled() == true) {
                        this.setAlpha(INACTIVE_ALPHA);
                        MainActivity.setVibroEnabled(false);
                    } else {
                        this.setAlpha(1.0f);
                        MainActivity.setVibroEnabled(true);
                    }
                    MainActivity.vibrate(60);
                }
                return true;
            }
        };

        mSpriteVibro.setScale(MainActivity.PIXEL_MULTIPLIER);
        mSpriteVibro.setPosition(MainActivity.getWidth() / 6, MainActivity.getHalfHeight());
        registerTouchArea(mSpriteVibro);
        attachChild(mSpriteVibro);
        mSpriteVibro.setColor(0.5f, 0.85f, 1.0f);

        Sprite mSpriteSound = new Sprite(0, 0, MainActivity.resources.getTextureRegion("ButtonSound"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    if (MainActivity.isSoundEnabled() == true) {
                        this.setAlpha(INACTIVE_ALPHA);
                        MainActivity.setSoundEnabled(false);
                    } else {
                        this.setAlpha(1.0f);
                        MainActivity.setSoundEnabled(true);
                        MainActivity.resources.playSound("click");
                    }
                    MainActivity.vibrate(30);
                }
                return true;
            }
        };

        mSpriteSound.setScale(MainActivity.PIXEL_MULTIPLIER);
        mSpriteSound.setPosition((MainActivity.getWidth()/6)*5, MainActivity.getHalfHeight());
        attachChild(mSpriteSound);
        registerTouchArea(mSpriteSound);
        mSpriteSound.setColor(0.5f,0.85f,1.0f);
        
        if (MainActivity.isVibroEnabled() == false) {
            mSpriteVibro.setAlpha(INACTIVE_ALPHA);
        }

        if (MainActivity.isSoundEnabled() == false) {
            mSpriteSound.setAlpha(INACTIVE_ALPHA);
        }
    }
}

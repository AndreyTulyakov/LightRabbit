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

import android.util.Log;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.scenes.utils.EaseScene;


public class SceneLoader extends EaseScene {

    private float AlphaTime = 0.0f;
    private float AlphaTime2 = -0.3f;
    private float AlphaTime3 = 0.0f;

    private boolean soundPlayed = false;

    public Text mCaptionTapScreen;

    public Sprite splashSprite;
    public Text textGameLogo;

    private boolean Clicked = false;
    
    public SceneLoader() {
        
        setBackgroundEnabled(true);
        setBackground(new Background(0.1f, 0.25f, 0.3f));
        
        MainActivity.resources.LoadResourcesForPreloader();
        Color captionsColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);

        // Tap text
        String TextMessage = MainActivity.Me.getString(R.string.textTap);
        mCaptionTapScreen = new Text(0, 0, MainActivity.resources.getFont("White Furore"), TextMessage, MainActivity.getVboManager());
        mCaptionTapScreen.setPosition(MainActivity.getHalfWidth(), (MainActivity.getHeight() / 4) * 1);
        mCaptionTapScreen.setVisible(false);
        mCaptionTapScreen.setAlpha(0.0f);
        mCaptionTapScreen.setColor(captionsColor);
        
        textGameLogo = new Text(0, 0, MainActivity.resources.getFont("Furore48"), " " + MainActivity.Me.getString(R.string.app_name),
                MainActivity.getVboManager());
        textGameLogo.setPosition(MainActivity.getHalfWidth(), (MainActivity.getHeight() / 4) * 3);
        textGameLogo.setAlpha(0.0f);
        textGameLogo.setColor(captionsColor);

        // tap-zone
        splashSprite = new Sprite(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(), MainActivity.resources.getTextureRegion("splash") , MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN && !Clicked) {
                    unregisterTouchArea(splashSprite);
                    Clicked = true;
                    Log.i(MainActivity.DEBUG_ID, "Splash Screen [ Tap ] button");
                    MainActivity.vibrate(30);
                }
                return true;
            }
        };

        splashSprite.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        splashSprite.setScale(2);

        attachChild(splashSprite);
        attachChild(textGameLogo);
        attachChild(mCaptionTapScreen);


        // Alpha Timer
        registerUpdateHandler(new TimerHandler(0.02f, true, new ITimerCallback() {
            // @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {

                if (mCaptionTapScreen.isVisible()) {

                    if (AlphaTime2 > 0.3f && soundPlayed == false) {
                        MainActivity.resources.playSound("switchOn");
                        soundPlayed = true;
                    }

                    if (AlphaTime2 < 0.99f) {
                        AlphaTime2 += 0.01f;
                        if (AlphaTime2 < 0.0f) {
                            textGameLogo.setAlpha(0.0f);
                        } else {
                            textGameLogo.setAlpha(AlphaTime2);
                        }
                    }

                    if (AlphaTime2 > 0.5f) {
                        AlphaTime += 0.05f;
                        if (AlphaTime > Math.PI)
                            AlphaTime = 0.0f;
                        mCaptionTapScreen.setAlpha((float) Math.sin(AlphaTime));
                    }
                }

                if (Clicked) {
                    // Splash off scene by rect
                    if (AlphaTime3 >= 1.0f) {
                        unregisterUpdateHandler(pTimerHandler);
                        MainActivity.getRootScene().SetState(SceneStates.MainMenu);
                    } else {
                        AlphaTime3 += 0.1f;
                    }
                    splashSprite.setVisible(true);
                    splashSprite.setAlpha(AlphaTime3);
                }
            }
        }));
    }
}

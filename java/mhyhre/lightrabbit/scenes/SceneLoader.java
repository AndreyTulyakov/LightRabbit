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
import mhyhre.lightrabbit.StringsBase;
import mhyhre.lightrabbit.scenes.utils.BubbleGenerator;
import mhyhre.lightrabbit.scenes.utils.EaseScene;


public class SceneLoader extends EaseScene {

    private float alphaTime = -0.8f;
    private float pressAlphaTime = 0.0f;
    private float leaveAlphaTime = 1.0f;

    public Text mCaptionTapScreen;
    public Sprite splashSprite;
    public Text textGameLogo;
    private BubbleGenerator bubbleGenerator;

    private boolean Clicked = false;
    
    public SceneLoader() {
        
        setBackgroundEnabled(true);
        setBackground(new Background(0.0f, 0.0f, 0.0f));
        
        MainActivity.resources.LoadResourcesForPreloader();
        Color captionsColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);



        // Tap text
        String TextMessage = StringsBase.getInstance().getValue("Press_For_Continue");
        float offsetDivider =  1.6f;
        if(TextMessage.length() > 20) {
            offsetDivider = 1.4f;
        }
        mCaptionTapScreen = new Text(0, 0, MainActivity.resources.getFont("White Furore"), TextMessage, MainActivity.getVboManager());
        mCaptionTapScreen.setPosition(MainActivity.getHalfWidth() * offsetDivider, (MainActivity.getHeight() / 4) * 1.6f);
        mCaptionTapScreen.setVisible(false);
        mCaptionTapScreen.setAlpha(0.0f);
        mCaptionTapScreen.setColor(captionsColor);
        
        textGameLogo = new Text(0, 0, MainActivity.resources.getFont("Furore48"), StringsBase.getInstance().getValue("Application_Name"), MainActivity.getVboManager());
        textGameLogo.setPosition(MainActivity.getHalfWidth(), (MainActivity.getHeight() / 4) * 2.1f);
        textGameLogo.setColor(captionsColor);
        textGameLogo.setAlpha(0.0f);

        // tap-zone
        splashSprite = new Sprite(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(), MainActivity.resources.getTextureRegion("splash") , MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN && !Clicked) {
                    unregisterTouchArea(splashSprite);
                    MainActivity.resources.playSound("click1");
                    Clicked = true;
                    Log.i(MainActivity.DEBUG_ID, "Splash Screen [ Tap ] button");
                    MainActivity.vibrate(20);
                }
                return true;
            }
        };

        splashSprite.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        splashSprite.setScale(2);


        attachChild(splashSprite);
        bubbleGenerator = new BubbleGenerator();
        attachChild(bubbleGenerator);

        attachChild(textGameLogo);
        attachChild(mCaptionTapScreen);

        // Alpha Timer
        registerUpdateHandler(new TimerHandler(0.02f, true, new ITimerCallback() {
            // @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {

                if (mCaptionTapScreen.isVisible()) {

                    pressAlphaTime += 0.05f;
                    if (pressAlphaTime > Math.PI) {
                        pressAlphaTime = 0.0f;
                    }
                    mCaptionTapScreen.setAlpha((float) Math.sin(pressAlphaTime));

                    if (alphaTime < 0.98f) {
                        alphaTime += 0.02f;
                        if (alphaTime > 0.0f) {
                            textGameLogo.setAlpha(alphaTime);
                        }
                    }
                }

                if (Clicked) {
                    // Splash off scene by rect
                    if (leaveAlphaTime > 0.33f) {
                        leaveAlphaTime -= 0.03f;
                        splashSprite.setAlpha(leaveAlphaTime);
                    } else {
                        unregisterUpdateHandler(pTimerHandler);
                        MainActivity.getRootScene().SetState(SceneStates.MainMenu);
                    }
                }
            }
        }));



    }
}

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

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.scene.utils.MhyhreScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import android.util.Log;

public class SceneLoader extends MhyhreScene {

    private float AlphaTime = 0.0f;
    private float AlphaTime2 = -0.3f;
    private float AlphaTime3 = 0.0f;

    boolean soundPlayed = false;

    Background backGround;

    public Text mCaptionTapScreen;

    public Rectangle TapRect;
    public Text textGameLogo;

    private boolean Clicked = false;

    public SceneLoader() {

        backGround = new Background(0.2f, 0.3f, 1.0f);
        setBackground(backGround);
        setBackgroundEnabled(true);

        MainActivity.resources.LoadResourcesForPreloader();

        // Tap text
        String TextMessage = MainActivity.Me.getString(R.string.textTap);
        mCaptionTapScreen = new Text(0, 0, MainActivity.resources.getFont("Furore"), TextMessage, MainActivity.getVboManager());
        mCaptionTapScreen.setPosition(MainActivity.getHalfWidth(), (MainActivity.getHeight() / 5) * 1);
        mCaptionTapScreen.setVisible(false);
        mCaptionTapScreen.setAlpha(0.0f);

        textGameLogo = new Text(0, 0, MainActivity.resources.getFont("Furore48"), MainActivity.Me.getString(R.string.app_name),
                MainActivity.getVboManager());
        textGameLogo.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        textGameLogo.setAlpha(0.0f);

        // tap-zone
        TapRect = new Rectangle(0, 0, MainActivity.getWidth(), MainActivity.getHeight(), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN && !Clicked) {
                    unregisterTouchArea(TapRect);
                    Clicked = true;
                    Log.i(MainActivity.DEBUG_ID, "Splash Screen [ Tap ] button");
                    MainActivity.vibrate(30);
                }
                return true;
            }
        };
        TapRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        TapRect.setVisible(false);
        TapRect.setAlpha(AlphaTime3);
        TapRect.setColor(0f, 0.2f, 0.0f);

        attachChild(textGameLogo);
        attachChild(mCaptionTapScreen);
        attachChild(TapRect);

        // Alpha Timer
        registerUpdateHandler(new TimerHandler(0.02f, true, new ITimerCallback() {
            // @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {

                if (mCaptionTapScreen.isVisible()) {

                    if (AlphaTime2 > 0.3f && soundPlayed == false) {
                        MainActivity.resources.playSound("switchOn");
                        soundPlayed = true;
                    }

                    // Splash on - logo
                    if (AlphaTime2 < 0.99f) {
                        AlphaTime2 += 0.01f;
                        if (AlphaTime2 < 0.0f) {
                            textGameLogo.setAlpha(0.0f);
                        } else {
                            textGameLogo.setAlpha(AlphaTime2);
                        }
                    }

                    // Splash on - tap text
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
                    TapRect.setVisible(true);
                    TapRect.setAlpha(AlphaTime3);
                }

            }
        }));

    }

}

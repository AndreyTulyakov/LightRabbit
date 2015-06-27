/*
 * Copyright (C) 2014-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *		http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.scenes;

import mhyhre.lightrabbit.R;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.scenes.utils.EaseScene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import android.util.Log;

public class SceneExit extends EaseScene {

    private Text textQuestion;
    private Text buttonTextBack;
    private Text buttonTextExit;
    

    public SceneExit() {

        setBackgroundEnabled(true);
        setBackground(new Background(0.2f, 0.2f, 0.2f));


        String textExit = MainActivity.Me.getString(R.string.QExit);
        String textBack = MainActivity.Me.getString(R.string.textBack);
        String textExitQuestion = MainActivity.Me.getString(R.string.textExit);

        // Creating sprite
        Sprite buttonExitRect = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    Log.i(MainActivity.DEBUG_ID, "Exit Scene [ Exit ] pressed");
                    MainActivity.vibrate(30);
                    MainActivity.Me.finish();
                }
                return true;
            }
        };

        Sprite buttonBackRect  = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    Log.i(MainActivity.DEBUG_ID, "Exit Scene [ Back ] pressed");
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.MainMenu);
                }
                return true;
            }
        };

        textQuestion = new Text(0, 0, MainActivity.resources.getFont("White Furore"), textExit, MainActivity.Me.getVertexBufferObjectManager());
        textQuestion.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());

        buttonExitRect.setColor(1.0f, 0.8f, 0.8f);
        buttonBackRect.setColor(0.8f, 0.8f, 1.0f);

        float OffsetX = MainActivity.getWidth() / 4.0f;
        float OffsetY = buttonExitRect.getHeight() * 1.5f;

        buttonBackRect.setPosition(OffsetX * 1, OffsetY);
        buttonExitRect.setPosition(OffsetX * 3, OffsetY);

        attachChild(buttonExitRect);
        registerTouchArea(buttonExitRect);
        attachChild(buttonBackRect);
        registerTouchArea(buttonBackRect);

        buttonTextBack = new Text(0, 0, MainActivity.resources.getFont("Furore"), textBack, MainActivity.Me.getVertexBufferObjectManager());
        buttonTextExit = new Text(0, 0, MainActivity.resources.getFont("Furore"), textExitQuestion, MainActivity.Me.getVertexBufferObjectManager());

        buttonTextBack.setPosition(buttonBackRect);
        buttonTextExit.setPosition(buttonExitRect);

        attachChild(textQuestion);
        attachChild(buttonTextBack);
        attachChild(buttonTextExit);
    }

    @Override
    public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }
}

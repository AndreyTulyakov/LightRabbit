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

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.StringsBase;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

public class SceneGameLoading extends EaseScene {

    private Rectangle mRotationRect;
    private Text mTextPress;
    private Text mLevelName;
    private Text mLevelChapter;

    private boolean mLoaded = false;

    public SceneGameLoading() {

        setBackgroundEnabled(false);

        Rectangle backRect = new Rectangle(0, 0, MainActivity.getWidth(), MainActivity.getHeight(), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

                    if (mLoaded) {

                        MainActivity.getRootScene().SetState(SceneStates.Game);

                        setLoaded(false);
                    }

                    Log.i(MainActivity.DEBUG_ID, "SceneGameLoading: Start game");
                    MainActivity.vibrate(30);
                }
                return true;
            }
        };
        backRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
        backRect.setColor(0.0f, 0.0f, 0.1f, 0.75f);
        attachChild(backRect);
        registerTouchArea(backRect);

        mTextPress = new Text(0, 0, MainActivity.resources.getFont("White Furore 24"), StringsBase.getInstance().getValue("Press_For_Continue"), 100,
                MainActivity.Me.getVertexBufferObjectManager());
        mTextPress.setPosition(MainActivity.getHalfWidth(), 30 + mTextPress.getHeight() / 2);

        mLevelChapter = new Text(0, 0, MainActivity.resources.getFont("White Furore"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
        mLevelChapter.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());

        mLevelName = new Text(0, 0, MainActivity.resources.getFont("White Furore 24"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
        mLevelName.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight() - 80);

        mRotationRect = new Rectangle(MainActivity.getHalfWidth(), mTextPress.getY(), MainActivity.getWidth(), mTextPress.getHeight() + 40,
                MainActivity.Me.getVertexBufferObjectManager());
        mRotationRect.setColor(Color.BLACK);

        attachChild(mRotationRect);
        attachChild(mTextPress);
        attachChild(mLevelName);
        attachChild(mLevelChapter);

        setLoaded(false);
    }

    float rectAlpha = 0;

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        if (mLoaded) {
            rectAlpha += 0.03f;
            if (rectAlpha >= Math.PI) {
                rectAlpha = 0;
            }
            mTextPress.setAlpha((float) (1.0f - Math.sin(rectAlpha)));
        }

        super.onManagedUpdate(pSecondsElapsed);
    }

    public boolean isLoaded() {
        return mLoaded;
    }

    public void setLoaded(boolean mLoaded) {
        this.mLoaded = mLoaded;

    }

    public void setLevelName(String levelName) {
        if(levelName != null) {
            mLevelName.setText(levelName);
        }
    }

    public void setLevelChapter(String levelChapter) {
        mLevelChapter.setText(levelChapter);
    }
}

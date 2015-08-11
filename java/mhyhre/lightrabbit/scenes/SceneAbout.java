/*
 * Copyright (C) 2013-2014 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.scenes;

import android.util.Log;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import java.io.IOException;
import java.io.InputStream;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.StringsBase;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

public class SceneAbout extends EaseScene {

    private Text textTop;
    private Text textInfo;
    private Sprite background;
    
    public SceneAbout() {

        setBackgroundEnabled(false);

        background = new Sprite(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),
                MainActivity.resources.getTextureRegion("backgroundLevelSelector"),
                MainActivity.Me.getVertexBufferObjectManager());
        background.setScale(2);
        attachChild(background);

        String strText1 = StringsBase.getInstance().getValue("About");
        textTop = new Text(0, 0, MainActivity.resources.getFont("White Furore"), strText1, MainActivity.Me.getVertexBufferObjectManager());

        Rectangle topRect = new Rectangle(0, 0, MainActivity.getWidth(), 1, MainActivity.Me.getVertexBufferObjectManager());
        topRect.setColor(0.0f, 0.0f, 0.0f, 0.3f);
        topRect.setHeight(40 + 40 + textTop.getFont().getLineHeight());
        topRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() - topRect.getHeight() / 2);
        textTop.setPosition(topRect);
        
        String strText2 = loadInfo("about.text");
        textInfo = new Text(0, 0, MainActivity.resources.getFont("White Furore 24"), strText2, MainActivity.Me.getVertexBufferObjectManager());
        textInfo.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());

        attachChild(topRect);
        attachChild(textTop);
        attachChild(textInfo);

        Sprite mBackButtonSprite = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    MainActivity.getRootScene().SetState(SceneStates.MainMenu);
                }
                return true;
            }
        };
        mBackButtonSprite.setScale(MainActivity.PIXEL_MULTIPLIER);

        mBackButtonSprite.setPosition(MainActivity.getHalfWidth(), 50);

        Text textBack = new Text(0, 0, MainActivity.resources.getFont("Furore"), StringsBase.getInstance().getValue("Back"), MainActivity.Me.getVertexBufferObjectManager());
        textBack.setPosition(mBackButtonSprite);

        attachChild(mBackButtonSprite);
        registerTouchArea(mBackButtonSprite);
        attachChild(textBack);
    }
    

    private String loadInfo(String filename) {

        String text = "";

        InputStream input;
        try {
            input = MainActivity.Me.getAssetManager().open(filename);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            text = new String(buffer);

        } catch (IOException e) {
            Log.e(MainActivity.DEBUG_ID, "SceneAbout::loadInfo: " + e.getMessage());
            e.printStackTrace();
        }
        return text;
    }
}

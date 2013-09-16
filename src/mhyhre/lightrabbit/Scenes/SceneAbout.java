/*
 * Copyright (C) 2013 Andrew Tulay
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *		http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.Scenes;

import java.io.IOException;
import java.io.InputStream;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class SceneAbout extends MhyhreScene {

	private Text textTop;
	private Text textInfo;
	private Text textBack;

	public SceneAbout() {
		setBackgroundEnabled(true);
		setBackground(new Background(0.9f, 0.9f, 1.0f));

		String strText1 = MainActivity.Me.getString(R.string.textAbout);
		textTop = new Text(0, 0, MainActivity.Res.getFont("White Furore"), strText1, MainActivity.Me.getVertexBufferObjectManager());
		textTop.setPosition(MainActivity.getHalfWidth() - textTop.getWidth() / 2.0f, 40);

		Rectangle topRect = new Rectangle(0, 0, MainActivity.getWidth(), 80, MainActivity.Me.getVertexBufferObjectManager());
		topRect.setColor(0.5f, 0.5f, 0.6f);
		topRect.setHeight(40 + 40 + textTop.getFont().getLineHeight());

		String strText2 = loadInfo("about.text");
		textInfo = new Text(0, 0, MainActivity.Res.getFont("Furore"), strText2, MainActivity.Me.getVertexBufferObjectManager());
		textInfo.setPosition(MainActivity.getHalfWidth() - textInfo.getWidth() / 2.0f, topRect.getHeight() + 40);

		attachChild(topRect);
		attachChild(textTop);
		attachChild(textInfo);

		String strBack = MainActivity.Me.getString(R.string.textBack);

		Sprite mBackButtonSprite = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					MainActivity.vibrate(30);
					MainActivity.getRootScene().SetState(SceneStates.MainMenu);
				}
				return true;
			}
		};
		mBackButtonSprite.setPosition(MainActivity.getHalfWidth() - mBackButtonSprite.getWidth() / 2, MainActivity.getHeight() - ((mBackButtonSprite.getHeight() / 2) + 50));

		textBack = new Text(0, 0, MainActivity.Res.getFont("Furore"), strBack, MainActivity.Me.getVertexBufferObjectManager());
		textBack.setPosition(MainActivity.getHalfWidth() - textBack.getWidth() / 2, MainActivity.getHeight() - ((textBack.getHeight() / 2) + 50));

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
			Log.e(MainActivity.DebugID, "SceneAbout::loadInfo: " + e.getMessage());
			e.printStackTrace();
		}

		return text;
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

}

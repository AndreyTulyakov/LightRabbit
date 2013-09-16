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

import mhyhre.lightrabbit.R;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class SceneExit extends MhyhreScene {

	Sprite mExitButtonSprite;
	Sprite mBackButtonSprite;

	private Text mCaptionItem1;
	private Text mCaptionItem2;
	private Text mCaptionItem3;

	public SceneExit() {

		setBackgroundEnabled(true);
		setBackground(new Background(0.9f, 0.9f, 1.0f));

		String strQExit = MainActivity.Me.getString(R.string.QExit);
		String TextItem1 = MainActivity.Me.getString(R.string.textBack);
		String TextItem2 = MainActivity.Me.getString(R.string.textExit);

		// Creating sprite
		mExitButtonSprite = new Sprite(0, 94, MainActivity.Res.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					Log.i(MainActivity.DebugID, "Exit Scene [ Exit ] pressed");
					MainActivity.vibrate(30);
					MainActivity.Me.finish();
				}
				return true;
			}
		};

		mBackButtonSprite = new Sprite(0, 154, MainActivity.Res.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					Log.i(MainActivity.DebugID, "Exit Scene [ Back ] pressed");
					MainActivity.vibrate(30);
					MainActivity.getRootScene().SetState(SceneStates.MainMenu);
				}
				return true;
			}
		};

		mCaptionItem1 = new Text(0, 0, MainActivity.Res.getFont("White Furore"), strQExit, MainActivity.Me.getVertexBufferObjectManager());
		mCaptionItem1.setPosition(MainActivity.getHalfWidth() - mCaptionItem1.getWidth() / 2, MainActivity.getHalfHeight() - mCaptionItem1.getHeight() / 2);

		Rectangle topRect = new Rectangle(0, 0, MainActivity.getWidth(), 120, MainActivity.Me.getVertexBufferObjectManager());
		topRect.setColor(0.5f, 0.5f, 0.6f);
		topRect.setHeight(mCaptionItem1.getHeight() + 80);
		topRect.setY(mCaptionItem1.getY() + mCaptionItem1.getHeight() / 2 - topRect.getHeight() / 2);
		attachChild(topRect);

		mExitButtonSprite.setColor(1.0f, 0.8f, 0.8f);
		mBackButtonSprite.setColor(0.8f, 0.8f, 1.0f);

		float OffsetX = MainActivity.getWidth() / 3.0f;
		float OffsetY = MainActivity.getHeight() - mExitButtonSprite.getHeight() * 1.5f;

		mBackButtonSprite.setPosition(OffsetX * 1 - mBackButtonSprite.getWidth() / 2, OffsetY);
		mExitButtonSprite.setPosition(OffsetX * 2 - mExitButtonSprite.getWidth() / 2, OffsetY);

		attachChild(mExitButtonSprite);
		registerTouchArea(mExitButtonSprite);
		attachChild(mBackButtonSprite);
		registerTouchArea(mBackButtonSprite);

		mCaptionItem2 = new Text(0, 0, MainActivity.Res.getFont("Furore"), TextItem1, MainActivity.Me.getVertexBufferObjectManager());
		mCaptionItem3 = new Text(0, 0, MainActivity.Res.getFont("Furore"), TextItem2, MainActivity.Me.getVertexBufferObjectManager());

		mCaptionItem2.setPosition(mBackButtonSprite.getX() + mBackButtonSprite.getWidth() / 2 - mCaptionItem2.getWidth() / 2, mBackButtonSprite.getY() + mBackButtonSprite.getHeight() / 2 - mCaptionItem2.getHeight() / 2);

		mCaptionItem3.setPosition(mExitButtonSprite.getX() + mExitButtonSprite.getWidth() / 2 - mCaptionItem3.getWidth() / 2, mExitButtonSprite.getY() + mExitButtonSprite.getHeight() / 2 - mCaptionItem3.getHeight() / 2);

		attachChild(mCaptionItem1);
		attachChild(mCaptionItem2);
		attachChild(mCaptionItem3);
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}

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

import java.util.Random;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;
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

	private boolean Loaded = false;
	private boolean Clicked = false;

	Random mRandom = new Random();

	public SceneLoader() {

		backGround = new Background(0.0f, 0.0f, 0.0f);
		setBackground(backGround);
		setBackgroundEnabled(true);

		MainActivity.Res.LoadResourcesForPreloader();

		// Tap text
		String TextMessage = MainActivity.Me.getString(R.string.textTap);
		mCaptionTapScreen = new Text(320, 400, MainActivity.Res.getFont("Furore"), TextMessage, MainActivity.Me.getVertexBufferObjectManager());
		mCaptionTapScreen.setPosition(MainActivity.getHalfWidth() - mCaptionTapScreen.getWidth() / 2, (MainActivity.getHeight() / 5) * 4);
		mCaptionTapScreen.setVisible(false);
		mCaptionTapScreen.setAlpha(0.0f);

		textGameLogo = new Text(0, 0, MainActivity.Res.getFont("Furore48"), MainActivity.Me.getString(R.string.app_name), MainActivity.Me.getVertexBufferObjectManager());
		textGameLogo.setPosition(MainActivity.getHalfWidth() - textGameLogo.getWidth() / 2, MainActivity.getHalfHeight() - textGameLogo.getHeight() / 2);
		textGameLogo.setAlpha(0.0f);
		

		// tap-zone
		TapRect = new Rectangle(0, 0, MainActivity.getWidth(), MainActivity.getHeight(), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN && !Clicked) {
					unregisterTouchArea(TapRect);
					Clicked = true;
					Log.i(MainActivity.DebugID, "Splash Screen [ Tap ] button");
					MainActivity.vibrate(30);
				}
				return true;
			}
		};
		TapRect.setVisible(false);
		TapRect.setAlpha(AlphaTime3);
		TapRect.setColor(0, 0, 0.1f);

		attachChild(textGameLogo);
		attachChild(mCaptionTapScreen);
		attachChild(TapRect);

		Loaded = true;

		// Alpha Timer
		registerUpdateHandler(new TimerHandler(0.02f, true, new ITimerCallback() {
			// @Override
			public void onTimePassed(final TimerHandler pTimerHandler) {

				if (mCaptionTapScreen.isVisible()) {

					if (AlphaTime2 > 0.3f && soundPlayed == false) {
						MainActivity.Res.playSound("switchOn");
						soundPlayed = true;
					}

					// Splash on - logo
					if (AlphaTime2 < 0.99f) {
						AlphaTime2 += 0.01f;
						if (AlphaTime2 < 0.0f) {
							textGameLogo.setAlpha(0.0f);
						} else {
							textGameLogo.setAlpha(AlphaTime2);

							backGround.setColor(0.1f * AlphaTime2, 0.2f * AlphaTime2, 0.7f * AlphaTime2);

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


	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		if (Loaded) {

		}

		super.onManagedUpdate(pSecondsElapsed);
	}

}

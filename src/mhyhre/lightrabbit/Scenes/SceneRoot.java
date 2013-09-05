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

import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class SceneRoot extends Scene {

	// STATES
	private static SceneStates state = SceneStates.None;

	public static final int STATE_SPLASH = 0;
	public static final int STATE_MAIN_MENU = 1;
	public static final int STATE_NEW_GAME = 2;
	public static final int STATE_LOAD_GAME = 3;
	public static final int STATE_ABOUT = 4;
	public static final int STATE_EXIT = 5;
	public static final int STATE_LOADING = 6;
	public static final int STATE_GAME = 7;
	public static final int STATE_NEVERWIN = 8;

	public static SceneLoader mSceneLoader;
	public static SceneMainMenu mSceneMainMenu;
	public static SceneAbout mSceneAbout;
	public static SceneExit mSceneExit;
	public static SceneGame mSceneGame;

	public static boolean Preloaded = false;

	public SceneRoot() {
		setBackgroundEnabled(false);

	}

	public void Initialize() {

		mSceneLoader = new SceneLoader();
		mSceneLoader.Show();
		attachChild(mSceneLoader);

		SetState(SceneStates.Splash);
		// Thread for menu loading

		Log.i(MainActivity.DebugID, "Splash Timer");

		MainActivity.Res.loadAtlases();
		MainActivity.Res.loadFonts();
		MainActivity.Res.loadSounds();

		// Scenes Creating ------------------------------------------------
		mSceneMainMenu = new SceneMainMenu();
		mSceneAbout = new SceneAbout();
		mSceneExit = new SceneExit();

		mSceneGame = new SceneGame();

		attachChild(mSceneMainMenu);
		attachChild(mSceneAbout);
		attachChild(mSceneExit);
		attachChild(mSceneGame);
		// ---------------------------------------------------------------

		Preloaded = true;
		mSceneLoader.mCaptionTapScreen.setVisible(true);
		mSceneLoader.registerTouchArea(mSceneLoader.TapRect);

		// TODO add timer ~1 sec for more smooth cloud loader

	}

	public static void SetState(SceneStates pState) {

		state = pState;

		if (Preloaded) {

			// Hide all scenes.
			mSceneLoader.Hide();
			mSceneMainMenu.Hide();
			mSceneAbout.Hide();
			mSceneExit.Hide();
			mSceneGame.Hide();

			switch (state) {
			case Splash:
				mSceneLoader.Show();
				break;

			case MainMenu:
				mSceneMainMenu.Show();
				break;

			case About:
				mSceneAbout.Show();
				break;

			case Exit:
				mSceneExit.Show();
				break;

			case NewGame:

				mSceneGame.Show();
				break;

			default:

				break;
			}
		}
	}

	public static SceneStates GetState() {
		return state;
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		if (Preloaded) {
			switch (state) {
			case Splash:
				mSceneLoader.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case MainMenu:
				mSceneMainMenu.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case About:
				mSceneAbout.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Exit:
				mSceneExit.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Loading:

				break;

			case NewGame:
				mSceneGame.onSceneTouchEvent(pSceneTouchEvent);
				break;

			default:
				break;

			}
		}
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	public void onSceneBackPress() {

		switch (state) {
		case Splash:

			break;

		case MainMenu:
			SetState(SceneStates.Exit);
			break;

		case NewGame:
			SetState(SceneStates.MainMenu);
			break;

		case LoadGame:
			SetState(SceneStates.MainMenu);
			break;

		case About:
			SetState(SceneStates.MainMenu);
			break;

		case Exit:
			SetState(SceneStates.MainMenu);
			break;

		case Loading:

			break;

		case Game:
			SetState(SceneStates.MainMenu);
			break;

		case NoWin:
			SetState(SceneStates.MainMenu);
			break;

		default:
			break;
		}
	}

}

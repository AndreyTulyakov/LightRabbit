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

package mhyhre.lightrabbit;

import mhyhre.lightrabbit.Scenes.SceneRoot;
import mhyhre.lightrabbit.Scenes.SceneStates;

import org.andengine.BuildConfig;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.res.AssetManager;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Main Activity - start point
 */

public class MainActivity extends SimpleBaseGameActivity {

	public static String DebugID = "MHYHRE";

	// singleton handle
	public static MainActivity Me;

	private static Vibrator mVibrator;
	public static Camera camera;
	


	// screen sizes
	private static int SCREEN_WIDTH, SCREEN_HEIGHT;	
	private static float HalfWidth, HalfHeight;

	public static ResourceManager Res;
	public static SceneRoot mSceneRoot;

	AssetManager assetManager;
	
	static private boolean vibroEnabled = true;
	static private boolean soundEnabled = true;
	

	@Override
	public EngineOptions onCreateEngineOptions() {

		Log.i(DebugID, "--------------------------------------------------");
		mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mVibrator.vibrate(50);

		Me = this;

		assetManager = getAssets();

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		SCREEN_WIDTH = 960;
		SCREEN_HEIGHT = 540;
		
		HalfWidth = SCREEN_WIDTH / 2.0f;
		HalfHeight = SCREEN_HEIGHT / 2.0f;

		camera = new Camera(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		if (BuildConfig.DEBUG)
			Log.i(DebugID, "Display Metrics: " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);

		EngineOptions mEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(metrics.widthPixels, metrics.heightPixels), camera);

		mEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return mEngineOptions;
	}

	@Override
	public void onCreateResources() {
		if (BuildConfig.DEBUG)
			Log.i(DebugID, this.getClass().getSimpleName() + ".onCreateResources");
	}

	@Override
	public Scene onCreateScene() {
		if (BuildConfig.DEBUG)
			Log.i(DebugID, this.getClass().getSimpleName() + ".onCreateScene");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		Res = new ResourceManager();

		mSceneRoot = new SceneRoot();
		mSceneRoot.Initialize();

		return mSceneRoot;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (BuildConfig.DEBUG)
				Log.i(DebugID, this.getClass().getSimpleName() + ".onKeyDown: KEYCODE_BACK");
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {

		if (SceneRoot.GetState() == SceneStates.Splash) {
			super.onBackPressed();
		} else {
			mSceneRoot.onSceneBackPress();
		}
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void onDestroy() {
		if (BuildConfig.DEBUG)
			Log.i(DebugID, this.getClass().getSimpleName() + ".onDestroy");
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	public static float getWidth(){
		return SCREEN_WIDTH;
	}
	
	public static float getHeight(){
		return SCREEN_HEIGHT;
	}
	
	public static float getHalfWidth(){
		return HalfWidth;
	}
	
	public static float getHalfHeight(){
		return HalfHeight;
	}
	

	public static boolean isVibroEnabled() {
		return vibroEnabled;
	}

	public static void setVibroEnabled(boolean vibroEnabled) {
		MainActivity.vibroEnabled = vibroEnabled;
	}

	public static boolean isSoundEnabled() {
		return soundEnabled;
	}

	public static void setSoundEnabled(boolean soundEnabled) {
		MainActivity.soundEnabled = soundEnabled;
	}

	public static void vibrate(long milliseconds){
		if(vibroEnabled){
			mVibrator.vibrate(milliseconds);
		}
	}
}
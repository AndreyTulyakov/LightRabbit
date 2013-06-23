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

import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import mhyhre.lightrabbit.BuildConfig;
import mhyhre.lightrabbit.Scenes.SceneRoot;
import mhyhre.lightrabbit.Scenes.SceneStates;

/**
 * Main Activity - start point
 */

public class MainActivity extends SimpleBaseGameActivity {

	public static String DebugID = "MHYHRE";
	
	// singleton handle
	public static MainActivity Me;
	
	public static Vibrator mVibrator;
	public static Camera camera;

	// screen sizes
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	
	public static ResourceManager Res;
	public static SceneRoot mSceneRoot;


	
	@Override
	public EngineOptions onCreateEngineOptions() {
		if(BuildConfig.DEBUG) Log.i(DebugID, "--------------------------------------------------");
		mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mVibrator.vibrate(50);

		Me = this;

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		SCREEN_WIDTH = 960;;
		SCREEN_HEIGHT = 540;
		
		camera = new Camera(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		if(BuildConfig.DEBUG) Log.i(DebugID, "Display Metrics: " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
		
		EngineOptions mEngineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						metrics.widthPixels,  metrics.heightPixels), camera);

		mEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return mEngineOptions;
	}

	
	@Override
	public void onCreateResources() {
		if(BuildConfig.DEBUG) Log.i(DebugID, this.getClass().getSimpleName()+".onCreateResources");
	}
	

	@Override
	public Scene onCreateScene() {
		if(BuildConfig.DEBUG) Log.i(DebugID, this.getClass().getSimpleName()+".onCreateScene");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		Res = new ResourceManager();
		
		mSceneRoot = new SceneRoot();
		mSceneRoot.Initialize();
		
		return mSceneRoot;
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(BuildConfig.DEBUG) Log.i(DebugID, this.getClass().getSimpleName()+".onKeyDown: KEYCODE_BACK");
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	public void onBackPressed() {
		
		if(SceneRoot.GetState()==SceneStates.Splash){
			super.onBackPressed();
		}else{
			mSceneRoot.onSceneBackPress();
		}
	}
	
	
	public void onDestroy() {
		if(BuildConfig.DEBUG) Log.i(DebugID, this.getClass().getSimpleName()+".onDestroy");
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
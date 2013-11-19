/*
 * Copyright (C) 2013 Andrey Tulyakov
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

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Main Activity - start point
 */

public class MainActivity extends SimpleBaseGameActivity {

    public static final String DEBUG_ID = "LRABBIT";
    public static final String MAPS_LIST_FILENAME = "levels/LevelsList.xml";
    public static final String LEVELS_FOLDER = "levels/";
    public static String MY_PREF = "MY_PREF";
    
    public static MainActivity Me;
    public static Camera camera;
    public static ResourceManager Res;
    public static SceneRoot mSceneRoot;
    
    
    private static Vibrator mVibrator;
    private static int SCREEN_WIDTH, SCREEN_HEIGHT;
    private static float HalfWidth, HalfHeight;

    private AssetManager assetManager;

    private static boolean vibroEnabled = true;
    private static boolean soundEnabled = true;
    
    
    protected void savePreferences() {

        int mode = Activity.MODE_PRIVATE;
        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREF, mode);

        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("appVersion", 2);
        editor.putBoolean("isVibroEnabled", isVibroEnabled());
        editor.putBoolean("isSoundEnabled", isSoundEnabled());
        editor.commit();
    }

    public static SceneRoot getRootScene() {
        return mSceneRoot;
    }

    public void loadPreferences() {

        int mode = Activity.MODE_PRIVATE;
        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREF, mode);

        setVibroEnabled(mySharedPreferences.getBoolean("isVibroEnabled", true));
        setSoundEnabled(mySharedPreferences.getBoolean("isSoundEnabled", true));
        // setUnlockedLevels( mySharedPreferences.getInt("unlockedLevels", 1));
    }

    @Override
    public EngineOptions onCreateEngineOptions() {

        Log.i(DEBUG_ID, "--------------------------------------------------");
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mVibrator.vibrate(50);

        Me = this;

        assetManager = getAssets();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        loadPreferences();

        SCREEN_WIDTH = 960;
        SCREEN_HEIGHT = 540;

        HalfWidth = SCREEN_WIDTH / 2.0f;
        HalfHeight = SCREEN_HEIGHT / 2.0f;

        camera = new Camera(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (BuildConfig.DEBUG)
            Log.i(DEBUG_ID, "Display Metrics: " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);

        EngineOptions mEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(metrics.widthPixels,
                metrics.heightPixels), camera);
        mEngineOptions.getAudioOptions().setNeedsSound(true);
        mEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
        return mEngineOptions;
    }

    @Override
    public void onCreateResources() {
        if (BuildConfig.DEBUG)
            Log.i(DEBUG_ID, this.getClass().getSimpleName() + ".onCreateResources");
    }

    @Override
    public Scene onCreateScene() {
        if (BuildConfig.DEBUG)
            Log.i(DEBUG_ID, this.getClass().getSimpleName() + ".onCreateScene");
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
                Log.i(DEBUG_ID, this.getClass().getSimpleName() + ".onKeyDown: KEYCODE_BACK");
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

        savePreferences();
        if (BuildConfig.DEBUG)
            Log.i(DEBUG_ID, this.getClass().getSimpleName() + ".onDestroy");
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static float getWidth() {
        return SCREEN_WIDTH;
    }

    public static float getHeight() {
        return SCREEN_HEIGHT;
    }

    public static float getHalfWidth() {
        return HalfWidth;
    }

    public static float getHalfHeight() {
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

    public static void vibrate(long milliseconds) {
        if (vibroEnabled) {
            mVibrator.vibrate(milliseconds);
        }
    }


}
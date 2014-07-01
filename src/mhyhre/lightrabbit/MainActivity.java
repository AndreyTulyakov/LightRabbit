/*
 * Copyright (C) 2013-2014 Andrey Tulyakov
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

import mhyhre.lightrabbit.scenes.SceneRoot;
import mhyhre.lightrabbit.scenes.SceneStates;

import org.andengine.BuildConfig;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;


public class MainActivity extends SimpleBaseGameActivity {

    public static final String DEBUG_ID = "LRABBIT";
    public static final String PREFERENCE_ID = "MY_PREF";
    
    public static final int PIXEL_MULTIPLIER = 4;
    
    public static final String MAPS_LIST_FILENAME = "levels/LevelsList.xml";
    public static final String LEVELS_FOLDER = "levels/";
    
    public static MainActivity Me;
    public static Camera camera;
    public static ResourceManager resources;
    public static SceneRoot sceneRoot;
    
    private static final int width = 960;
    private static final int height = 540;
    private static float halfWidth;
    private static float halfHeight;
    
    private AssetManager assetManager;
    private Vibrator vibrator;
    
    private boolean vibroEnabled = true;
    private boolean soundEnabled = true;
    
    
    protected void savePreferences() {

        int mode = Activity.MODE_PRIVATE;
        SharedPreferences mySharedPreferences = getSharedPreferences(PREFERENCE_ID, mode);

        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("appVersion", 2);
        editor.putBoolean("isVibroEnabled", isVibroEnabled());
        editor.putBoolean("isSoundEnabled", isSoundEnabled());
        editor.commit();
    }

    public static SceneRoot getRootScene() {
        return sceneRoot;
    }

    public void loadPreferences() {

        int mode = Activity.MODE_PRIVATE;
        SharedPreferences mySharedPreferences = getSharedPreferences(PREFERENCE_ID, mode);

        setVibroEnabled(mySharedPreferences.getBoolean("isVibroEnabled", true));
        setSoundEnabled(mySharedPreferences.getBoolean("isSoundEnabled", true));
    }

    @Override
    public EngineOptions onCreateEngineOptions() {

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(40);

        Me = this;
        assetManager = getAssets();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        loadPreferences();

        halfWidth = width / 2.0f;
        halfHeight = height / 2.0f;

        camera = new Camera(0, 0, width, height);

        EngineOptions mEngineOptions = new EngineOptions(
                true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(metrics.widthPixels,
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

        resources = new ResourceManager();

        sceneRoot = new SceneRoot();
        return sceneRoot;
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

        if (SceneRoot.getState() == SceneStates.Splash) {
            super.onBackPressed();
        } else {
            sceneRoot.onSceneBackPress();
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
        return width;
    }

    public static float getHeight() {
        return height;
    }

    public static float getHalfWidth() {
        return halfWidth;
    }

    public static float getHalfHeight() {
        return halfHeight;
    }

    public static boolean isVibroEnabled() {
        return Me.vibroEnabled;
    }

    public static void setVibroEnabled(boolean enabled) {
        Me.vibroEnabled = enabled;
    }

    public static boolean isSoundEnabled() {
        return Me.soundEnabled;
    }

    public static void setSoundEnabled(boolean enabled) {
            Me.soundEnabled = enabled;
    }

    public static void vibrate(long milliseconds) {
        if (Me.vibroEnabled) {
            Me.vibrator.vibrate(milliseconds);
        }
    }
    
    public static VertexBufferObjectManager getVboManager() {
        if(Me != null) {
            return Me.getVertexBufferObjectManager();
        }
        return null;
    }

}
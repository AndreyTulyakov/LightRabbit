/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.LayoutGameActivity;

import mhyhre.lightrabbit.ads.ScreenAdvertisement;
import mhyhre.lightrabbit.scenes.SceneRoot;
import mhyhre.lightrabbit.scenes.SceneStates;

public class MainActivity extends LayoutGameActivity {

    public static final String LOCALIZATION = "EN";
    public static final String DEBUG_ID = "LRABBIT";
    public static final String PREFERENCE_ID = "LIGHT_RABBIT_PREF";
    public static final String LEVELS_FOLDER = "levels_" + LOCALIZATION + "/";
    public static final String LOCATE_STRINGS_FILENAME = "strings_" + LOCALIZATION + ".xml";

    public static final int PIXEL_MULTIPLIER = 4;

    private static final int width = 960;
    private static final int height = 540;
    private static float halfWidth;
    private static float halfHeight;

    public static MainActivity Me;
    public static Camera camera;
    public static ResourceManager resources;
    public static SceneRoot sceneRoot;

    private LevelUnlocker levelUnlocker;
    private AssetManager assetManager;
    private Vibrator vibrator;

    private boolean vibroEnabled = true;
    private boolean soundEnabled = true;

    private ScreenAdvertisement mAdvertisement;


    @Override
    public EngineOptions onCreateEngineOptions() {

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(40);

        Me = this;
        assetManager = getAssets();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        loadPreferences();

        levelUnlocker = new LevelUnlocker();

        halfWidth = width / 2.0f;
        halfHeight = height / 2.0f;

        camera = new Camera(0, 0, width, height);

        EngineOptions mEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(metrics.widthPixels,
                metrics.heightPixels), camera);
        mEngineOptions.getAudioOptions().setNeedsSound(true);
        mEngineOptions.getTouchOptions().setNeedsMultiTouch(true);
        return mEngineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
        if (BuildConfig.DEBUG) {
            Log.i(DEBUG_ID, this.getClass().getSimpleName() + ".onCreateResources");
        }

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }



    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        if (BuildConfig.DEBUG)
            Log.i(DEBUG_ID, this.getClass().getSimpleName() + ".onCreateScene");
        this.mEngine.registerUpdateHandler(new FPSLogger());

        resources = new ResourceManager();
        sceneRoot = new SceneRoot();

        pOnCreateSceneCallback.onCreateSceneFinished(sceneRoot);
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
        if (Me != null) {
            return Me.getVertexBufferObjectManager();
        }
        return null;
    }

    public LevelUnlocker getLevelUnlocker() {
        return levelUnlocker;
    }

    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public synchronized void onGameCreated() {

        this.runOnUiThread(new Runnable() {
            public void run() {
                mAdvertisement = new ScreenAdvertisement(MainActivity.Me, R.id.adViewId);
            }
        });

        super.onGameCreated();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.game_layout;
    }

    @Override
    protected int getRenderSurfaceViewID() {
        return R.id.SurfaceViewId;
    }

    public void showAd() {
        mAdvertisement.showAdvertisement();
    }

    public void hideAd() {
        mAdvertisement.hideAdvertisement();
    }
}
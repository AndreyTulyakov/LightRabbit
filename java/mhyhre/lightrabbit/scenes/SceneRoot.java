/*
 * Copyright (C) 2013 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.scenes;

import android.util.Log;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import mhyhre.lightrabbit.MainActivity;

public class SceneRoot extends Scene {

    private static SceneStates state = SceneStates.None;

    public SceneLoader mSceneLoader;
    public SceneMainMenu mSceneMainMenu;
    public SceneAbout mSceneAbout;
    public SceneExit mSceneExit;
    public SceneGame mSceneGame;
    public ScenePauseGame mScenePauseGame;
    public SceneLevelSelector mSceneLevelSelector;
    public SceneGameLoading mSceneGameLoading;


    public static boolean Preloaded = false;

    public SceneRoot() {
        setBackgroundEnabled(false);

        mSceneLoader = new SceneLoader();
        mSceneLoader.show();
        attachChild(mSceneLoader);

        SetState(SceneStates.Splash);

        MainActivity.resources.loadAtlases();
        MainActivity.resources.loadFonts();
        MainActivity.resources.loadSounds();

        // Scenes Creating ------------------------------------------------
        mSceneMainMenu = new SceneMainMenu();
        mSceneAbout = new SceneAbout();
        mSceneExit = new SceneExit();
        mSceneLevelSelector = new SceneLevelSelector();
        mSceneGameLoading = new SceneGameLoading();
        mScenePauseGame = new ScenePauseGame();

        mSceneGame = null;

        attachChild(mSceneMainMenu);
        attachChild(mSceneAbout);
        attachChild(mSceneExit);
        attachChild(mSceneLevelSelector);
        attachChild(mSceneGameLoading);

        // ---------------------------------------------------------------

        Preloaded = true;
        mSceneLoader.mCaptionTapScreen.setVisible(true);
        mSceneLoader.registerTouchArea(mSceneLoader.splashSprite);

    }

    public void SetState(SceneStates pState) {

        state = pState;
        MainActivity.resources.stopMusic("mainTheme");

        if (Preloaded) {

            // Hide all scenes.
            MainActivity.Me.hideAd();
            mSceneLoader.hide();
            mSceneMainMenu.hide();
            mSceneAbout.hide();
            mSceneExit.hide();
            mSceneLevelSelector.hide();
            mSceneGameLoading.hide();
            mScenePauseGame.hide();

            if (mSceneGame != null)
                mSceneGame.hide();

            switch (state) {
                case Splash:
                    mSceneLoader.show();
                    MainActivity.Me.hideAd();
                    break;

                case MainMenu:
                    if(MainActivity.resources.getMusic("mainTheme").isPlaying() == false)
                    {
                        MainActivity.resources.playMusic("mainTheme");
                    }
                    mSceneMainMenu.show();
                    MainActivity.Me.showAd();
                    break;

                case LevelSelector:
                    mSceneLevelSelector.show();
                    MainActivity.Me.showAd();
                    break;

                case About:
                    mSceneAbout.show();
                    break;

                case Exit:
                    mSceneExit.show();
                    break;

                case GameLoading:

                    mSceneGameLoading.setLoaded(false);

                    detachChild(mSceneGameLoading);

                    if (mSceneGame != null) {
                        this.detachChild(mSceneGame);
                        this.detachChild(mScenePauseGame);
                        mSceneGame = null;
                    }

                    Log.i(MainActivity.DEBUG_ID, "level filename: " + mSceneLevelSelector.getSelectedLevel().getFilename());
                    mSceneGame = new SceneGame(mSceneLevelSelector.getSelectedLevel().getFilename());
                    mSceneGame.onManagedUpdate(0);
                    mSceneGame.pause();

                    mSceneGameLoading.setLevelName(mSceneGame.getLevel().getName());
                    mSceneGameLoading.setLevelChapter(mSceneGame.getLevel().getChapter());

                    this.attachChild(mSceneGame);
                    this.attachChild(mSceneGameLoading);
                    this.attachChild(mScenePauseGame);

                    mSceneGameLoading.show();
                    mSceneGame.show();

                    mSceneGameLoading.setLoaded(true);
                    break;

                case Game:

                    if (mSceneGame != null) {
                        mSceneGame.show();
                        mSceneGame.start();
                    }
                    break;

                case PauseMenu:
                    if (mSceneGame != null) {
                        mSceneGame.show();
                        mSceneGame.pause();
                        mScenePauseGame.show();
                    }
                    break;

                case EndGame:

                    SetState(SceneStates.LevelSelector);
                    break;

                default:

                    break;
            }
        }

    }

    public static SceneStates getState() {
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

                case LevelSelector:
                    mSceneLevelSelector.onSceneTouchEvent(pSceneTouchEvent);
                    break;

                case About:
                    mSceneAbout.onSceneTouchEvent(pSceneTouchEvent);
                    break;

                case Exit:
                    mSceneExit.onSceneTouchEvent(pSceneTouchEvent);
                    break;

                case GameLoading:
                    mSceneGameLoading.onSceneTouchEvent(pSceneTouchEvent);
                    break;

                case Game:
                    mSceneGame.onSceneTouchEvent(pSceneTouchEvent);
                    break;

                case PauseMenu:
                    mScenePauseGame.onSceneTouchEvent(pSceneTouchEvent);
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

            case GameLoading:

                break;

            case Game:
                SetState(SceneStates.PauseMenu);
                break;

            case PauseMenu:
                SetState(SceneStates.Game);
                break;

            case LevelSelector:
                SetState(SceneStates.MainMenu);
                break;

            case About:
                SetState(SceneStates.MainMenu);
                break;

            case Exit:
                SetState(SceneStates.MainMenu);
                break;

            default:
                break;
        }
    }

}

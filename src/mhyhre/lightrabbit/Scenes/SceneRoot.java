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

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

public class SceneRoot extends Scene {

    private static SceneStates state = SceneStates.None;

    public SceneLoader mSceneLoader;
    public SceneMainMenu mSceneMainMenu;
    public SceneAbout mSceneAbout;
    public SceneExit mSceneExit;
    public SceneGame mSceneGame;
    public SceneLevelSelector mSceneLevelSelector;
    public SceneGameLoading mSceneGameLoading;

    public static boolean Preloaded = false;

    public SceneRoot() {
        setBackgroundEnabled(false);
    }

    public void Initialize() {

        mSceneLoader = new SceneLoader();
        mSceneLoader.Show();
        attachChild(mSceneLoader);

        SetState(SceneStates.Splash);

        MainActivity.Res.loadAtlases();
        MainActivity.Res.loadFonts();
        MainActivity.Res.loadSounds();

        // Scenes Creating ------------------------------------------------
        mSceneMainMenu = new SceneMainMenu();
        mSceneAbout = new SceneAbout();
        mSceneExit = new SceneExit();
        mSceneLevelSelector = new SceneLevelSelector();
        mSceneGameLoading = new SceneGameLoading();

        mSceneGame = null;

        attachChild(mSceneMainMenu);
        attachChild(mSceneAbout);
        attachChild(mSceneExit);
        attachChild(mSceneLevelSelector);
        attachChild(mSceneGameLoading);

        // ---------------------------------------------------------------

        Preloaded = true;
        mSceneLoader.mCaptionTapScreen.setVisible(true);
        mSceneLoader.registerTouchArea(mSceneLoader.TapRect);

    }

    public void SetState(SceneStates pState) {

        state = pState;

        if (Preloaded) {

            // Hide all scenes.
            mSceneLoader.Hide();
            mSceneMainMenu.Hide();
            mSceneAbout.Hide();
            mSceneExit.Hide();
            mSceneLevelSelector.Hide();
            mSceneGameLoading.Hide();

            if (mSceneGame != null)
                mSceneGame.Hide();

            switch (state) {
            case Splash:
                mSceneLoader.Show();
                break;

            case MainMenu:
                mSceneMainMenu.Show();
                break;

            case LevelSelector:
                mSceneLevelSelector.Show();
                break;

            case About:
                mSceneAbout.Show();
                break;

            case Exit:
                mSceneExit.Show();
                break;

            case GameLoading:

                mSceneGameLoading.setLoaded(false);

                detachChild(mSceneGameLoading);

                if (mSceneGame != null) {
                    this.detachChild(mSceneGame);
                    mSceneGame = null;
                }

                mSceneGame = new SceneGame(mSceneLevelSelector.getSelectedLevel().filename);
                mSceneGame.onManagedUpdate(0);
                mSceneGame.pause();

                mSceneGameLoading.setLevelName(mSceneGame.level.getName());
                mSceneGameLoading.setLevelChapter(mSceneGame.level.getChapter());

                this.attachChild(mSceneGame);
                this.attachChild(mSceneGameLoading);

                mSceneGameLoading.Show();
                mSceneGame.Show();

                mSceneGameLoading.setLoaded(true);

                break;

            case Game:

                if (mSceneGame != null) {

                    mSceneGame.Show();
                    mSceneGame.start();
                }
                break;

            case Win:

                SetState(SceneStates.LevelSelector);
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
            SetState(SceneStates.LevelSelector);
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

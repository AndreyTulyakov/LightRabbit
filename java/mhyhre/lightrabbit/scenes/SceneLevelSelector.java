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

import com.google.android.gms.analytics.HitBuilders;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.LevelUnlocker;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.LevelItem;
import mhyhre.lightrabbit.game.levels.LevelsPage;
import mhyhre.lightrabbit.scenes.utils.EaseScene;
import mhyhre.lightrabbit.utils.LevelListLoader;

public class SceneLevelSelector extends EaseScene {

    private static final float HORIZONTAL_DISTANCE = 80;
    private static final int MAX_LEVELS_ON_SCREEN = 10;
    private static final float NOT_ACTIVE_ALPHA = 0.25f;

    private LevelUnlocker unlocker;

    private List<LevelsPage> pages;
    private List<Text> levelsTexts;
    private int currentPage = 0;
    private float moveCounter = 0;

    private SpriteBatch iconsBatch;
    private Text textCaption;
    private LevelItem selectedLevel;
    private ITextureRegion levelCellRegion;

    private Sprite nextPage;
    private Sprite prevPage;
    private Sprite background;

    public SceneLevelSelector() {

        setBackgroundEnabled(false);

        unlocker = MainActivity.Me.getLevelUnlocker();

        pages = LevelListLoader.load();

        levelsTexts = new ArrayList<Text>(MAX_LEVELS_ON_SCREEN);

        background = new Sprite(MainActivity.getHalfWidth(), MainActivity.getHalfHeight(),
                MainActivity.resources.getTextureRegion("backgroundLevelSelector"),
                MainActivity.Me.getVertexBufferObjectManager());
        background.setScale(2);
        attachChild(background);

        nextPage = new Sprite(MainActivity.getWidth() - 100, MainActivity.getHeight() * 0.75f, MainActivity.resources.getTextureRegion("RowRight"),
                MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionDown()) {
                    if (currentPage < pages.size() - 1) {
                        MainActivity.resources.playSound("click");
                        currentPage++;
                        configureLevelsItem();
                        configureLevelsCaption();
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }

            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (currentPage == pages.size() - 1) {
                    setVisible(false);
                } else {
                    setVisible(true);
                }
                super.onManagedUpdate(pSecondsElapsed);
            }
        };

        prevPage = new Sprite(100, MainActivity.getHeight() * 0.25f, MainActivity.resources.getTextureRegion("RowLeft"),
                MainActivity.Me.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionDown()) {
                    if (currentPage > 0) {
                        MainActivity.resources.playSound("click");
                        currentPage--;
                        configureLevelsItem();
                        configureLevelsCaption();
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }

            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (currentPage == 0) {
                    setVisible(false);
                } else {
                    setVisible(true);
                }
                super.onManagedUpdate(pSecondsElapsed);
            }
        };

        nextPage.setScale(MainActivity.PIXEL_MULTIPLIER * 2);
        prevPage.setScale(MainActivity.PIXEL_MULTIPLIER * 2);

        registerTouchArea(nextPage);
        registerTouchArea(prevPage);

        attachChild(nextPage);
        attachChild(prevPage);

        iconsBatch = new SpriteBatch(MainActivity.resources.getTextureAtlas("User_Interface"), MAX_LEVELS_ON_SCREEN,
                MainActivity.Me.getVertexBufferObjectManager());
        attachChild(iconsBatch);

        levelCellRegion = MainActivity.resources.getTextureRegion("LevelCell");

        textCaption = new Text(0, 0, MainActivity.resources.getFont("White Furore"), "", 64, MainActivity.Me.getVertexBufferObjectManager());
        textCaption.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() - 50);
        attachChild(textCaption);

        for (int i = 0; i < MAX_LEVELS_ON_SCREEN; i++) {
            Text caption = new Text(0, 0, MainActivity.resources.getFont("White Furore 24"), "", 48, MainActivity.Me.getVertexBufferObjectManager());
            levelsTexts.add(caption);
            attachChild(caption);
        }

        configureLevelsItem();
        configureLevelsCaption();
    }

    public LevelItem getSelectedLevel() {
        return selectedLevel;
    }

    private void configureLevelsCaption() {

        textCaption.setText(pages.get(currentPage).getCaption());

        List<LevelItem> levels = pages.get(currentPage).getLevelList();

        for (Text text : levelsTexts) {
            text.setVisible(false);
        }

        for (int i = 0; i < levels.size(); i++) {

            LevelItem item = levels.get(i);
            Text caption = levelsTexts.get(i);
            caption.setText(item.getLabel());
            caption.setPosition(item.getX(), item.getY() - ((item.getHeight()*MainActivity.PIXEL_MULTIPLIER) / 2 + 20));
            caption.setVisible(true);
        }
    }

    private void configureLevelsItem() {

        List<LevelItem> levels = pages.get(currentPage).getLevelList();

        // Positioning
        float hStep = 40;
        float xStep = (levelCellRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER + HORIZONTAL_DISTANCE);
        float xStart = MainActivity.getHalfWidth() - (xStep * ((levels.size() - 1) / 2.0f));
        float yStart = MainActivity.getHalfHeight() + hStep * levels.size() / 2;

        for (int i = 0; i < levels.size(); i++) {

            LevelItem item = levels.get(i);

            item.setWidth(levelCellRegion.getWidth());
            item.setHeight(levelCellRegion.getHeight());

            item.setX(xStart + i * xStep);
            if(unlocker.isLevelUnlocked(item.getId())) {
                item.setY(yStart - hStep * i + (float)(30.0f * Math.sin(moveCounter + i)));
            } else {
                item.setY(yStart - hStep * i);
            }
        }
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        moveCounter += pSecondsElapsed;
        if(moveCounter > 6.286f) {
            moveCounter = moveCounter % 6.286f;
        }

        configureLevelsItem();
        configureLevelsCaption();

        for (LevelItem item : pages.get(currentPage).getLevelList()) {

            int levelIndex = item.getId();
            float colorsPower = 1.0f;

            if (unlocker.isLevelUnlocked(levelIndex)) {
                colorsPower = 1.0f;
            } else {
                colorsPower = 0.55f;
            }
            iconsBatch.draw(levelCellRegion, item.getLeftX(), item.getDownY(), item.getWidth(), item.getHeight(),
                    MainActivity.PIXEL_MULTIPLIER, MainActivity.PIXEL_MULTIPLIER, colorsPower, colorsPower, colorsPower, 1);
        }

        iconsBatch.submit();

        super.onManagedUpdate(pSecondsElapsed);
    }

    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
            for (LevelItem item : pages.get(currentPage).getLevelList()) {

                boolean isLevelUnlocked = unlocker.isLevelUnlocked(item.getId());

                if (isLevelUnlocked == true && item.isCollided(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
                    Log.i(MainActivity.DEBUG_ID, "SceneLevelSelector: Selected level [" + item.getLabel() + "]");

                    selectedLevel = item;
                    MainActivity.Me.tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Selected level index:").setValue(item.getId())
                            .build());

                    MainActivity.getRootScene().SetState(SceneStates.GameLoading);
                    MainActivity.resources.playSound("click");
                    MainActivity.vibrate(30);
                }
            }
        }

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override
    public void show() {
        super.show();
    }
}

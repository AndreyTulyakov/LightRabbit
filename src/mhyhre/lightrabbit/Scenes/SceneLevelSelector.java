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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.game.LevelItem;
import mhyhre.lightrabbit.scene.utils.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class SceneLevelSelector extends MhyhreScene {

    List<LevelItem> mItems;

    // Whats level was selected
    int touchDownToLevel = -1;

    SpriteBatch iconsBatch;

    private Text textCaption;
    LevelItem selectedLevel;

    private final float HORIZONTAL_DISTANCE = 80;

    ITextureRegion levelCellRegion;

    public SceneLevelSelector() {

        setBackgroundEnabled(true);
        setBackground(new Background(0.5f, 0.6f, 0.9f));

        mItems = new ArrayList<LevelItem>();

        loadLevelsList(MainActivity.MAPS_LIST_FILENAME);

        iconsBatch = new SpriteBatch(MainActivity.resources.getTextureAtlas("User_Interface"), mItems.size(), MainActivity.Me.getVertexBufferObjectManager());
        attachChild(iconsBatch);

        levelCellRegion = MainActivity.resources.getTextureRegion("LevelCell");

        textCaption = new Text(0, 0, MainActivity.resources.getFont("White Furore"), MainActivity.Me.getString(R.string.selectLevel),
                MainActivity.Me.getVertexBufferObjectManager());
        textCaption.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() - 50);
        attachChild(textCaption);

        configureLevelsItem();
        configureLevelsCaption();

    }

    public LevelItem getSelectedLevel() {
        return selectedLevel;
    }

    private void loadLevelsList(String listFilename) {

        try {
            InputStream is = MainActivity.Me.getAssets().open(listFilename);

            parseLevelListXml(is);

        } catch (IOException e) {
            Log.e(MainActivity.DEBUG_ID, "Level list loading: " + e);

        }
    }

    private void parseLevelListXml(InputStream is) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);

            Element root = dom.getDocumentElement();

            Log.i(MainActivity.DEBUG_ID, "Start parsing");

            NodeList items = root.getElementsByTagName("level");
            Log.i(MainActivity.DEBUG_ID, "Levels count:" + items.getLength());

            for (int i = 0; i < items.getLength(); i++) {

                Node levelNode = items.item(i);  
                String filename = levelNode.getTextContent();
                String label = levelNode.getAttributes().getNamedItem("label").getNodeValue();
                
                LevelItem levelItem = new LevelItem(filename, label);
                if(levelItem.isCorrect()) {
                    mItems.add(levelItem);
                }
            }

            Log.i(MainActivity.DEBUG_ID, "Good parsing!");

        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_ID, "Level list - parseXml: " + e);
        }

    }

    private void configureLevelsCaption() {

        for (int i = 0; i < mItems.size(); i++) {

            LevelItem item = mItems.get(i);

            Text caption = new Text(0, 0, MainActivity.resources.getFont("Furore"), mItems.get(i).label, MainActivity.Me.getVertexBufferObjectManager());
            caption.setPosition(item.getX(), item.getY() - (item.getHeight() / 2 + 20));
            attachChild(caption);
        }

    }

    private void configureLevelsItem() {

        // Positioning
        float hStep = 40;
        float xStep = (levelCellRegion.getWidth() + HORIZONTAL_DISTANCE);
        float xStart = MainActivity.getHalfWidth() - (xStep * ((mItems.size() - 1) / 2.0f));
        float yStart = MainActivity.getHalfHeight() + hStep * mItems.size() / 2;

        for (int i = 0; i < mItems.size(); i++) {

            LevelItem item = mItems.get(i);

            item.setWidth(levelCellRegion.getWidth());
            item.setHeight(levelCellRegion.getHeight());

            item.setX(xStart + i * xStep);
            item.setY(yStart - hStep * i);

        }

    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        float rgbMod = 1.0f;

        for (LevelItem item : mItems) {

            if (item.isLocked())
                rgbMod = 0.9f;
            else
                rgbMod = 1.0f;

            iconsBatch.draw(levelCellRegion, item.getLeftX(), item.getDownY(), item.getWidth(), item.getHeight(), 0, rgbMod, rgbMod, rgbMod, 1);
        }

        iconsBatch.submit();

        super.onManagedUpdate(pSecondsElapsed);
    }

    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
            for (LevelItem item : mItems) {
                if (item.isCollided(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()) && item.isLocked() == false) {
                    Log.i(MainActivity.DEBUG_ID, "SceneLevelSelector: Selected level [" + item.label + "]");
                    
                    selectedLevel = item;
                    MainActivity.getRootScene().SetState(SceneStates.Equipment);
                }
            }
        }

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override
    public void show() {
        touchDownToLevel = -1;
        super.show();
    }
}

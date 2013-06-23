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

import java.util.ArrayList;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

public class SceneGame extends MhyhreScene {

	public class Vector3f {
		public float x, y, z;

		public Vector3f() {
			x = 0;
			y = 0;
			z = 0;
		}
	}

	Background background;

	String uiAtlasName = "User_Interface";
	ArrayList<ITextureRegion> TextureRegions;
	ArrayList<Text> wordsText;
	public SpriteBatch UIBatch;

	int ItemMaxCount = 32;
	int ItemCount = 5;
	final float verticalStep = 80;

	long time = 100;
	long lasttime;

	public SceneGame() {

		// Задаем фон сцене
		background = new Background(0.78f, 0.78f, 0.80f);
		setBackgroundEnabled(true);
		setBackground(background);

		// Текстурные регионы для каждого элемента
		TextureRegions = new ArrayList<ITextureRegion>();
		TextureRegions.add(TextureRegionFactory.extractFromTexture(MainActivity.Res.getTextureAtlas(uiAtlasName), 0, 0, 310, 70, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(MainActivity.Res.getTextureAtlas(uiAtlasName), 320, 0, 75, 80, false));

		Vector3f posOffset = new Vector3f();
		SetupSpriteBatch(posOffset);
		SetupWordText(posOffset.x, posOffset.y, posOffset.z);
	}

	private void SetupSpriteBatch(Vector3f posOffset) {

		UIBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas(uiAtlasName), ItemMaxCount, MainActivity.Me.getVertexBufferObjectManager());
		UIBatch.setPosition(0, 0);

		float posFirstColumn = MainActivity.SCREEN_WIDTH / 2 - (TextureRegions.get(0).getWidth() + TextureRegions.get(1).getWidth() / 2);
		float posSecondColumn = MainActivity.SCREEN_WIDTH / 2 + TextureRegions.get(1).getWidth() / 2;
		float posEqualColumn = MainActivity.SCREEN_WIDTH / 2 - TextureRegions.get(1).getWidth() / 2;

		// Расчет вертикального смещения
		float verticalMult = (MainActivity.SCREEN_HEIGHT / 2) - ((ItemCount * verticalStep) / 2 - (verticalStep - TextureRegions.get(0).getHeight()) / 2);

		// First Column
		for (int i = 0; i < ItemCount; i++) {
			UIBatch.draw(TextureRegions.get(0), posFirstColumn, verticalMult + i * verticalStep, TextureRegions.get(0).getWidth(), TextureRegions.get(0).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		// Second Column
		for (int i = 0; i < ItemCount; i++) {
			UIBatch.draw(TextureRegions.get(0), posSecondColumn, verticalMult + i * verticalStep, TextureRegions.get(0).getWidth(), TextureRegions.get(0).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		// Equal
		for (int i = 0; i < ItemCount; i++) {
			UIBatch.draw(TextureRegions.get(1), posEqualColumn, verticalMult + i * verticalStep, TextureRegions.get(1).getWidth(), TextureRegions.get(1).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		UIBatch.submit();

		attachChild(UIBatch);

		posOffset.x = verticalMult;
		posOffset.y = posFirstColumn + TextureRegions.get(0).getWidth() / 2;
		posOffset.z = posSecondColumn + TextureRegions.get(0).getWidth() / 2;

	}

	String pstr(int x) {
		String s = "";
		for (int i = 0; i <= x; i++) {
			s = s + i;
		}
		return s = "Человечество";
	}

	private void SetupWordText(float posVertical, float c1Horizontal, float c2Horizontal) {

		posVertical += TextureRegions.get(0).getHeight() / 2 - MainActivity.Res.getFont("Pixel").getLineHeight() / 2;

		wordsText = new ArrayList<Text>();

		float textHalfWidht;

		// Left column
		for (int i = 0; i < ItemCount; i++) {
			wordsText.add(new Text(0, 0, MainActivity.Res.getFont("Pixel"), pstr(i), MainActivity.Me.getVertexBufferObjectManager()));
			textHalfWidht = wordsText.get(i).getWidth() / 2;
			wordsText.get(i).setPosition(c1Horizontal - textHalfWidht, posVertical + i * verticalStep);
			attachChild(wordsText.get(i));
		}

		// Left column
		for (int i = 0; i < ItemCount; i++) {
			wordsText.add(new Text(0, 0, MainActivity.Res.getFont("Pixel"), pstr(i + ItemCount), MainActivity.Me.getVertexBufferObjectManager()));
			textHalfWidht = wordsText.get(ItemCount + i).getWidth() / 2;
			wordsText.get(ItemCount + i).setPosition(c2Horizontal - textHalfWidht, posVertical + i * verticalStep);
			attachChild(wordsText.get(ItemCount + i));
		}
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		if (System.currentTimeMillis() - lasttime > time) {
			lasttime = System.currentTimeMillis();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

}

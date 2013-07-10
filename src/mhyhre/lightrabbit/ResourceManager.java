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

import java.util.HashMap;
import java.util.Map;

import org.andengine.BuildConfig;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.graphics.Color;
import android.util.Log;

/**
 * Менеджер ресурсов.
 */
public class ResourceManager {

	/** Контейнеры */
	Map<String, ITextureRegion> regions;
	Map<String, BitmapTextureAtlas> atlases;
	Map<String, Font> fonts;

	public ResourceManager() {
		regions = new HashMap<String, ITextureRegion>();
		atlases = new HashMap<String, BitmapTextureAtlas>();
		fonts = new HashMap<String, Font>();
	}

	public ITextureRegion getTextureRegion(String key) {
		if (!regions.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getTextureRegion: invalid key - " + key);
		return regions.get(key);
	}

	public BitmapTextureAtlas getTextureAtlas(String key) {
		if (!atlases.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getTextureAtlas: invalid key - " + key);
		return atlases.get(key);
	}

	public Font getFont(String key) {
		if (!fonts.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getFont: invalid key - " + key);
		return fonts.get(key);
	}

	public void loadAtlases() {

		BitmapTextureAtlas atlas;
		ITextureRegion region;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 512, 128, TextureOptions.BILINEAR);
		region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "MenuButton.png", 0, 0);
		atlas.load();

		regions.put("Button1", region);
		atlases.put("guiButtons", atlas);

		// Создание текстурного атласа
		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "User_Interface.png", 0, 0);
		atlas.load();
		atlases.put("User_Interface", atlas);

		// ***
		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "Command_Icons.png", 0, 0);
		atlas.load();
		atlases.put("Command_Icons", atlas);

		// ***
		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "tileset.png", 0, 0);
		atlas.load();
		atlases.put("tileset", atlas);

		Log.i(MainActivity.DebugID, "ResourceManager::loadAtlases: OK");
	}

	public void loadFonts() {

		FontFactory.setAssetBasePath("font/");
		Font mFont;

		final ITexture TextureFontPixelWhite = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelWhite, MainActivity.Me.getAssets(), "Hardpixel.OTF", 46, true, Color.WHITE);
		mFont.load();
		fonts.put("Pixel White", mFont);

		Log.i(MainActivity.DebugID, "ResourceManager::loadFonts: OK");
	}

	public void LoadResourcesForPreloader() {

		BitmapTextureAtlas atlas;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		Font mFont;
		FontFactory.setAssetBasePath("font/");

		// press text
		final ITexture TextureFontPixelBlack = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelBlack, MainActivity.Me.getAssets(), "Hardpixel.OTF", 28, true, Color.BLACK);
		mFont.load();
		fonts.put("Pixel", mFont);

		// preloader texture;
		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "tex_01.png", 0, 0);
		atlas.load();
		atlases.put("tex_01", atlas);

	}

}

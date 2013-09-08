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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import android.graphics.Color;
import android.util.Log;


public class ResourceManager {


	Map<String, ITextureRegion> regions;
	Map<String, ITiledTextureRegion> tiledRegions;
	Map<String, BitmapTextureAtlas> atlases;
	Map<String, Font> fonts;
	Map<String, Sound> sounds;

	public ResourceManager() {
		regions = new HashMap<String, ITextureRegion>();
		tiledRegions = new HashMap<String, ITiledTextureRegion>();
		atlases = new HashMap<String, BitmapTextureAtlas>();
		fonts = new HashMap<String, Font>();
		sounds = new HashMap<String, Sound>();
	}

	public ITextureRegion getTextureRegion(String key) {
		if (!regions.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getTextureRegion: invalid key - " + key);
		return regions.get(key);
	}
	
	public ITiledTextureRegion getTiledTextureRegion(String key) {
		if (!tiledRegions.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getTiledTextureRegion: invalid key - " + key);
		return tiledRegions.get(key);
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
	
	public Sound getSound(String key) {
		if (!sounds.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getSound: invalid key - " + key);
		return sounds.get(key);
	}
	
	public void playSound(String key) {
		if (!sounds.containsKey(key))
			Log.e(MainActivity.DebugID, "ResourceManager::getSound: invalid key - " + key);
		if(MainActivity.isSoundEnabled()){
			sounds.get(key).play();
		}
	}


	public void loadAtlases() {

		BitmapTextureAtlas atlas;
		ITextureRegion region;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Load ui graphics
		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "User_Interface.png", 0, 0);
		atlas.load();
		atlases.put("User_Interface", atlas);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 310, 70, false);
		regions.put("Button1", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 160, 70, 60, false);
		regions.put("ButtonVibration", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 230, 70, 60, false);
		regions.put("ButtonSound", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 1, 447, 64, 64, false);
		regions.put("ParticlePoint", region);

	

		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "tex_01.png", 0, 0);
		atlas.load();
		atlases.put("texture01", atlas);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 80, 64, 64);
		regions.put("boat_body", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 64, 64);
		regions.put("shark_body", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 160, 32, 32);
		regions.put("heart", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 32, 160, 32, 32);
		regions.put("heart_died", region);

		region = TextureRegionFactory.extractFromTexture(atlas, 0, 200, 16, 16);
		regions.put("bullet", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 16, 200, 16, 16);
		regions.put("bullet_boom", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 150, 0, 50, 50);
		regions.put("sun", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 200, 0, 50, 50);
		regions.put("moon", region);
		
		// Clouds texture
		atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 128, 192, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "Clouds.png", 0, 0);
		atlas.load();
		atlases.put("Clouds", atlas);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 90, 45);
		regions.put("cloud1", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 50, 90, 45);
		regions.put("cloud2", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 100, 70, 45);
		regions.put("cloud3", region);
		
		region = TextureRegionFactory.extractFromTexture(atlas, 0, 150, 90, 45);
		regions.put("cloud4", region);

		Log.i(MainActivity.DebugID, "ResourceManager::loadAtlases: OK");
	}
	
	public void loadSounds() {
		SoundFactory.setAssetBasePath("sound/");

		addSound("untitled.ogg", "roboClick");
		addSound("SwitchOn.ogg", "switchOn");
		addSound("error.ogg", "error");
		addSound("yes_1.ogg", "yes1");
		addSound("yes_2.ogg", "yes2");
	}
	
	private void addSound(String filename, String name){
		Sound snd = null;
		try {
			snd = SoundFactory.createSoundFromAsset(MainActivity.Me.getSoundManager(), MainActivity.Me.getApplicationContext(), filename);
		} catch (IOException e) {
			Log.e(MainActivity.DebugID, "ResourceManager::addSound: " + e.getMessage());
			e.printStackTrace();
		}
		sounds.put(name, snd);
	}

	public void loadFonts() {

		FontFactory.setAssetBasePath("font/");
		Font mFont;

		final ITexture TextureFontPixelWhite = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelWhite, MainActivity.Me.getAssets(), "Furore.otf", 32, true, Color.WHITE);
		mFont.load();
		fonts.put("White Furore", mFont);
		
		Log.i(MainActivity.DebugID, "ResourceManager::loadFonts: OK");
	}

	public void LoadResourcesForPreloader() {

		Font mFont;
		FontFactory.setAssetBasePath("font/");

		// press text
		ITexture TextureFontFurore = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontFurore, MainActivity.Me.getAssets(), "Furore.otf", 24, true, Color.BLACK);
		mFont.load();
		fonts.put("Furore", mFont);
		
		TextureFontFurore = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontFurore, MainActivity.Me.getAssets(), "Furore.otf", 72, true, Color.BLACK);
		mFont.load();
		fonts.put("Furore48", mFont);
	}

}

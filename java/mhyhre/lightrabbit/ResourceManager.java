/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.graphics.Color;
import android.util.Log;

public class ResourceManager {
    
    private Map<String, ITextureRegion> regions;
    private Map<String, ITiledTextureRegion> tiledRegions;
    private Map<String, BitmapTextureAtlas> atlases;
    private Map<String, Font> fonts;
    private Map<String, Sound> sounds;

    public ResourceManager() {
        regions = new HashMap<String, ITextureRegion>();
        tiledRegions = new HashMap<String, ITiledTextureRegion>();
        atlases = new HashMap<String, BitmapTextureAtlas>();
        fonts = new HashMap<String, Font>();
        sounds = new HashMap<String, Sound>();
    }

    public ITextureRegion getTextureRegion(String key) {
        if (!regions.containsKey(key))
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::getTextureRegion: invalid key - " + key);
        return regions.get(key);
    }

    public ITiledTextureRegion getTiledTextureRegion(String key) {
        if (!tiledRegions.containsKey(key))
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::getTiledTextureRegion: invalid key - " + key);
        return tiledRegions.get(key);
    }

    public BitmapTextureAtlas getTextureAtlas(String key) {
        if (!atlases.containsKey(key))
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::getTextureAtlas: invalid key - " + key);
        return atlases.get(key);
    }

    public Font getFont(String key) {
        if (!fonts.containsKey(key))
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::getFont: invalid key - " + key);
        return fonts.get(key);
    }

    public Sound getSound(String key) {
        if (!sounds.containsKey(key))
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::getSound: invalid key - " + key);
        return sounds.get(key);
    }

    public void playSound(String key) {
        if (!sounds.containsKey(key))
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::playSound: invalid key - " + key);
        if (MainActivity.isSoundEnabled()) {
            sounds.get(key).play();
        }
    }

    public void loadAtlases() {

        BitmapTextureAtlas atlas;
        ITextureRegion region;

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 512, 512, TextureOptions.NEAREST);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "User_Interface.png", 0, 0);
        atlas.load();
        atlases.put("User_Interface", atlas);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 310, 70, false);
        regions.put("Button1", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 110, 450, 92, 60, false);
        regions.put("Button2", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 9, 160, 64, 64, false);
        regions.put("ButtonVibration", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 9, 234, 64, 64, false);
        regions.put("ButtonSound", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 1, 447, 64, 64, false);
        regions.put("ParticlePoint", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 360, 104, 144, false);
        regions.put("LevelCell", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 380, 386, 124, 124, false);
        regions.put("EquipmentCell", region);
        regions.put("Button", TextureRegionFactory.extractFromTexture(atlas, 0, 0, 310, 70, false));

        atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.NEAREST);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "tex_01.png", 0, 0);
        atlas.load();
        atlases.put("texture01", atlas);
        
        // Level game ui
        
        // Buttons
        regions.put("Left", TextureRegionFactory.extractFromTexture(atlas, 0, 60, 8, 8, false));
        regions.put("Right", TextureRegionFactory.extractFromTexture(atlas, 8, 60, 8, 8, false));
        regions.put("Fire", TextureRegionFactory.extractFromTexture(atlas, 16, 60, 8, 8, false));
        regions.put("Jump", TextureRegionFactory.extractFromTexture(atlas, 24, 60, 8, 8, false));
        regions.put("ButtonUp", TextureRegionFactory.extractFromTexture(atlas, 0, 90, 8, 8, false));
        regions.put("ButtonDown", TextureRegionFactory.extractFromTexture(atlas, 8, 90, 8, 8, false));
        
        region = TextureRegionFactory.extractFromTexture(atlas, 34, 33, 16, 10);
        regions.put("SteamShip", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 100, 50, 16, 10);
        regions.put("SteamShipGhost", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 76, 50, 20, 12);
        regions.put("NovaSteamShip", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 16, 14, 6);
        regions.put("PirateBoat", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 25, 32, 18);
        regions.put("PirateShip", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 16, 10);
        regions.put("Shark", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 62, 46, 9, 9);
        regions.put("MarineBomb", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 52, 32, 22, 11);
        regions.put("MerchantShip", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 76, 0, 32, 12);
        regions.put("Barge", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 20, 0, 52, 27);
        regions.put("ImperialDirigible", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 76, 14, 24, 11);
        regions.put("ImperialPoliceShip", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 102, 19, 15, 6);
        regions.put("ImperialBoat", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 76, 67, 40, 14);
        regions.put("ImperialBigShip", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 100, 40, 27, 5);
        regions.put("ImperialAirTypeA", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 71, 87, 26, 13);
        regions.put("ImperialHelicopter", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 76, 26, 20, 12);
        regions.put("IndustrialShip", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 76, 39, 20, 9);
        regions.put("PirateAir", region);
        
        
        // Misc
        region = TextureRegionFactory.extractFromTexture(atlas, 32, 46, 8, 8);
        regions.put("heart", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 40, 46, 8, 8);
        regions.put("heart_died", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 48, 46, 8, 8);
        regions.put("gold", region);

        // Bullets
        region = TextureRegionFactory.extractFromTexture(atlas, 51, 74, 3, 1);
        regions.put("bullet20", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 32, 60, 4, 4);
        regions.put("bullet90", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 36, 60, 8, 8);
        regions.put("bullet90_boom", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 44, 60, 6, 6);
        regions.put("bullet150", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 50, 60, 12, 12);
        regions.put("bullet150_boom", region);     
        
        region = TextureRegionFactory.extractFromTexture(atlas, 62, 60, 5, 8);
        regions.put("x-barrel", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 68, 60, 3, 3);
        regions.put("bomb", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 62, 72, 7, 3);
        regions.put("RocketA", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 68, 64, 3, 3);
        regions.put("imperial_bomb", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 46, 72, 3, 3);
        regions.put("ray_piece", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 16, 70, 16, 16);
        regions.put("sun", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 70, 16, 16);
        regions.put("moon", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 32, 70, 12, 12);
        regions.put("highlight_spot", region);
        
        regions.put("boat_smoke",TextureRegionFactory.extractFromTexture(atlas, 0, 46, 8, 8));
        

        // Clouds texture
        atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 128, 192, TextureOptions.NEAREST);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "Clouds.png", 0, 0);
        atlas.load();
        
        atlases.put("Clouds", atlas);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 45, 22);
        regions.put("cloud1", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 25, 45, 22);
        regions.put("cloud2", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 50, 35, 22);
        regions.put("cloud3", region);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 75, 45, 22);
        regions.put("cloud4", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 60, 0, 1, 8);
        regions.put("rain_particle", region);
        
        region = TextureRegionFactory.extractFromTexture(atlas, 50, 0, 3, 6);
        regions.put("lighing", region);

        Log.i(MainActivity.DEBUG_ID, "ResourceManager::loadAtlases: Success");
    }
    
    
    public static Map<String, TextureRegion> loadRegionsToMap(Map<String, String> filenameAndRegionName) {
        
        Map<String, TextureRegion> result = new HashMap<String, TextureRegion>();
        
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        for(String filename: filenameAndRegionName.keySet()) {
            
            BitmapTextureAtlas atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 960, 540, TextureOptions.BILINEAR);
            BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, filename, 0, 0);
            atlas.load();
            
            result.put(filenameAndRegionName.get(filename), TextureRegionFactory.extractFromTexture(atlas));
        }
        
        return result;
    }

    
    public void loadSounds() {
        SoundFactory.setAssetBasePath("sound/");

        addSound("untitled.ogg", "roboClick");
        addSound("SwitchOn.ogg", "switchOn");
        addSound("shoot01.ogg", "shoot01");
        addSound("shoot02.ogg", "shoot02");
        addSound("boom01.ogg", "boom01");
        addSound("shipDie.ogg", "shipDie");
        addSound("lighting.ogg", "lighting");
        addSound("dropToWater.ogg", "dropToWater");
        addSound("message.ogg", "message");
    }
    

    private void addSound(String filename, String name) {
        Sound snd = null;
        try {
            snd = SoundFactory.createSoundFromAsset(MainActivity.Me.getSoundManager(), MainActivity.Me.getApplicationContext(), filename);
        } catch (IOException e) {
            Log.e(MainActivity.DEBUG_ID, "ResourceManager::addSound: " + e.getMessage());
            e.printStackTrace();
        }
        sounds.put(name, snd);
    }
    

    public void loadFonts() {

        FontFactory.setAssetBasePath("font/");
        Font mFont;

        final ITexture TextureFontPixelWhite = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

        ITexture TextureFontFurore = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontFurore, MainActivity.Me.getAssets(), "Sangha.ttf", 32, true,
                Color.WHITE);
        mFont.load();
        fonts.put("White Sangha 28", mFont);
        
        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelWhite, MainActivity.Me.getAssets(), "Furore.otf", 32, true,
                Color.WHITE);
        mFont.load();
        fonts.put("White Furore", mFont);

        final ITexture TextureFontPixelWhite24 = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelWhite24, MainActivity.Me.getAssets(), "Furore.otf", 24, true,
                Color.WHITE);
        mFont.load();
        fonts.put("White Furore 24", mFont);

        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelWhite24, MainActivity.Me.getAssets(), "Furore.otf", 16, true,
                Color.WHITE);
        mFont.load();
        fonts.put("White Furore 16", mFont);

        Log.i(MainActivity.DEBUG_ID, "ResourceManager::loadFonts: OK");
    }

    public void LoadResourcesForPreloader() {

        BitmapTextureAtlas atlas;
        ITextureRegion region;

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 128, 64, TextureOptions.NEAREST);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, "loader_graphics.png", 0, 0);
        atlas.load();
        atlases.put("preloader", atlas);

        region = TextureRegionFactory.extractFromTexture(atlas, 0, 0, 100, 64, false);
        regions.put("PreloaderBoat", region);
        
        
        Font mFont;
        FontFactory.setAssetBasePath("font/");

        // press text
        ITexture TextureFontFurore = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontFurore, MainActivity.Me.getAssets(), "Furore.otf", 24, true,
                Color.BLACK);
        mFont.load();
        fonts.put("Furore", mFont);

        final ITexture TextureFontPixelWhite = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontPixelWhite, MainActivity.Me.getAssets(), "Furore.otf", 24, true,
                Color.WHITE);
        mFont.load();
        fonts.put("White Furore", mFont);
        
        TextureFontFurore = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mFont = FontFactory.createFromAsset(MainActivity.Me.getFontManager(), TextureFontFurore, MainActivity.Me.getAssets(), "Furore.otf", 72, true,
                Color.WHITE);
        mFont.load();
        fonts.put("Furore48", mFont);
    }
}

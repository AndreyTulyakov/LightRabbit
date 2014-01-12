package mhyhre.lightrabbit.game.levels;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

public class Character {

    private static final String CHARACTER_ICON_DIR = "characters_icons/";

    private final String mName;
    private final String mIconName;
    private final ITextureRegion iconRegion;

    public Character(String name, String iconName) {
        this.mName = name;
        this.mIconName = iconName;
        
        if(iconName.isEmpty()) {
            iconRegion = null;
            return;
        }

        // icon loading
        String filename = CHARACTER_ICON_DIR + mIconName + ".png";
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        BitmapTextureAtlas atlas = new BitmapTextureAtlas(MainActivity.Me.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, MainActivity.Me, filename, 0, 0);
        atlas.load();
        
        iconRegion = TextureRegionFactory.extractFromTexture(atlas);
    }

    public String getName() {
        return mName;
    }

    public String getIconName() {
        return mIconName;
    }

    public ITextureRegion getIconRegion() {
        return iconRegion;
    }

    @Override
    public String toString() {
        return "[Name:" + mName + ", Icon:" + mIconName + "]";
    }

}

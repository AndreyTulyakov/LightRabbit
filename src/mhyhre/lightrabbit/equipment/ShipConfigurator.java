package mhyhre.lightrabbit.equipment;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;

public class ShipConfigurator extends Scene {

    private static final int CELLS_CONFIGURATION_COUNT = 4;
    private static final int CELLS_AMMUNITION = 2;
    
    SpriteBatch equipmentCells;
    

    
    public ShipConfigurator() {
        
        Rectangle rect2 = new Rectangle(MainActivity.getHalfWidth(), 3*MainActivity.getHalfHeight()/4, MainActivity.getWidth()-10, 320, MainActivity.getVboManager());
        rect2.setColor(0.8f, 0.9f, 0.9f);
        attachChild(rect2);
        
        int sumOfCells = CELLS_CONFIGURATION_COUNT + CELLS_AMMUNITION;
        
        equipmentCells = new SpriteBatch(MainActivity.resources.getTextureAtlas("User_Interface"), sumOfCells, MainActivity.getVboManager());
        attachChild(equipmentCells);
    }
    
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
      
        
        
        // FIXME: rewrite it, dude!
        /*
        ITextureRegion region = MainActivity.resources.getTextureRegion("EquipmentCell");
        for(int x = 0; x < HORIZONTAL_CELLS; x++){
            for(int y = 0; y < VERTICAL_CELLS; y++){
                equipmentCells.draw(region, x*100, y*100, region.getWidth(), region.getHeight(),
                        0, 1, 1, 1, 1);
            }
        }
        */
        equipmentCells.submit();
        
        super.onManagedUpdate(pSecondsElapsed);
    }
}

package mhyhre.lightrabbit.Scenes;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.game.LevelItem;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class SceneLevelSelector extends MhyhreScene {

	List<LevelItem> mItems;

	SpriteBatch iconsBatch;

	private Text textCaption;

	private final int LEVELS_COUNT = 8;
	private final float VERTICAL_DISTANCE = 50;
	private final float HORIZONTAL_DISTANCE = 80;

	ITextureRegion levelCellRegion;

	public SceneLevelSelector() {
		setBackgroundEnabled(true);
		setBackground(new Background(0.5f, 0.6f, 0.9f));

		iconsBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas("User_Interface"), LEVELS_COUNT, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(iconsBatch);

		levelCellRegion = MainActivity.Res.getTextureRegion("LevelCell");

		textCaption = new Text(0, 0, MainActivity.Res.getFont("White Furore"), MainActivity.Me.getString(R.string.selectLevel), MainActivity.Me.getVertexBufferObjectManager());
		textCaption.setPosition(MainActivity.getHalfWidth() - textCaption.getWidth() / 2, 50);
		attachChild(textCaption);

		configureLevelsItem();
		comfigureLevelsCaption();

	}

	private void comfigureLevelsCaption() {
		
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < LEVELS_COUNT/2; i++) {
				
				int number = j*LEVELS_COUNT/2 + i;
				LevelItem item = mItems.get(number);
				
				Text caption = new Text(0, 0, MainActivity.Res.getFont("Furore"), "" + (number+1), MainActivity.Me.getVertexBufferObjectManager());
				caption.setPosition(item.getX()-caption.getWidth()/2, item.getY()-caption.getHeight()/2);
				attachChild(caption);
			}
		}
	}

	private void configureLevelsItem() {
		
		
		// Positioning
		float xStep = (levelCellRegion.getWidth() + HORIZONTAL_DISTANCE);
		float yStep = (levelCellRegion.getHeight()+VERTICAL_DISTANCE);
		float xStart = MainActivity.getHalfWidth() - (xStep * ((LEVELS_COUNT/2) -1)/2);
		float yStart = MainActivity.getHalfHeight() - yStep/2 + 50;

		// Fill list
		
		if(mItems != null)
			mItems.clear();
		
		mItems = new ArrayList<LevelItem>(LEVELS_COUNT);

		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < LEVELS_COUNT/2; i++) {

				LevelItem item = new LevelItem( 0, 0, levelCellRegion.getWidth(), levelCellRegion.getHeight(), j*LEVELS_COUNT/2 + i + 1);

				item.setX( xStart + i * xStep);
				item.setY( yStart + yStep*j );
				
				if (item.getLevelNumber() > MainActivity.getUnlockedLevels())
					item.setLocked(true);
				
				mItems.add(item);
			}
		}
		
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		float rgbMod = 1.0f;

		
		for (LevelItem item : mItems) {

			if(item.isLocked())
				rgbMod = 0.9f;
			else
				rgbMod = 1.0f;
			
			iconsBatch.draw(levelCellRegion, item.getLeftTopX(), item.getLeftTopY(), item.getWidth(), item.getHeight(), 0, rgbMod, rgbMod, rgbMod, 1);
		}


		iconsBatch.submit();

		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		

		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP )
		{
			for (LevelItem item : mItems) {
				if(item.isCollided(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()) && item.isLocked() == false){
					
					MainActivity.getRootScene().SetState(SceneStates.Game);
				}
			}
		}
		
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}

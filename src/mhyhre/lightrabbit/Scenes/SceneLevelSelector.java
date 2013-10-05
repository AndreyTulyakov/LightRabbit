package mhyhre.lightrabbit.Scenes;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.game.LevelItem;
import mhyhre.lightrabbit.game.Levels.LevelsList;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class SceneLevelSelector extends MhyhreScene {

	int lastLevelSelection = 0;
	
	List<LevelItem> mItems;
	
	int touchDownToLevel = -1;

	SpriteBatch iconsBatch;

	private Text textCaption;

	private int levelsCount = 0;
	private final float HORIZONTAL_DISTANCE = 80;
	
	
	LevelsList levelsList;
	
	

	ITextureRegion levelCellRegion;

	public SceneLevelSelector() {
		
		levelsList = new LevelsList(MainActivity.MAPS_LIST_FILENAME);
		levelsList.printList();
		levelsCount = levelsList.getLevelsCount();
		
		setBackgroundEnabled(true);
		setBackground(new Background(0.5f, 0.6f, 0.9f));

		iconsBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas("User_Interface"), levelsCount, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(iconsBatch);

		levelCellRegion = MainActivity.Res.getTextureRegion("LevelCell");

		textCaption = new Text(0, 0, MainActivity.Res.getFont("White Furore"), MainActivity.Me.getString(R.string.selectLevel), MainActivity.Me.getVertexBufferObjectManager());
		textCaption.setPosition(MainActivity.getHalfWidth(), MainActivity.getHeight() - 50);
		attachChild(textCaption);

		configureLevelsItem();
		configureLevelsCaption();

	}
	
	public int getLastLevelSelection(){
		return lastLevelSelection;
	}

	private void configureLevelsCaption() {

			for (int i = 0; i < levelsCount; i++) {

				LevelItem item = mItems.get(i);
				
				Text caption = new Text(0, 0, MainActivity.Res.getFont("Furore"), "" + (i+1), MainActivity.Me.getVertexBufferObjectManager());
				caption.setPosition(item.getX(), item.getY());
				attachChild(caption);
				
				Text desc = new Text(0, 0, MainActivity.Res.getFont("Furore"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
				desc.setPosition(item.getX(), item.getY() - (10 + MainActivity.Res.getFont("Furore").getLineHeight()));
				desc.setText(levelsList.getPageCaption(levelsList.getLevel(i).pageId));
				
				attachChild(desc);
			}

	}

	private void configureLevelsItem() {
		
		
		// Positioning
		float hStep = 40;
		float xStep = (levelCellRegion.getWidth() + HORIZONTAL_DISTANCE);
		float xStart = MainActivity.getHalfWidth() - (xStep * ((levelsCount-1)/2.0f));
		float yStart = MainActivity.getHalfHeight() + hStep*levelsCount/2;

		// Fill list
		
		if(mItems != null)
			mItems.clear();
		
		mItems = new ArrayList<LevelItem>(levelsCount);

			for (int i = 0; i < levelsCount; i++) {

				LevelItem item = new LevelItem( 0, 0, levelCellRegion.getWidth(), levelCellRegion.getHeight(), i+1);

				item.setX( xStart + i * xStep);
				item.setY( yStart - hStep * i);
				
				if (item.getLevelNumber() > MainActivity.getUnlockedLevels())
					item.setLocked(true);
				
				mItems.add(item);
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
			
			iconsBatch.draw(levelCellRegion, item.getLeftX(), item.getDownY(), item.getWidth(), item.getHeight(), 0, rgbMod, rgbMod, rgbMod, 1);
		}


		iconsBatch.submit();

		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		

		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN )
		{
			for (LevelItem item : mItems) {
				if(item.isCollided(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()) && item.isLocked() == false){

					lastLevelSelection = item.getLevelNumber();
					MainActivity.getRootScene().SetState(SceneStates.GameLoading);
				}
			}
		}

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
	
	@Override
	public void Show() {
		touchDownToLevel =-1;
		super.Show();
	}
}

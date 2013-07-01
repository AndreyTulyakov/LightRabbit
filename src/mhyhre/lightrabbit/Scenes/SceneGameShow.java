package mhyhre.lightrabbit.Scenes;

import java.util.ArrayList;

import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

public class SceneGameShow extends MhyhreScene {

	private int maxItemCount;
	private int currentItemCount;
	private int errorsCount = 0;

	
	ArrayList<WordItem> wordItems;
	ArrayList<Text> labels;
	SpriteBatch UIBatch;
	Font itemFont;

	final float borderSize = 100;
	final float heightStep = 80;
	final float centerOffset = 180;

	public SceneGameShow(int maxItemCount) {

		Background background = new Background(0.78f, 0.78f, 0.80f);
		setBackgroundEnabled(true);
		setBackground(background);

		this.maxItemCount = maxItemCount;

		// UI batch setup
		UIBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas(SceneGame.uiAtlasName), maxItemCount * 2, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(UIBatch);

		// Text setup
		itemFont = MainActivity.Res.getFont("Pixel");
		wordItems = new ArrayList<WordItem>(maxItemCount);
		labels = new ArrayList<Text>(maxItemCount);

		// Labels setup
		for (int i = 0; i < maxItemCount; i++) {
			labels.add(new Text(0, 0, itemFont, "", 50, MainActivity.Me.getVertexBufferObjectManager()));
			attachChild(labels.get(i));
		}
	}

	public void setWordsList(final ArrayList<String> inWords) {

		if (inWords.size() > maxItemCount || inWords.size()%2 == 1) {
			Log.e(MainActivity.DebugID, "Scene::setWordsList: strange list size");
			return;
		}

		currentItemCount = inWords.size();

		wordItems.clear();
		
		for (int i = 0; i < currentItemCount; i++) {
			wordItems.add(new WordItem(new String(inWords.get(i))));
		}
	}

	public void update() {

		ITextureRegion itemRegion = SceneGame.TextureRegions.get(0);
		final float leftCenterOffset = MainActivity.getHalfWidth() - centerOffset;
		final float rightCenterOffset = MainActivity.getHalfWidth() + centerOffset;
		
		float colorDelta = 0.01f;
		
		for (int i = 0; i < currentItemCount; i += 2) {
			
			// Left column
			WordItem leftItem = wordItems.get(i);
			leftItem.setX(leftCenterOffset);
			leftItem.setY(borderSize + (i / 2) * heightStep);
			leftItem.setWidth(itemRegion.getWidth());
			leftItem.setHeight(itemRegion.getHeight());
			leftItem.colorNegativeFill(colorDelta, 0.5f, 0.5f, 0.5f);
			
			// Right column
			WordItem rightItem = wordItems.get(i + 1);
			rightItem.setX(rightCenterOffset);
			rightItem.setY(borderSize + (i / 2) * heightStep);
			rightItem.setWidth(itemRegion.getWidth());
			rightItem.setHeight(itemRegion.getHeight());
			rightItem.colorNegativeFill(colorDelta, 0.5f, 0.5f, 0.5f);
		}
		
		updateLabels();
		updateUIBatch();
	}

	private void updateLabels() {

		final float textBorderSize = itemFont.getLineHeight() / 2;

		for (int i = 0; i < currentItemCount; i++) {

			WordItem item = wordItems.get(i);
			Text text = labels.get(i);
			
			text.setText(item.getWord());
			text.setPosition(item.getX() - text.getWidth()/2, item.getY() - textBorderSize);
			text.setVisible(true);
		}

		// Hide not used labels
		for (int i = currentItemCount; i < maxItemCount; i++) {
			labels.get(i).setVisible(false);
		}
	}

	private void updateUIBatch() {

		ITextureRegion itemTextureRegion = SceneGame.TextureRegions.get(0);

		for (int i = 0; i < currentItemCount; i++) {
			WordItem item = wordItems.get(i);
			UIBatch.draw(itemTextureRegion, item.getRectX(), item.getRectY(), item.getWidth(), item.getHeight(), 0, item.getRed(), item.getGreen(), item.getBlue(), item.getAlpha());
		}
		
		if(SceneGame.getMode() == GameState.Memorize){
			
			ITextureRegion equallRegion = SceneGame.TextureRegions.get(1);
			int halfItemCount = currentItemCount/2;
			float xPosition = MainActivity.getHalfWidth() - equallRegion.getWidth()/2;
			float mBorder = borderSize - equallRegion.getHeight()/2;
			
			for (int i = 0; i < halfItemCount; i++) {
				UIBatch.draw(equallRegion, xPosition, mBorder + i * heightStep, equallRegion.getWidth(), equallRegion.getHeight(), 0, 1, 1, 1, 1);
			}
		}
		
		UIBatch.submit();
	}

	boolean isTouchDown = false;
	boolean isSlidingMove = false;
	TouchEvent oldTouchEvent = null;
	float lastY = 0;

	@Override
	public boolean onSceneTouchEvent(final TouchEvent event) {

		float sensitivity = 10.0f;

		if (event.isActionUp()) {
			isTouchDown = false;
			lastY = event.getY();

			if (oldTouchEvent != null && isSlidingMove == false) {
				clickEvent(oldTouchEvent.getX()-getX(),oldTouchEvent.getY()-getY());
			}
		}

		if (event.isActionMove()) {
			if (oldTouchEvent != null) {

				// Check slide move
				float offsetX = event.getX() - oldTouchEvent.getX();
				float offsetY = event.getY() - oldTouchEvent.getY();

				if (offsetX > sensitivity || offsetX < -sensitivity) {
					isSlidingMove = true;
				}

				if (offsetY > sensitivity || offsetY < -sensitivity) {
					isSlidingMove = true;
				}
			}

			if (isTouchDown == true && isSlidingMove == true) {
				scrollScene(event.getY() - lastY);
				lastY = event.getY();
			}
		}

		if (event.isActionDown()) {
			isTouchDown = true;
			isSlidingMove = false;
			oldTouchEvent = event.clone();
			lastY = event.getY();
		}

		return super.onSceneTouchEvent(event); 
	}

	private void clickEvent(float x, float y) {
	
		for (int i = 0; i < currentItemCount; i++) {

			WordItem item = wordItems.get(i);
		
			if (isPointInRect(x, y, item.getRectX(), item.getRectY(), item.getWidth(), item.getHeight())) {
				Log.i(MainActivity.DebugID, "Scene::clicked in rect: " + i);
				
				item.setBlue(1);
				item.setGreen(1);
				item.setRed(1);
			}
		}
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		update();
		super.onManagedUpdate(pSecondsElapsed);
	}

	private void scrollScene(float scrollValue) {

		setX(0);
		setY(getY() + scrollValue);

		float scrollSize = MainActivity.getHeight() - ((borderSize * 2) + (heightStep * currentItemCount / 2) - SceneGame.TextureRegions.get(0).getHeight());

		if (getY() < scrollSize) {
			setY(scrollSize);
		}

		if (getY() > 0) {
			setY(0);
		}
	}

	private boolean isPointInRect(float pointX, float pointY, float x, float y, float w, float h) {

		if (pointX < x || pointX > x + w)
			return false;

		if (pointY < y || pointY > y + h)
			return false;

		return true;
	}

	private void shuffleWords(){
		
	}
	
	public int getErrorsCount() {
		return errorsCount;
	}

	public void setErrorsCount(int errorsCount) {
		this.errorsCount = errorsCount;
	}

}

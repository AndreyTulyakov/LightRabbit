package mhyhre.lightrabbit.Scenes;

import java.util.ArrayList;

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
	
	ArrayList<String> words;
	ArrayList<Text> labels;
	SpriteBatch UIBatch;
	Font itemFont;
	
	final float borderSize = 60;
	final float heightStep = 80;
	final float centerOffset = 180;
	
	public SceneGameShow(int maxItemCount) {
		
		Background background = new Background(0.78f, 0.78f, 0.80f);
		setBackgroundEnabled(true);
		setBackground(background);
		
		this.maxItemCount = maxItemCount;
		
		// UI batch setup
		UIBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas(SceneGame.uiAtlasName),  maxItemCount*2, MainActivity.Me.getVertexBufferObjectManager());
        attachChild(UIBatch);
		
		// Text setup
		itemFont = MainActivity.Res.getFont("Pixel");
		words = new ArrayList<String>(maxItemCount);
		labels = new ArrayList<Text>(maxItemCount);
		
		// Labels setup
		for(int i=0; i<maxItemCount; i++){
			labels.add(new Text(0,0,itemFont,"",50,MainActivity.Me.getVertexBufferObjectManager()));
			attachChild(labels.get(i));
		}
	}
	
	
	public void setWordsList(final ArrayList<String> inWords){
		
		if(inWords.size()>maxItemCount){
			return;
		}
		
		currentItemCount = inWords.size();
		
		for(int i = 0; i < currentItemCount; i++){
			words.add(new String(inWords.get(i)));
		}
	}
	
	public void update(){
		
		updateLabels();
		updateUIBatch();
	}

	
	
	private void updateLabels(){

		final float leftCenterOffset = MainActivity.getHalfWidth() - centerOffset;
		final float rightCenterOffset = MainActivity.getHalfWidth() + centerOffset;
		final float textBorderSize = borderSize - itemFont.getLineHeight()/2;
		
		for(int i = 0; i < currentItemCount; i+=2){
			
			// Left column
			Text textLeft = labels.get(i);
			textLeft.setText(words.get(i));
			textLeft.setPosition(leftCenterOffset - textLeft.getWidth()/2, textBorderSize + (i/2) * heightStep);
			textLeft.setVisible(true);
			
			// Right column
			Text textRight = labels.get(i+1);
			textRight.setText(words.get(i+1));
			textRight.setPosition(rightCenterOffset - textRight.getWidth()/2, textBorderSize + (i/2) * heightStep);
			textRight.setVisible(true);
		}
		
		// Hide not used labels
		for(int i = currentItemCount; i < maxItemCount; i++){
			labels.get(i).setVisible(false);
		}

	}
	
	private void updateUIBatch(){

		ITextureRegion itemRegion = SceneGame.TextureRegions.get(0);

		float leftCenterOffset = (MainActivity.getHalfWidth() - centerOffset) - itemRegion.getWidth()/2;
		float rightCenterOffset = (MainActivity.getHalfWidth() + centerOffset) - itemRegion.getWidth()/2;
		float SpecialBorderSize = borderSize - itemRegion.getHeight()/2;

		for(int i = 0; i < currentItemCount; i+=2){
			
			// Left column
			UIBatch.draw(itemRegion, leftCenterOffset , SpecialBorderSize + (i/2) * heightStep, itemRegion.getWidth(), itemRegion.getHeight(), 0, 1, 1, 1, 1);
			
			// Right column			
			UIBatch.draw(itemRegion, rightCenterOffset, SpecialBorderSize + (i/2) * heightStep, itemRegion.getWidth(), itemRegion.getHeight(), 0, 1, 1, 1, 1);			
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
            
            if(event.isActionUp()){
            	isTouchDown = false;
            	lastY = event.getY();
            	
            	if(oldTouchEvent != null && isSlidingMove == false){
            		clickEvent(oldTouchEvent.getX(), oldTouchEvent.getY());
            	}
            }
            
            if(event.isActionMove()){
            	if(oldTouchEvent != null){
            		
            		// Check slide move
            		float offsetX = event.getX() - oldTouchEvent.getX();
            		float offsetY = event.getY() - oldTouchEvent.getY();
            		
            		if(offsetX > sensitivity || offsetX < - sensitivity){
            			isSlidingMove = true;
            		}
            		
            		if(offsetY > sensitivity || offsetY < - sensitivity){
            			isSlidingMove = true;
            		}
            	}
            	
            	if(isTouchDown == true && isSlidingMove == true){
            		scrollScene(event.getY() - lastY);
            		lastY = event.getY();
            	}

            }
            
            if(event.isActionDown()){
            	isTouchDown = true;
            	isSlidingMove = false;
            	oldTouchEvent = event.clone();
            	lastY = event.getY();
            }

            return super.onSceneTouchEvent(event);
    }
	
	private void clickEvent(float x, float y){
		
		if(isPointInRect(x, y, 0, 0, 200, 200)){
			Log.i(MainActivity.DebugID, "Scene::clicked in rect: [" + x + ", " + y + "]");
		}
	}
	
	private void scrollScene(float scrollValue){
		
		setX(0);
		setY(getY() + scrollValue);
				
		float scrollSize = MainActivity.getHeight() - ((borderSize * 2) + (heightStep * currentItemCount/2) - SceneGame.TextureRegions.get(0).getHeight());
	
		if(getY() < scrollSize){
			setY(scrollSize);
		}
		
		if(getY() > 0){
			setY(0);
		}
		
		
	}
	
	private boolean isPointInRect(float pointX, float pointY, float x, float y, float w, float h){
		
		if(pointX < x || pointX > x+w)
			return false;
		
		if(pointY < y || pointY > y+h)
			return false;
		
		return true;
	}

}

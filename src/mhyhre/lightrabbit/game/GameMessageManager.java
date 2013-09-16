package mhyhre.lightrabbit.game;

import org.andengine.entity.text.Text;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;


import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

public class GameMessageManager extends MhyhreScene  {
	
	Text testText;
	Rectangle clickRect;
	
	boolean activeMessage = false;
	
	public GameMessageManager() {
		setBackgroundEnabled(false);
		
		
		testText = new Text(100,100,MainActivity.Res.getFont("Furore"),"TEST TEXT",MainActivity.Me.getVertexBufferObjectManager());

		
		clickRect = new Rectangle(100, 100, 100, 100, MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					Log.i(MainActivity.DebugID, "GameMessageManager: clickRect");
					MainActivity.vibrate(30);
					Hide();
					setIgnoreUpdate(true);
					activeMessage = false;
				}
				return true;
			}
		};
		clickRect.setColor(0, 0.3f, 0.8f);
		registerTouchArea(clickRect);
		
		attachChild(clickRect);
		attachChild(testText);
	}
	
	public void loadDialogs(int dialogBaseIndex){
		
	}
	
	public boolean showDialog(int id){
		
		this.Show();
		setIgnoreUpdate(false);
		
		activeMessage = true;
		
		return false;
	}

	public boolean isActiveMessage() {
		return activeMessage;
	}
}

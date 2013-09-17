package mhyhre.lightrabbit.game;

import org.andengine.entity.text.Text;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;


import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

public class GameMessageManager extends MhyhreScene  {
	
	Text textMessage;
	Rectangle clickRect;
	
	boolean activeMessage = false;
	
	public GameMessageManager() {
		setBackgroundEnabled(false);
		
		clickRect = new Rectangle(0, 0, MainActivity.getWidth()-200, MainActivity.getHeight()-200, MainActivity.Me.getVertexBufferObjectManager()) {
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
		clickRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
		clickRect.setColor(0.9f, 0.9f, 1.0f,0.5f);
		registerTouchArea(clickRect);
		
		
		textMessage = new Text(100,100,MainActivity.Res.getFont("Furore"),"",100 ,MainActivity.Me.getVertexBufferObjectManager());

		
		attachChild(clickRect);
		attachChild(textMessage);
	}
	
	public void loadDialogs(int dialogBaseIndex){
		
	}
	
	public boolean showEndDialog(String text){
		
		this.Show();
		setIgnoreUpdate(false);
		
		textMessage.setText(text);
		textMessage.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
		
		activeMessage = true;
		
		return false;
	}

	public boolean isActiveMessage() {
		return activeMessage;
	}
}

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

package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;



public class SceneAbout extends MhyhreScene {

	private Text mCaptionItem1;

	
	public SceneAbout()
	{
		setBackgroundEnabled(true);	
		setBackground(new Background(0.5f,0.5f,0.6f));
		
		String TextLoadCaption = "About";
		mCaptionItem1 = new Text(0, 0, MainActivity.Res.getFont("Pixel White"), TextLoadCaption, MainActivity.Me.getVertexBufferObjectManager());
		mCaptionItem1.setPosition(
				MainActivity.SCREEN_WIDTH/2.0f-mCaptionItem1.getWidth()/2.0f,
				mCaptionItem1.getHeight()/2);
		attachChild(mCaptionItem1);
	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {
		
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

}

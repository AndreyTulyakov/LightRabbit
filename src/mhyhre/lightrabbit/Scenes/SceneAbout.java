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

import java.io.IOException;
import java.io.InputStream;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class SceneAbout extends MhyhreScene {

	private Text textTop;
	private Text textInfo;

	public SceneAbout() {
		setBackgroundEnabled(true);
		setBackground(new Background(0.9f, 0.9f, 1.0f));
	
		String strText1 = "About";
		textTop = new Text(0, 0, MainActivity.Res.getFont("Pixel White"), strText1, MainActivity.Me.getVertexBufferObjectManager());
		textTop.setPosition(MainActivity.getHalfWidth() - textTop.getWidth() / 2.0f, 40);

		
		Rectangle topRect = new Rectangle(0, 0, MainActivity.getWidth(), 80, MainActivity.Me.getVertexBufferObjectManager());
		topRect.setColor(0.5f, 0.5f, 0.6f);
		topRect.setHeight(40 + 40 + textTop.getFont().getLineHeight());
		
		String strText2 = loadInfo("about.text");
		textInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), strText2, MainActivity.Me.getVertexBufferObjectManager());
		textInfo.setPosition( MainActivity.getHalfWidth() - textInfo.getWidth() / 2.0f, topRect.getHeight()+40);
		
		attachChild(topRect);
		attachChild(textTop);
		attachChild(textInfo);
	}

	private String loadInfo(String filename) {
		
		String text = "";

        InputStream input;
        try {
            input = MainActivity.Me.getAssetManager().open(filename);
             
             int size = input.available();
             byte[] buffer = new byte[size];
             input.read(buffer);
             input.close();
 
             text = new String(buffer);
            
        } catch (IOException e) {
        	Log.e(MainActivity.DebugID, "SceneAbout::loadInfo: " + e.getMessage());
            e.printStackTrace();
        }
        
		return text;
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

}

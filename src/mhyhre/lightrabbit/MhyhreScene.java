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

package mhyhre.lightrabbit;
import org.andengine.entity.scene.CameraScene;

public class MhyhreScene extends CameraScene {
	
	public MhyhreScene() {
		super(MainActivity.camera);
		Hide();
	}
	
	public void Show() {
		setVisible(true);
		setIgnoreUpdate(false);
	}

	public void Hide() {
		setVisible(false);
		setIgnoreUpdate(true);
	}

	
}

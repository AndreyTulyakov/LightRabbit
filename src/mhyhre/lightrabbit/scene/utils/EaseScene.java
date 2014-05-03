/*
 * Copyright (C) 2013 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *		http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.scene.utils;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.scene.CameraScene;

public class EaseScene extends CameraScene {

    public EaseScene() {
        super(MainActivity.camera);
        hide();
    }

    public void show() {
        setVisible(true);
        setIgnoreUpdate(false);
    }

    public void hide() {
        setVisible(false);
        setIgnoreUpdate(true);
    }

}
/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.scenes.utils;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.scene.CameraScene;

// Yes, i know what is bad name for this class.
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

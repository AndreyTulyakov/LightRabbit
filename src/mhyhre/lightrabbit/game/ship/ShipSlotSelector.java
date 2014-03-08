/*
 * Copyright (C) 2013-2014 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */
package mhyhre.lightrabbit.game.ship;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.equipment.ShipSlot;
import mhyhre.lightrabbit.scene.utils.EaseSceneWidget;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class ShipSlotSelector extends EaseSceneWidget {
    
    int currentShipSlotIndex;
    List<ShipSlot> shipSlotsList;

    
    public ShipSlotSelector() {
        
        setBackgroundEnabled(false);
        
        shipSlotsList = new ArrayList<ShipSlot>();
        currentShipSlotIndex = 0;
        
        // Loading slots from device to slots list
        shipSlotsList = ShipSlotReader.readSlotsFromInternalMemory();
        
        addNextAndPrevSlotButtons();
        
        updateViews();
    }
    
    public ShipSlot getCurrentShipSlot() {
        if(currentShipSlotIndex >= 0 && currentShipSlotIndex < shipSlotsList.size()) {
                return shipSlotsList.get(currentShipSlotIndex);
        }
        Log.e(MainActivity.DEBUG_ID, "ShipSlotSelector::getCurrentShipSlot: List not has slots!");
        return null;
    }
    
    private void nextSlot() {
        if(currentShipSlotIndex < (shipSlotsList.size() - 1)) {
            currentShipSlotIndex++;
        }
        updateViews();
    }
    
    private void previewSlot() {
        if(currentShipSlotIndex > 0) {
            currentShipSlotIndex--;
        }
        updateViews();
    }
    
    private void updateViews() {
        // TODO: need update child's items
    }
    
    private void addNextAndPrevSlotButtons() {
        
        float buttonsVerticalCenter = MainActivity.getHalfWidth();
        
        // Previous button
        Sprite buttonPrevSlot = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Left"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    previewSlot();
                }
                return true;
            }
        };
        
        buttonPrevSlot.setPosition(buttonPrevSlot.getWidth(), buttonsVerticalCenter);
        attachChild(buttonPrevSlot);
        registerTouchArea(buttonPrevSlot);
        
        
        // Next button
        Sprite buttonNextSlot = new Sprite(0, 0, MainActivity.resources.getTextureRegion("Right"), MainActivity.getVboManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
                    MainActivity.vibrate(30);
                    nextSlot();
                }
                return true;
            }
        };
        
        buttonNextSlot.setPosition(MainActivity.getWidth() - buttonNextSlot.getWidth(), buttonsVerticalCenter);
        attachChild(buttonNextSlot);
        registerTouchArea(buttonNextSlot);
    }
    
}

/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.levels;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mhyhre.lightrabbit.MainActivity;

@SuppressLint("UseSparseArrays")
public class Dialog {

    private Map<Integer, Replic> replics;

    public Dialog() {
        replics = new HashMap<Integer, Replic>();
    }

    public Map<Integer, Replic> getReplicsMap() {
        return replics;
    }

    public Replic getReplic(int id) {

        if (replics.containsKey(id)) {
            return replics.get(id);
        }

        Log.e(MainActivity.DEBUG_ID, "Dialog::getReplic: invalid id");
        return null;
    }
    
    // Return replic with minimal id
    public Replic getFirstReplic() {
        
        if(replics.isEmpty()) {
            return null;
        }
        
        Set<Integer> keys = replics.keySet();
        int minimalId = keys.iterator().next();
        
        for (Integer key: keys) {
            if(minimalId > key) {
                minimalId = key;
            }
        }

        return replics.get(minimalId);
    }

    public void putReplic(int id, Replic replic) {
        replics.put(id, replic);
    }

    @Override
    public String toString() {

        String result = "DIALOG:\n";

        for (final Map.Entry<Integer, Replic> entry : replics.entrySet()) {
            result += "\n" + "    Replic ID:" + entry.getKey() + " " + entry.getValue();
        }
        return result;
    }
}

/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import mhyhre.lightrabbit.utils.StreamUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

@SuppressLint("UseSparseArrays")

public class LevelUnlocker {
    
    private static final String IS_FIRST_RUN = "IS_FIRST_RUN";
    private static final String LOCKS_FILENAME = "LEVEL_LOCKS.bin";
    private static final int LEVELS_TOTAL_COUNT = 16;
    private HashMap<Integer, Boolean> levelsLockData;

    public LevelUnlocker() {
        
        levelsLockData = new HashMap<Integer, Boolean>();
        
        SharedPreferences mySharedPreferences = MainActivity.Me.getSharedPreferences(MainActivity.PREFERENCE_ID, Activity.MODE_PRIVATE);

        boolean isFirstRun = mySharedPreferences.getBoolean(IS_FIRST_RUN, true);
        
        if(isFirstRun == true) {
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putBoolean(IS_FIRST_RUN, false);
            editor.commit();
            
            lockAllLevels();
        } else {
            loadFromFile();
        }
    }
    
    public boolean isLevelUnlocked(int index) {
        if(levelsLockData.containsKey(index)) {
            return levelsLockData.get(index);
        }
        return false;
    }

    private void lockAllLevels() {

        for(int levelIndex = 0; levelIndex < LEVELS_TOTAL_COUNT; levelIndex++) {
            levelsLockData.put(levelIndex, false);
        }
        
        unlockFirstLevel();    
        saveToFile();
    }
    
    public void unlockLevel(int index) {
        levelsLockData.put(index, true);
        saveToFile();

    }
    
    private void unlockFirstLevel() {
        levelsLockData.put(0, true);
    }

    private void saveToFile() {
        
        FileOutputStream outputStream = null;
        DataOutputStream dataStream = null;
        
        try {
            outputStream = MainActivity.Me.openFileOutput(LOCKS_FILENAME, MainActivity.MODE_PRIVATE);
            dataStream = new DataOutputStream(outputStream);

            dataStream.writeInt(levelsLockData.entrySet().size());
            
            for(Entry<Integer, Boolean> entry: levelsLockData.entrySet()) {
                dataStream.writeInt(entry.getKey());
                dataStream.writeBoolean(entry.getValue());
            }
            
        } catch (FileNotFoundException e) {
            Log.i(MainActivity.DEBUG_ID, "LevelUnlocker::saveScores:" + e.getMessage());
        } catch (IllegalStateException e) {
            Log.i(MainActivity.DEBUG_ID, "LevelUnlocker::saveScores:" + e.getMessage());  
        } catch (IOException e) {
            Log.i(MainActivity.DEBUG_ID, "LevelUnlocker::saveScores:" + e.getMessage());  
        } finally {
            StreamUtils.silentClose(dataStream);
        }
    }

    private void loadFromFile() {

        DataInputStream dataStream = null;
        
        try {
            FileInputStream inputStream = MainActivity.Me.openFileInput(LOCKS_FILENAME);
            dataStream = new DataInputStream(inputStream);
            
            int countOfRecords = dataStream.readInt();
            if(countOfRecords < 0) {
                throw new IllegalStateException("Incorrect records count!");
            }
                       
            for(int i = 0; i < countOfRecords; i++) {
                int levelIndex = dataStream.readInt();
                boolean isLevelUnlocked = dataStream.readBoolean();
                levelsLockData.put(levelIndex, isLevelUnlocked);
            }
            
          } catch (FileNotFoundException e) {  
              Log.i(MainActivity.DEBUG_ID, "LevelUnlocker::loadFromFile:" + e.getMessage());
          } catch (IOException e) {
              Log.i(MainActivity.DEBUG_ID, "LevelUnlocker::loadFromFile:" + e.getMessage());
          } catch (IllegalStateException e) {
              Log.i(MainActivity.DEBUG_ID, "LevelUnlocker::loadFromFile: " + e.getMessage());
          } finally {
              StreamUtils.silentClose(dataStream);
          }
    }
}

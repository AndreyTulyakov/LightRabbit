/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.levels;

import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.game.LevelItem;

public class LevelsPage {

    private int id;
    private String caption;
    private LinkedList<LevelItem> levels;
    
    public LevelsPage(int id, String caption) {
        this.id = id;
        this.caption = caption;
        levels = new LinkedList<LevelItem>();
    }
    
    public void addLevelItem(int id, String label, String filename) {
        LevelItem item = new LevelItem(id, filename, label);
        levels.add(item);
    }
    
    public List<LevelItem> getLevelList() {
        return levels;
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }
}

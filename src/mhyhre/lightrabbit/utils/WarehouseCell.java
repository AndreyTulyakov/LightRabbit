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

package mhyhre.lightrabbit.utils;

public class WarehouseCell {
    
    final int itemId;
    final int count;
    
    private WarehouseCell(int itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public int getItemId() {
        return itemId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Cell [itemId:" + itemId + ", count:" + count + "]";
    }
}

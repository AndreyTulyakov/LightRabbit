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

import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.utils.WarehouseCell;

public class ShipConfiguration {
    
    final int shipId;
    List<Integer> configuration;
    List<WarehouseCell> equipment;
    
    public ShipConfiguration(int shipId, final int[] config, final int[] equip) {
        this.shipId = shipId;
        this.configuration = new LinkedList<Integer>();
        this.equipment = new LinkedList<WarehouseCell>();
    }

    public int getShipId() {
        return shipId;
    }

    public int getConfiguration(ShipConfigurationType type) {
        return configuration.get(type.ordinal());
    }

    public List<WarehouseCell> getEquipment() {
        return equipment;
    }

    public void setConfiguration(List<Integer> configuration) {
        this.configuration = configuration;
    }

    public void setEquipment(List<WarehouseCell> equipment) {
        this.equipment = equipment;
    }
    
    
    
    
}

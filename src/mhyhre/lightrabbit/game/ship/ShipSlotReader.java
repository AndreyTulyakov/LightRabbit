package mhyhre.lightrabbit.game.ship;

import mhyhre.lightrabbit.equipment.ShipSlot;
import java.util.ArrayList;
import java.util.List;

public class ShipSlotReader {
    
    public static final String SLOTS_FILENAME = "SHIPS_SLOTS.xml";
    
    private ShipSlotReader() {
    }
    
    /**
     * Read file from internal device memory and parse it to ShipSlots items.
     * @return succes readed slots
     */
    public static List<ShipSlot> readSlotsFromInternalMemory() {
        List<ShipSlot> slots = new ArrayList<ShipSlot>();
        
        
        return slots;
    }

}

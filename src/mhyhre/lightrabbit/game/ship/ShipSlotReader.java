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
     * 
     * @return succes readed slots
     */
    public static List<ShipSlot> readSlotsFromInternalMemory() {
        List<ShipSlot> slots = new ArrayList<ShipSlot>();

        return slots;
    }
/*
    private static void readFile() {
        
        try {
            InputStreamReader streamReader = new InputStreamReader(MainActivity.Me.openFileInput(SLOTS_FILENAME));
            BufferedReader br = new BufferedReader(streamReader);
                    
            String str = "";
            
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(MainActivity.DEBUG_ID, str);
            }
        } catch (FileNotFoundException e) {
            Log.w(MainActivity.DEBUG_ID, "ShipSlots file not found");
            
        } catch (IOException e) {
            Log.w(MainActivity.DEBUG_ID, "ShipSlotReader:" + e.getMessage());
        }
    }
    */

}

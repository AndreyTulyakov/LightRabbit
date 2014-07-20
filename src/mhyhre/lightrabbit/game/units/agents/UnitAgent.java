package mhyhre.lightrabbit.game.units.agents;

import mhyhre.lightrabbit.game.units.models.UnitModel;

public abstract class UnitAgent {
    
    protected UnitModel unit;
    protected boolean active;

    protected UnitAgent(UnitModel unit) {
        this.unit = unit;
        active = true;
    }
    
    public abstract void update();
    
    public boolean isActive() {
        return active;
    }
    
    protected void deactivate() {
        active = false;
    }
}

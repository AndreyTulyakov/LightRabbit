/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.agents;

import mhyhre.lightrabbit.game.units.Unit;

public abstract class UnitAgent {
    
    protected Unit unit;
    protected boolean active;

    protected UnitAgent(Unit unit) {
        
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

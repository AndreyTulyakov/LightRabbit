package mhyhre.lightrabbit.game.units.agents;

import mhyhre.lightrabbit.game.units.models.UnitModel;

public class UnitStopAgent extends UnitAgent {
    
    private int position;
    private float epselon;

    public UnitStopAgent(UnitModel unit, int position, float epselon) {
        super(unit);
        this.position = position;
        this.epselon = epselon;
    }

    @Override
    public void update() {
        if(Math.abs(unit.getX()-position) < epselon) {
            unit.setStopped(true);
            deactivate();
        }

    }
}

package mhyhre.lightrabbit.game.units.agents;

import mhyhre.lightrabbit.game.units.Unit;

public class UnitStopAgent extends UnitAgent {
    
    private int position;
    private float epselon;

    public UnitStopAgent(Unit unit, int position, float epselon) {
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

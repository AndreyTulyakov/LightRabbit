package mhyhre.lightrabbit.game.weapons;

public abstract class ProtoGun {
    
    protected Ammunition projectilesType;
    protected int projectilesAmount;

    
    private ProtoGun(Ammunition projectilesType, int projectilesAmount) {
        this.projectilesType = projectilesType;
        this.projectilesAmount = projectilesAmount;
    }
    
    public Ammunition fire() {
        if(projectilesAmount > 0) {
            return projectilesType;
        }   
        return null;
    }
}

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;



public abstract class Gun {
    
    protected GunType type;
    protected int projectilesAmount;

    protected Gun(GunType type, int projectilesAmount) {
        this.type = type;
        this.projectilesAmount = projectilesAmount;
    }
    
    public abstract void update(float currentTime);
    public abstract boolean canFireNow(float currentTime);
    public abstract Projectile fire();
}

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;



public class Gun90 extends Gun {
    
    private static final float RELOADING_TIME = 0.6f;
    
    private float lastFireTime;
    private float currentTime;

    public Gun90(int amount) {
        super(GunType.BULLET_GUN, amount);
        
    }

    @Override
    public void update(float currentTime) {
        
    }

    @Override
    public Projectile fire() {
        if(canFireNow(currentTime)) {
            
        }
        return null;
    }

    @Override
    public boolean canFireNow(float currentTime) {
        if(this.projectilesAmount > 0) {
            if(Math.abs(currentTime - lastFireTime) >= RELOADING_TIME) {
                return true;
            }
        }
        return false;
    }
}

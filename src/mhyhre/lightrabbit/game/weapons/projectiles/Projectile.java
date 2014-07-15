package mhyhre.lightrabbit.game.weapons.projectiles;

public class Projectile {
    public ProjectileType type;
    
    public Projectile(ProjectileType type) {
        this.type = type;
    }

    public ProjectileType getType() {
        return type;
    }

    public void setType(ProjectileType type) {
        this.type = type;
    }
    
    
}

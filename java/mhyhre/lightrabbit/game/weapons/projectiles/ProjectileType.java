/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.projectiles;

public enum ProjectileType {
    
    Undefined(0), Bullet20(50), Bullet90(50), Bullet150(80), Ray(120), Impulse(200),
    SimpleBarrel(5), Bomb(90), ImperialBomb(120), Rocket(90), GhostBullet(999);
    
    private final int armorPenetration;
    
    private ProjectileType(int armorPenetration) {
        this.armorPenetration = armorPenetration;
    }

    public int getArmorPenetration() {
        return armorPenetration;
    }
}

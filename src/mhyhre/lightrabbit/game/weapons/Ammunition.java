package mhyhre.lightrabbit.game.weapons;

import java.util.Random;

public enum Ammunition {

    BULLET_50("Bullet 50", 50, 40, 30),
    BULLET_75("Bullet 75", 120, 30, 50),
    BULLET_90("Bullet 90", 175, 30, 70),
    BULLET_90S("Bullet 90S", 190, 20, 90),
    BULLET_100("Bullet 100", 250, 20, 100),
    
    ROCKET_D1("Rocket D1", 200, 50, 75),
    ROCKET_D2("Rocket D2", 300, 40, 100),
    ROCKET_IMBA("Rocket D1", 500, 20, 150),
    
    LASER_5MW("Laser 5MW", 10, 5, 200),
    SCREAMER_S("Screamer S", 300, 10, 100);
    ;
    
    private static Random random = new Random();
    private String Name;
    
    private int damagePower;
    private int damageDispersionPercent;
    
    private int penetrationPower;   
    
    private Ammunition(String name, int damagePower, int damageDispersionPercent, int penetrationPower) {
        Name = name;
        this.damagePower = damagePower;
        this.damageDispersionPercent = damageDispersionPercent;
        this.penetrationPower = penetrationPower;
    }


    public String getName() {
        return Name;
    }

    public int getDamagePower() {
        return damagePower;
    }

    public int getPenetrationPower() {
        return penetrationPower;
    }

    public int calculateDamage(int armor) {
        float currentDamageKoefficient = (random.nextInt(damageDispersionPercent*2)-damageDispersionPercent)
                /100.0f + 1.0f;
        int damage = (int) (damagePower * currentDamageKoefficient);
        return damage;
    }
    
    
}

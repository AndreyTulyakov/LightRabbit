/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky;

import org.andengine.entity.Entity;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import mhyhre.lightrabbit.MainActivity;

public class Rain extends Entity {
    
    private final SpriteParticleSystem particleSystem;  
    
    
    public Rain() {    
        ITextureRegion rainTextureRegion = MainActivity.resources.getTextureRegion("rain_particle");
        
        final RectangleParticleEmitter particleEmitter 
        = new RectangleParticleEmitter(MainActivity.getHalfWidth(), MainActivity.getHeight(),
                MainActivity.getWidth() + 400, 1);
        particleSystem = new SpriteParticleSystem(particleEmitter, 0, 50, 100, rainTextureRegion,MainActivity.getVboManager());
        
        particleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(3));
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-200, -400));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2.0f));
        particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(1));
        particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(25));
        attachChild(particleSystem);
    }
}

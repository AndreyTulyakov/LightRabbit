package mhyhre.lightrabbit.scenes.utils;

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


public class BubbleGenerator extends Entity {

    private final SpriteParticleSystem particleSystem;


    public BubbleGenerator() {
        ITextureRegion rainTextureRegion = MainActivity.resources.getTextureRegion("bubble");

        final RectangleParticleEmitter particleEmitter = new RectangleParticleEmitter(
                MainActivity.getHalfWidth(), 0,
                MainActivity.getWidth(), 10);
        particleSystem = new SpriteParticleSystem(particleEmitter, 0, 5, 25, rainTextureRegion,MainActivity.getVboManager());

        particleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.2f, 1));
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-20,20, 100, 150));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(6.0f));
        particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0.5f,0.9f));
        particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0));
        attachChild(particleSystem);
    }
}
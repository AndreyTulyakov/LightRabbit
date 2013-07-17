package mhyhre.lightrabbit.Particles;

import mhyhre.lightrabbit.MainActivity;

import java.util.ArrayList;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;


public class ParticleManager extends SpriteBatch {

	ArrayList<Particle> particles;
	ITextureRegion region;
	
	public ParticleManager(float pX, float pY, ITextureRegion iTextureRegion, int pCapacity) {
		super(pX/2, pY/2, iTextureRegion.getTexture(), pCapacity, MainActivity.Me.getVertexBufferObjectManager());

		
		region = iTextureRegion;
		particles = new ArrayList<Particle>(pCapacity);
		
		for(int i=0; i<pCapacity; i++){
			Particle particle = new Particle(getX(),getY());
			particle.setMaxLifeSpan(100);
			particle.setLifespan(6+i, 5);
			particles.add(particle);
		}
	}

	public void update(){
		
		float w = region.getWidth();
		float h = region.getHeight();
		
		for(Particle particle: particles){
			particle.update();
			
			if(particle.isDead()){
				particle.respawn(getX(), getY());
				particle.setLifespan(60, 40);
			}
			
			draw(region, particle.getX() - w/2, particle.getY() - h/2, w, h, 0, 1, 1, 1, (float)particle.getLifespan()/(float)particle.getMaxLifeSpan());
		}
		super.submit();
	}
	
	
}

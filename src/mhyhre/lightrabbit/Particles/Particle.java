package mhyhre.lightrabbit.Particles;

import java.util.Random;

public class Particle {
	
	private static Random random = new Random();

	Vector2f location;
	Vector2f velocity;
	Vector2f diminution;
	int lifespan;
	int maxLifeSpan;


	Particle(float x, float y) {
		setMaxLifeSpan(100);
		respawn(x, y);
	}

	public void respawn(float x, float y) {
		diminution = new Vector2f(0.98f, 0.99f);
		velocity = new Vector2f( -( 10 + random.nextFloat()*4.0f), random.nextFloat() - 0.5f);
		location = new Vector2f(x, y);
		lifespan = maxLifeSpan;
	}
	

	
	public int getLifespan() {
		return lifespan;
	}

	public void setLifespan(int time){
		lifespan = time;
	}
	
	public void setLifespan(int time, int offset){
		lifespan = time + random.nextInt(offset) - offset/2;
	}

	public int getMaxLifeSpan() {
		return maxLifeSpan;
	}

	public void setMaxLifeSpan(int maxLifeSpan) {
		this.maxLifeSpan = maxLifeSpan;
	}
	
	
	public int getLefespan(){
		return lifespan;
	}
	
	public Vector2f getLocation(){
		return location;
	}
	
	public float getX(){
		return location.x;
	}

	public float getY(){
		return location.y;
	}

	public void update() {
		velocity.mult(diminution);
		location.add(velocity);
		lifespan--;
	}

	public boolean isDead() {
		if (lifespan < 0) {
			return true;
		} else {
			return false;
		}
	}
}

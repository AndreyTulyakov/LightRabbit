package mhyhre.lightrabbit.game;

import org.andengine.entity.primitive.Vector2;

public class GameEvent {

	private GameEventType type;
	private Vector2 position;
	private int startTime;
	
	public GameEvent() {
		position = new Vector2();
		type = GameEventType.NONE; 
	}

	public GameEventType getType() {
		return type;
	}

	public void setType(GameEventType pType) {
		type = pType;
	}
	
	public void setType(String pType) {
		
		if(pType.equals(GameEventType.WAIT.getValue())){
			type = GameEventType.WAIT;
			return;
		}

		if(pType.equals(GameEventType.NEW_SHARK.getValue())){
			type = GameEventType.NEW_SHARK;
			return;
		}
		
		if(pType.equals(GameEventType.NEW_PIRATE_BOAT.getValue())){
			type = GameEventType.NEW_PIRATE_BOAT;
			return;
		}
		
		if(pType.equals(GameEventType.NEW_PIRATE_SHIP.getValue())){
			type = GameEventType.NEW_PIRATE_SHIP;
			return;
		}
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		this.position = new Vector2(x, y);
	}
	
	public void setPosition(Vector2 position) {
		this.position = position.cpy();
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
}

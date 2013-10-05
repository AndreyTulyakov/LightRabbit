package mhyhre.lightrabbit.game.Levels;

public class Replic {
	
	private int mActorId;
	private int mNextId;
	private String mText;
	
	public Replic(int actorId, int nextId, String text) {
		mActorId = actorId;
		mNextId = nextId;
		mText = text;
	}

	public int getActorId() {
		return mActorId;
	}

	public int getNextId() {
		return mNextId;
	}

	public String getText() {
		return mText;
	}
}

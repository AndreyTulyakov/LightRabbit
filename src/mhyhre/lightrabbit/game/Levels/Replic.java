package mhyhre.lightrabbit.game.levels;

public class Replic {

    private int mActorId;
    private int mNextId;
    private String mText;

    public int getActorId() {
        return mActorId;
    }

    public int getNextId() {
        return mNextId;
    }

    public String getText() {
        return mText;
    }

    public void setActorId(int mActorId) {
        this.mActorId = mActorId;
    }

    public void setNextId(int mNextId) {
        this.mNextId = mNextId;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    @Override
    public String toString() {
        return "[Actor:" + mActorId + ", Next:" + mNextId + "]:" + mText;
    }
}

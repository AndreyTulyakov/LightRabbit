package mhyhre.lightrabbit.game.Levels;

public class Character {
	
	private String mName;
	private String mIconName;
	
	public String getName() {
		return mName;
	}
	public String getIconName() {
		return mIconName;
	}
	public void setName(String name) {
		this.mName = name;
	}
	public void setIconName(String iconName) {
		this.mIconName = iconName;
	}

	@Override
	public String toString() {
		return "[Name:" + mName + ", Icon:" + mIconName + "]";
	}
}

package mhyhre.lightrabbit;

public class WordItem {
	
	String word;
	private float x,y;
	private float width, height;
	
	public String getWord(){
		return word;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public WordItem(String word, float x, float y, float width, float height) {
		
		this.word = word;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean checkPick(float xpoint, float ypoint) {

		if (xpoint >= x && xpoint <= x + width) {
			if (ypoint >= y && ypoint <= y + height) {
				return true;
			}
		}
		return false;
	}

}

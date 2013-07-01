package mhyhre.lightrabbit.Scenes;

public class WordItem {

	String word;
	float x, y;
	float width, height;
	float red, green, blue, alpha;

	public WordItem(String word) {
		this.word = word;
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.red = 1;
		this.green = 1;
		this.blue = 1;
		this.alpha = 1;
	}
	
	public WordItem(String word, float x, float y, float width, float height) {
		this.word = word;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.red = 1;
		this.green = 1;
		this.blue = 1;
		this.alpha = 1;
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public float getRectX() {
		return x-width/2;
	}
	
	public float getRectY() {
		return y-height/2;
	}

	public void colorPositiveFill(float delta){
		colorPositiveFill(delta, 1, 1, 1);
	}
	
	public void colorPositiveFill(float delta, float maxRed, float maxGreen, float maxBlue){
		
		if(red < maxRed){
			red+=delta;
		}
		if(green < maxGreen){
			green+=delta;
		}
		if(blue < maxBlue){
			blue+=delta;
		}
		
		if(red > maxRed){
			red = maxRed;
		}
		if(green > maxGreen){
			green = maxGreen;
		}
		if(blue > maxBlue){
			blue = maxBlue;
		}
	}
	
	public void colorNegativeFill(float delta){
		colorNegativeFill(delta, 0, 0, 0);
	}
	
	public void colorNegativeFill(float delta, float minRed, float minGreen, float minBlue){
		
		if(red > minRed){
			red-=delta;
		}
		if(green > minGreen){
			green-=delta;
		}
		if(blue > minBlue){
			blue-=delta;
		}
		
		if(red < minRed){
			red = minRed;
		}
		if(green < minGreen){
			green = minGreen;
		}
		if(blue < minBlue){
			blue = minBlue;
		}
	}
}

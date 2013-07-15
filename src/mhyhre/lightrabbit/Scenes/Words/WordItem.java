package mhyhre.lightrabbit.Scenes.Words;

public class WordItem {

	String word;
	float x, y;
	float width, height;
	float red, green, blue, alpha;
	boolean enabled = true;
	boolean errorState = false;


	public boolean isErrorState() {
		return errorState;
	}

	public void setErrorState(boolean errorState) {
		this.errorState = errorState;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public WordItem(String word) {
		this.word = word; // new String(word);
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
		this.word = word; // new String(word);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.red = 1;
		this.green = 1;
		this.blue = 1;
		this.alpha = 1;
	}

	public void setColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
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
		this.word = word; // new String(word);
	}

	public float getRectX() {
		return x - width / 2;
	}

	public float getRectY() {
		return y - height / 2;
	}

	public void desireColor(float delta, float inRed, float inGreen, float inBlue) {
		if (delta <= 0) {
			return;
		}
		red = desire(delta, inRed, red);
		green = desire(delta, inGreen, green);
		blue = desire(delta, inBlue, blue);
	}

	private float desire(float delta, float des, float real) {
		float m = real - des;
		if (m <= delta && m >= -delta) {
			real = des;
		}
		if (real > des) {
			real -= delta;
		}
		if (real < des) {
			real += delta;
		}
		return real;
	}

}

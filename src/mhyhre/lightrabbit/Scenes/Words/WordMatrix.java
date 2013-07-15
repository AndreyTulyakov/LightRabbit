package mhyhre.lightrabbit.Scenes.Words;

public class WordMatrix {
	
	private int width, height;
	private String[][] array;
	
	
	public WordMatrix(int w, int h) {
		
		array = new String[w][h];
		
		width = w;
		height = h;
	}
	
	public void setValue(int x, int y, String value) {
		array[x][y] = value;
	}
	
	public String getValue(int x, int y) {
		return array[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}

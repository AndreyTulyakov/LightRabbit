package mhyhre.lightrabbit.Scenes;

import java.util.ArrayList;
import java.util.List;
import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Polygon;
import org.andengine.entity.primitive.Vector2;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class WaterPolygon extends Polygon {

	private List<Vector2> waterCoordinates;
	float[] vertexX1;
	float[] vertexY1;

	private final int waterResolution;
	float waveRepeating = 1.5f;
	float waveHeight = 50;
	float waterLevel = 200;
	
	Line lineV, lineH;

	public WaterPolygon(int resolution, VertexBufferObjectManager pVertexBufferObjectManager) {

		super(0, 0, new float[resolution + 3], new float[resolution + 3], pVertexBufferObjectManager);

		setDrawMode(DrawMode.TRIANGLE_FAN);
		//setDrawMode(DrawMode.LINE_LOOP);
		waterResolution = resolution;

		float step = MainActivity.getWidth() / (waterResolution - 1);

		setColor(0, 0.2f, 1.0f, 0.2f);

		// Water coordinates list
		waterCoordinates = new ArrayList<Vector2>();

		waterCoordinates.add(new Vector2(MainActivity.getHalfWidth(), MainActivity.getHeight()));
		waterCoordinates.add(new Vector2(0, MainActivity.getHeight()));

		for (int i = 0; i < waterResolution; i++) {

			waterCoordinates.add(new Vector2(i * step, MainActivity.getHalfHeight()));
		}

		waterCoordinates.add(new Vector2(MainActivity.getWidth(), MainActivity.getHeight()));

		// Separate coordinates
		vertexX1 = new float[waterCoordinates.size()];
		vertexY1 = new float[waterCoordinates.size()];

		for (int i = 0; i < waterCoordinates.size(); i++) {
			vertexX1[i] = waterCoordinates.get(i).x;
			vertexY1[i] = waterCoordinates.get(i).y;
		}
		
		lineH = new Line(0, 0, 0, 0, pVertexBufferObjectManager);
		lineV = new Line(0, 0, 0, 0, pVertexBufferObjectManager);
		
		attachChild(lineH);
		attachChild(lineV);
	}

	private static float tick = 0;

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {


		tick += 0.02f;

		if (tick > Math.PI * 2.0f)
			tick = 0.0f;

		float step = MainActivity.getWidth() / (waterResolution - 1);

		int startWaveOffset = 2;


		for (int i = startWaveOffset; i < vertexX1.length - 1; i++) {

			int j = i - startWaveOffset;
			float waveAngle = getWaveAngle(j * step);
			
			float vertexHeight = getHeightOnWave(waveAngle);

			vertexX1[i] = j * step;
			vertexY1[i] = vertexHeight;
		}

		updateVertices(vertexX1, vertexY1);

		super.onManagedUpdate(pSecondsElapsed);
	}

	public float getWaveRepeating() {
		return waveRepeating;
	}

	public void setWaveRepeating(float waveRepeating) {
		this.waveRepeating = waveRepeating;
	}
	
	private float getWaveAngle(float i){
		float c = (i/(MainActivity.getWidth() / (waterResolution-1)));
		float waveAngle = (float) ((c * waveRepeating / (Math.PI)) + tick);
		return waveAngle;
	}
	
	private float getHeightOnWave(float angle){
		float vertexHeight = (float) (MainActivity.getHeight() - waterLevel + waveHeight * Math.sin(angle));
		return vertexHeight;
	}
	
	public float getYPositionOnWave(float pX){

		float waveAngle = getWaveAngle(pX);

		float vertexHeight = getHeightOnWave(waveAngle);
		
		lineH.setPosition(0, vertexHeight, MainActivity.getWidth(), vertexHeight);
		lineV.setPosition(pX, 0 ,pX, MainActivity.getHeight());
		
		return vertexHeight;
	}

	
	public float getAngleOnWave(float pX){
		double waveAngle = getWaveAngle(pX) - Math.PI/2;
		waveAngle = Math.sin(waveAngle) * -45;
		return (float) waveAngle;
	}
	
	
}

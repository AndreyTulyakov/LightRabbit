package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.utils.Polygon;
import mhyhre.lightrabbit.utils.Vector2;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class WaterPolygon extends Polygon {

    private static final int WATER_RESOLUTION = 16;
    private static final int OVERHEAD_VERTICES = 3;
    
    private static float tick = 0;
    
    private List<Vector2> waterCoordinates;
    float[] vertexX1;
    float[] vertexY1;

    float waveRepeating = 1.5f;
    float waveHeight = 50;
    float waterLevel = 220;

    public WaterPolygon(VertexBufferObjectManager pVertexBufferObjectManager) {
        
        super(0, 0, new float[WATER_RESOLUTION + OVERHEAD_VERTICES], new float[WATER_RESOLUTION + OVERHEAD_VERTICES], pVertexBufferObjectManager);
        
        setDrawMode(DrawMode.TRIANGLE_FAN);
        // setDrawMode(DrawMode.LINE_LOOP);


        setColor(0.0f, 0.1f, 0.6f, 0.7f);

        // Water coordinates list
        waterCoordinates = new ArrayList<Vector2>();

        // Add "Loop" vertices
        waterCoordinates.add(new Vector2(MainActivity.getHalfWidth(), 0));
        waterCoordinates.add(new Vector2(0, 0));

        float step = MainActivity.getWidth() / (WATER_RESOLUTION - 1);
        for (int i = 0; i < WATER_RESOLUTION; i++) {
            waterCoordinates.add(new Vector2(i * step, MainActivity.getHalfHeight()));
        }
        waterCoordinates.add(new Vector2(MainActivity.getWidth(), 0));
        

        // Separate coordinates
        vertexX1 = new float[waterCoordinates.size()];
        vertexY1 = new float[waterCoordinates.size()];

        for (int i = 0; i < waterCoordinates.size(); i++) {
            vertexX1[i] = waterCoordinates.get(i).x;
            vertexY1[i] = waterCoordinates.get(i).y;
        }
    }



    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        tick += 0.02f;

        if (tick > Math.PI * 2.0f)
            tick = 0.0f;

        float step = MainActivity.getWidth() / (WATER_RESOLUTION - 1);

        int startVerticesOffset = 2;
        int endVerticesOffset = -1;

        for (int vertexIndex = startVerticesOffset; vertexIndex < vertexX1.length+endVerticesOffset; vertexIndex++) {

            int relativeIndex = vertexIndex - startVerticesOffset;
            float waveAngle = getWaveAngle(relativeIndex * step);

            vertexX1[vertexIndex] = relativeIndex * step;
            vertexY1[vertexIndex] = getHeightOnWave(waveAngle);
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


    /**
     * Return wave height by angle
     */
    private float getHeightOnWave(float angle) {
        float vertexHeight = (float) (waterLevel - waveHeight * Math.sin(angle));
        return vertexHeight;
    }

    /**
     * Calculate wave angular value by X offset
     */
    private float getWaveAngle(float xOffset) {
        float c = (xOffset / (MainActivity.getWidth() / (WATER_RESOLUTION - 1)));
        float waveAngle = (float) ((c * waveRepeating / (Math.PI)) + tick);
        return waveAngle;
    }
    

    public float getObjectYPosition(float xPosition) {

        float waveAngle = getWaveAngle(xPosition);
        float vertexHeight = getHeightOnWave(waveAngle);
        return vertexHeight;
    }
    
    /**
     * Calculate angle of object on current wave position
     */
    public float getObjectAngle(float xPosition) {
        double waveAngle = getWaveAngle(xPosition) - Math.PI / 2;
        waveAngle = Math.sin(waveAngle) * -45;
        return (float) waveAngle;
    }

    public float getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(float waterLevel) {
        this.waterLevel = waterLevel;
    }

}

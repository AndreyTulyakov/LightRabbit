/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky.lighting;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.utils.Polygon;
import android.graphics.Point;


public class LightingUnit extends Polygon {

    public static final int MAXIMUM_OF_ELEMENTS = 16;
    public static final int RANDOM_OFFSET_PER_STEP = 30;
    public static final int LIGHTING_WIDTH = 5;
    private static Random rand;
    private List<Point> points;
    private float[] vertexX1;
    private float[] vertexY1;
    private int lightingCounter = 0;
    
    public LightingUnit(Point startPoint, Point endPoint, VertexBufferObjectManager pVertexBufferObjectManager) {
        
        super(0, 0,
                new float[LightingUnit.MAXIMUM_OF_ELEMENTS * 2],
                new float[LightingUnit.MAXIMUM_OF_ELEMENTS * 2], 
                pVertexBufferObjectManager);
        
        setDrawMode(DrawMode.TRIANGLE_STRIP);
        setColor(0.9f, 0.9f, 1.0f, 1.0f);


        if(rand == null) {
            rand = new Random();
        }

        points = generateMidPoints(startPoint, endPoint, MAXIMUM_OF_ELEMENTS - 2);
        generationPartTwo();
        
        setAlpha(1.0f);
        MainActivity.resources.playSound("lighting");
    }
    
    private void generationPartTwo() {
        offsetFromEnd(points);

        vertexX1 = new float[LightingUnit.MAXIMUM_OF_ELEMENTS * 2];
        vertexY1 = new float[LightingUnit.MAXIMUM_OF_ELEMENTS * 2];

        for(int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            vertexX1[i*2] = point.x;
            vertexY1[i*2] = point.y;
            vertexX1[i*2+1] = point.x + LIGHTING_WIDTH;
            vertexY1[i*2+1] = point.y - LIGHTING_WIDTH/4;
        }
        
        updateVertices(vertexX1, vertexY1);
    }
    
    private void offsetFromEnd(List<Point> points) {
        
        int offsetFactor = RANDOM_OFFSET_PER_STEP;
        
        for(int i = 1; i < points.size() - 1; i++) {
            Point point = points.get(i);
            int randomOffsetX = rand.nextInt(offsetFactor*2) - offsetFactor;
            int randomOffsetY = rand.nextInt(offsetFactor/2) - offsetFactor/4;
            
            point.set(point.x + randomOffsetX, point.y + randomOffsetY);
            offsetFactor = (int) (offsetFactor * 0.9f) + 1;
        }
    }


    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        
        if(lightingCounter < 15) {
            lightingCounter++;
        }
        
        if(lightingCounter < 3) {
            setAlpha((float) (0.1f + Math.pow(0.9f, (float)(lightingCounter))));
        } else {
            if(getAlpha() > 0.01f) {
                setAlpha(getAlpha() - 0.08f);
            } else {
                setAlpha(0.0f);
            }  
        }
        super.onManagedUpdate(pSecondsElapsed);
    }
    
    
    private List<Point> generateMidPoints(final Point startPoint, final Point endPoint, int countOfMidPoints) {
        
        LinkedList<Point> resultPoints = new LinkedList<Point>();
        
        int offsetX = (endPoint.x - startPoint.x) / (countOfMidPoints + 1);
        int offsetY = (endPoint.y - startPoint.y) / (countOfMidPoints + 1);

        resultPoints.add(startPoint);
        
        for(int counter = 1; counter <= countOfMidPoints; counter++) {

            int resultX = startPoint.x + offsetX * counter;
            int resultY = startPoint.y + offsetY * counter;
            
            Point completePoint = new Point(resultX, resultY);
            resultPoints.add(completePoint);            
        }
        
        resultPoints.add(endPoint);
        
        return resultPoints;
    }

}

package mhyhre.lightrabbit.utils;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.primitive.vbo.HighPerformanceMeshVertexBufferObject;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class Polygon extends Mesh {
    // ===========================================================
    // Constants
    // ===========================================================
    
    private static final float VERTEX_SIZE_DEFAULT_RATIO = 1.f;
    
    // ===========================================================
    // Fields
    // ===========================================================
    
    protected float[] mVertexX;
    protected float[] mVertexY;

    // ===========================================================
    // Constructors
    // ===========================================================
    
    /**
     * Uses a default {@link HighPerformanceMeshVertexBufferObject} in {@link DrawType#STATIC} with the {@link VertexBufferObjectAttribute}s: {@link Mesh#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public Polygon(final float pX, final float pY, final float[] pVertexX, final float[] pVertexY, final VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pVertexX, pVertexY, pVertexBufferObjectManager, DrawType.STATIC);
    }
    
    /**
     * Uses a default {@link HighPerformanceMeshVertexBufferObject} with the {@link VertexBufferObjectAttribute}s: {@link Mesh#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public Polygon(final float pX, final float pY, final float[] pVertexX, float[] pVertexY, final VertexBufferObjectManager pVertexBufferObjectManager, final DrawType pDrawType) {
        this(pX, pY, buildVertexList(buildListOfVector2(pVertexX, pVertexY)), VERTEX_SIZE_DEFAULT_RATIO, pVertexBufferObjectManager, pDrawType );
        
        assert( mVertexX.length == mVertexY.length );
        mVertexX = pVertexX;
        mVertexY = pVertexY;
    }


    /**
     * Uses a default {@link HighPerformanceMeshVertexBufferObject} with the {@link VertexBufferObjectAttribute}s: {@link Mesh#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
     */
    public Polygon(final float pX, final float pY, final float[] pBufferData, final float sizeRatio, final VertexBufferObjectManager pVertexBufferObjectManager, final DrawType pDrawType) {
        super(pX, pY, (int) ((pBufferData.length / VERTEX_SIZE)* sizeRatio), DrawMode.TRIANGLES, 
                new HighPerformanceMeshVertexBufferObject(pVertexBufferObjectManager, pBufferData, (int) ((pBufferData.length / VERTEX_SIZE )*sizeRatio), pDrawType, true, Mesh.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));

        onUpdateVertices();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    
    public float[] getVertexX()
    {
        return mVertexX;
    }
    
    public float[] getVertexY()
    {
        return mVertexY;
    }
    
    /**
     * 
     * @param pVertexX
     * @param pVertexY
     * @return  true if vertices were correctly updated
     *          false otherwise
     */
    public boolean updateVertices( float[] pVertexX, float[] pVertexY )
    {
        mVertexX = pVertexX;
        mVertexY = pVertexY;
        assert( mVertexX.length == mVertexY.length );
        
        //List<Vector2> verticesVectors = mTriangulator.computeTriangles(buildListOfVector2(pVertexX, pVertexY));
        List<Vector2> verticesVectors = buildListOfVector2(pVertexX, pVertexY);
        if( verticesVectors.size() == 0 )
        {
            Log.e(MainActivity.DEBUG_ID, "Error: Polygon - Polygon can't be triangulated. Will not update vertices");
            return false;
        }

        updateVertices(verticesVectors, getBufferData());
        onUpdateVertices();
        
        return true;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    protected static List<Vector2> buildListOfVector2(float[] pX, float [] pY )
    {
        assert(pX.length == pY.length );
        ArrayList<Vector2> vectors = new ArrayList<Vector2>( pX.length );
        
        for( int i = 0; i < pX.length; i++ )
        {
            // TODO avoid using new
            Vector2 v = new Vector2( pX[i], pY[i]);
            vectors.add(v);
        }
        
        return vectors;
    }
    
    
    protected static float[] buildVertexList(List<Vector2> vertices )
    {
        float[] bufferData = new float[VERTEX_SIZE * vertices.size()];
        updateVertices( vertices, bufferData );
        return bufferData;
    }
    
    
    protected static void updateVertices(List<Vector2> vertices, float[] pBufferData) {
        int i = 0;
        
        assert(vertices.size() == pBufferData.length * Mesh.VERTEX_SIZE);
        
        for( Vector2 vertex : vertices )
        {
            pBufferData[(i * Mesh.VERTEX_SIZE) + Mesh.VERTEX_INDEX_X] = vertex.x;
            pBufferData[(i * Mesh.VERTEX_SIZE) + Mesh.VERTEX_INDEX_Y] = vertex.y;
            i++;
        }
    }

}
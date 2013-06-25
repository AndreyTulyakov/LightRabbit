package mhyhre.lightrabbit;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.opengl.shader.PositionTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramException;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

import android.content.res.AssetManager;
import android.opengl.GLES20;

public class ShockwaveShaderProgram extends ShaderProgram {

	public static String loadFile(String Filename) {

		AssetManager am = MainActivity.Me.getAssetManager();
		try {
			InputStream input;

			input = am.open(Filename);
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();
			String value = new String(buffer);
			input.close();
			return value;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ShockwaveShaderProgram(String fragmentShader) {
		super(PositionTextureCoordinatesShaderProgram.VERTEXSHADER, fragmentShader);

	}

	public int sUniformModelViewPositionMatrixLocation = ShaderProgramConstants.LOCATION_INVALID;
	public int sUniformTexture0Location = ShaderProgramConstants.LOCATION_INVALID;
	public int sUniformTimeLocation = ShaderProgramConstants.LOCATION_INVALID;
	public int sUniformResolutionLocation = ShaderProgramConstants.LOCATION_INVALID;

	@Override
	protected void link(final GLState pGLState) throws ShaderProgramLinkException {
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION, ShaderProgramConstants.ATTRIBUTE_POSITION);
		GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES_LOCATION, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);

		super.link(pGLState);

		sUniformModelViewPositionMatrixLocation = this.getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
		sUniformResolutionLocation = this.getUniformLocation("resolution");
		sUniformTimeLocation = this.getUniformLocation("time");
	}

	float mtime = 0;

	@Override
	public void bind(final GLState pGLState, final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
		GLES20.glDisableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);

		super.bind(pGLState, pVertexBufferObjectAttributes);

		GLES20.glUniformMatrix4fv(sUniformModelViewPositionMatrixLocation, 1, false, pGLState.getModelViewProjectionGLMatrix(), 0);
		GLES20.glUniform2f(sUniformResolutionLocation, MainActivity.getWidth(), MainActivity.getHeight());

		if (mtime < 4) {
			mtime += 0.01f;
		} else {
			mtime = 0;
		}
		GLES20.glUniform1f(sUniformTimeLocation, mtime);

	}

	@Override
	public void unbind(final GLState pGLState) throws ShaderProgramException {
		GLES20.glEnableVertexAttribArray(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION);

		super.unbind(pGLState);
	}
}

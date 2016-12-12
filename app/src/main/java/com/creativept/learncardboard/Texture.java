package com.creativept.learncardboard;

import android.content.Context;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * 类名
 * 创建时间 2016/12/12
 * 实现的主要功能
 *
 * @author zjc
 */

public class Texture extends Shape {


    private final float[] vertexArray = {
            -1.0f, 1.0f, 0.5f,   //左上角
            -1.0f, -1.0f, 0.5f,  //左下角
            1.0f, 1.0f, 0.5f,  //右上角
            1.0f, -1.0f, 0.5f //右下角
    };

    private final float[] textureCoordinate = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };

    private int aPositionHandler;
    private int uMVPMatrixHandler;
    private int uTextureUnitHandler;
    private int aTextureCoordinateHandler;
    private FloatBuffer textureBuffer;
    private FloatBuffer vertexBuffer;
    private int mTextureId;

    protected Texture(Context context) {
        super(context);
        createProgram();
        initData();
    }

    @Override
    protected void createProgram() {

        mProgram = MyGLUtils.newLinkProgram(
                MyGLUtils.loadShader(mContext, GL_VERTEX_SHADER, R.raw.texture_vertex),
                MyGLUtils.loadShader(mContext, GL_FRAGMENT_SHADER, R.raw.texture_fragment)
        );
        aPositionHandler = glGetAttribLocation(mProgram, "a_Position");
        aTextureCoordinateHandler = glGetAttribLocation(mProgram, "a_TextureCoordinates");
        uMVPMatrixHandler = glGetUniformLocation(mProgram, "u_Matrix");
        uTextureUnitHandler = glGetUniformLocation(mProgram, "u_TextureUnit");
    }

    @Override
    protected void initData() {

        vertexBuffer = MyGLUtils.newFloatBuffer(vertexArray);
        vertexBuffer.position(0);

        textureBuffer = MyGLUtils.newFloatBuffer(textureCoordinate);
        textureBuffer.position(0);

        mTextureId = MyGLUtils.loadTexture(mContext, R.drawable.test);
    }

    @Override
    protected void draw(float[] matrix) {

        glUseProgram(mProgram);

        glUniformMatrix4fv(uMVPMatrixHandler, 1, false, matrix, 0);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mTextureId);
        glUniform1i(uTextureUnitHandler, 0);

        glVertexAttribPointer(aPositionHandler, 3,
                GL_FLOAT, false, 0, vertexBuffer);
        glVertexAttribPointer(aTextureCoordinateHandler, 2,
                GL_FLOAT, false, 0, textureBuffer);

        glEnableVertexAttribArray(aPositionHandler);
        glEnableVertexAttribArray(aTextureCoordinateHandler);


        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }
}

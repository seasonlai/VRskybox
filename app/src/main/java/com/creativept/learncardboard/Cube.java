package com.creativept.learncardboard;

import android.content.Context;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
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

public class Cube extends Shape {

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private int colorHandler;
    private int positionHandler;
    private int matrixHandler;

    protected Cube(Context context) {
        super(context);
        createProgram();
        initData();
    }

    @Override
    protected void createProgram() {
        mProgram = MyGLUtils.newLinkProgram(
                MyGLUtils.loadShader(mContext, GL_VERTEX_SHADER, R.raw.triangle_vertex),
                MyGLUtils.loadShader(mContext, GL_FRAGMENT_SHADER, R.raw.triangle_fragment));

        matrixHandler = glGetUniformLocation(mProgram, "u_Matrix");
        positionHandler = glGetAttribLocation(mProgram, "a_Position");
        colorHandler = glGetAttribLocation(mProgram, "a_Color");
    }

    @Override
    protected void initData() {

        vertexBuffer = MyGLUtils.newFloatBuffer(WorldLayoutData.CUBE_COORDS);
        vertexBuffer.position(0);

        colorBuffer = MyGLUtils.newFloatBuffer(WorldLayoutData.CUBE_COLORS);
        colorBuffer.position(0);
    }

    @Override
    protected void draw(float[] matrix) {
        glUseProgram(mProgram);
        glUniformMatrix4fv(matrixHandler, 1, false, matrix, 0);
        glVertexAttribPointer(positionHandler, 3, GL_FLOAT, false, 0, vertexBuffer);
        glVertexAttribPointer(colorHandler, 4, GL_FLOAT, false, 0, colorBuffer);
        glEnableVertexAttribArray(positionHandler);
        glEnableVertexAttribArray(colorHandler);
        glDrawArrays(GL_TRIANGLES, 0, 36);

    }

}

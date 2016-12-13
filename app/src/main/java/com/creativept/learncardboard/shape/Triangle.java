package com.creativept.learncardboard.shape;

import android.content.Context;

import com.creativept.learncardboard.R;
import com.creativept.learncardboard.util.CommonUtil;
import com.creativept.learncardboard.util.ShaderProgramUtil;

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
 * Created by season on 2016/12/11.
 */

public class Triangle extends Shape {


    private final float[] vertex = {
            0f, 1f, 0.5f,
            -1f, -1f, 0.5f,
            1f, 1f, 0.5f,
    };
    private final float[] color = {
            1.0f, 0f, 0f, 1.0f,
            0f, 1f, 0f, 1.0f,
            0f, 0f, 1f, 1.0f,
    };
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private int colorHandler;
    private int positionHandler;
    private int matrixHandler;

    public Triangle(Context context) {
        super(context);
        createProgram();
        initData();
    }

    @Override
    protected void createProgram() {
        mProgram = ShaderProgramUtil.newLinkProgram(
                ShaderProgramUtil.loadShader(mContext, GL_VERTEX_SHADER, R.raw.triangle_vertex),
                ShaderProgramUtil.loadShader(mContext, GL_FRAGMENT_SHADER, R.raw.triangle_fragment));

        matrixHandler = glGetUniformLocation(mProgram, "u_Matrix");
        positionHandler = glGetAttribLocation(mProgram, "a_Position");
        colorHandler = glGetAttribLocation(mProgram, "a_Color");

    }

    @Override
    protected void initData() {
        vertexBuffer = CommonUtil.newFloatBuffer(vertex);
        vertexBuffer.position(0);

        colorBuffer = CommonUtil.newFloatBuffer(color);
        colorBuffer.position(0);
    }

    @Override
    public void draw(float[] matrix) {
        glUseProgram(mProgram);
        glUniformMatrix4fv(matrixHandler, 1, false, matrix, 0);
        glVertexAttribPointer(positionHandler, 3, GL_FLOAT, false, 0, vertexBuffer);
        glVertexAttribPointer(colorHandler, 4, GL_FLOAT, false, 0, colorBuffer);
        glEnableVertexAttribArray(positionHandler);
        glEnableVertexAttribArray(colorHandler);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}

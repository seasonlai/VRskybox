package com.creativept.learncardboard.shape;

import android.content.Context;
import android.util.Log;

import com.creativept.learncardboard.util.CommonUtil;
import com.creativept.learncardboard.util.ShaderProgramUtil;
import com.creativept.learncardboard.util.TextureUtil;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisableVertexAttribArray;
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
 * 创建时间 2016/12/14
 * 实现的主要功能
 *
 * @author zjc
 */

public abstract class BaseTexture extends Shape {


    private final float[] vertexArray = {
            -1.0f, 1.0f, 0f,   //左上角
            -1.0f, -1.0f, 0f,  //左下角
            1.0f, 1.0f, 0f,  //右上角
            1.0f, -1.0f, 0f //右下角
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

    public BaseTexture(Context context) {
        super(context);
        initData();
    }

    @Override
    protected void createProgram() {
        mProgram = ShaderProgramUtil.newLinkProgram(
                ShaderProgramUtil.loadShader(GL_VERTEX_SHADER, vShaderStr),
                ShaderProgramUtil.loadShader(GL_FRAGMENT_SHADER, fShaderStr)
        );
        aPositionHandler = glGetAttribLocation(mProgram, "a_Position");
        aTextureCoordinateHandler = glGetAttribLocation(mProgram, "a_TextureCoordinates");
        uMVPMatrixHandler = glGetUniformLocation(mProgram, "u_Matrix");
        uTextureUnitHandler = glGetUniformLocation(mProgram, "u_TextureUnit");
    }

    @Override
    protected void initData() {

        mTextureId = getTextureId();
        if (mTextureId == 0) {
            Log.e("BaseTexture", "纹理未加载成功");
            return;
        }

        vertexBuffer = CommonUtil.newFloatBuffer(vertexArray);
        vertexBuffer.position(0);

        textureBuffer = CommonUtil.newFloatBuffer(textureCoordinate);
        textureBuffer.position(0);


        createProgram();

    }

    @Override
    public void draw(float[] matrix) {

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
        glDisableVertexAttribArray(aPositionHandler);
        glDisableVertexAttribArray(aTextureCoordinateHandler);
    }

    protected abstract int getTextureId();

    @Override
    public void destroy() {
        super.destroy();
        TextureUtil.delete(mTextureId);
    }

    private final String vShaderStr = "uniform mat4 u_Matrix;\n" +
            "attribute vec4 a_Position;\n" +
            "attribute vec2 a_TextureCoordinates;\n" +
            "varying vec2 v_TextureCoordinates;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    v_TextureCoordinates = a_TextureCoordinates;\n" +
            "    gl_Position = u_Matrix * a_Position;\n" +
            "}";

    private final String fShaderStr = "precision mediump float;\n" +
            "uniform sampler2D u_TextureUnit;\n" +
            "varying vec2 v_TextureCoordinates;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);\n" +
            "}";
}

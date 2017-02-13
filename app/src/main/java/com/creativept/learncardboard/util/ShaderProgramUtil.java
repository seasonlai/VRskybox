package com.creativept.learncardboard.util;

import android.content.Context;
import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Created by season on 2016/12/8.
 */

public class ShaderProgramUtil {

    private static final String TAG = "ShaderProgramUtil";

    /**
     * 编译shader
     *
     * @param type
     * @param shaderCode
     * @return
     */
    public static int loadShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Fail Create Shader");
            }
            throw new RuntimeException("Fail Create Shader");
        }
        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (LoggerConfig.ON) {
            Log.v(TAG, "result of compiling program: \n"
                    + shaderCode + "\n"
                    + glGetShaderInfoLog(shaderObjectId));
        }
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Error Compile shader");
            }
            throw new RuntimeException("Error Compile shader");
        }
        return shaderObjectId;
    }

    /**
     * 编译shader
     *
     * @param context
     * @param type
     * @param shaderCodeResId
     * @return
     */
    public static int loadShader(Context context, int type, int shaderCodeResId) {
        return loadShader(type, FileUtil.readTFfromRes(context, shaderCodeResId));
    }

    /**
     * 新建program
     *
     * @param vertexShaderId
     * @param fragmentShaderId
     * @return
     */
    public static int newLinkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Fail Create Program");
            }
            throw new RuntimeException("Fail Create Program");
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (LoggerConfig.ON) {
            Log.v(TAG, "result of linking program: " +
                    glGetProgramInfoLog(programObjectId));
        }
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Error Link Program");
            }
            throw new RuntimeException("Error Link Program");
        }
        if (LoggerConfig.ON) {
            validateProgram(programObjectId);
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:");
        return validateStatus[0] != 0;
    }

    public static void delete(int programId){
        glDeleteProgram(programId);
    }
}

package com.creativept.learncardboard.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * 类名
 * 创建时间 2016/12/13
 * 实现的主要功能
 *
 * @author zjc
 */

public class CommonUtil {

    public static FloatBuffer newFloatBuffer(float[] data) {
        return ByteBuffer.allocateDirect(data.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(data);
    }

    public static ShortBuffer newShortBuffer(short[] data) {
        return ByteBuffer.allocateDirect(data.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(data);
    }

    public static ByteBuffer newByteBuffer(byte[] data) {
        return ByteBuffer.allocateDirect(data.length)
                .order(ByteOrder.nativeOrder())
                .put(data);
    }

    public static void perspectiveM(float[] m, float yFovInDegrees, float aspect,
                                    float n, float f) {
        final float angleInRadians = (float) (yFovInDegrees * Math.PI / 180.0);
        final float a = (float) (1.0 / Math.tan(angleInRadians / 2.0));

        m[0] = a / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;
    }

    public static boolean checkIfSupportES2(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager
                .getDeviceConfigurationInfo();
        return
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")));
    }
}

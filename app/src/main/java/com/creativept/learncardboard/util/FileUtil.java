package com.creativept.learncardboard.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 类名
 * 创建时间 2016/12/13
 * 实现的主要功能
 *
 * @author zjc
 */

public class FileUtil {
    /**
     * 读取资源文件数据
     */
    public static String readTFfromRes(Context context, int resId) {
        StringBuilder body = new StringBuilder();
        InputStream inputStream = context.getResources().openRawResource(resId);
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader bReader = new BufferedReader(isReader);
        String nextLine;
        try {
            while ((nextLine = bReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }
}

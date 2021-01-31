package com.sk.skvideo.utils;

import android.os.Environment;

/**
 * 常量
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class Constant {

    /**
     * 版本
     */
    public static final int EGL_CLIENT_VERSION = 2;

    /**
     * 顶点着色器旋转矩阵
     */
    public static final float[] VERTEX = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f
    };

    /**
     * 片元着色器旋转3矩阵
     */
    public static final float[] TEXTURE = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
    };

    /**
     * 视频录制保存路径
     */
    public static final String VIDEO_PATH = "/sdcard/record.mp4";
}

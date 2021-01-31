package com.sk.skvideo.utils;

import android.content.Context;
import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * openGl 工具类
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class OpenGLUtils {
    private static final String TAG = "OpenGLUtils";

    public static void glGenTextures(int[] textures) {
        GLES20.glGenTextures(textures.length, textures, 0);
        for (int texture : textures) {
            //绑定纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

            //设置纹理缩放过滤
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                    GLES20.GL_LINEAR);//放大过滤
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                    GLES20.GL_LINEAR);//缩小过滤

            //设置纹理环绕方向
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
//                    GLES20.GL_CLAMP_TO_EDGE);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
//                    GLES20.GL_CLAMP_TO_EDGE);

            //纹理解绑
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        }
    }


    /**
     * 着色器代码执行
     *
     * @param vSource 顶点着色器
     * @param fSource 片元着色器
     * @return
     */
    public static int loadProgram(String vSource, String fSource) {
        /**
         * 顶点着色器
         */
        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        //加载着色器代码
        GLES20.glShaderSource(vShader, vSource);
        //编译（配置）
        GLES20.glCompileShader(vShader);

        //查看配置 是否成功
        int[] status = new int[1];
        GLES20.glGetShaderiv(vShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GLES20.GL_TRUE) {
            //失败
            throw new IllegalStateException("load vertex shader:" + GLES20.glGetShaderInfoLog
                    (vShader));
        }

        /**
         *  片元着色器
         *  流程和上面一样
         */
        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        //加载着色器代码
        GLES20.glShaderSource(fShader, fSource);
        //编译（配置）
        GLES20.glCompileShader(fShader);

        //查看配置 是否成功
        GLES20.glGetShaderiv(fShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if (status[0] != GLES20.GL_TRUE) {
            //失败
            throw new IllegalStateException("load fragment shader:" + GLES20.glGetShaderInfoLog
                    (vShader));
        }


        /**
         * 创建着色器程序
         */
        int program = GLES20.glCreateProgram();
        //绑定顶点和片元
        GLES20.glAttachShader(program, vShader);
        GLES20.glAttachShader(program, fShader);
        //链接着色器程序
        GLES20.glLinkProgram(program);


        //获得状态
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, status, 0);
        if (status[0] != GLES20.GL_TRUE) {
            throw new IllegalStateException("link program:" + GLES20.glGetProgramInfoLog(program));
        }
        GLES20.glDeleteShader(vShader);
        GLES20.glDeleteShader(fShader);
        return program;
    }


    /**
     * 根据着色器资源id获取着色器代码
     *
     * @param context 上下文
     * @param rawId   资源id
     * @return 着色器代码
     */
    public static String readRawTextFile(Context context, int rawId) {
        InputStream is = context.getResources().openRawResource(rawId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 数据流拷贝
     *
     * @param context 上下文
     * @param src     文件名
     * @param dst     路径
     */
    public static void copyAssets2SdCard(Context context, String src, String dst) {
        try {
            File file = new File(dst);
            if (!file.exists()) {
                InputStream is = context.getAssets().open(src);
                FileOutputStream fos = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[2048];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

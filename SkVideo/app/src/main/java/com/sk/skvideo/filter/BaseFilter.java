package com.sk.skvideo.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.sk.skvideo.utils.Constant;
import com.sk.skvideo.utils.OpenGLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class BaseFilter {
    FloatBuffer vertexBuffer; //顶点坐标缓存区
    FloatBuffer textureBuffer; // 纹理坐标
    int program;
    int vPosition;
    int vCoord;
    int vTexture;

    /**
     * 构造器
     *
     * @param context          上下文
     * @param vertexShaderId   顶点着色器id
     * @param fragmentShaderId 片元着色器id
     */
    public BaseFilter(Context context, int vertexShaderId, int fragmentShaderId) {
        vertexBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

        initCoord();
        initGL(context, vertexShaderId, fragmentShaderId);
    }

    public void initCoord() {
        vertexBuffer.clear();
        vertexBuffer.put(Constant.VERTEX);

        textureBuffer.clear();
        textureBuffer.put(Constant.TEXTURE);
    }

    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {

        String vertexShader = OpenGLUtils.readRawTextFile(context, vertexShaderId);
        String fragShader = OpenGLUtils.readRawTextFile(context, fragmentShaderId);

        //加载着色器程序
        program = OpenGLUtils.loadProgram(vertexShader, fragShader);

        //获取程序中的变量，索引
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        vCoord = GLES20.glGetAttribLocation(program, "vCoord");
        vTexture = GLES20.glGetUniformLocation(program, "vTexture");
    }


    /**
     * 绘制
     *
     * @param texture 纹理
     */
    public int onDraw(int texture, FilterChain filterChain) {
        FilterContext filterContext = filterChain.mFilterContext;
        //设置绘制区域
        GLES20.glViewport(0, 0, filterContext.width, filterContext.height);
        GLES20.glUseProgram(program);

        vertexBuffer.position(0);
        //归一化
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        //参数启用
        GLES20.glEnableVertexAttribArray(vPosition);

        textureBuffer.position(0);
        //归一化
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        //参数启用
        GLES20.glEnableVertexAttribArray(vCoord);

        //激活一个用来显示图片的画框
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(vTexture, 0);

        beforeDraw();

        //通知画画
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        //解绑
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return texture;
    }

    /**
     * 可用于绘制之前设置参数
     */
    public void beforeDraw() {
    }

    /**
     * 资源释放
     */
    public void release() {
        GLES20.glDeleteProgram(program);
    }


}

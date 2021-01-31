package com.sk.skvideo.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.sk.skvideo.R;

/**
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class CameraFilter extends BaseFboFilter {
    private float[] mtx;
    private int vMatrix;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public CameraFilter(Context context) {
        super(context, R.raw.camera_vert, R.raw.camera_frag);
    }

    @Override
    public void initGL(Context context, int vertexShaderId, int fragmentShaderId) {
        super.initGL(context, vertexShaderId, fragmentShaderId);
        vMatrix = GLES20.glGetUniformLocation(program, "vMatrix");
    }

    @Override
    public void beforeDraw() {
        super.beforeDraw();
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);
    }

    public void setTransformMatrix(float[] mtx) {
        this.mtx = mtx;
    }
}

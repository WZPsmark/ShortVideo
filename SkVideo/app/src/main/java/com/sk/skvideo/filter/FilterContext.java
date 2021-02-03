package com.sk.skvideo.filter;

import com.sk.skvideo.face.Face;

/**
 * 上下文数据缓存
 * Create by smark
 * Time:2021/2/1
 * email:smarkwzp@163.com
 */
public class FilterContext {
    /**
     * 摄像头转换矩阵
     */
    public float[] cameraMtx;

    /**
     * 宽
     */
    public int width;
    /**
     * 高
     */
    public int height;

    /**
     * 人脸信息
     */
    public Face face;

    /**
     * 设置尺寸
     *
     * @param width  宽
     * @param height 高
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 设置脸部信息
     *
     * @param face face
     */
    public void setFace(Face face) {
        this.face = face;
    }

    /**
     * 设置吧矩阵
     *
     * @param mtx 矩阵
     */
    public void setTransformMatrix(float[] mtx) {
        this.cameraMtx = mtx;
    }

}

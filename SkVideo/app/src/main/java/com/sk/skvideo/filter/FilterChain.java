package com.sk.skvideo.filter;

import java.util.List;

/**
 * 任务执行链
 * Create by smark
 * Time:2021/2/1
 * email:smarkwzp@163.com
 */
public class FilterChain {
    /**
     * 上下文数据
     */
    public FilterContext mFilterContext;

    /**
     * 待执行任务链
     */
    private List<BaseFilter> mFilters;

    /**
     * 执行下标
     */
    private int mIndex;

    /**
     * 构造方法
     *
     * @param filterContext 上下文
     * @param filters       任务链
     * @param index         下标
     */
    public FilterChain(FilterContext filterContext, List<BaseFilter> filters, int index) {
        this.mFilterContext = filterContext;
        this.mFilters = filters;
        this.mIndex = index;
    }

    /**
     * 任务执行
     *
     * @param textureId 纹理id
     * @return 纹理id
     */
    public int proceed(int textureId) {
        if (mIndex >= mFilters.size()) {
            return textureId;
        }

        FilterChain filterChain = new FilterChain(mFilterContext, mFilters, mIndex + 1);
        BaseFilter mFilter = mFilters.get(mIndex);
        return mFilter.onDraw(textureId, filterChain);
    }

    /**
     * 设置尺寸
     *
     * @param width  宽
     * @param height 高
     */
    public void setSize(int width, int height) {
        mFilterContext.setSize(width, height);
    }

    /**
     * 设置转换矩阵
     *
     * @param mtx 矩阵
     */
    public void setTransformMatrix(float[] mtx) {
        mFilterContext.setTransformMatrix(mtx);
    }

//    public void setFace(Face face) {
//        mFilterContext.setFace(face);
//    }

    /**
     * 任务资源释放
     */
    public void release() {
        for (BaseFilter filter : mFilters) {
            filter.release();
        }
    }
}

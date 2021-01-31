package com.sk.skvideo.widget;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.sk.skvideo.utils.Constant;

/**
 * 相机预览view
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class CameraView extends GLSurfaceView {

    /**
     * 渲染器
     */
    private final CameraRender mCameraRender;
    /**
     * 播放速度
     */
    private Speed mSpeed = Speed.MODE_NORMAL;


    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(Constant.EGL_CLIENT_VERSION);
        //设置渲染回调接口
        mCameraRender = new CameraRender(this);
        setRenderer(mCameraRender);

        //设置刷新方式（手动）
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        mCameraRender.onSurfaceDestroyed();
    }

    /**
     * 播放速度设置
     *
     * @param speed 播放速度
     */
    public void setSpeed(Speed speed) {
        this.mSpeed = speed;
    }

    /**
     * 开始录制
     */
    public void startRecord() {
        //速度  时间/速度 speed小于就是放慢 大于1就是加快
        float speed = 1.f;
        switch (mSpeed) {
            case MODE_EXTRA_SLOW:
                speed = 0.3f;
                break;
            case MODE_SLOW:
                speed = 0.5f;
                break;
            case MODE_NORMAL:
                break;
            case MODE_FAST:
                speed = 2.f;
                break;
            case MODE_EXTRA_FAST:
                speed = 3.f;
                break;
        }
        mCameraRender.startRecord(speed);
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        mCameraRender.stopRecord();
    }

    /**
     * 播放速度枚举类
     */
    public enum Speed {
        /**
         * 极慢
         */
        MODE_EXTRA_SLOW,
        /**
         * 慢
         */
        MODE_SLOW,
        /**
         * 正常
         */
        MODE_NORMAL,
        /**
         * 快速
         */
        MODE_FAST,
        /**
         * 极快
         */
        MODE_EXTRA_FAST
    }
}

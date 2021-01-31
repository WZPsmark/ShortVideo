package com.sk.skvideo.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.GLSurfaceView;

import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

import com.sk.skvideo.filter.CameraFilter;
import com.sk.skvideo.filter.ScreenFilter;
import com.sk.skvideo.record.MediaRecorder;
import com.sk.skvideo.utils.CameraHelper;
import com.sk.skvideo.utils.Constant;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * openGL 渲染器实现
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class CameraRender implements GLSurfaceView.Renderer, Preview.OnPreviewOutputUpdateListener, SurfaceTexture.OnFrameAvailableListener {
    private CameraView mCameraView;
    private CameraHelper mCameraHelper;
    private SurfaceTexture mCameraTexture;
    private int[] textures;
    private float[] mtx = new float[16];
    private ScreenFilter screenFilter;
    private CameraFilter cameraFilter;
    private MediaRecorder mRecorder;


    public CameraRender(CameraView cameraView) {
        this.mCameraView = cameraView;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) cameraView.getContext();
        mCameraHelper = new CameraHelper(lifecycleOwner, this);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        mCameraTexture.updateTexImage();
        mCameraTexture.getTransformMatrix(mtx);
        cameraFilter.setTransformMatrix(mtx);
        int id = cameraFilter.onDraw(textures[0]);
        //....
        id = screenFilter.onDraw(id);

        mRecorder.fireFrame(id, mCameraTexture.getTimestamp());

    }

    @Override
    public void onUpdated(Preview.PreviewOutput output) {
        mCameraTexture = output.getSurfaceTexture();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mCameraView.requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建openGL纹理,把摄像头的数据与这个纹理关联
        textures = new int[1];
        mCameraTexture.attachToGLContext(textures[0]);
        //摄像头数据更新监听
        mCameraTexture.setOnFrameAvailableListener(this);

        Context context = mCameraView.getContext();
        cameraFilter = new CameraFilter(context);
        screenFilter = new ScreenFilter(context);

        //录制视频的宽、高
        mRecorder = new MediaRecorder(mCameraView.getContext(), Constant.VIDEO_PATH,
                EGL14.eglGetCurrentContext(),
                480, 640);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        cameraFilter.setSize(width, height);
        screenFilter.setSize(width, height);
    }

    /**
     * 资源释放
     */
    public void onSurfaceDestroyed() {
        cameraFilter.release();
        screenFilter.release();
    }

    /**
     * 开始录制
     *
     * @param speed 速度
     */
    public void startRecord(float speed) {
        try {
            mRecorder.start(speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        mRecorder.stop();
    }
}

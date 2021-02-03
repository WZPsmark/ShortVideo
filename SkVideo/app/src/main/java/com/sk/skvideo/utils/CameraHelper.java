package com.sk.skvideo.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;

import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.sk.skvideo.face.Face;
import com.sk.skvideo.face.FaceTracker;


/**
 * 相机显示帮助类
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class CameraHelper implements ImageAnalysis.Analyzer, LifecycleObserver {

    private FaceTracker faceTracker;
    /**
     * 执行线程
     */
    private HandlerThread mHandler;
    /**
     * 摄像头朝向
     */
    private CameraX.LensFacing currentFacing = CameraX.LensFacing.BACK;
    /**
     * 摄像头数据更新监听
     */
    private Preview.OnPreviewOutputUpdateListener listener;

    /**
     * 脸部信息
     */
    private Face face;

    /**
     * 构造器
     *
     * @param lifecycleOwner lifecycle
     * @param listener       Preview 更新监听
     */
    public CameraHelper(LifecycleOwner lifecycleOwner, Preview.OnPreviewOutputUpdateListener listener) {
        this.listener = listener;
        lifecycleOwner.getLifecycle().addObserver(this);
        mHandler = new HandlerThread("Analyze-thread");
        mHandler.start();
        CameraX.bindToLifecycle(lifecycleOwner, getPreView(), getImageAnalysis());
    }

    private Preview getPreView() {
        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setTargetResolution(new Size(640, 480))
                .setLensFacing(currentFacing)
                .build();
        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(listener);
        return preview;
    }

    private ImageAnalysis getImageAnalysis() {
        ImageAnalysisConfig imageAnalysisConfig = new ImageAnalysisConfig.Builder()
                .setCallbackHandler(new Handler(mHandler.getLooper()))
                .setLensFacing(currentFacing).setTargetResolution(new Size(640, 480))
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                .build();
        ImageAnalysis imageAnalysis = new ImageAnalysis(imageAnalysisConfig);
        imageAnalysis.setAnalyzer(this);
        return imageAnalysis;
    }

    @Override
    public void analyze(ImageProxy image, int rotationDegrees) {
        byte[] bytes = ImageUtils.getBytes(image);
        synchronized (this) {
            face = faceTracker.detect(bytes, image.getWidth(), image.getHeight(), rotationDegrees);
        }
    }

    /**
     * 获取脸部信息
     *
     * @return Face
     */
    public synchronized Face getFace() {
        return face;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner owner) {
        faceTracker = new FaceTracker("/sdcard/lbpcascade_frontalface.xml",
                "/sdcard/pd_2_00_pts5.dat");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(LifecycleOwner owner) {
        faceTracker.start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(LifecycleOwner owner) {
        faceTracker.stop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        faceTracker.release();
        owner.getLifecycle().removeObserver(this);
    }

}

package com.sk.skvideo.utils;

import android.os.HandlerThread;
import android.util.Size;

import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;


/**
 * 相机显示帮助类
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class CameraHelper {

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
     * 构造器
     *
     * @param lifecycleOwner lifecycle
     * @param listener       Preview 更新监听
     */
    public CameraHelper(LifecycleOwner lifecycleOwner, Preview.OnPreviewOutputUpdateListener listener) {
        this.listener = listener;
        mHandler = new HandlerThread("Analyze-thread");
        mHandler.start();
        CameraX.bindToLifecycle(lifecycleOwner, getPreView());
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
}

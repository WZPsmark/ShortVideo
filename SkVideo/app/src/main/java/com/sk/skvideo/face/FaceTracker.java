package com.sk.skvideo.face;

/**
 * Create by smark
 * Time:2021/2/3
 * email:smarkwzp@163.com
 */
public class FaceTracker {

    static {
        System.loadLibrary("FaceTracker");
    }

    private long mNativeObj = 0;

    /**
     * 构造器
     *
     * @param faceModel       脸模型
     * @param landMarkerModel
     */
    public FaceTracker(String faceModel, String landMarkerModel) {
        mNativeObj = nativeCreateObject(faceModel, landMarkerModel);
    }

    public void start() {
        nativeStart(mNativeObj);
    }

    public void stop() {
        nativeStop(mNativeObj);
    }

    /**
     * 资源释放
     */
    public void release() {
        nativeDestroyObject(mNativeObj);
        mNativeObj = 0;
    }


    public Face detect(byte[] inputImage, int width, int height, int rotationDegrees) {
        return nativeDetect(mNativeObj, inputImage, width, height, rotationDegrees);
    }

    private static native long nativeCreateObject(String faceModel, String landMarkerModel);

    private static native void nativeDestroyObject(long thiz);

    private static native void nativeStart(long thiz);

    private static native void nativeStop(long thiz);

    private static native Face nativeDetect(long thiz, byte[] inputImage, int width, int height, int rotationDegrees);
}

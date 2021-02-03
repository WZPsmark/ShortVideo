#include <jni.h>
#include <string>
#include "FaceTrack.h"

extern "C"
JNIEXPORT jlong JNICALL
Java_com_sk_skvideo_face_FaceTracker_nativeCreateObject(JNIEnv *env, jclass clazz,
                                                        jstring face_model,
                                                        jstring land_marker_model) {

    const char *faceModel = env->GetStringUTFChars(face_model, 0);
    const char *landMarkerModel = env->GetStringUTFChars(land_marker_model, 0);

    FaceTrack *faceTrack = new FaceTrack(faceModel, landMarkerModel);

    env->ReleaseStringUTFChars(face_model, faceModel);
    env->ReleaseStringUTFChars(land_marker_model, landMarkerModel);
    return reinterpret_cast<jlong>(faceTrack);

}extern "C"
JNIEXPORT void JNICALL
Java_com_sk_skvideo_face_FaceTracker_nativeDestroyObject(JNIEnv *env, jclass clazz, jlong thiz) {
    if (thiz != 0) {
        FaceTrack *faceTrack = reinterpret_cast<FaceTrack *>(thiz);
        faceTrack->stop();
        delete faceTrack;
    }

}extern "C"
JNIEXPORT void JNICALL
Java_com_sk_skvideo_face_FaceTracker_nativeStart(JNIEnv *env, jclass clazz, jlong thiz) {
    if (thiz != 0) {
        FaceTrack *faceTrack = reinterpret_cast<FaceTrack *>(thiz);
        faceTrack->run();
    }

}extern "C"
JNIEXPORT void JNICALL
Java_com_sk_skvideo_face_FaceTracker_nativeStop(JNIEnv *env, jclass clazz, jlong thiz) {
    if (thiz != 0) {
        FaceTrack *tracker = reinterpret_cast<FaceTrack *>(thiz);
        tracker->stop();
    }

}extern "C"
JNIEXPORT jobject JNICALL
Java_com_sk_skvideo_face_FaceTracker_nativeDetect(JNIEnv *env, jclass clazz, jlong thiz,
                                                  jbyteArray input_image, jint width, jint height,
                                                  jint rotation_degrees) {

    if (thiz == 0) {
        return 0;
    }
    FaceTrack *faceTrack = reinterpret_cast<FaceTrack *>(thiz);
    jbyte *inputImage = env->GetByteArrayElements(input_image, 0);
    //I420
    Mat src(height * 3 / 2, width, CV_8UC1, inputImage);

    // 转为RGBA
    cvtColor(src, src, CV_YUV2RGBA_I420);

    if (rotation_degrees == 90) {
        rotate(src, src, ROTATE_90_CLOCKWISE);
    } else if (rotation_degrees == 270) {
        rotate(src, src, ROTATE_90_COUNTERCLOCKWISE);
    }

    //镜像问题，可以使用此方法进行垂直翻转
    Mat gray;
    cvtColor(src, gray, CV_RGBA2GRAY);
    equalizeHist(gray, gray);

    cv::Rect face;
    std::vector<SeetaPointF> points;
    faceTrack->process(gray, face, points);

    int w = src.cols;
    int h = src.rows;
    gray.release();
    src.release();
    env->ReleaseByteArrayElements(input_image, inputImage, 0);
    if (!face.empty() && !points.empty()) {
        jclass cls = env->FindClass("com/sk/skvideo/face/Face");
        jmethodID methodId = env->GetMethodID(cls, "<init>", "(IIIIIIFFFF)V");
        SeetaPointF left = points[0];
        SeetaPointF right = points[1];
        jobject object = env->NewObject(cls, methodId, face.width, face.height, w, h, face.x,
                                        face.y, left.x, left.y, right.x, right.y);
        return object;
    }

}
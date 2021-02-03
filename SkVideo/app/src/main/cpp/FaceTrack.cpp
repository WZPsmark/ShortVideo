//
// Created by smark on 2021/2/2.
//


#include "FaceTrack.h"


FaceTrack::FaceTrack(const char *faceModel, const char *landMarkerModel) {
    Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(
            makePtr<CascadeClassifier>(faceModel)
    );
    Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(
            makePtr<CascadeClassifier>(faceModel)
    );

    //跟踪器
    DetectionBasedTracker::Parameters DetectorParams;
    tracker = new DetectionBasedTracker(
            DetectionBasedTracker(mainDetector, trackingDetector, DetectorParams));

    ModelSetting::Device device = seeta::ModelSetting::CPU;
    int id = 0;
    ModelSetting FD_model(landMarkerModel, device, id);
    landMarker = new FaceLandmarker(FD_model);
}

FaceTrack::~FaceTrack() {
    delete tracker;
    delete landMarker;
}

void FaceTrack::stop() {
    tracker->stop();
}

void FaceTrack::run() {
    tracker->run();
}

void FaceTrack::process(Mat src, cv::Rect &face, vector<SeetaPointF> &points) {
    tracker->process(src);
    vector<cv::Rect> faces;
    tracker->getObjects(faces);
    //有人脸
    if (faces.size() != 0) {
        face = faces[0];
        seeta::ImageData simage(src.data, src.cols, src.rows, src.channels());
        seeta::Rect rect(face.x, face.y, face.width, face.height);
        points = landMarker->mark(simage, rect);
    }
}

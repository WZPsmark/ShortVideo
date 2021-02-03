//
// Created by smark on 2021/2/2.
//

#ifndef SKVIDEO_FACETRACK_H
#define SKVIDEO_FACETRACK_H

#include <seeta/FaceLandmarker.h>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc/types_c.h>

using namespace cv;
using namespace seeta;
using namespace std;

class CascadeDetectorAdapter : public DetectionBasedTracker::IDetector {

public:
    CascadeDetectorAdapter(Ptr<CascadeClassifier> detector) :
            IDetector(),
            Detector(detector) {
        CV_Assert(detector);
    }

    void detect(const Mat &Image, vector<cv::Rect> &objects) {
        Detector->detectMultiScale(Image, objects,)
    }

    virtual ~CascadeDetectorAdapter() {

    }

private:
    CascadeDetectorAdapter();

    Ptr<CascadeClassifier> Detector;

};

class FaceTrack {
public:
    FaceTrack(const char *faceModel, const char *landMarkerModel);

    ~FaceTrack();

    void stop();

    void run();

    void process(Mat src, cv::Rect &face, vector<SeetaPointF> &points);

private:
    DetectionBasedTracker *tracker;
    FaceLandmarker *landMarker;
};

#endif //SKVIDEO_FACETRACK_H





cmake_minimum_required(VERSION 3.4.1)


project("skvideo")

add_library(
        FaceTrack
        SHARED
        native-lib.cpp
        FaceTrack.cpp
)


include_directories(include)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}")


target_link_libraries(
        FaceTrack
        opencv_java4
        SeetaFaceLandmarker
        log)
#include <jni.h>
#include <string>

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;




extern "C" {
    JNIEXPORT void JNICALL
    Java_com_taek_1aaa_opencv_MainActivity_ConvertRGBtoGray(JNIEnv *env, jobject instance,
                                                        jlong matAddrInput, jlong matAddrResult) {

        Mat &matInput = *(Mat *)matAddrInput;
        Mat &matResult = *(Mat *)matAddrResult;


        cvtColor(matInput, matResult, CV_RGB2GRAY);


    }

}

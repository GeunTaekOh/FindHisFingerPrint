#include <jni.h>
#include <string>
#include <iostream>
#include <opencv2/core/mat.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>




using namespace cv;
using namespace std;




extern "C" {
    JNIEXPORT void JNICALL
    Java_com_taek_1aaa_opencv_MainActivity_ConvertRGBtoGray(JNIEnv *env, jobject instance,
                                                        jlong matAddrInput, jlong matAddrResult) {

        //namedWindow("찾을 색범위 설정", CV_WINDOW_AUTOSIZE);


        //트랙바에서 사용되는 변수 초기화
        int LowH = 170;
        int HighH = 179;

        int LowS = 50;
        int HighS = 255;

        int LowV = 0;
        int HighV = 255;


        //트랙바 생성
//        cvCreateTrackbar("LowH", "찾을 색범위 설정", &LowH, 179); //Hue (0 - 179)
//        cvCreateTrackbar("HighH", "찾을 색범위 설정", &HighH, 179);
//
//        cvCreateTrackbar("LowS", "찾을 색범위 설정", &LowS, 255); //Saturation (0 - 255)
//        cvCreateTrackbar("HighS", "찾을 색범위 설정", &HighS, 255);
//
//        cvCreateTrackbar("LowV", "찾을 색범위 설정", &LowV, 255); //Value (0 - 255)
//        cvCreateTrackbar("HighV", "찾을 색범위 설정", &HighV, 255);

        Mat &img_binary = *(Mat *)matAddrResult;
        /////위부분이상하수있음
        Mat &matInput = *(Mat *)matAddrInput;
        Mat &matResult = *(Mat *)matAddrResult;

            //사진을 캡쳐해서 무한루프로 돌리고출력한다음에 그 사이에 색상평균값을계산해주면됨
        //인식하려면 hsv로해야 훨씬인식잘됨

        cvtColor(matInput, matResult, CV_RGB2HSV);

        inRange(matResult, Scalar(LowH, LowS, LowV), Scalar(HighH, HighS, HighV), img_binary);
//
//
//        //morphological opening 작은 점들을 제거
//        erode(img_binary, img_binary, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)) );
//        dilate( img_binary, img_binary, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)) );
//
//
//        //morphological closing 영역의 구멍 메우기
//        dilate( img_binary, img_binary, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)) );
//        erode(img_binary, img_binary, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)) );


        //라벨링
        Mat img_labels,stats, centroids;
        int numOfLables = connectedComponentsWithStats(img_binary, img_labels,
                                                       stats, centroids, 8,CV_32S);


        //영역박스 그리기
        int max = -1, idx=0;
        for (int j = 1; j < numOfLables; j++) {
            int area = stats.at<int>(j, CC_STAT_AREA);
            if ( max < area )
            {
                max = area;
                idx = j;
            }
        }



        int left = stats.at<int>(idx, CC_STAT_LEFT);
        int top  = stats.at<int>(idx, CC_STAT_TOP);
        int width = stats.at<int>(idx, CC_STAT_WIDTH);
        int height  = stats.at<int>(idx, CC_STAT_HEIGHT);


        rectangle( matInput, Point(left,top), Point(left+width,top+height),
                   Scalar(0,0,255),1 );


    }
//    JNIEXPORT void JNICALL
//    Java_com_taek_1aaa_opencv_MainActivity_imshow(JNIEnv *env, jobject instance, jlong matAddr) {
//
//        cvShowImage("test", &matAddr);
//
//    }



}

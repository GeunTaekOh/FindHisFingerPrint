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
    JNIEXPORT int JNICALL
    Java_com_taek_1aaa_opencv_MainActivity_ConvertRGBtoGray(JNIEnv *env, jobject instance,
                                                        jlong matAddrInput, jlong matAddrResult) {

        //트랙바에서 사용되는 변수 초기화
        int LowH = 170;
        int HighH = 179;

        int LowS = 50;
        int HighS = 255;

        int LowV = 0;
        int HighV = 255;
        Mat binary;

//        int LowH = 0;
//        int HighH = 180;
//
//        int LowS = 0;
//        int HighS = 100;
//
//        int LowV = 0;
//        int HighV = 100;
//http://babytiger.tistory.com/entry/opencv%EC%97%90%EC%84%9C-HSV%EC%9D%98-%EA%B0%81-%EC%B1%84%EB%84%90-%EB%B2%94%EC%9C%84

        long inputSaveAddr = matAddrInput;

        Mat &img_binary = binary;         //이렇게하면 hsv  화면
        //Mat &img_binary = *(Mat *)inputSaveAddr;        //테스트중
        //Mat &img_binary = *(Mat *)matAddrResult;        //이렇게 하면 흑백화면
        /////위부분이상할수있음
        Mat &matInput = *(Mat *)matAddrInput;
        Mat &matResult = *(Mat *)matAddrResult;
        Mat &matTmp = binary;

        //사진을 캡쳐해서 무한루프로 돌리고출력한다음에 그 사이에 색상평균값을계산해주면됨
        //인식하려면 hsv로해야 훨씬인식잘됨

        cvtColor(matInput, matTmp, CV_RGB2HSV);
        cvtColor(matTmp, matResult, CV_HSV2RGB);

        inRange(matTmp, Scalar(LowH, LowS, LowV), Scalar(HighH, HighS, HighV), img_binary);
        //inRange 함수는 그 범위안에 들어가게되면 0으로 만들어주고 나머지는 1로 만들어 흑백사진을 만든다.
        //이거 주석처리하면 오류뜸
        // void cvInRangeS(const CvArr* input, CvScalar lower, CvScalar upper, CvArr* output)

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
                ///////테스트겸 지금 일단 액티비티 넘어가는 것은 꺼둠
        int left = stats.at<int>(idx, CC_STAT_LEFT);
        int top  = stats.at<int>(idx, CC_STAT_TOP);
        int width = stats.at<int>(idx, CC_STAT_WIDTH);
        int height  = stats.at<int>(idx, CC_STAT_HEIGHT);

        rectangle(matResult, Point(left, top), Point(left + width, top + height), Scalar(255, 0, 0), 3);
        //나중에 빨간색말고 다른색 인식할때 선 색상 바꿔야할듯
        //matInput이아니라 matResult에 그려야하나
        //차례대로, 영상 Mat, 좌표점1, 좌표점2, 색상, 두께(-1이면 color 색상으로 채운 사각형을 그림), 타입, 시프트연산을 뜻한다.

            if(height * width < 9000 || height * width > 2000000)
                return 0;
            else
                return 1;
        //return height * width;

    }
}

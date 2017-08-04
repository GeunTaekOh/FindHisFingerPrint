package com.taek_aaa.opencv;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by taek_aaa on 2017. 8. 4..
 */

public class PageFragment2 extends Fragment {

    private int mPageNumber;
    private Uri video;
    private static String packageName;
    private int index=0;
    static private boolean isFirst = true;  //이변수를 static 빼버리면 부드럽게 넘어가는데 소리가 엉망임
    private boolean isSound;
    VideoView videoView;

    public static PageFragment2 create(int pageNumber, String pn) {
        PageFragment2 fragment = new PageFragment2();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);    //페이지 넘버를 intent로 넘겨줌
        fragment.setArguments(args);
        packageName = pn;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("page");    //intent 로 page number를 받음
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_page, container, false);

        switch (mPageNumber) {
            case 0:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.test_mpeg);
                index=0;
                break;
            case 1:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample02);
                index=1;
                break;
            case 2:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample03);
                index =2;
                break;


            //메인에 페이지 개수 수정하기
        }

        videoView = ((VideoView) layout.findViewById(R.id.videoView));

        if (isFirst) {
            playVideo(true);    //첫 영상이 소리가있느냐 여부   //이건 하드코딩 첫페이지는 소리가 있는 영상이므로 이렇게 작업을 함
            isFirst=false;
        }else{
            playVideo(false);       //첫 영상이 아닌 경우는 일단 소리 출력 없이 재생을 시킴
        }

        return layout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("index","index : "+index);
        if (videoView != null) {
            if (isVisibleToUser) {      //현재 보이는 화면이면 소리 출력후 재생
                playVideo(true);
            }
            if (!isVisibleToUser) {     //보이지 않으면 일시정지
                videoView.pause();
            }
        }

    }

    private void playVideo(boolean isVolume) {
        isSound=isVolume;
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                if(!isSound)            //이함수에 들어온 인자값이 false 인 경우 소리재생을 하지 않음
                    mp.setVolume(0,0);
                videoView.start();
            }
        });
    }
}
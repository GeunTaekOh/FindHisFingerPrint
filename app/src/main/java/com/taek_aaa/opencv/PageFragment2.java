package com.taek_aaa.opencv;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                break;

            //sub main activity 에 페이지 개수 수정하기
        }

        videoView = ((VideoView) layout.findViewById(R.id.videoView));
        playVideo();

        return layout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (videoView != null) {
            if (isVisibleToUser) {      //현재 보이는 화면이면 소리 출력후 재생
                playVideo();
            }
            if (!isVisibleToUser) {     //보이지 않으면 일시정지
                videoView.pause();
            }
        }
    }

    private void playVideo() {
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });
    }
}
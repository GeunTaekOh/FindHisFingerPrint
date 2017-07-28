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
 * Created by taek_aaa on 2017. 7. 26..
 */

public class PageFragment extends Fragment {

    private int mPageNumber;
    private Uri video;
    private static String packageName;
    private int numOfPage = 7;
    private int index=0;
    static private boolean isFirst = true;  //이변수를 static 빼버리면 부드럽게 넘어가는데 소리가 엉망임
    private boolean isSound;



    VideoView videoView;

    public static PageFragment create(int pageNumber, String pn) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        packageName = pn;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("page");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_page, container, false);

        Log.e("test", "test : " + mPageNumber);
        String videoName;
        switch (mPageNumber) {
            case 0:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample01_sound);
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
            case 3:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample04);
                index=3;
                break;
            case 4:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample05);
                index=4;
                break;
            case 5:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample06);
                index=5;
                break;
            case 6:
                video = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample07_sound);
                index=6;
                break;

            //메인에 페이지 개수 수정하기
        }

        videoView = ((VideoView) layout.findViewById(R.id.videoView));

        if (isFirst) {
            playVideo(true);    //첫 영상이 소리가있느냐 여부
            isFirst=false;
        }else{
            playVideo(false);
        }



        return layout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("index","index : "+index);
        if (videoView != null) {
            if (isVisibleToUser) {
                playVideo(true);
            }
            if (!isVisibleToUser) {
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
                if(!isSound)
                    mp.setVolume(0,0);

                videoView.start();
            }
        });
    }
}
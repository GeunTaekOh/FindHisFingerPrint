package com.taek_aaa.opencv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by taek_aaa on 2017. 8. 4..
 */

public class ContentsTableActivity extends AppCompatActivity {

    public static int contentsTableIndex = 0;
    private Intent intent;
    private long lastTimeBackPressed;           //뒤로가기 버튼을 2번 누르면 종료하기 위해 담은 변수
    private VideoView videoView;
    private Uri video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentstable);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView = (VideoView)findViewById(R.id.contents_table_video);
        video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.menu_background);
        videoView.setVideoURI(video);
        playVideo();


        Button btn1 = (Button) findViewById(R.id.first_btn);
        intent = new Intent(this, SubMainActivity.class);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                contentsTableIndex = 0;
            }
        });

        Button btn2 = (Button) findViewById(R.id.second_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                contentsTableIndex = 1;
            }
        });
    }

    private void playVideo() {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });
    }

    @Override
    public void onBackPressed() {       //뒤로가기 2번 1.5초 안에 클릭시 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            moveTaskToBack(true);
            finish();       //현재 보이는 페이지 종료
            android.os.Process.killProcess(android.os.Process.myPid());     //어플 완전 종료
            return;
        }
        Toast.makeText(ContentsTableActivity.this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

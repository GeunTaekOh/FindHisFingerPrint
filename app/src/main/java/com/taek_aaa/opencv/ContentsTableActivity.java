package com.taek_aaa.opencv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by taek_aaa on 2017. 8. 4..
 */

public class ContentsTableActivity extends AppCompatActivity{

    public static int contentsTableIndex =0;
    private Intent intent;
    private long lastTimeBackPressed;           //뒤로가기 버튼을 2번 누르면 종료하기 위해 담은 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentstable);
        Button btn1 = (Button)findViewById(R.id.first_btn);

        intent = new Intent(this, SubMainActivity.class);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                contentsTableIndex=0;
            }
        });

        Button btn2 = (Button)findViewById(R.id.second_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                contentsTableIndex=1;
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

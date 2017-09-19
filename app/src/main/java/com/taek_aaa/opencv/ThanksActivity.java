package com.taek_aaa.opencv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by taek_aaa on 2017. 9. 15..
 */

public class ThanksActivity extends AppCompatActivity {
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgView = (ImageView) findViewById(R.id.thanks_img_view);

    }
}



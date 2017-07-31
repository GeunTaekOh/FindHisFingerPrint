package com.taek_aaa.opencv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.widget.Toast;

import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;


/**
 * Created by taek_aaa on 2017. 7. 26..
 */

public class SubMainActivity extends FragmentActivity {
    private VerticalViewPager mViewPager;       //수직으로 넘기는 VerticalViewPager 사용
    private PagerAdapter mPagerAdapter;
    SparseArray<Fragment> fragments = new SparseArray<Fragment>();
    private long lastTimeBackPressed;           //뒤로가기 버튼을 2번 누르면 종료하기 위해 담은 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);
        mViewPager = (VerticalViewPager)findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(false, new DefaultTransformer());
        mViewPager.setOffscreenPageLimit(5);    //미리 5개의 페이지를 로드함
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성.
            Fragment frag = fragments.get(position);
            if (frag != null) {
                return frag;
            }
            fragments.put(position, frag);
            return PageFragment.create(position, getPackageName());
        }

        @Override
        public int getCount() {
            return 7;  // 총 n개의 page를 보여줍니다.       이 부분은 수동적으로 개수를 넣어줘야함
            //return fragments.size();
        }
    }

    @Override
    public void onBackPressed() {       //뒤로가기 2번 1.5초 안에 클릭시 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            moveTaskToBack(true);
            finish();       //현재 보이는 페이지 종료
            android.os.Process.killProcess(android.os.Process.myPid());     //어플 완전 종료
            return;
        }
        Toast.makeText(SubMainActivity.this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }


}

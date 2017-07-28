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
    private VerticalViewPager mViewPager;


    private PagerAdapter mPagerAdapter;
    SparseArray<Fragment> fragments = new SparseArray<Fragment>();
    private long lastTimeBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        mViewPager = (VerticalViewPager)findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(false, new DefaultTransformer());

    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성합니다.
            Fragment frag = fragments.get(position);
            if (frag != null) {
                return frag;
            }
            fragments.put(position, frag);

            return PageFragment.create(position, getPackageName());
        }

        @Override
        public int getCount() {
            return 7;  // 총 n개의 page를 보여줍니다.
            //return fragments.size();
        }

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }
        Toast.makeText(SubMainActivity.this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }


}

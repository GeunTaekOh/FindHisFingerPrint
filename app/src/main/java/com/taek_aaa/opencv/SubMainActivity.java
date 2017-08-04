package com.taek_aaa.opencv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

import static com.taek_aaa.opencv.ContentsTableActivity.contentsTableIndex;


/**
 * Created by taek_aaa on 2017. 7. 26..
 */

public class SubMainActivity extends FragmentActivity {
    private VerticalViewPager mViewPager;       //수직으로 넘기는 VerticalViewPager 사용
    private PagerAdapter mPagerAdapter;
    SparseArray<Fragment> fragments = new SparseArray<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);
        mViewPager = (VerticalViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(false, new DefaultTransformer());
        //mViewPager.setOffscreenPageLimit(5);    //미리 5개의 페이지를 로드함
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
            if (contentsTableIndex == 0) {
                return PageFragment.create(position, getPackageName());
            } else{
                return PageFragment2.create(position, getPackageName());
            }

        }

        @Override
        public int getCount() {
            if (contentsTableIndex == 0) {
                return 7;
            } else{
                return 3;
            }
            // 총 n개의 page를 보여줍니다.       이 부분은 수동적으로 개수를 넣어줘야함
            //return fragments.size();
        }
    }



}

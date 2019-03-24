package com.wangyi.test.startapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wangyi.test.Adapter.ViewPagerAdapter;
import com.wangyi.test.PageTransformer.ScalePageTransformer;
import com.wangyi.test.tempapplication.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> list = new ArrayList<>();
    private ViewPagerAdapter adapter;
    private int currentPage;
    private int startX;
    private int endX;
    private static final String TAG = "GuideActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ScreenUtils.init(this);
        initView();
    }

    /**
     * 初始化ViewPager，设置适配器和滑动监听
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        list.add(View.inflate(this, R.layout.view_one, null));
        list.add(View.inflate(this, R.layout.view_two, null));
        list.add(View.inflate(this, R.layout.view_three, null));
        adapter = new ViewPagerAdapter(list);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ScalePageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                Log.d(TAG, "onPageSelected: "+currentPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = (int)motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = (int)motionEvent.getX();
                        if (currentPage == list.size()-1) {
                            if (startX - endX > ScreenUtils.width / 4) {
                                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }
}

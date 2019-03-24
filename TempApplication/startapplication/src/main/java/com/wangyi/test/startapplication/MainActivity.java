package com.wangyi.test.startapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private ProgressBar pb;
    private TextView tv;
    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stub);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        pb = (ProgressBar) findViewById(R.id.pb);
        tv = (TextView) findViewById(R.id.tv);
        viewStub = (ViewStub) findViewById(R.id.viewStub);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                viewStub.inflate();
            }
        }, 2000);
    }
}

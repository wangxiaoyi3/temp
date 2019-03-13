package com.wangyi.test.tempapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wangyi.test.tempapplication.utils.DialogUtils;
import com.wangyi.test.tempapplication.utils.LogUtils;
import com.wangyi.test.tempapplication.utils.ScreenUtils;
import com.wangyi.test.tempapplication.utils.SharedPreUtils;
import com.wangyi.test.tempapplication.utils.VersionUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Switch switch1;
    private Boolean pattern;
    private static LinearLayout ll;
    private static TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }


    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        switch1 = (Switch) findViewById(R.id.switch1);
        ll = (LinearLayout) findViewById(R.id.ll);
        tv1 = (TextView) findViewById(R.id.tv1);
        switch1.setChecked(false);
        switch1.setSwitchTextAppearance(MainActivity.this, R.style.switch_false);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    switch1.setSwitchTextAppearance(MainActivity.this, R.style.switch_true);
                    SharedPreUtils.putBoolean(MainActivity.this, "pattern", true);
                    SetColor.night();
                } else {
                    switch1.setSwitchTextAppearance(MainActivity.this, R.style.switch_false);
                    SharedPreUtils.putBoolean(MainActivity.this, "pattern", false);
                    SetColor.day();
                }
            }
        });
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                ScreenUtils.init(MainActivity.this);
                int width = ScreenUtils.width;
                int height = ScreenUtils.height;
//                LogUtils.d("宽："+width);
//                LogUtils.d("高："+height);
                DialogUtils.normal(this, "屏幕尺寸", width + "X" + height);
                break;
            case R.id.btn2:
                int version = VersionUtils.getVersion();
//                LogUtils.d("SDK："+version);
                DialogUtils.normal(this, "系统版本", "SDK："+version);
                break;
        }
    }

    public void init() {
        pattern = SharedPreUtils.getBoolean(MainActivity.this, "pattern", false);
        LogUtils.d("pattern："+pattern);
        if (pattern) {
            SetColor.night();
            switch1.setChecked(true);
        }
    }

    public static class SetColor {
        public static void night() {
            ll.setBackgroundColor(Color.BLACK);
            tv1.setTextColor(Color.WHITE);
        }

        public static void day() {
            ll.setBackgroundColor(Color.WHITE);
            tv1.setTextColor(Color.BLACK);
        }
    }
}

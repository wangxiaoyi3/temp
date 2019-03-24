package com.wangyi.test.startapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.wangyi.test.tempapplication.utils.SharedPreUtils;
import com.wangyi.test.tempapplication.utils.VersionUtils;

public class PermissionActivity extends AppCompatActivity {

    private String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA
    };
    private static final String TAG = "PermissionActivity";
    private Message mMessage = new Message();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        checkVersion();
    }

    /**
     * 检查系统版本
     */
    public void checkVersion() {
        if (VersionUtils.isM()) {
            requestPermissions();
        }
        else {
            isFirstStart();
        }
    }

    /**
     * 检测是否第一次启动
     */
    public void isFirstStart() {
        boolean isFirst = SharedPreUtils.getBoolean(this,"isFirst",true);
        if (isFirst) {
            mMessage.what = 1;
            mHandler.sendMessageDelayed(mMessage, 2000);
        }
        else {
            mMessage.what = 2;
            mHandler.sendMessageDelayed(mMessage, 2000);
        }
    }

    /**
     * 请求权限
     */
    public void requestPermissions(){
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isFirstStart();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("提示");
                            builder.setMessage("未授权可能会影响程序的使用");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    isFirstStart();
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();

                }
        }
    }
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SharedPreUtils.putBoolean(PermissionActivity.this, "isFirst",false);
                    startActivity(new Intent(PermissionActivity.this, GuideActivity.class));
                    finish();
                    break;
                case 2:
                    startActivity(new Intent(PermissionActivity.this, MainActivity.class));
                    finish();
                    break;
            }
            return false;
        }
    });
}

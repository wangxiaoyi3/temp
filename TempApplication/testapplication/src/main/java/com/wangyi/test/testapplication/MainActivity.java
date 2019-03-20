package com.wangyi.test.testapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_num;
    private Button btn_call;
    private String num;
    private String[] permission;
    private List<String> permissionList = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        et_num = (EditText) findViewById(R.id.et_num);
        btn_call = (Button) findViewById(R.id.btn_call);

        btn_call.setOnClickListener(this);
    }

    /**
     * 初始化权限数组
     */
    private void initData() {
        permission = new String[] {
                Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call:
                submit();
                break;
        }
    }

    /**
     * 判断是否输入电话号码
     */
    private void submit() {
        // validate
        num = et_num.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(this, "请输入电话号码！", Toast.LENGTH_SHORT).show();
            return;
        }
        checkPeriPermission();

    }

    /**
     * 执行拨号操作
     * @param number
     */
    private void callPhone(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    /**
     * 检查权限
     */
    public void checkPeriPermission() {
        //判断是否拥有权限
        permissionList.clear();
        for (int i=0; i<permission.length; i++) {
            if (ContextCompat.checkSelfPermission(this,permission[i])
                    != PackageManager.PERMISSION_GRANTED) {
               permissionList.add(permission[i]);
            }
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
        else {
            Toast.makeText(this,"你已经申请了权限！", Toast.LENGTH_SHORT).show();
            callPhone(num);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        if (requestCode == 1) {
            Log.d(TAG, "权限数量: "+permissions.length);
//            for (int i=0; i<permissions.length; i++) {
//                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) { //选择了允许
//                    Toast.makeText(this,"申请权限成功！", Toast.LENGTH_SHORT).show();
//                    callPhone(num);
//                }
//                else {
//                    //选择了禁止
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i])) {
//                        Toast.makeText(this,"授权失败！", Toast.LENGTH_SHORT).show();
//                        ActivityCompat.requestPermissions(this,permissions,1);
//                    }
//                    else { //选择了禁止并不在询问
//                        Toast.makeText(this,"请在设置里添加该权限！", Toast.LENGTH_SHORT).show();
//                        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivityForResult(intent, 2);
//                    }
//                }
//            }
            for (int i=0; i<grantResults.length; i++) {
                Log.d(TAG, permissions[i]+" 状态: "+grantResults[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否选择了禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(
                            this, permissions[i]);
                    if (showRequestPermission) { //选择了禁止，重新申请权限
                        checkPeriPermission();
                        return;
                    }
                    else { //选择了禁止并且勾选了不在询问
                        Toast.makeText(this,"请在设置里添加该权限！", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 2);
                    }
                    hasPermissionDismiss = true;
                }
            }
            if (!hasPermissionDismiss) {
                callPhone(num);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) { // 判断设置里是否选择了允许
            checkPeriPermission();
        }
    }
}

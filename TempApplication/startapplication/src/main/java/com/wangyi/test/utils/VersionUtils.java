package com.wangyi.test.tempapplication.utils;

import android.os.Build;

/*
 *  描述：    版本管理
 */
public class VersionUtils {

    //获取当前系统版本
    public static int getVersion() {
        return Build.VERSION.SDK_INT;
    }

    //是否大于4.3
    public static boolean isJELLY_BEAN_MR1() {
        if (getVersion() >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return true;
        }
        return false;
    }

    //是否大于5.0
    public static boolean isLollipop() {
        if (getVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        return false;
    }

    //是否大于6.0
    public static boolean isM() {
        if (getVersion() >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }
}

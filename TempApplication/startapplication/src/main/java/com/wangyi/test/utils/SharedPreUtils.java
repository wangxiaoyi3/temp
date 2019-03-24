package com.wangyi.test.tempapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *  描述：    SharedPreferences
 */
public class SharedPreUtils {

    private static final String SP_NAME = "config";

    /**
     * 存储string
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putString(Context mContext, String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, values);
        editor.commit();
    }

    /**
     * 读取string
     *
     * @param mContext
     * @param key
     * @param defValues
     * @return
     */
    public static String getString(Context mContext, String key, String defValues) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValues);
    }

    /**
     * 存储int
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putInt(Context mContext, String key, int values) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, values);
        editor.commit();
    }

    /**
     * 读取int
     *
     * @param mContext
     * @param key
     * @param defValues
     * @return
     */
    public static int getInt(Context mContext, String key, int defValues) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValues);
    }

    /**
     * 存储boolean
     *
     * @param mContext
     * @param key
     * @param values
     */
    public static void putBoolean(Context mContext, String key, boolean values) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    /**
     * 读取boolean
     *
     * @param mContext
     * @param key
     * @param defValues
     * @return
     */
    public static boolean getBoolean(Context mContext, String key, boolean defValues) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValues);
    }

    /**
     * 删除一条数据
     *
     * @param mContext
     * @param key
     */
    public static void deleKey(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 删除整个数据
     *
     * @param mContext
     * @param key
     */
    public static void deleAll(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}

package com.happy.beijingnews.urils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by happy on 2017/5/29.
 */

public class CacheUtil {
    /**
     * 获取是否进入主界面
     * @param context
     * @param key
     * @param Default
     * @return
     */
    public static boolean getBoolean(Context context, String key,boolean Default){
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        return sp.getBoolean(key,Default);
    }

    /**
     * 设置是否进入主界面
     * @param context
     * @param key
     * @param isBoolean
     */
    public static void putBoolean(Context context, String key,boolean isBoolean) {
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,isBoolean).commit();
    }

    /**
     * 缓存网络请求数据
     * @param context
     * @param key
     */
    public static void putString(Context context, String key,String value) {
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     * 获取缓存请求数据方法
     * @param context
     * @param key
     * @param def 默认值
     * @return
     */
    public static String getString(Context context, String key, String def) {
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        return sp.getString(key,def);
    }
}

package com.happy.beijingnews.urils;

import android.app.Application;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by happy on 2017/5/30.
 */

public class BeijingNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}

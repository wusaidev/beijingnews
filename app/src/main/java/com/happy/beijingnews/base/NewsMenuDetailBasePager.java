package com.happy.beijingnews.base;

import android.content.Context;
import android.view.View;

import com.happy.beijingnews.pagers.NewsCenterPager;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：新闻中心页面中  的各个详情pager基类（新闻  专题  组图  互动）
 * Created by happy on 2017/5/31.
 */

public abstract class NewsMenuDetailBasePager {
    public Context context;
    public View rootView;
    public NewsMenuDetailBasePager(Context context){
        this.context=context;
        rootView=initView();
    }
    public abstract View initView();
    public abstract void initData();
}

package com.happy.beijingnews.newsmenupager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.happy.beijingnews.base.NewsMenuDetailBasePager;
import com.happy.beijingnews.urils.LogUtil;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：互动详情页面
 * Created by happy on 2017/5/31.
 */

public class InteracDetailPager extends NewsMenuDetailBasePager {

    private TextView textContent;

    public InteracDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        //添加主页面内容;
        textContent = new TextView(context);

        return textContent;
    }

    @Override
    public void initData() {
        LogUtil.e("NewsCenterDetailPager","互动详情页面被创建了");
        textContent.setText("互动详情页面内容");
        textContent.setTextColor(Color.RED);
        textContent.setGravity(Gravity.CENTER);
        textContent.setTextSize(25);
    }
}

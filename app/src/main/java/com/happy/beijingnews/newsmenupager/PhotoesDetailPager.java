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
 * 作用：组图详情页面
 * Created by happy on 2017/5/31.
 */

public class PhotoesDetailPager extends NewsMenuDetailBasePager {
    public PhotoesDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        //添加主页面内容;
        TextView textContent=new TextView(context);
        textContent.setText("组图详情页面内容");
        textContent.setTextColor(Color.RED);
        textContent.setGravity(Gravity.CENTER);
        textContent.setTextSize(25);
        return textContent;
    }

    @Override
    public void initData() {
        LogUtil.e("NewsCenterDetailPager","组图详情页面被创建了");
    }
}

package com.happy.beijingnews.pagers;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.happy.beijingnews.base.BasePager;
import com.happy.beijingnews.urils.LogUtil;

/**
 * 作者：wusai
 * QQ:2713183194
 * Created by happy on 2017/5/30.
 */

public class SmartServicePager extends BasePager {
    /**
     * @param context rootView  返回各個pager頁面方便調用
     */
    public SmartServicePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        return super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("SmartServicePager","智慧服务内容被创建了");

        //设置标题名
        tv_title.setText("智慧服务");
        //添加智慧服务内容;
        TextView textContent=new TextView(context);
        textContent.setText("智慧服务内容");
        textContent.setTextColor(Color.RED);
        textContent.setGravity(Gravity.CENTER);
        textContent.setTextSize(25);
        fl_basepager_content.addView(textContent);

    }
}

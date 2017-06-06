package com.happy.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.happy.beijingnews.R;
import com.happy.beijingnews.activity.MainActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：wusai
 * QQ:2713183194
 * Created by happy on 2017/5/30.
 */

public class BasePager {
    /**
     * 代表各個不同的頁面
     */
    public View rootView;
    public Context context;
    /**
     * 標題
     */
    public TextView tv_title;
    /**
     * 菜單按鈕
     */
    public ImageButton ib_title_menu;
    /**
     * pager內容幀佈局
     */
    public FrameLayout fl_basepager_content;

    /**
     * @param context
     * rootView  返回各個pager頁面方便調用
     */
    public BasePager(Context context){
        this.context=context;
        rootView=initView();
    }

    /**
     * 初始化子类pager中公共部分view方法
     * @return
     */
    public View initView(){
        View view=View.inflate(context, R.layout.view_basepager,null);
        tv_title = (TextView) view.findViewById(R.id.tv_basepager_title);
        ib_title_menu = (ImageButton) view.findViewById(R.id.ib_basepager_title_menu);
        fl_basepager_content = (FrameLayout) view.findViewById(R.id.fl_basepager_content);
        ib_title_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });
        return view;
    }

    /**
     * 初始化子类请求的数据
     */
    public void initData(){

    }
}

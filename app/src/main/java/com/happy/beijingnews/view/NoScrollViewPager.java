package com.happy.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：wusai
 * QQ:2713183194
 * Created on 2017/5/30.
 * 作用自定义ViewPager 消费掉触摸事件，禁止滑动，
 * 禁止拦截触摸事件
 */

public class NoScrollViewPager extends ViewPager {
    /**
     * 在代码中实例化 调用方法
     * @param context
     */
    public NoScrollViewPager(Context context) {
        super(context);
    }

    /**
     * 在不居中引用盖雷调用方法，不可少，否则崩溃
     * @param context
     * @param attrs
     */
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 消费掉触摸事件方法
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 拦截触摸事件方法，返回false  不再拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}

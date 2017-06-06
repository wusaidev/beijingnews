package com.happy.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static org.xutils.http.HttpMethod.MOVE;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/3.
 */

public class HorizontalScrollViewPager extends ViewPager {

    private float startX;
    private float startY;

    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 自己处理触摸事件方法
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //禁止父类拦截触摸事件方法
        //getParent().requestDisallowInterceptTouchEvent(true);
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX=ev.getX();
                float endY=ev.getY();
                float distanceX=endX- startX;
                float distanceY=endY- startY;
                if(Math.abs(distanceX)>Math.abs(distanceY)){
                    if (getCurrentItem()==0&&distanceX>0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(getCurrentItem()==(getAdapter().getCount()-1)&&distanceX<0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }
                startX=endX;
                startY=endY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

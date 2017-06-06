package com.happy.beijingnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.happy.beijingnews.base.BasePager;

import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：ContentFragmentPager适配器
 * Created by happy on 2017/5/31.
 */

public class ContentFragmentPagerAdapter extends PagerAdapter {
    private final List<BasePager> pagerList;

    public ContentFragmentPagerAdapter(List<BasePager> pagerList){
        this.pagerList=pagerList;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager = pagerList.get(position);
        //屏蔽预加载时  加载数据 在页面被选中时再调用
        //pager.initData();
        View rootView = pager.rootView;
        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}


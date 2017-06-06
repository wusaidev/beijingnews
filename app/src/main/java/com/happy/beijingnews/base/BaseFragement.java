package com.happy.beijingnews.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by happy on 2017/5/30.
 */

public abstract class BaseFragement extends Fragment {
    public Context context;
    /**
     * 当fragment被创建的时候回调
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    /**
     * 当驶入被创建的时候回调
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 让孩子自己实现自己的视图，达到自己的效果
     * @return
     */
    public abstract View initView();

    /**
     * 当Activity被创建后 回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 1 如果子页面没有数据，联网请求数据，并且绑定到initView初始化视图上
     * 2  绑定到initView初始化视图上
     */
    public void initData() {
    }
}

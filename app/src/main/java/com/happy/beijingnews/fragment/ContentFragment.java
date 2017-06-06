package com.happy.beijingnews.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.happy.beijingnews.adapter.ContentFragmentPagerAdapter;
import com.happy.beijingnews.R;
import com.happy.beijingnews.activity.MainActivity;
import com.happy.beijingnews.base.BaseFragement;
import com.happy.beijingnews.base.BasePager;
import com.happy.beijingnews.pagers.GovaffairPager;
import com.happy.beijingnews.pagers.HomePager;
import com.happy.beijingnews.pagers.NewsCenterPager;
import com.happy.beijingnews.pagers.SettingPager;
import com.happy.beijingnews.pagers.SmartServicePager;
import com.happy.beijingnews.urils.LogUtil;
import com.happy.beijingnews.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by happy on 2017/5/30.
 */

public class ContentFragment extends BaseFragement {
    public String tag = "ContentFragment";
    //2 初始化控件 (不用再findViewById)
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewPager;
    @ViewInject(R.id.rg_main_framentcontent)
    private RadioGroup rg_main;

    private List<BasePager> pagerList;
    private MainActivity mainActivity;

    @Override
    public View initView() {
        LogUtil.e(tag, "正文Fragment视图视图视图被初始化了");
        View view = View.inflate(context, R.layout.view_main_fragmentcontent, null);
        //1  将视图注入到框架中  让contentFragment和view关联起来
        x.view().inject(ContentFragment.this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e(tag, "正文Fragment数据数据数据被初始化了");
        //初始化五个pager 存到List中  被pagerAdapter使用
        pagerList = new ArrayList<BasePager>();
        pagerList.add(new HomePager(context));
        pagerList.add(new NewsCenterPager(context));
        pagerList.add(new SmartServicePager(context));
        pagerList.add(new GovaffairPager(context));
        pagerList.add(new SettingPager(context));
        viewPager.setAdapter(new ContentFragmentPagerAdapter(pagerList));
        viewPager.addOnPageChangeListener(new MainOnPageChangeListener());
        rg_main.check(R.id.rb_main_framentcontent_home);
        pagerList.get(0).initData();
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
        rg_main.setOnCheckedChangeListener(new MainRadioGroupListener());
    }

    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) pagerList.get(1);
    }

    /**
     * viewpager 页面改变监听
     */
    class MainOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //页面被选中  加载数据
            pagerList.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * rg_main点击监听
     */
    class MainRadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_framentcontent_home:
                    viewPager.setCurrentItem(0, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_main_framentcontent_newscenter:
                    viewPager.setCurrentItem(1, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_main_framentcontent_smartserver:
                    viewPager.setCurrentItem(2, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_main_framentcontent_govaffair:
                    viewPager.setCurrentItem(3, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_main_framentcontent_setting:
                    viewPager.setCurrentItem(4, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 设置当前pager是否可以滑动方法
     * @param touchMode 滑动模式
     */
    private void isEnableSlidingMenu(int touchMode) {
        mainActivity = (MainActivity) context;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.setTouchModeAbove(touchMode);
    }
}

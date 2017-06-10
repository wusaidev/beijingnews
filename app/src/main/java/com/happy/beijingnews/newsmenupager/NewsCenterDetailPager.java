package com.happy.beijingnews.newsmenupager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.happy.beijingnews.R;
import com.happy.beijingnews.activity.MainActivity;
import com.happy.beijingnews.base.NewsMenuDetailBasePager;
import com.happy.beijingnews.domain.NewsCenterPagerBean2;
import com.happy.beijingnews.newsmenupager.newstabledetailpager.TabDetailPager;
import com.happy.beijingnews.urils.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：新闻中心详情页面
 * Created by happy on 2017/5/31.
 */

public class NewsCenterDetailPager extends NewsMenuDetailBasePager {
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    //实例化tablepagerIndicator
    @ViewInject(R.id.tabl_layout)
    private TabLayout tabl_layout;
    @ViewInject(R.id.ib_table_next)
    private ImageButton ib_table_next;
    /**
     * 页签页面的初步数据的集合
     */
    List<NewsCenterPagerBean2.DataBean2.ChildrenBean2> childrenBean2List;
    /**
     * 页签页面的集合  页面
     */
    List<TabDetailPager> tablePagerList;
    public NewsCenterDetailPager(Context context, NewsCenterPagerBean2.DataBean2 dataBean2) {
        super(context);
        childrenBean2List=dataBean2.getChildren();
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.news_menu_detail_pager,null);
        x.view().inject(NewsCenterDetailPager.this,view);
        return view;
    }

    @Override
    public void initData() {
        LogUtil.e("NewsCenterDetailPager","新闻详情页面被初始化了。。。。");
        //准备新闻详情页面的数据
        tablePagerList=new ArrayList<>();
        //添加主页面内容;
        for(int i=0;i<childrenBean2List.size();i++){
            TabDetailPager tabDetailPager=new TabDetailPager(context,childrenBean2List.get(i));
            tablePagerList.add(tabDetailPager);
        }
        viewPager.setAdapter(new TabPagerAdapter());
        //关联viewPager与tabPageIndicator
        //tabl_layout.setViewPager(viewPager);
        tabl_layout.setupWithViewPager(viewPager);
        //设置Tab可以滑动，若不可滑动由于太挤文本无法显示
        tabl_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //以后监听viewPager  等操作用tablePagerIndicator
        ib_table_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        //监听viewPager只在第0 个页面时可以画出左侧菜单
        //tabl_layout.setOnPageChangeListener(new TabPageChangeListener());
        viewPager.addOnPageChangeListener(new TabPageChangeListener());
    }

    class TabPageChangeListener implements OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position==0){
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void isEnableSlidingMenu(int touchmodeMargin) {
        MainActivity mainActivity = (MainActivity) context;
        SlidingMenu slidingMenu=mainActivity.getSlidingMenu();
        slidingMenu.setTouchModeAbove(touchmodeMargin);
    }

    /**
     * tab页面适配器
     */
    class TabPagerAdapter extends PagerAdapter{
        @Override
        public CharSequence getPageTitle(int position) {
            return childrenBean2List.get(position).getTitle();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager=tablePagerList.get(position);
            View view=tabDetailPager.rootView;
            tabDetailPager.initData();//初始化数据
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return childrenBean2List.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}

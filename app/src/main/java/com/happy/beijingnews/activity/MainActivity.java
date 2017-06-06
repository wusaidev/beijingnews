package com.happy.beijingnews.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.FrameLayout;

import com.happy.beijingnews.R;
import com.happy.beijingnews.fragment.ContentFragment;
import com.happy.beijingnews.fragment.LeftMenuFragment;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.Dp2PxUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        initFragment();
    }

    /**
     * 初始化侧滑菜单方法
     */
    private void initSlidingMenu() {
        //1  设置主页面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //2  设置左侧菜单
        setBehindContentView(R.layout.activity_left_menu);
        //3  设置右侧菜单
        SlidingMenu slidingMenu=getSlidingMenu();
        //slidingMenu.setSecondaryMenu(R.layout.activity_right_menu);
        //4 设置模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5 设置滑动模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //6 设置这页面大小
        DisplayMetrics outmetrics=new DisplayMetrics();
        int ScreeWidth=outmetrics.widthPixels;
        slidingMenu.setBehindWidth(Dp2PxUtil.dip2px(getApplicationContext(), 150));
    }

    /**
     * 替换 主页面及菜单页面 布局方法
     */
    private void initFragment() {
        //1获取fragmentManager对象
        FragmentManager fm=getFragmentManager();
        //2开启一个fragmentmanager事务
        FragmentTransaction ft = fm.beginTransaction();
        //3事务 去替换布局
        ft.replace(R.id.fl_main,new ContentFragment(), ConstantValue.MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_left_menu,new LeftMenuFragment(), ConstantValue.LEFT_MENU_TAG);
        //4提交
        ft.commit();
    }

    /**
     * 获取左侧菜单方法，用于向左侧菜单传递数据
     * @return
     */
    public LeftMenuFragment getLeftMenuFragment() {
        //1获取fragmentManger对象
        FragmentManager fm=getFragmentManager();
        //2 根据tag,huoqudao leftmenuFragment
        LeftMenuFragment leftMenuFragment= (LeftMenuFragment) fm.findFragmentByTag(ConstantValue.LEFT_MENU_TAG);
        return leftMenuFragment;
    }

    public ContentFragment getContentFragment() {
        //1获取fragmentManger对象
        FragmentManager fm=getFragmentManager();
        //2 根据tag,huoqudao leftmenuFragment
        ContentFragment contentFragment= (ContentFragment) fm.findFragmentByTag(ConstantValue.MAIN_CONTENT_TAG);
        return contentFragment;
    }
}

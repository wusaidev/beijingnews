package com.happy.beijingnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.happy.beijingnews.R;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.LogUtil;
import com.happy.beijingnews.urils.Dp2PxUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by happy on 2017/5/29.
 */
public class GuideActivity extends Activity {
    private String tag = "GuideActivity";
    private ViewPager viewPager;
    private Button bt_start_main;
    private LinearLayout ll_point_group;
    private List<ImageView> imageList;
    private LinearLayout.LayoutParams params;
    private ImageView iv_point_red;
    /**
     * 两点的间距
     */
    private int pointDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initUI();
        initData();
    }

    /**
     * 初始化数据方法
     */
    private void initData() {

        //需要获取到三张图片的集合是ImageView 类型
        //可以一次添加，多的时候需要通过先获取ID再循环
        int[] imgIds = new int[]{R.drawable.guide_1,
                R.drawable.guide_2, R.drawable.guide_3};
        int widthPx= Dp2PxUtil.dip2px(getApplicationContext(),10);
        LogUtil.e(tag, widthPx+"px--------------");
        LogUtil.e(tag, imgIds[0] + "");
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(imgIds[i]);
            imageList.add(imageView);
            ImageView pointNor = new ImageView(getApplicationContext());
            pointNor.setBackgroundResource(R.drawable.shape_guide_point_nor);
            params = new LinearLayout.LayoutParams(widthPx, widthPx);
            if (i > 0) {
                params.leftMargin = widthPx;
            }
            ll_point_group.addView(pointNor,params);
        }
        iv_point_red.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListener());
        viewPager.addOnPageChangeListener(new MyPagerListener());
        LogUtil.e(tag, imageList.size() + "");
        viewPager.setAdapter(new MyPagerAdapter());

    }

    /**
     * 页面改变监听
     */
    class MyPagerListener implements ViewPager.OnPageChangeListener {
        /**
         * 页面滚动回调方法
         * @param position 当前位置
         * @param positionOffset 滚动屏幕百分比
         * @param positionOffsetPixels 滚动像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LogUtil.e(tag,position+"   "+positionOffset+"   "+positionOffsetPixels);
            //监听页面滑动，实时获取滑动百分比，百分比X两点间距得点的移动距离
            //获取点的params对象，用于设置margin
            int redLeftMargin= (int) ( position*pointDistance+(positionOffset*pointDistance));
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) iv_point_red.getLayoutParams();
            params.leftMargin=redLeftMargin;
            iv_point_red.setLayoutParams(params);
        } 

        /**
         * 选中页面回调方法
         * @param position 选中页面位置
         */
        @Override
        public void onPageSelected(int position) {
            if(position==imageList.size()-1){
                bt_start_main.setVisibility(View.VISIBLE);
            }else {
                bt_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 滚动状态改变回调方法
         * @param state 滚动状态：1.拖  2.静  3.惯性滚动
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    /**
     * 红点创建时的监听
     */
    class MyGlobalListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            iv_point_red.getViewTreeObserver().removeGlobalOnLayoutListener(MyGlobalListener.this);
            //得到两点间距
            pointDistance = ll_point_group.getChildAt(1).getLeft()
                    - ll_point_group.getChildAt(0).getLeft();
        }
    }

    /**
     * 初始化UI方法
     */
    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bt_start_main = (Button) findViewById(R.id.bt_guide_start_main);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_guide_point_group);
        iv_point_red = (ImageView) findViewById(R.id.iv_guide_point_red);
        bt_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1 记录进入过此应用，下次不需要进入引导页面
                CacheUtil.putBoolean(getApplicationContext(), ConstantValue.START_MAIN,true);
                //2跳转到主页面
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                //3 结束此页面
                finish();
            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        /**
         * @param container
         * @param position
         * @return 返回和创建当前页面右关系值
         */
        //getview
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageList.get(position);
            container.addView(imageView);
            return imageView;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}

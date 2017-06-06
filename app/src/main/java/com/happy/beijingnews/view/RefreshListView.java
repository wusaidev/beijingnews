package com.happy.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.happy.beijingnews.R;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/3.
 */

public class RefreshListView extends ListView {

    private View view_pull_refresh_header;
    private LinearLayout ll_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_refresh_status;
    private TextView tv_refresh_time;
    private int pullRefreshViewHeight;
    private float startY = -1;
    public static final int PULL_DOWN_REFRESH = 0;
    public static final int RELEASE_REFRESH = 1;
    public static final int REFRESHING = 2;
    private int currentStatus = PULL_DOWN_REFRESH;
    private String refreshTime;
    /**
     * 加载更多控件
     */
    private View footerView;
    /**
     * 加载更多控件的高
     */
    private int footerViewHeight;
    /**
     * 是否已经加载更多
     */
    private boolean isLoadMore;
    private LinearLayout headerView;
    private int listViewOnScreenY=-1;
    private int topNewsViewY;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    private void initFooterView(Context context) {
        footerView = View.inflate(context, R.layout.view_refresh_footer,null);
        footerView.measure(0,0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0,-footerViewHeight,0,0);
        //listView 添加footer
        addFooterView(footerView);

        //监听listView
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //当静止  或者惯性滚动的时候
                if(scrollState==OnScrollListener.SCROLL_STATE_IDLE||scrollState==OnScrollListener.SCROLL_STATE_FLING){
                    if (getLastVisiblePosition()>=getCount()-2){
                        //1 显示加载更多布局
                        footerView.setPadding(8,8,8,8);
                        //2  状态该表
                        isLoadMore = true;
                        //3  回调借口
                        if(mOnRefreshListener!=null){
                            mOnRefreshListener.onLoadMore();
                        }
                    }
                }
                // 并且是最后一条可见

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private Animation upAnimation;
    private Animation downAnimation;

    /**
     * 初始化动画方法
     */
    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        //停留在播放完成后的状态
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
        view_pull_refresh_header = View.inflate(context, R.layout.view_pull_refresh_header, null);
        view_pull_refresh_header.measure(0, 0);
        headerView = (LinearLayout) view_pull_refresh_header.findViewById(R.id.header_view);
        pullRefreshViewHeight = view_pull_refresh_header.getMeasuredHeight();
        view_pull_refresh_header.setPadding(0, -pullRefreshViewHeight, 0, 0);
        ll_refresh = (LinearLayout) view_pull_refresh_header.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) view_pull_refresh_header.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) view_pull_refresh_header.findViewById(R.id.pb_status);
        tv_refresh_status = (TextView) view_pull_refresh_header.findViewById(R.id.tv_refresh_status);
        tv_refresh_time = (TextView) view_pull_refresh_header.findViewById(R.id.tv_refresh_time);
        addHeaderView(view_pull_refresh_header);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = ev.getY();
                }
                //判断顶部轮播图是否完全显示
                boolean isDisplayTopNews=isDisplayTopNews();
                LogUtil.e("是否完全显示",isDisplayTopNews()+"");
                //if(!isDisplayTopNews){
                    //break;
                //}
                //如果是在刷新 就不允许再刷新
                if (currentStatus == REFRESHING) {
                    break;
                }
                float endY = ev.getY();
                float deistanceY = endY - startY;
                int paddingTop = (int) (-pullRefreshViewHeight + deistanceY);
                if (deistanceY > 0) {
                    if (paddingTop < 0 && currentStatus != PULL_DOWN_REFRESH) {
                        //下拉刷新状态
                        currentStatus = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewState();
                    } else if (paddingTop > 0 && currentStatus != RELEASE_REFRESH) {
                        //释放刷新状态
                        currentStatus = RELEASE_REFRESH;
                        //更新状态
                        refreshViewState();
                    }
                }
                view_pull_refresh_header.setPadding(0, paddingTop, 0, 0);
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentStatus == PULL_DOWN_REFRESH) {
                    view_pull_refresh_header.setPadding(0, -pullRefreshViewHeight, 0, 0);
                } else if (currentStatus == RELEASE_REFRESH) {
                    currentStatus = REFRESHING;
                    view_pull_refresh_header.setPadding(0, 0, 0, 0);
                    //更新状态
                    refreshViewState();
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isDisplayTopNews() {
        if(topNewsView!=null){
            //1 得到listview在屏幕上的坐标
            int[] location=new int[2];
            if(listViewOnScreenY ==-1){
                getLocationOnScreen(location);
                listViewOnScreenY =location[1];
            }
            return listViewOnScreenY <=topNewsViewY;
        }
        return true;
    }

    private void refreshViewState() {
        switch (currentStatus) {
            case PULL_DOWN_REFRESH:
                iv_arrow.startAnimation(downAnimation);
                tv_refresh_status.setText("下拉刷新...");
                break;
            case RELEASE_REFRESH:
                iv_arrow.startAnimation(upAnimation);
                tv_refresh_status.setText("手松刷新...");
                break;
            case REFRESHING:
                tv_refresh_status.setText("正在刷新...");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;
        }
    }

    //  当联网成功和失败的时候回调该方法
    // 用于刷新状态的还原   记录什么时候请求的数据
    public void onRefreshFinish(boolean success) {
        if(isLoadMore){
            isLoadMore=false;
            //隐藏加载更多布局
            footerView.setPadding(0,-footerViewHeight,0,0);
        }else {
            tv_refresh_status.setText("下拉刷新...");
            currentStatus = PULL_DOWN_REFRESH;
            iv_arrow.clearAnimation();
            pb_status.setVisibility(GONE);
            iv_arrow.setVisibility(VISIBLE);
            //隐藏下拉刷新控件
            view_pull_refresh_header.setPadding(0, -pullRefreshViewHeight, 0, 0);
            if (success) {
                refreshTime = CacheUtil.getString(getContext(), ConstantValue.REFRESH_TIME, "2017-6-5");
                //设置最新的更新时间
                tv_refresh_time.setText("上次更新时间：" + refreshTime);
                getSystemTime();
            }
        }

    }

    /**
     * 得到安卓系统的时间
     *
     * @return
     */
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        refreshTime = format.format(new Date());
        CacheUtil.putString(getContext(), ConstantValue.REFRESH_TIME, refreshTime);
        LogUtil.e("更新时间", refreshTime);
        return refreshTime;
    }
    private View topNewsView;
    /**
     *代表顶部轮播图
     */
    public void addTopNewsView(View topNewsView) {
        this.topNewsView=topNewsView;
        headerView.addView(topNewsView);
        int[] location=new int[2];
        topNewsView.getLocationOnScreen(location);
        topNewsViewY = location[1];

    }

    /**
     * 监听控件的刷新
     */
    public interface OnRefreshListener {
        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDownRefresh();

        /**
         * 上拉加载更多回调此方法
         */
        public void onLoadMore();
    }


    private OnRefreshListener mOnRefreshListener;

    /**
     * 设置监听刷新，由 外界调用
     *
     * @param l
     */
    public void setOnRefreshListener(OnRefreshListener l) {
        this.mOnRefreshListener = l;
    }
}

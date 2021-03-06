package com.happy.beijingnews.newsmenupager.newstabledetailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.happy.beijingnews.R;
import com.happy.beijingnews.base.NewsMenuDetailBasePager;
import com.happy.beijingnews.domain.NewsCenterPagerBean2;
import com.happy.beijingnews.domain.TabDetailPagerBean;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.LogUtil;
import com.happy.beijingnews.urils.ToastUtil;
import com.happy.beijingnews.view.HorizontalScrollViewPager;
import com.happy.beijingnews.view.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/6.
 */

public class TopicDetailPager extends NewsMenuDetailBasePager {

    private String tag="TabDetailPager";
    private ImageOptions imageOptions;
    private final NewsCenterPagerBean2.DataBean2.ChildrenBean2 childrenData;
    private HorizontalScrollViewPager viewPager;
    private TextView tv_top_news_title;
    private LinearLayout ll_point_group;
    private ListView lv_news_list;
    private int prePosition;
    private List<TabDetailPagerBean.DataBean.TopnewsData> topnewsList;
    private List<TabDetailPagerBean.DataBean.NewsData> newsList;
    private String moreUrl;
    private boolean isLoadMore;
    private TopicDetailPager.NewsAdapter newsAdapter;
    private PullToRefreshListView mPullRefreshListView;

    public TopicDetailPager(Context context, NewsCenterPagerBean2.DataBean2.ChildrenBean2 childrenData) {
        super(context);
        this.childrenData=childrenData;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(90), DensityUtil.dip2px(90))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.view_topicdetail_pager,null);
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        lv_news_list=mPullRefreshListView.getRefreshableView();

        //刷新声音
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(context);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);

        View newslist_head=View.inflate(context,R.layout.view_newslist_head,null);
        viewPager = (HorizontalScrollViewPager) newslist_head.findViewById(R.id.viewpager);
        tv_top_news_title = (TextView) newslist_head.findViewById(R.id.tv_topnews_title);
        ll_point_group = (LinearLayout) newslist_head.findViewById(R.id.ll_point_group);
        lv_news_list.addHeaderView(newslist_head);
        //lv_news_list.addTopNewsView(newslist_head);
        //设置监听下拉刷新
        //lv_news_list.setOnRefreshListener(new TopicDetailPager.MyOnRefreshListener());
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                ToastUtil.show(context,"下拉刷新被回调了");
                getDataFromNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                ToastUtil.show(context,"加载更多刷新被回调了");
                if(TextUtils.isEmpty(moreUrl)){
                    ToastUtil.show(context,"没有更多数据");
                    //lv_news_list.onRefreshFinish(false);
                    mPullRefreshListView.onRefreshComplete();
                }else {
                    getMoreDataFromNet();
                }
            }
        });
        return view;
    }
    /*class MyOnRefreshListener implements RefreshListView.OnRefreshListener{

        @Override
        public void onPullDownRefresh() {
            ToastUtil.show(context,"下拉刷新被回调了");
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {
            ToastUtil.show(context,"加载更多刷新被回调了");
            if(TextUtils.isEmpty(moreUrl)){
                ToastUtil.show(context,"没有更多数据");
                lv_news_list.onRefreshFinish(false);
            }else {
                getMoreDataFromNet();
            }
        }
    }*/

    private void getMoreDataFromNet() {
        RequestParams params=new RequestParams(moreUrl);
        LogUtil.e( "加载更多请求地址" ,moreUrl);
        x.http().get(params,new  Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                LogUtil.e(tag, "联网请求成功了：" + result);
                //一定要放在前面
                isLoadMore = true;
                processData(result);
                //lv_news_list.onRefreshFinish(false);
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(tag, "联网请求出错了：" + ex.getMessage());
//                lv_news_list.onRefreshFinish(false);
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(tag, "联网请求出取消了：" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(tag, "联网请求完成了" );
            }
        });
    }

    @Override
    public void initData() {
        LogUtil.e("TabDetailPager",childrenData.getTitle()+"页面请求地址："
                + ConstantValue.BASE_URL+childrenData.getUrl());
        String cacheData= CacheUtil.getString(context,ConstantValue.BASE_URL+childrenData.getUrl(),"");
        if(!TextUtils.isEmpty(cacheData)){
            processData(cacheData);
        }
        getDataFromNet();

    }

    /**
     * viewPager的监听类
     */
    class TopPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            ll_point_group.getChildAt(position).setEnabled(true);
            tv_top_news_title.setText(topnewsList.get(position).getTitle());
            prePosition=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    /**
     * 从网络获取数据方法
     */
    public void getDataFromNet() {
        RequestParams params= new RequestParams(ConstantValue.BASE_URL+childrenData.getUrl());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(tag, "联网请求成功了：" + result);
                CacheUtil.putString(context,ConstantValue.BASE_URL+childrenData.getUrl(),result);
                processData(result);
                //隐藏下拉刷新控件-更新时间-重新显示数据
//                lv_news_list.onRefreshFinish(true);
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(tag, "联网请求出错了：" + ex.getMessage());
                //隐藏下拉刷新控件，不更新  之隐藏
//                lv_news_list.onRefreshFinish(false);
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(tag, "联网请求取消了：" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(tag, "联网请求完成了");
            }
        });
    }

    /**
     * 处理数据方法
     * @param result
     */
    private void processData(String result) {
        TabDetailPagerBean bean=parseWithGson(result);
        LogUtil.e(tag, bean.getData().getNews().get(0).getTitle());
        moreUrl="";
        if(TextUtils.isEmpty(bean.getData().getMore())){
            moreUrl="";
        }else {
            moreUrl=ConstantValue.BASE_URL+bean.getData().getMore();
            LogUtil.e( "加载更多请求地址" ,moreUrl);
        }
        //默认和  加载更多
        if(!isLoadMore){
            topnewsList = bean.getData().getTopnews();
            //viewPager设置适配器
            viewPager.setAdapter(new TopicDetailPager.TopPagerAdapter());
            addPoint();
            tv_top_news_title.setText(topnewsList.get(prePosition).getTitle());
            //viewPager 加页面改变监听器
            viewPager.addOnPageChangeListener(new TopicDetailPager.TopPageChangeListener());
            //获取下方新闻列表数据
            newsList = bean.getData().getNews();
            //为lv  设置适配器
            newsAdapter = new TopicDetailPager.NewsAdapter();
            lv_news_list.setAdapter(newsAdapter);
        }else {
            //加载更多
            isLoadMore=false;
            //List<TabDetailPagerBean.DataBean.NewsData> moreNews = bean.getData().getNews();
            //添加原来集合中
            newsList.addAll(bean.getData().getNews());
            newsAdapter.notifyDataSetChanged();

        }
        //获取页签页面顶部轮播图数据

    }

    /**
     * 新闻列表适配器
     */
    class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TopicDetailPager.ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new TopicDetailPager.ViewHolder();
                convertView=View.inflate(context,R.layout.view_newslist_item,null);
                viewHolder.iv_list_img= (ImageView) convertView.findViewById(R.id.iv_list_img);
                viewHolder.iv_list_speak= (ImageView) convertView.findViewById(R.id.iv_list_speak);
                viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_refresh_time);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (TopicDetailPager.ViewHolder) convertView.getTag();
            }
            String imgUrl=ConstantValue.BASE_URL+newsList.get(position).getListimage();
            x.image().bind(viewHolder.iv_list_img,imgUrl);
            viewHolder.tv_title.setText(newsList.get(position).getTitle());
            viewHolder.tv_time.setText(newsList.get(position).getPubdate());
            return convertView;
        }
    }
    private class ViewHolder{
        ImageView iv_list_img;
        ImageView iv_list_speak;
        TextView tv_title;
        TextView tv_time;
    }
    /**
     * 添加点的方法
     */
    private void addPoint() {
        //processData执行两次（缓存  网络）需要移除原有point
        ll_point_group.removeAllViews();
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dip2px(5),DensityUtil.dip2px(5));

        if(topnewsList!=null&&topnewsList.size()>0){
            for (int i=0;i<topnewsList.size();i++){
                ImageView ponitImg=new ImageView(context);
                ponitImg.setBackgroundResource(R.drawable.selector_topnews_point);
                if(i==0){
                    ponitImg.setEnabled(true);
                }else {
                    ponitImg.setEnabled(false);
                    params.leftMargin= DensityUtil.dip2px(8);
                }
                ll_point_group.addView(ponitImg,params);
            }
        }
    }

    class TopPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(context);
            imageView.setBackgroundResource(R.drawable.news_pic_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl=ConstantValue.BASE_URL+topnewsList.get(position).getTopimage();
            LogUtil.e("图片请求地址","imageUrl");
            x.image().bind(imageView,imageUrl,imageOptions);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public int getCount() {
            return topnewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

    }
    /**
     * gson 解析json数据方法
     * @param result
     * @return
     */
    private TabDetailPagerBean parseWithGson(String result) {
        return new Gson().fromJson(result,TabDetailPagerBean.class);
    }
}

package com.happy.beijingnews.newsmenupager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.happy.beijingnews.R;
import com.happy.beijingnews.base.NewsMenuDetailBasePager;
import com.happy.beijingnews.domain.NewsCenterPagerBean2;
import com.happy.beijingnews.domain.PhotoesDetailPagerBean;
import com.happy.beijingnews.urils.BitmapCacheUtil;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.LogUtil;
import com.happy.beijingnews.urils.NetCacheUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：互动详情页面
 * Created by happy on 2017/5/31.
 */

public class InteracDetailPager extends NewsMenuDetailBasePager {

    private String tag="PhotoesDetailPager";
    private NewsCenterPagerBean2.DataBean2 dataBean2;
    private String url;
    private List<PhotoesDetailPagerBean.DataBean.NewsBean> newsList;
    private RequestQueue queue;
    private Bitmap requestBitMap;
    private PhotoesPagerAdapter adapter;
    private BitmapCacheUtil bitmapCacheUtil;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NetCacheUtil.SUCCESS://图片请求成功
                    int position=msg.arg1;
                    Bitmap bitmap= (Bitmap) msg.obj;
                    ImageView iv_img= (ImageView) listView.findViewWithTag(position);
                    if (listView.isShown()){
                        if(bitmap!=null&&iv_img!=null){
                            iv_img.setImageBitmap(bitmap);
                        }
                    }
                    if(gridView.isShown()){
                        if(bitmap!=null&&iv_img!=null){
                            iv_img.setImageBitmap(bitmap);
                        }
                    }

                    org.xutils.common.util.LogUtil.e("请求图片成功"+position);
                    break;
                case NetCacheUtil.FAIL://图片请求失败
                    position=msg.arg1;
                    org.xutils.common.util.LogUtil.e("请求图片失败"+position);
                    break;
            }
        }
    };

    public InteracDetailPager(Context context, NewsCenterPagerBean2.DataBean2 dataBean2) {
        super(context);
        this.dataBean2=dataBean2;
        url= ConstantValue.BASE_URL+dataBean2.getUrl();
        bitmapCacheUtil = new BitmapCacheUtil(mHandler);
    }
    @ViewInject(R.id.list_view)
    private ListView listView;
    @ViewInject(R.id.grid_view)
    private GridView gridView;
    @Override
    public View initView() {
        //添加主页面内容;
        View view=View.inflate(context, R.layout.interac_menu_detail_pager,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        LogUtil.e(tag,"组图详情页面被创建了");
        String cacheData= CacheUtil.getString(context, url, "");
        if(!TextUtils.isEmpty(cacheData)){
            processData(cacheData);
        }
        getDataFromNet();
    }
    private void getDataFromNet() {
        LogUtil.e(tag,url);
        RequestParams params = new RequestParams(url);
        LogUtil.e(tag,"组图详情页面被创建了");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(tag, "联网请求成功了：" + result);
                CacheUtil.putString(context, url, result);

                processData(result);
                //initDefaultDetailPager();此方法会导致切换主页面  智慧 政要 设置后无法记忆已选择的新闻中心中的详情页面（被初始化）

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(tag, "联网请求失败了：" + ex.getMessage());
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
     *
     * @param json
     */
    private void processData(String json) {
        PhotoesDetailPagerBean bean = parsedJsonWithGSON(json);
        String title = bean.getData().getNews().get(0).getTitle();
        LogUtil.e(tag, "title==" + title);
        newsList = bean.getData().getNews();
        adapter = new PhotoesPagerAdapter();
        listView.setAdapter(adapter);
    }

    private PhotoesDetailPagerBean parsedJsonWithGSON(String json) {
        return new Gson().fromJson(json, PhotoesDetailPagerBean.class);
    }

    /**
     * true显示ListView
     * false 显示GridView
     */
    private boolean isShowListView=true;
    /**
     * 开关  ListView  GridView 方法
     * @param ib_change_format
     */
    public void switchListAndGrid(ImageButton ib_change_format) {
        if(isShowListView){
            isShowListView=false;
            listView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            listView.setAdapter(adapter);
            ib_change_format.setImageResource(R.drawable.icon_pic_grid_type);
        }else {
            isShowListView=true;
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(adapter);
            ib_change_format.setImageResource(R.drawable.icon_pic_list_type);
        }
    }

    class PhotoesPagerAdapter extends BaseAdapter {

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
            ViewHolder viewHolder;
            if(convertView==null){
                //convertView=View.inflate(context,R.layout.view_leftmenu_item,null);
                convertView=View.inflate(context,R.layout.view_photoes_item,null);
                viewHolder=new ViewHolder();
                viewHolder.iv_img= (ImageView) convertView.findViewById(R.id.iv_img);
                viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_title.setText(newsList.get(position).getTitle());

            String imgUrl=ConstantValue.BASE_URL+newsList.get(position).getSmallimage();

            //x.image().bind(viewHolder.iv_img,imgUrl);
            viewHolder.iv_img.setTag(position);
            Bitmap bitmap= bitmapCacheUtil.getBitmap(imgUrl,position);
            if (bitmap!=null){
                viewHolder.iv_img.setImageBitmap(bitmap);
            }
            return convertView;
        }


    }
    class ViewHolder{
        ImageView iv_img;
        TextView tv_title;
    }
}

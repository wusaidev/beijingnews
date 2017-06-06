package com.happy.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.happy.beijingnews.R;
import com.happy.beijingnews.activity.MainActivity;
import com.happy.beijingnews.base.BaseFragement;
import com.happy.beijingnews.domain.NewsCenterPagerBean2;
import com.happy.beijingnews.pagers.NewsCenterPager;
import com.happy.beijingnews.urils.Dp2PxUtil;
import com.happy.beijingnews.urils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by happy on 2017/5/30.
 */

public class LeftMenuFragment extends BaseFragement {
    public String tag="LeftMenuFragment";
    private List<NewsCenterPagerBean2.DataBean2> data2List;
    private ListView listView;
    private List<String> titleList=new ArrayList<>();
    private int prePosition;
    private LeftMenuListAdapter leftMenuListAdapter;

    @Override
    public View initView() {
        LogUtil.e(tag,"左侧菜单Fragment视图视图视图被初始化了");
        listView = new ListView(context);
        listView.setPadding(0, Dp2PxUtil.dip2px(context,30),0,0);
        listView.setDividerHeight(0);
        //按下listview  item不变色
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prePosition=position;
                leftMenuListAdapter.notifyDataSetChanged();
                MainActivity mainActivity= (MainActivity) context;
                //点击后关闭slidingMenu  toggle(),原来开启-->关闭  ，原来关闭-->开启
                mainActivity.getSlidingMenu().toggle();
                //切换页面流程获取到MainActivity-->获取到ContentFragment
                // -->获取到NewsCenterPager-->获取到内容部分framLayout
                //-->移除原View，添加Detail页面
                switchPager (prePosition);


            }
        });
        return listView;
    }

    /**
     * 更改newsCenterPager 详情页面的方法
     * @param prePosition 用于显示并记录选择的页面
     */
    private void switchPager(int prePosition) {
        MainActivity mainActivity= (MainActivity) context;
        ContentFragment contentFragment=mainActivity.getContentFragment();
        NewsCenterPager newsCenterPager=contentFragment.getNewsCenterPager();
        newsCenterPager.switchPagerAndTitle(prePosition);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e(tag,"左侧菜单Fragment数据数据数据被初始化了");
    }

    public void setData(List<NewsCenterPagerBean2.DataBean2> dataBean2List) {
        this.data2List=dataBean2List;
        titleList.clear();
        for (int i=0;i<data2List.size();i++){
            LogUtil.e(tag,"title=="+data2List.get(i).getTitle());
            titleList.add(data2List.get(i).getTitle());
        }
        leftMenuListAdapter = new LeftMenuListAdapter();
        listView.setAdapter(leftMenuListAdapter);
        newsCenterDefaultPager(data2List);

    }

    /**
     * 设置新闻中心默认详情页面方法
     * @param dataBean2List
     */
    private void newsCenterDefaultPager(List<NewsCenterPagerBean2.DataBean2> dataBean2List) {
        String title=dataBean2List.get(prePosition).getTitle();
        MainActivity mainActivity= (MainActivity) context;
        ContentFragment contentFragment=mainActivity.getContentFragment();
        contentFragment.getNewsCenterPager().setDefaultDetailPager(prePosition,title);
    }

    class LeftMenuListAdapter extends BaseAdapter {
        /*private final List<String> titleList;
        private Context context;
        private int prePosition;
        public LeftMenuListAdapter(List<String> titleList,Context context,int prePosition){
            this.titleList=titleList;
            this.context=context;
            this.prePosition=prePosition;
        }*/
        @Override
        public int getCount() {
            LogUtil.e("LeftMenuListAdapter","数目："+titleList.size());
            return titleList.size();
        }

        @Override
        public Object getItem(int position) {
            return titleList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LogUtil.e("LeftMenuListAdapter","getView方法："+"position"+position+"  prePosetion"+prePosition);
            TextView textView= (TextView) View.inflate(context, R.layout.view_leftmenu_item,null);
            textView.setText(titleList.get(position));
            textView.setEnabled(position==prePosition);
            return textView;
        }
    }
}

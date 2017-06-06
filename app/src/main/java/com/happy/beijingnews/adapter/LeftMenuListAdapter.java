package com.happy.beijingnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.happy.beijingnews.R;
import com.happy.beijingnews.urils.LogUtil;

import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/5/31.
 */

public class LeftMenuListAdapter extends BaseAdapter {
    private final List<String> titleList;
    private Context context;
    private int prePosition;
    public LeftMenuListAdapter(List<String> titleList,Context context,int prePosition){
        this.titleList=titleList;
        this.context=context;
        this.prePosition=prePosition;
    }
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
        TextView textView= (TextView) View.inflate(context,R.layout.view_leftmenu_item,null);
        textView.setText(titleList.get(position));
        textView.setEnabled(position==prePosition);
        return textView;
    }
}

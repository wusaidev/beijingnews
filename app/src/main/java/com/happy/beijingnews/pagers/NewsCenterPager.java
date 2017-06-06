package com.happy.beijingnews.pagers;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.happy.beijingnews.activity.MainActivity;
import com.happy.beijingnews.base.BasePager;
import com.happy.beijingnews.base.NewsMenuDetailBasePager;
import com.happy.beijingnews.domain.NewsCenterPagerBean;
import com.happy.beijingnews.domain.NewsCenterPagerBean2;
import com.happy.beijingnews.fragment.LeftMenuFragment;
import com.happy.beijingnews.newsmenupager.InteracDetailPager;
import com.happy.beijingnews.newsmenupager.NewsCenterDetailPager;
import com.happy.beijingnews.newsmenupager.PhotoesDetailPager;
import com.happy.beijingnews.newsmenupager.TopicDetailPager;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wusai
 * QQ:2713183194
 * Created by happy on 2017/5/30.
 */

public class NewsCenterPager extends BasePager {
    private String tag = "NewsCenterPager";
    private List<NewsCenterPagerBean2.DataBean2> dataBean2List;
    private List<NewsMenuDetailBasePager> newsDetailPagerList;

    /**
     * @param context rootView  返回各個pager頁面方便調用
     */
    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {

        return super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("NewsCenterPager", "新闻中心内容被创建了");

        //设置标题名
        tv_title.setText("新闻中心");
        //添加新闻中心内容;
        TextView textContent = new TextView(context);
        textContent.setText("新闻中心内容");
        textContent.setTextColor(Color.RED);
        textContent.setGravity(Gravity.CENTER);
        textContent.setTextSize(25);
        fl_basepager_content.addView(textContent);
        ib_title_menu.setVisibility(View.VISIBLE);


        String cacheResult = CacheUtil.getString(context, ConstantValue.NEWSCENTER_PAGER_URL, "");
        if (!TextUtils.isEmpty(cacheResult)) {
            processData(cacheResult);
        }
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(ConstantValue.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(tag, "联网请求成功了：" + result);

                CacheUtil.putString(context, ConstantValue.NEWSCENTER_PAGER_URL, result);

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
     * 网络请求成功后初始化默认显示详情页面（新闻详情）
     *
     * @param prePosition
     * @param title
     */
    public void setDefaultDetailPager(int prePosition, String title) {
        tv_title.setText(title);
        fl_basepager_content.removeAllViews();
        fl_basepager_content.addView(newsDetailPagerList.get(prePosition).rootView);
        newsDetailPagerList.get(prePosition).initData();
    }

    /**
     * 处理数据方法
     *
     * @param json
     */
    private void processData(String json) {
        NewsCenterPagerBean2 bean2 = parsedJsonWithJSONObject(json);
        String title = bean2.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e(tag, "title==" + title);
        dataBean2List = bean2.getData();
        /*//菜单页面获取不到数据试验
        for (int i=0;i<dataBeanList.size();i++){
            LogUtil.e(tag,"title=="+dataBeanList.get(i).getTitle());
        }*/
        //新闻中心中各个详情页面的集合
        newsDetailPagerList = new ArrayList<>();
        newsDetailPagerList.add(new NewsCenterDetailPager(context,bean2.getData().get(0)));//添加新闻详情页面
        newsDetailPagerList.add(new TopicDetailPager(context));//添加 专题详情页面
        newsDetailPagerList.add(new PhotoesDetailPager(context));//添加 组图详情页面
        newsDetailPagerList.add(new InteracDetailPager(context));//添加 互动详情页面
        sendData2LeftMenu();//dataBeanList赋值后为全局变量，不用再传入

    }

    /**
     * 发送dataBeanList到侧滑菜单方法
     */
    private void sendData2LeftMenu() {
        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setData(dataBean2List);
    }

    private NewsCenterPagerBean2 parsedJsonWithJSONObject(String json) {
        //Gson gson=new Gson();
        //NewsCenterPagerBean2 newsCenterPagerDataBean2=gson.fromJson(json,NewsCenterPagerBean2.class);
        //String myTitle=newsCenterPagerDataBean2.data.get(0).children.get(1).getTitle();
        NewsCenterPagerBean2 bean2 = new NewsCenterPagerBean2();
        try {
            JSONObject jsonObject = new JSONObject(json);
            LogUtil.e(tag, "retcode" + jsonObject.optInt("retcode"));
            int retcode = jsonObject.optInt("retcode");
            bean2.setRetcode(retcode);//recode字段解析成功
            JSONArray dataJsonArray = jsonObject.optJSONArray("data");//解析data字段 为一个Array数组
            //LogUtil.e(tag, "ataJsonArray.length==" + dataJsonArray.length());
            //判断data字段非空
            if (dataJsonArray != null && dataJsonArray.length() > 0) {
                List<NewsCenterPagerBean2.DataBean2> dataBean2List = new ArrayList<>();
                //添加data集合
                bean2.setData(dataBean2List);
                //循环解析data内的每一个对象，存入newsCenterPagerBean2.data集合中
                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject dataJsonObject = (JSONObject) dataJsonArray.get(i);
                    NewsCenterPagerBean2.DataBean2 dataBean2 =
                            new NewsCenterPagerBean2.DataBean2();
                    //添加到集合中
                    dataBean2List.add(dataBean2);
                    //添加数据
                    int id = dataJsonObject.optInt("id");
                    dataBean2.setId(id);
                    int type = dataJsonObject.optInt("type");
                    dataBean2.setType(type);
                    String title = dataJsonObject.optString("title");
                    dataBean2.setTitle(title);
                    String url = dataJsonObject.optString("url");
                    dataBean2.setUrl(url);
                    String url1 = dataJsonObject.optString("url1");
                    dataBean2.setUrl1(url1);
                    String dayurl = dataJsonObject.optString("dayurl");
                    dataBean2.setDayurl(dayurl);
                    String excurl = dataJsonObject.optString("excurl");
                    dataBean2.setExcurl(excurl);
                    String weekurl = dataJsonObject.optString("weekurl");
                    dataBean2.setWeekurl(weekurl);
                    LogUtil.e(tag, "Data.Title==" + title);

                    JSONArray childJsonArray = dataJsonObject.optJSONArray("children");
                    if (childJsonArray != null && childJsonArray.length() > 0) {
                        List<NewsCenterPagerBean2.DataBean2.ChildrenBean2> childrenBean2List = new ArrayList<>();
                        dataBean2.setChildren(childrenBean2List);
                        for (int j = 0; j < childJsonArray.length(); j++) {
                            JSONObject childJsonObject = (JSONObject) childJsonArray.get(j);
                            NewsCenterPagerBean2.DataBean2.ChildrenBean2 childrenBean2 = new NewsCenterPagerBean2.DataBean2.ChildrenBean2();
                            childrenBean2List.add(childrenBean2);
                            int childId = childJsonObject.optInt("id");
                            String childTitle = childJsonObject.optString("title");
                            LogUtil.e(tag, "Children.title==" + childTitle);
                            int childType = childJsonObject.optInt("type");
                            String childUrl = childJsonObject.optString("url");
                            childrenBean2.setTitle(childTitle);
                            childrenBean2.setId(childId);
                            childrenBean2.setType(childType);
                            childrenBean2.setUrl(childUrl);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bean2;
    }

    /**
     * 解析json数据方法
     *
     * @param json
     * @return
     */
    private NewsCenterPagerBean parsedJsonWithGson(String json) {
        //Gson gson=new Gson();
        //NewsCenterPagerBean newsCenterPagerBean=gson.fromJson(json,NewsCenterPagerBean.class);

        return new Gson().fromJson(json, NewsCenterPagerBean.class);
    }

    /**
     * 新闻中心页面中 更换详情页面以及标题 方法
     *
     * @param position 左侧菜单传入的position 与各个页面相对应
     */
    public void switchPagerAndTitle(int position) {
        fl_basepager_content.removeAllViews();
        fl_basepager_content.addView(newsDetailPagerList.get(position).rootView);
        newsDetailPagerList.get(position).initData();
        tv_title.setText(dataBean2List.get(position).getTitle());
    }
}

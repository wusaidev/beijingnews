package com.happy.beijingnews.urils;

/**
 * Created by happy on 2017/5/29.
 */

public class ConstantValue {
    /**
     * 是否进入主页面key
     */
    public static final String START_MAIN ="start_main";

    /**
     * 主页面Fragment替换tag
     */
    public static final String MAIN_CONTENT_TAG="main_content_tag" ;
    /**
     * 左侧菜单Fragment替换tag
     */
    public static final String LEFT_MENU_TAG="left_menu_tag" ;
    /**
     * 网络请求基本地址
     */
    public static final String BASE_URL="http://192.168.1.103:8080/web_home" ;
    /**
     * 新闻页面网络请求地址 static\api\news  categories.json
     */
    public static final String NEWSCENTER_PAGER_URL=BASE_URL+"/static/api/news/categories.json";
    public static final String REFRESH_TIME="refresh_time";

    /**
     * 读过新闻id的存储 key
     */
    public static final String READ_ARRAY_ID="read_array_id";
    public static final String WEB_TEXT_SIZE="web_text_size";
    //http://192.168.1.103:8080/web_home/static/api/news/categories.json" ;
}

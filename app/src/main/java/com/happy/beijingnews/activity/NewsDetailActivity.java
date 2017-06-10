package com.happy.beijingnews.activity;

import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.happy.beijingnews.R;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;
import com.happy.beijingnews.urils.ToastUtil;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibBack;
    private ImageButton ibTextsize;
    private ImageButton ibShare;
    private WebView webView;
    private ProgressBar pbstate;
    private String url;
    private WebSettings webSettings;
    private int realSizeIndex=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        initData();
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_basepager_title_back);
        ibTextsize = (ImageButton) findViewById(R.id.ib_basepager_title_textsize);
        ibShare = (ImageButton) findViewById(R.id.ib_basepager_title_share);
        webView = (WebView) findViewById(R.id.web_view);
        pbstate = (ProgressBar) findViewById(R.id.pb_web_state);

        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        ibBack.setOnClickListener(this);
        ibTextsize.setOnClickListener(this);
        ibShare.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == ibBack) {
            // Handle clicks for ibBasepagerTitleBack
            finish();
        } else if (v == ibTextsize) {
            // Handle clicks for ibBasepagerTitleTextsize
            ToastUtil.show(getApplicationContext(),"改变字体大小");
            showChangeTextSizeDialog();
        } else if (v == ibShare) {
            // Handle clicks for ibBasepagerTitleShare
            ToastUtil.show(getApplicationContext(),"分享新闻");

        }
    }
    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("设置文字大小");
        String[] items={"超大字体","大字体","普通字体","小字体","超小字体"};
        builder.setSingleChoiceItems(items, realSizeIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realSizeIndex =which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeTextSize(realSizeIndex);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    private void changeTextSize(int which) {
        int realSize = 100;
        if(which==0){
            realSize=200;
        }else if(which==1){
            realSize=150;
        }else if(which==2){
            realSize=100;
        }else if(which==3){
            realSize=50;
        }else if(which==4){
            realSize=30;
        }
        webSettings.setTextZoom(realSize);
        CacheUtil.putInt(getApplicationContext(),ConstantValue.WEB_TEXT_SIZE,realSize);
    }

    private void initData() {
        url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
//        webView.loadUrl("http://www.cdpc.edu.cn/");
        webSettings = webView.getSettings();
        //支持js
        webSettings.setJavaScriptEnabled(true);
        //双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //设置字体大小
        int textSize= CacheUtil.getInt(getApplicationContext(), ConstantValue.WEB_TEXT_SIZE,100);
        webSettings.setTextZoom(textSize);
        //不让当前网页跳转到浏览器中
        webView.setWebViewClient(new WebViewClient(){
            @Override
            //当加载完成后调用方法
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbstate.setVisibility(View.GONE);
            }
        });
    }


}


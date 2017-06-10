package com.atguigu.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JsCallJavaVideoActivity extends Activity {


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);
        initWebView();

    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/RealNetJSCallJavaActivity.htm");
        // webView.loadUrl("http://192.168.1.103:8080/RealNetJSCallJavaActivity.htm");
        WebSettings webSettings=webView.getSettings();
        //支持js
        webSettings.setJavaScriptEnabled(true);
        //双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //设置字体大小
        webSettings.setTextZoom(100);
        //不让当前网页跳转到浏览器中
        webView.setWebViewClient(new WebViewClient(){
            @Override
            //当加载完成后调用方法
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.addJavascriptInterface(new MyJavaScriptInterface(),"android");
    }

    public class MyJavaScriptInterface {
        @JavascriptInterface
        public void playVideo(int itemId,String videourl,String itemtitle){
            Intent intent=new Intent();
            intent.setDataAndType(Uri.parse(videourl),"video/*");
            startActivity(intent);
        }

    }
}

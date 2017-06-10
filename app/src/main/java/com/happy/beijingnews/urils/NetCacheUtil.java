package com.happy.beijingnews.urils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/8.
 */

public class NetCacheUtil {
    /**
     * 请求图片成功
     */
    public static final int SUCCESS=0;
    /**
     * 请求图片失败
     */
    public static final int FAIL=1;
    private final LocalCacheUtil localCacheUtil;
    private  Handler handler;
    private ExecutorService executorService;
    private MemoryCacheUtil memoryCacheUtil;

    public NetCacheUtil(Handler mHandler, LocalCacheUtil localCacheUtil, MemoryCacheUtil memoryCacheUtil) {
        handler =mHandler;
        executorService = Executors.newFixedThreadPool(10);
        this.localCacheUtil =localCacheUtil;
        this.memoryCacheUtil=memoryCacheUtil;

    }

    /**
     * 联网请求得到图片
     * @param imgUrl
     * @param position
     */
    public void getBitmapFromNet(String imgUrl, int position) {
        //new Thread(new MyRunable(imgUrl,position)).start();

        executorService.execute(new MyRunable(imgUrl,position));
    }
    class MyRunable implements Runnable{
        private String imgUrl;
        private int position;

        public MyRunable(String imgUrl, int position){
            this.imgUrl=imgUrl;
            this.position =position;
        }
        @Override
        public void run() {
            //子线程
            //请求网络图片
            try {
                URL url=new URL(imgUrl);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if(responseCode==200){
                    InputStream is=connection.getInputStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    //显示到控件上
                    Message msg=Message.obtain();

                    msg.what=SUCCESS;
                    msg.arg1=position;
                    msg.obj=bitmap;
                    handler.sendMessage(msg);
                    //在内存中缓存一份
                    localCacheUtil.putBitmap(imgUrl,bitmap);
                    //在本地种缓存一份
                    memoryCacheUtil.putBitmap(imgUrl,bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg=Message.obtain();

                msg.what=FAIL;
                msg.arg1=position;
                handler.sendMessage(msg);
            }
        }
    }
}

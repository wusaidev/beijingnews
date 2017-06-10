package com.happy.beijingnews.urils;

import android.graphics.Bitmap;

import org.xutils.cache.LruCache;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：图片内存缓存工具
 * Created by happy on 2017/6/8.
 */
public class MemoryCacheUtil {
    private LruCache<String,Bitmap> lruCache;
    public MemoryCacheUtil(){
        int maxSize= (int) (Runtime.getRuntime().maxMemory()/8/1024);
        lruCache=new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    public Bitmap getBitmapFromUrl(String imgUrl) {
        LogUtil.e("","图片内存获取成功");
        return lruCache.get(imgUrl);
    }

    public void putBitmap(String imgUrl, Bitmap bitmap) {
        lruCache.put(imgUrl,bitmap);
        LogUtil.e("","图片内存缓存成功");
    }
}

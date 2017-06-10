package com.happy.beijingnews.urils;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：图片三级缓存工具
 * Created by happy on 2017/6/8.
 */

public class BitmapCacheUtil {
    /**
     * 网络缓存工具（请求）
     */
    private NetCacheUtil netCacheUtil;
    /**
     * 本地缓存图片工具
     */
    private LocalCacheUtil localCacheUtil;
    private MemoryCacheUtil memoryCacheUtil;
    public BitmapCacheUtil(Handler mHandler){
        memoryCacheUtil=new MemoryCacheUtil();
        localCacheUtil=new LocalCacheUtil(memoryCacheUtil);
        netCacheUtil=new NetCacheUtil(mHandler,localCacheUtil,memoryCacheUtil);
    }
    public Bitmap getBitmap(String imgUrl, int position){
        //1 从内存中取图片
        if(memoryCacheUtil!=null){
            Bitmap bitmap=memoryCacheUtil.getBitmapFromUrl(imgUrl);
            if(bitmap!=null){
                LogUtil.e("","本地图片加载成功======="+position);
                return bitmap;
            }
        }
        //2 从本地文件中取图片
        if(localCacheUtil!=null){
            Bitmap bitmap=localCacheUtil.getBitmapFromUrl(imgUrl);
            if(bitmap!=null){
                LogUtil.e("","本地图片加载成功======="+position);
                return bitmap;
            }
        }
        //3 请求网络图片
        netCacheUtil.getBitmapFromNet(imgUrl,position);
        return null;
    }
}

package com.happy.beijingnews.urils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.xutils.common.util.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/8.
 */
public class LocalCacheUtil {
    private MemoryCacheUtil memoryCacheUtil;
    public LocalCacheUtil(MemoryCacheUtil memoryCacheUtil) {
        this.memoryCacheUtil=memoryCacheUtil;
    }

    /**
     * 根据URL获取图片
     * @param imgUrl
     * @return
     */
    public Bitmap getBitmapFromUrl(String imgUrl) {
        //获取图片在 mnt/sdcard/beijingnews/http://192.168.21.165.8080/
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String fileName= MD5.md5(imgUrl);
            File file=new File(Environment.getExternalStorageDirectory()+"/beijingnews",fileName);
            if (file.exists()){
                try {
                    InputStream is=new FileInputStream(file) ;
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    is.close();
                    LogUtil.e("","图片本地获取成功====");
                    memoryCacheUtil.putBitmap(imgUrl,bitmap);
                    //bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
                    return bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e("","图片本地获取失败====");
                }
            }
        }

        return null;
    }

    /**
     * 根据url保存图片
     * @param imgUrl
     * @param bitmap
     */
    public void putBitmap(String imgUrl, Bitmap bitmap) {
        //保存图片在 mnt/sdcard/beijingnews/http://192.168.21.165.8080/
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String fileName= MD5.md5(imgUrl);
            File file=new File(Environment.getExternalStorageDirectory()+"/beijingnews",fileName);
            File parentFile=file.getParentFile();
            if (!parentFile.exists()){
                //创建目录
                parentFile.mkdirs();
            }
            if (!file.exists()){
                try {
                    file.createNewFile();
                    //保存图片
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                    fileOutputStream.close();
                    LogUtil.e("","图片本地缓存成功====");
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e("","图片本地缓存失败====");
                }
            }
        }
    }
}

package com.happy.beijingnews.urils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.xutils.common.util.MD5;
import org.xutils.db.converter.ByteArrayColumnConverter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by happy on 2017/5/29.
 */

public class CacheUtil {
    /**
     * 获取是否进入主界面
     * @param context
     * @param key
     * @param Default
     * @return
     */
    public static boolean getBoolean(Context context, String key,boolean Default){
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        return sp.getBoolean(key,Default);
    }

    /**
     * 设置是否进入主界面
     * @param context
     * @param key
     * @param isBoolean
     */
    public static void putBoolean(Context context, String key,boolean isBoolean) {
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,isBoolean).commit();
    }

    /**
     * 缓存网络请求数据
     * @param context
     * @param key
     */
    public static void putString(Context context, String key,String value) {
        //保存图片在 mnt/sdcard/beijingnews/http://192.168.21.165.8080/
        //以文件方式，以防清理缓存的时候清理掉，每个key一个文件
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String fileName= MD5.md5(key);
            File file=new File(Environment.getExternalStorageDirectory()+"/beijingnews",fileName);
            File parentFile=file.getParentFile();
            if (!parentFile.exists()){
                //创建目录
                parentFile.mkdirs();
            }
            if (!file.exists()){
                try {
                    file.createNewFile();
                    //文件方式保存字符串
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(value.getBytes());
                    fileOutputStream.close();
                    LogUtil.e("","图片本地缓存成功====");
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e("","图片本地缓存失败====");
                }
            }
        }else {
            SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
            sp.edit().putString(key,value).commit();
        }
    }

    /**
     * 获取缓存请求数据方法
     * @param context
     * @param key
     * @param def 默认值
     * @return
     */
    public static String getString(Context context, String key, String def) {
        String result="";
        //获取图片在 mnt/sdcard/beijingnews/http://192.168.21.165.8080/
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String fileName= MD5.md5(key);
            File file=new File(Environment.getExternalStorageDirectory()+"/beijingnews",fileName);
            if (file.exists()){
                try {
                    InputStream is=new FileInputStream(file) ;
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    //FileOutputStream outputStream=new FileOutputStream(result);
                    byte[] buffer=new byte[1024];
                    int length;
                    while ((length=is.read(buffer))!=-1){
                        stream.write(buffer,0,length);
                    }
                    result=stream.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e("","图片本地获取失败====");
                }
            }
        }else {
            SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
            result=sp.getString(key,def);
        }

        return result;
    }

    public static int getInt(Context context, String key, int size) {
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        return sp.getInt(key,size);

    }

    public static void putInt(Context context, String key, int realSize) {
        SharedPreferences sp=context.getSharedPreferences("BeijingNews",Context.MODE_PRIVATE);
        sp.edit().putInt(key,realSize).commit();
    }
}

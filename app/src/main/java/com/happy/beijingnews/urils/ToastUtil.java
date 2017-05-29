package com.happy.beijingnews.urils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by happy on 2017/5/28.
 */

public class ToastUtil {
    public static void show(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}

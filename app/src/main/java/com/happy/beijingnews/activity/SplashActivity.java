package com.happy.beijingnews.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.happy.beijingnews.R;
import com.happy.beijingnews.urils.CacheUtil;
import com.happy.beijingnews.urils.ConstantValue;

public class SplashActivity extends AppCompatActivity {
    private  RelativeLayout rl_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
        initAnimation();

    }

    /**
     * 初始化动画方法
     */
    private void initAnimation() {
        AlphaAnimation aa=new AlphaAnimation(0,1);
        RotateAnimation ra=new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //从无到有，中心缩放
        ScaleAnimation sa=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AnimationSet set=new AnimationSet(true);
        set.addAnimation(aa);
        set.addAnimation(ra);
        set.addAnimation(sa);
        set.setDuration(2000);
        rl_splash.setAnimation(set);
        set.setAnimationListener(new MyAnimationListener());

    }
    class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //ToastUtil.show(getApplicationContext(),"动画播放完成");
            //判断是否进入主页面，否则进入引导页面
            boolean start_main=CacheUtil.getBoolean(getApplicationContext(), ConstantValue.START_MAIN,false);
            if(start_main){
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }else{
                startActivity(new Intent(SplashActivity.this,GuideActivity.class));
            }
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private void initUI() {
        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash_root);
    }
}

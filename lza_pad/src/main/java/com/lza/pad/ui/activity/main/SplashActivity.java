package com.lza.pad.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lza.pad.R;
import com.lza.pad.ui.activity.base.AbstractActivity;

/**
 * APP的载入页面，主要用途：
 * 1. 显示封面
 * 2. 检查版本更新
 * 3. 检查是否为首次登录，如果是，则加载帮助页面
 *
 * @author Sam
 * @Date 2014-9-8
 */
public class SplashActivity extends AbstractActivity {

    public static final int DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查更新
        //UmengUtils.checkUpdate(this);
        setContentView(R.layout.splash);
        new Handler().postDelayed(mRunnable, DELAY);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.alphain, R.anim.alphaout);
        }
    };


}

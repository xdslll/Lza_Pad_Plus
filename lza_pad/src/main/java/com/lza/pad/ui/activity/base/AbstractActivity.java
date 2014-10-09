package com.lza.pad.ui.activity.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xiads on 14-9-8.
 */
public class AbstractActivity extends SherlockFragmentActivity {

    protected int theme = 0;

    @Override
    protected void onResume() {
        super.onResume();
        AppLogger.d("onResume");
        GlobalContext.getInstance().setCurrentRunningActivity(this);
        //启动当前Activity的友盟统计
        MobclickAgent.onResume(this);

        /*if (theme != SettingUtility.getAppTheme()) {
            reload();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppLogger.d("onPause");
        if (GlobalContext.getInstance().getCurrentRunningActivity() == this) {
            GlobalContext.getInstance().setCurrentRunningActivity(null);
        }
        //关闭当前Activity的友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        AppLogger.d("onConfigurationChanged");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppLogger.d("onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        AppLogger.d("onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLogger.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppLogger.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLogger.d("onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AppLogger.d("onSaveInstanceState");
        outState.putInt("theme", theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if (savedInstanceState == null) {
            theme = SettingUtility.getAppTheme();
        } else {
            theme = savedInstanceState.getInt("theme");
        }*/
        setTheme(theme);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppLogger.d("onCreate");
        GlobalContext.getInstance().setActivity(this);
        //设置友盟的DEBUG模式，发布时应关闭
        MobclickAgent.setDebugMode(true);
    }

}

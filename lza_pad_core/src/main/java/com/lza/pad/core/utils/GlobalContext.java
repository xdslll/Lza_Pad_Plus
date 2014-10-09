package com.lza.pad.core.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.lza.pad.core.BuildConfig;
import com.lza.pad.core.db.base.PadDatabaseHelper;
import com.lza.pad.lib.support.debug.AppLogger;

/**
 * 全局Application对象
 *
 * @author Sam
 * @Date 2014-9-1
 */
public class GlobalContext extends Application {

    /**
     * 控制Application实例为单例
     */
    private static GlobalContext sGlobalContext = null;

    /**
     * 当前创建的Activity
     */
    private Activity activity = null;

    /**
     * 正在运行的Activity
     */
    private Activity currentRunningActivity = null;

    /**
     * UI线程的Handler
     */
    private Handler handler = new Handler();

    private static PadDatabaseHelper sDatabaseHelper = null;

    public static PadDatabaseHelper getDatabaseHelper() {
        if (sDatabaseHelper == null)
            sDatabaseHelper = OpenHelperManager.getHelper(sGlobalContext, PadDatabaseHelper.class);
        return sDatabaseHelper;
    }

    public static GlobalContext getInstance() {
        return sGlobalContext;
    }

    /**
     * 将String资源的值转化为int
     *
     * @param resId
     * @return
     */
    public int getInteger(int resId) {
        String target = getString(resId);
        if (sGlobalContext != null) {
            return Integer.parseInt(target);
        }
        throw new NullPointerException("GlobalContext is null!");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sGlobalContext = this;
        AppLogger.DEBUG = BuildConfig.DEBUG;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OpenHelperManager.releaseHelper();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getCurrentRunningActivity() {
        return currentRunningActivity;
    }

    public void setCurrentRunningActivity(Activity currentRunningActivity) {
        this.currentRunningActivity = currentRunningActivity;
    }

    public Handler getUIHandler() {
        return handler;
    }
}

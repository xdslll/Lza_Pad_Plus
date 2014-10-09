package com.lza.pad.utils;

import android.content.Context;

import com.lza.pad.BuildConfig;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.debug.AppLogger;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

/**
 * Created by xiads on 14-9-8.
 */
public class UmengUtils {

    /**
     * 检查APK是否有更新
     *
     * @param context
     */
    public static void checkUpdate(Context context) {
        //通过友盟插件检查是否存在apk更新
        UmengUpdateAgent.setUpdateCheckConfig(true);
        //不在WIFI状态也可以更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        //设置更新监听器
        UmengUpdateListener updateListener = new UmengUpdateListener() {
            /**
             *
             * @param statusCode 0表示有更新，1表示无更新，2表示非wifi状态，3表示请求超时
             * @param updateResponse
             */
            @Override
            public void onUpdateReturned(int statusCode, UpdateResponse updateResponse) {
                AppLogger.d("statusCode=" + statusCode);
                if (updateResponse != null) {
                    AppLogger.d("updateResponse.version=" + updateResponse.version);
                }
                switch (statusCode) {
                    case 0:
                        ToastUtilsSimplify.show("检查到更新!");
                        break;
                    case 1:
                        if (BuildConfig.DEBUG)
                            ToastUtilsSimplify.show("没有检查到更新!");
                        break;
                    case 2:
                        ToastUtilsSimplify.show("非wifi状态!");
                        break;
                    case 3:
                        ToastUtilsSimplify.show("更新请求超时!");
                        break;
                }
            }
        };
        UmengUpdateAgent.setUpdateListener(updateListener);
        UmengUpdateAgent.update(context);
    }
}

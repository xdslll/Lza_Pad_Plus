package com.lza.pad.core.utils;

import com.lza.pad.lib.support.debug.AppLogger;

/**
 * Created by xiads on 14-9-7.
 */
public class AppLoggerSimplify {

    public static void e(int resId) {
        AppLogger.e(GlobalContext.getInstance(), resId);
    }

    public static void e(int resId, Throwable t) {
        AppLogger.e(GlobalContext.getInstance(), resId, t);
    }

    public static void d(String msg) {
        AppLogger.d(msg);
    }

    public static void e(String msg) {
        AppLogger.e(msg);
    }
}
